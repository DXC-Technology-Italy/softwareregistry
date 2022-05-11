package com.cm.dev.service.impl;

import com.cm.dev.domain.Dependency;
import com.cm.dev.domain.Project;
import com.cm.dev.domain.Repository;
import com.cm.dev.exception.ServiceException;
import com.cm.dev.service.PlainFileReaderService;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.gson.stream.JsonReader;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;
import java.util.regex.Pattern;


/**
 * Reads the files repositories.json and dependencies.txt in order to fill 
 * data structures that represent all the repositories and relationships between projects.
 *  
 */

@Service
public class PlainFileReaderServiceImpl implements PlainFileReaderService {

    private static final Logger LOGGER = LoggerFactory.getLogger(PlainFileReaderServiceImpl.class);
    private static final String LONGNAME = "longName";

    private static PlainFileReaderServiceImpl instance;


    @Value("${repository.path}")
    private String repositoryPath;

    @Value("${dependencies.path}")
    private String dependenciesPath;

    @Value("${nexus.url}")
    private String nexusBaseUrl;

    @Value("${maven.libraries.regex}")
    private String mavenLibrariesRegex;

    @Value("${maven.custom.libraries.regex}")
    private String mavenCustomLibrariesRegex;

    private final Map<String, Repository> repositories;
    private final HashMap<String, Project> projects;
    private final List<Dependency> dependencies;
    private final HashMap<String, JsonElement> repositoryMap;

    private final HashMap<Integer, String> dependenciesFileLines = new HashMap<>();


    public PlainFileReaderServiceImpl() throws ServiceException, IOException {
        this.repositories = new HashMap<>();
        this.projects = new HashMap<>();
        this.dependencies = new ArrayList<>();
        this.repositoryMap = new HashMap<>();

    }

    
    /** 
     * @return Map<String, Repository>
     */
    public Map<String, Repository> getRepositories() {
        return this.repositories;
    }

    
    /** 
     * @return Map<String, Project>
     */
    public Map<String, Project> getProjects() {
        return this.projects;
    }

    
    /** 
     * @return Map<String, JsonElement>
     */
    public Map<String, JsonElement> getRepositoryMap() {
        return this.repositoryMap;
    }

    
    /** 
     * @throws ServiceException
     * @throws IOException
     */
    @PostConstruct
    public void init() throws ServiceException, IOException {
        loadDataFromRepositoriesFile();
        loadDataFromDependenciesFile();
    }

    
    /** 
     * @return Map<Integer, String>
     */
    public Map<Integer, String> getDependenciesFileLines() {
        return this.dependenciesFileLines;
    }

    
    /** 
     * @throws ServiceException
     * @throws FileNotFoundException
     */
    public void loadDataFromDependenciesFile() throws ServiceException, FileNotFoundException {

        String projectName = "";

        Pattern ptrnLibrary = Pattern.compile(mavenLibrariesRegex);
        Pattern ptrnGeneratedLib = Pattern.compile(mavenCustomLibrariesRegex);
        Pattern ptrnDependency = Pattern.compile("^(\\s)*[+\\\\|]");

        File myObj = new File(dependenciesPath);

        try (Scanner myReader = new Scanner(myObj)) {

            int i = 0;
            while (myReader.hasNextLine()) {
                String data = myReader.nextLine();

                if (data.startsWith("Building")) {
                    projectName = data.split(" ")[1];
                    this.dependenciesFileLines.put(i, "");
                    i++;
                    this.dependenciesFileLines.put(i, data);
                    i++;
                    this.dependenciesFileLines.put(i, "------------------------------------------------------------------------");
                    i++;
                    this.dependenciesFileLines.put(i, "");
                    i++;
                } else if (ptrnGeneratedLib.matcher(data).find() || ptrnDependency.matcher(data).find()) {
                    this.dependenciesFileLines.put(i, data);
                    i++;
                }

                fillDependecies(projectName, ptrnLibrary, i, data);
            }
        } catch (Exception e) {
            LOGGER.error(String.valueOf(e));
        }

    }

    
    /** 
     * @param projectName
     * @param ptrnLibrary
     * @param i
     * @param data
     */
    private void fillDependecies(String projectName, Pattern ptrnLibrary, int i, String data) {
        if (ptrnLibrary.matcher(data).find()) {

            Dependency dependency = new Dependency();

            String[] element = data.substring(data.lastIndexOf(' ') + 1).split(":");
            dependency.setGroupId(element[0]);
            dependency.setArtifactId(element[1]);
            dependency.setArtifactType(element[2]);
            dependency.setVersion( "sources".equals(element[3]) ? element[4] : element[3] );
            String nexusRepository = "/mef-releases/";
            String nexusUrl = nexusBaseUrl +
                    "repository" +
                    nexusRepository +
                    dependency.getGroupId().replace("\\.", "/") +
                    "/" +
                    dependency.getArtifactId() +
                    "/" +
                    dependency.getVersion() +
                    "/" + dependency.getArtifactId() + "-" + dependency.getVersion() + "." + dependency.getArtifactType();

            dependency.setNexusUrl(nexusUrl);

            if (projects.get(projectName) != null) {
                dependency.generateIdForInsertion();
                dependency.setParent(projectName);
                dependency.setLine(i);
                if (projects.get(projectName).getDependencies() == null)
                    projects.get(projectName).setDependencies(new ArrayList<>());
                projects.get(projectName).getDependencies().add(dependency);
                dependencies.add(dependency);
            }

        }
    }

    
    /** 
     * @throws ServiceException
     * @throws IOException
     */
    private void loadDataFromRepositoriesFile() throws ServiceException, IOException {
        JsonReader reader;


        JsonParser parser = new JsonParser();
        reader = new JsonReader(new FileReader(repositoryPath));
        JsonArray rootObj = parser.parse(reader).getAsJsonArray();

        LOGGER.info("Parsing Json file");
        for (JsonElement element : rootObj) {
            JsonObject obj = element.getAsJsonObject();
            Repository repository = new Repository();
            repository.setName(obj.get("name").getAsString());
            repository.setLongName(obj.get(LONGNAME).getAsString());
            repository.setUrl(obj.get("url").getAsString());
            repository.setKind(obj.get("kind").getAsString());
            repository.setArea(obj.get(LONGNAME).getAsString().split("/")[0]);
            repositoryMap.put(obj.get(LONGNAME).getAsString(), element);
            repository.setProjects(new ArrayList<>());
            for (JsonElement projectElement : obj.getAsJsonArray("projects")) {
                JsonObject projectObj = projectElement.getAsJsonObject();
                Project p = new Project();
                p.setName(projectObj.get("name").getAsString());
                p.setVersion(projectObj.get("version").getAsString());
                p.setArea(repository.getArea());
                p.setUrl(repository.getUrl());
                p.setRepository(repository.getName());
                repository.getProjects().add(p);

                projects.put(p.getName(), p);
            }
            repositories.put(repository.getLongName(), repository);
        }


        LOGGER.info("Done parsing Json file");


        reader.close();


    }


    
    /** 
     * @return List<Dependency>
     */
    public List<Dependency> getDependencies() {
        return this.dependencies;
    }


}
