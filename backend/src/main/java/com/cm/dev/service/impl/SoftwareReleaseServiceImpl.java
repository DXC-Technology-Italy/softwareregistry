package com.cm.dev.service.impl;

import com.cm.dev.bean.DevelopmentItem;
import com.cm.dev.bean.Release;
import com.cm.dev.bean.ReleaseItem;
import com.cm.dev.dao.SoftwareReleaseDAO;
import com.cm.dev.domain.SoftwareRelease;
import com.cm.dev.exception.ServiceException;
import com.cm.dev.service.SoftwareReleaseService;
import com.cm.dev.util.FileUtils;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.gson.stream.JsonReader;
import org.apache.commons.io.FilenameUtils;
import org.apache.tomcat.util.codec.binary.Base64;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;


/**
 * Business Logic for SoftwareRelease Objects
 * 
 */
@Service
public class SoftwareReleaseServiceImpl implements SoftwareReleaseService {

    private static final Logger LOGGER = LoggerFactory.getLogger(SoftwareReleaseServiceImpl.class);
    private static final String CUSTOM_FIELDS = "custom_fields";
    private static final String VALUE = "value";
    private static final String REDMINE_PROJECTS = "projects/release-deployment-management/issues.json?utf8=%E2%9C%93&set_filter=1&sort=id%3Adesc&f[]=status_id&op[status_id]=c&f[]=tracker_id&op[tracker_id]=%3D&v[tracker_id][]=15&f[]=cf_4&op[cf_4]=~&v";

    @Value("${gitlab.url}")
    private String gitlabUrl;

    @Value("${gitlab.token}")
    private String gitlabToken;

    @Value("${redmine.url}")
    private String redmineUrl;

    @Autowired
    private SoftwareReleaseDAO softwareReleaseDAO;


    
    /** 
     * @param urlToCall
     * @param auth
     * @return InputStream
     * @throws ServiceException
     * @throws IOException
     */
    private InputStream getURLInputStream(String urlToCall, String auth) throws ServiceException, IOException {
        URL url = new URL(urlToCall);

        LOGGER.info(urlToCall);

        byte[] encodedAuth = Base64.encodeBase64(auth.getBytes(StandardCharsets.UTF_8));
        String authHeaderValue = "Basic " + new String(encodedAuth);

        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        connection.setRequestProperty("Authorization", authHeaderValue);
        connection.setRequestProperty("accept", "application/json");

        return connection.getInputStream();

    }

    
    /** 
     * @param urlToCall
     * @param auth
     * @return JsonObject
     * @throws ServiceException
     * @throws IOException
     */
    private JsonObject getJsonObjectFromUrl(String urlToCall, String auth) throws ServiceException, IOException {
        JsonReader reader;
        JsonObject rootObj;
        InputStream responseStream;


        JsonParser parser = new JsonParser();
        responseStream = getURLInputStream(urlToCall, auth);
        reader = new JsonReader(new BufferedReader(new InputStreamReader(responseStream)));
        rootObj = parser.parse(reader).getAsJsonObject();


        responseStream.close();
        reader.close();
        return rootObj;
    }

    
    /** 
     * @param urlToCall
     * @param auth
     * @return JsonArray
     * @throws ServiceException
     * @throws IOException
     */
    private JsonArray getJsonArrayFromUrl(String urlToCall, String auth) throws ServiceException, IOException {
        JsonReader reader;
        JsonArray rootObj;
        InputStream responseStream;
        JsonParser parser = new JsonParser();
        responseStream = getURLInputStream(urlToCall, auth);
        reader = new JsonReader(new BufferedReader(new InputStreamReader(responseStream)));
        rootObj = parser.parse(reader).getAsJsonArray();

        responseStream.close();
        reader.close();

        return rootObj;
    }

    
    /** 
     * @param bigcode
     * @return List<DevelopmentItem>
     * @throws ServiceException
     * @throws IOException
     */
    @Override
    public List<DevelopmentItem> getLOS(String bigcode) throws ServiceException, IOException {

        List<String> repositoriesFromRedmine = new ArrayList<>();
        List<DevelopmentItem> matches = new ArrayList<>();

        SoftwareRelease softwareRelease = new SoftwareRelease();
        String auth = "developer" + ":" + "Developer!";

        JsonObject rootObj = getJsonObjectFromUrl(redmineUrl + "/projects/release-deployment-management/issues.json?utf8=%E2%9C%93&set_filter=1&sort=id%3Adesc&f[]=status_id&op[status_id]=c&f[]=tracker_id&op[tracker_id]=%3D&v[tracker_id][]=11&f[]=cf_4&op[cf_4]=~&v[cf_4][]=" + bigcode + "&f[]=&c[]=tracker&c[]=category&c[]=status&c[]=priority&c[]=subject&c[]=assigned_to&c[]=updated_on&c[]=start_date&c[]=due_date&c[]=done_ratio&group_by=&t[]=estimated_hours&t[]=", auth);

        for (JsonElement element : rootObj.getAsJsonArray("issues")) {
            JsonObject obj = element.getAsJsonObject();
            softwareRelease.setBigCode(bigcode);
            JsonObject repositoryObj = obj.getAsJsonArray(CUSTOM_FIELDS).get(4).getAsJsonObject();
            String newRepository = repositoryObj.get(VALUE).getAsString().trim().toLowerCase();
            repositoriesFromRedmine.add(newRepository);
        }
        String area = "area" + bigcode.split("-")[1];
        softwareRelease.setArea(area);
        JsonArray projectsFromGit = getJsonArrayFromUrl(String.format("%s/api/v4/groups/%s/projects?private_token=%s&per_page=1000",
                gitlabUrl, area, gitlabToken), auth);
        for (JsonElement element : projectsFromGit) {
            String repositoryWithPath = element.getAsJsonObject().get("path_with_namespace").getAsString();
            String repositoryId = element.getAsJsonObject().get("id").getAsString();
            if (repositoriesFromRedmine.contains(repositoryWithPath)) {

                JsonObject diffObject = getJsonObjectFromUrl(String.format("%s/api/v4/projects/%s/repository/compare?from=master&to=%s&&private_token=%s",
                        gitlabUrl,
                        repositoryId,
                        "develop-" + bigcode,
                        gitlabToken
                ), auth);
                matches = fillMatches(repositoryWithPath, diffObject);
            }
        }
        softwareRelease.setDevelopmentItemList(matches);

        if (!matches.isEmpty()) {
            softwareReleaseDAO.createOrUpdateLos(softwareRelease);
        } else {
            matches = softwareReleaseDAO.getLosByBigCode(bigcode).getDevelopmentItemList();
        }

        Comparator<DevelopmentItem> compareByRepositoryPath = Comparator
                .comparing(DevelopmentItem::getRepository)
                .thenComparing(DevelopmentItem::getPath);

        return matches.stream()
                .sorted(compareByRepositoryPath)
                .collect(Collectors.toList());
    }

    
    /** 
     * @param repositoryWithPath
     * @param diffObject
     * @return List<DevelopmentItem>
     */
    private List<DevelopmentItem> fillMatches(String repositoryWithPath, JsonObject diffObject) {
        List<DevelopmentItem> matches = new ArrayList<>();
        if (diffObject != null) {
            for (JsonElement diffElement : diffObject.getAsJsonArray("diffs")) {
                JsonObject diffFileObject = diffElement.getAsJsonObject();
                DevelopmentItem match = new DevelopmentItem();
                boolean isNew = diffFileObject.get("new_file").getAsBoolean();
                boolean isRenamed = diffFileObject.get("renamed_file").getAsBoolean();
                boolean isDeleted = diffFileObject.get("deleted_file").getAsBoolean();

                String objectPath = diffFileObject.get("new_path").getAsString();

                match.setExtension(FilenameUtils.getExtension(objectPath));
                match.setName(FileUtils.basename(objectPath));
                match.setPath(FileUtils.basepath(objectPath));
                match.setRepository(repositoryWithPath.split("/")[1]);

                if (isNew) {
                    match.setAction("ADDED");
                }else if (isRenamed) {
                    match.setAction("RENAMED");
                }
                else if (isDeleted) {
                    match.setAction("DELETED");
                }
                else {
                    match.setAction("MODIFIED");
                }
                matches.add(match);
            }
        }
        return matches;
    }

    
    /** 
     * @param bigCode
     * @return List<Release>
     * @throws ServiceException
     * @throws IOException
     */
    @Override
    public List<Release> getDeliveryCheck(String bigCode) throws ServiceException, IOException {
        SoftwareRelease softwareRelease = new SoftwareRelease();
        List<Release> releases = new ArrayList<>();

        String auth = "developer" + ":" + "Developer!";

        JsonObject rootObj = getJsonObjectFromUrl(
                String.format("%s/%s[cf_4][]=%s&f[]=&c[]=tracker&c[]=category&c[]=status&c[]=priority&c[]=subject&c[]=assigned_to&c[]=updated_on&c[]=start_date&c[]=due_date&c[]=done_ratio&group_by=&t[]=estimated_hours&t[]=",
                        redmineUrl,
                        REDMINE_PROJECTS,
                        bigCode
                ), auth);

        for (JsonElement element : rootObj.getAsJsonArray("issues")) {
            List<ReleaseItem> releaseItems = new ArrayList<>();
            Release release = new Release();
            JsonObject obj = element.getAsJsonObject();
            softwareRelease.setBigCode(bigCode);
            softwareRelease.setArea("area" + bigCode.split("-")[1]);
            release.setDate(obj.getAsJsonPrimitive("closed_on").getAsString());
            release.setAuthor(obj.getAsJsonObject("author").get("name").getAsString());
            release.setMergeRequestType(obj.getAsJsonObject("author").get("name").getAsString());
            JsonObject urlNexusObj = obj.getAsJsonArray(CUSTOM_FIELDS).get(6).getAsJsonObject();
            String urlNexus = urlNexusObj.get(VALUE).getAsString().trim();
            String[] item = urlNexus.split("\r\n");
            List<String> nexusurls = new ArrayList<>();
            for (String line : item) {
                if (line.startsWith("http:")) {
                    nexusurls.add(line.replace(",", "") + "\n");
                }
            }
            release.setNexusUrls(nexusurls);
            JsonObject tipoMRObj = obj.getAsJsonArray(CUSTOM_FIELDS).get(3).getAsJsonObject();
            String tipoMR = tipoMRObj.get(VALUE).getAsString().trim();
            release.setMergeRequestType(Release.getDecodeTipoMergeRequest(tipoMR));
            JsonObject dettagliRilascioObj = obj.getAsJsonArray(CUSTOM_FIELDS).get(10).getAsJsonObject();
            String dettagliRilascio = dettagliRilascioObj.get(VALUE).getAsString().trim();

            releaseItems = fillReleaseItems(tipoMR, dettagliRilascio);

            release.setReleaseItems(releaseItems);
            releases.add(release);
        }
        softwareRelease.setReleases(releases);

        if (!releases.isEmpty()) {
            softwareReleaseDAO.createOrUpdateDeliveryCheck(softwareRelease);
        }

        Comparator<Release> compareDate = Comparator
                .comparing(Release::getDate);

        return releases.stream()
                .sorted(compareDate)
                .collect(Collectors.toList());
    }

    
    /** 
     * @param tipoMR
     * @param dettagliRilascio
     * @return List<ReleaseItem>
     */
    private List<ReleaseItem> fillReleaseItems(String tipoMR, String dettagliRilascio) {
        List<ReleaseItem> releaseItems = new ArrayList<>();
        Pattern pattern = Pattern.compile("(area[0-9]{4})/([^/]+)/(.*)");
        for (String dettaglio : dettagliRilascio.split("\r\n")) {
            if (dettaglio != null && !"".equals(dettaglio)) {
                ReleaseItem releaseItem = new ReleaseItem();
                String[] elements = dettaglio.split(":");
                releaseItem.setSha1sum(elements[1]);

                Matcher matcher = pattern.matcher(elements[0]);
                if (matcher.find()) {
                    releaseItem.setRepository(matcher.group(2));
                    releaseItem.setFilename(matcher.group(3));

                    releaseItem.setPathMef("ESERCIZIO".equals(Release.getDecodeTipoMergeRequest(tipoMR)) ? ReleaseItem.getPathMefMapEsercizio(matcher.group(1) + "/" + matcher.group(2)) : ReleaseItem.getPathMefMapCollaudo(matcher.group(1) + "/" + matcher.group(2)));

                }
                releaseItems.add(releaseItem);
            }
        }
        return releaseItems;
    }

}