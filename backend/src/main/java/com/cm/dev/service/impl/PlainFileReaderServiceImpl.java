package com.cm.dev.service.impl;

import com.cm.dev.domain.Dependency;
import com.cm.dev.domain.Project;
import com.cm.dev.domain.Repository;
import com.cm.dev.service.PlainFileReaderService;
import com.google.gson.*;
import com.google.gson.stream.JsonReader;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;
import java.util.regex.Pattern;

@Service
public class PlainFileReaderServiceImpl implements PlainFileReaderService {

    private static PlainFileReaderServiceImpl INSTANCE;

    @Value("${repository.path}")
    private final String repositoryPath   = "/data/repositories.json";

    @Value("${dependencies.path}")
    private final String dependenciesPath = "/data/dependencies.txt";

    @Value("${nexus.url}")
    private String nexusBaseUrl;

    @Value("${maven.libraries.regex}")
    private String mavenLibrariesRegex;

    @Value("${maven.custom.libraries.regex}")
    private String mavenCustomLibrariesRegex;

    private final String NEXUS_REPOSITORY = "/mef-releases/";

    private Map<String, Repository> repositories;
    private HashMap<String, Project>  projects;
    private List<Dependency> dependencies;
    private HashMap<String, JsonElement>  repositoryMap;

    private final HashMap<Integer, String> dependenciesFileLines = new HashMap<>();

    public Map<String, Repository> getRepositories() {
        return this.repositories;
    }
    public Map<String, Project> getProjects() {
        return this.projects;
    }

    public HashMap<String, JsonElement> getRepositoryMap() {
        return this.repositoryMap;
    }

    public PlainFileReaderServiceImpl() {
        this.repositories = new HashMap<>();
        this.projects     = new HashMap<>();
        this.dependencies = new ArrayList<>();
        this.repositoryMap = new HashMap<>();
        loadDataFromRepositoriesFile();
        loadDataFromDependenciesFile();
    }

    public static PlainFileReaderServiceImpl getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new PlainFileReaderServiceImpl();
        }
        return INSTANCE;
    }
    public HashMap<Integer, String> getDependenciesFileLines() {
        return this.dependenciesFileLines;
    }

    public void loadDataFromDependenciesFile() {

        String projectName = "";

        Pattern ptrnLibrary      = Pattern.compile("^((\\|\\s+)?(\\\\-)?(\\+-)?(\\s)*[(a-z)]).*.noipa");
        Pattern ptrnGeneratedLib = Pattern.compile("^(it.gov.mef.)?noipa");
        Pattern ptrnDependency   = Pattern.compile("^(\\s)*[+\\\\|]");
        String currentLine = "";
        try {
            File myObj = new File(dependenciesPath);
            Scanner myReader = new Scanner(myObj);

            int i = 0;
            while (myReader.hasNextLine()) {
                String data = myReader.nextLine();
                currentLine = data;

                if (data.startsWith("Building")) {
                    projectName = data.split(" ")[1];
                    dependenciesFileLines.put(i, "");
                    i++;
                    dependenciesFileLines.put(i, data);
                    i++;
                    dependenciesFileLines.put(i, "------------------------------------------------------------------------");
                    i++;
                    dependenciesFileLines.put(i, "");
                    i++;
                }

                if (ptrnGeneratedLib.matcher(data).find() || ptrnDependency.matcher(data).find()) {
                    dependenciesFileLines.put(i, data);
                    i++;
                }

                if (ptrnLibrary.matcher(data).find()) {

                    Dependency dependency = new Dependency();

                    String[] element = data.substring(data.lastIndexOf(' ') + 1).split(":");
                    dependency.setGroupId(element[0]);
                    dependency.setArtifactId(element[1]);
                    dependency.setArtifactType(element[2]);
                    if (element[3].equals("sources")) {
                        dependency.setVersion(element[4]);
                    } else {
                        dependency.setVersion(element[3]);
                    }
                    StringBuilder nexusUrl = new StringBuilder()
                            .append(nexusBaseUrl)
                            .append("repository")
                            .append(NEXUS_REPOSITORY)
                            .append( dependency.getGroupId().replaceAll("\\.", "/") )
                            .append("/")
                            .append( dependency.getArtifactId() )
                            .append("/")
                            .append( dependency.getVersion() )
                            .append("/")
                            .append( dependency.getArtifactId() + "-" + dependency.getVersion() + "." + dependency.getArtifactType() );

                    dependency.setNexusUrl(nexusUrl.toString());

                    // System.out.println(projectName);
                    if (projects.get(projectName) != null) {
                        dependency.generateIdForInsertion();
                        dependency.setParent(projectName);
                        dependency.setLine(i);
                        if (projects.get(projectName).getDependencies() == null)
                            projects.get(projectName).setDependencies(new ArrayList<>());
                        projects.get(projectName).getDependencies().add(dependency);
                        dependencies.add(dependency);
                        if (dependency.getArtifactId().equals("stip-core")) {
                            System.out.println(dependency);
                        }
                    }

                }
            }
            myReader.close();
        } catch (FileNotFoundException fnfe) {
            System.out.println("File not found: " + fnfe.getMessage());
        } catch (Exception e) {
            System.out.println("Generic error: " + e.getMessage());
            System.out.println(currentLine);
            e.printStackTrace();
        }
    }

    private void loadDataFromRepositoriesFile() {
        JsonReader reader = null;

        try {
            JsonParser parser = new JsonParser();
            reader = new JsonReader(new FileReader(repositoryPath));
            JsonArray rootObj = parser.parse(reader).getAsJsonArray();

            System.out.println("Parsing Json file");
            for (JsonElement element : rootObj) {
                JsonObject obj = element.getAsJsonObject();
                Repository repository = new Repository();
                repository.setName( obj.get("name").getAsString() );
                repository.setLongName( obj.get("longName").getAsString() );
                repository.setUrl( obj.get("url").getAsString() );
                repository.setKind( obj.get("kind").getAsString() );
                repository.setArea( obj.get("longName").getAsString().split("/")[0] );
                repositoryMap.put(obj.get("longName").getAsString(), element);
                repository.setProjects(new ArrayList<>());
                for (JsonElement projectElement : obj.getAsJsonArray("projects")) {
                    JsonObject projectObj = projectElement.getAsJsonObject();
                    Project p = new Project();
                    p.setName( projectObj.get("name").getAsString() );
                    p.setVersion( projectObj.get("version").getAsString() );
                    p.setArea(repository.getArea());
                    p.setUrl(repository.getUrl());
                    p.setRepository(repository.getName());
                    repository.getProjects().add(p);

                    projects.put(p.getName(), p);
                }
                repositories.put(repository.getLongName(), repository);
            }


            System.out.println("Done parsing Json file");
        } catch (Exception e) {
            e.printStackTrace();
        } finally {

            if (reader != null) {
                try {
                    reader.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public List<Dependency> getDependencies() {
        return this.dependencies;
    }

    public static void main(String[] args) {
        PlainFileReaderServiceImpl reader = new PlainFileReaderServiceImpl();

    }
}
