package com.cm.dev.service.impl;

import com.cm.dev.bean.DevelopmentItem;
import com.cm.dev.bean.Release;
import com.cm.dev.bean.ReleaseItem;
import com.cm.dev.dao.SoftwareReleaseDAO;
import com.cm.dev.domain.SoftwareRelease;
import com.cm.dev.service.SoftwareReleaseService;
import com.cm.dev.util.FileUtils;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.gson.stream.JsonReader;
import org.apache.commons.io.FilenameUtils;
import org.apache.tomcat.util.codec.binary.Base64;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.BufferedReader;
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

@Service
public class SoftwareReleaseServiceImpl implements SoftwareReleaseService {

    @Value("${gitlab.url}")
    private String gitlabUrl;

    @Value("${gitlab.token}")
    private String gitlabToken;

    @Value("${redmine.url}")
    private String redmineUrl;

    @Autowired
    private SoftwareReleaseDAO softwareReleaseDAO;


    private InputStream getURLInputStream(String urlToCall, String auth) throws Exception {
        URL url = new URL(urlToCall);

        System.out.println(urlToCall);

        byte[] encodedAuth = Base64.encodeBase64(auth.getBytes(StandardCharsets.UTF_8));
        String authHeaderValue = "Basic " + new String(encodedAuth);
        JsonReader reader = null;

        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        connection.setRequestProperty("Authorization", authHeaderValue);
        connection.setRequestProperty("accept", "application/json");

        return connection.getInputStream();

    }

    private JsonObject getJsonObjectFromUrl(String urlToCall, String auth) throws Exception {
        JsonReader reader = null;
        JsonObject rootObj = null;
        InputStream responseStream = null;

        try {
            JsonParser parser = new JsonParser();
            responseStream = getURLInputStream(urlToCall, auth);
            reader = new JsonReader(new BufferedReader(new InputStreamReader(responseStream)));
            rootObj = parser.parse(reader).getAsJsonObject();

            //System.out.println(rootObj);

        } catch (Exception ex) {
            ex.printStackTrace();
        } finally {
            if (responseStream != null)
                responseStream.close();
            if (reader != null)
                reader.close();
        }

        return rootObj;
    }

    private JsonArray getJsonArrayFromUrl(String urlToCall, String auth) throws Exception {
        JsonReader reader = null;
        JsonArray rootObj = null;
        InputStream responseStream = null;
        try {
            JsonParser parser = new JsonParser();
            responseStream = getURLInputStream(urlToCall, auth);
            reader = new JsonReader(new BufferedReader(new InputStreamReader(responseStream)));
            rootObj = parser.parse(reader).getAsJsonArray();

            //System.out.println(rootObj);

        } catch (Exception ex) {
            ex.printStackTrace();
        } finally {
            if (responseStream != null)
                responseStream.close();
            if (reader != null)
                reader.close();
        }

        return rootObj;
    }

    @Override
    public List<DevelopmentItem> getLOS(String bigcode) throws Exception {

        List<String> repositoriesFromRedmine = new ArrayList<>();
        List<String> projectsFromGitToCheck = new ArrayList<>();
        List<DevelopmentItem> matches = new ArrayList<>();
        SoftwareRelease softwareRelease = new SoftwareRelease();
        try {
            String auth = "developer" + ":" + "Developer!";

            JsonObject rootObj = getJsonObjectFromUrl(redmineUrl + "/projects/release-deployment-management/issues.json?utf8=%E2%9C%93&set_filter=1&sort=id%3Adesc&f[]=status_id&op[status_id]=c&f[]=tracker_id&op[tracker_id]=%3D&v[tracker_id][]=11&f[]=cf_4&op[cf_4]=~&v[cf_4][]=" + bigcode + "&f[]=&c[]=tracker&c[]=category&c[]=status&c[]=priority&c[]=subject&c[]=assigned_to&c[]=updated_on&c[]=start_date&c[]=due_date&c[]=done_ratio&group_by=&t[]=estimated_hours&t[]=", auth);

            for (JsonElement element : rootObj.getAsJsonArray("issues")) {
                JsonObject obj = element.getAsJsonObject();
                softwareRelease.setBigCode(bigcode);
                JsonObject repositoryObj = obj.getAsJsonArray("custom_fields").get(4).getAsJsonObject();
                String newRepository = repositoryObj.get("value").getAsString().trim().toLowerCase();
                repositoriesFromRedmine.add(newRepository);
                System.out.println(newRepository);
            }
            String area = "area" + bigcode.split("-")[1];
            softwareRelease.setArea(area);
            JsonArray projectsFromGit = getJsonArrayFromUrl(String.format(gitlabUrl + "/api/v4/groups/%s/projects?private_token=%s&per_page=1000",
                    area, gitlabToken), auth);
            for (JsonElement element : projectsFromGit) {
                String repositoryWithPath = element.getAsJsonObject().get("path_with_namespace").getAsString();
                String repositoryId = element.getAsJsonObject().get("id").getAsString();
                if (repositoriesFromRedmine.contains(repositoryWithPath)) {

                    JsonObject diffObject = getJsonObjectFromUrl(String.format(gitlabUrl + "/api/v4/projects/%s/repository/compare?from=master&to=%s&&private_token=%s",
                            repositoryId,
                            "develop-" + bigcode,
                            gitlabToken
                    ), auth);
                    if (diffObject != null) {
                        //System.out.println(diffObject.getAsJsonArray("diffs"));

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
                            }
                            if (isRenamed) {
                                match.setAction("RENAMED");
                            }
                            if (isDeleted) {
                                match.setAction("DELETED");
                            }
                            if (!(isNew || isRenamed || isDeleted)) {
                                match.setAction("MODIFIED");
                            }
                            matches.add(match);
                        }
                    }
                }
            }
            softwareRelease.setDevelopmentItemList(matches);
            if (matches.size() > 0) {
                softwareReleaseDAO.CreateOrUpdateLos(softwareRelease);
            } else {
                matches = softwareReleaseDAO.getLosByBigCode(bigcode).getDevelopmentItemList();
            }

        } catch (Exception ex) {
            ex.printStackTrace();
        }

        Comparator<DevelopmentItem> compareByRepositoryPath = Comparator
                .comparing(DevelopmentItem::getRepository)
                .thenComparing(DevelopmentItem::getPath);

        return matches.stream()
                .sorted(compareByRepositoryPath)
                .collect(Collectors.toList());
    }

    @Override
    public List<DevelopmentItem> getLosByBigCode(String bigcode) throws Exception {
        SoftwareRelease softwareRelease = softwareReleaseDAO.getLosByBigCode(bigcode);
        return softwareRelease.getDevelopmentItemList();
    }

    @Override
    public List<Release> getDeliveryCheck(String bigCode) throws Exception {
        SoftwareRelease softwareRelease = new SoftwareRelease();
        Pattern pattern = Pattern.compile("(area[0-9]{4})/([^/]+)/(.*)");


        List<Release> releases = new ArrayList<>();
        try {
            String auth = "developer" + ":" + "Developer!";

            JsonObject rootObj = getJsonObjectFromUrl(redmineUrl + "/projects/release-deployment-management/issues.json?utf8=%E2%9C%93&set_filter=1&sort=id%3Adesc&f[]=status_id&op[status_id]=c&f[]=tracker_id&op[tracker_id]=%3D&v[tracker_id][]=15&f[]=cf_4&op[cf_4]=~&v[cf_4][]=" + bigCode + "&f[]=&c[]=tracker&c[]=category&c[]=status&c[]=priority&c[]=subject&c[]=assigned_to&c[]=updated_on&c[]=start_date&c[]=due_date&c[]=done_ratio&group_by=&t[]=estimated_hours&t[]=", auth);
            for (JsonElement element : rootObj.getAsJsonArray("issues")) {
                List<ReleaseItem> releaseItems = new ArrayList<>();
                Release release = new Release();
                JsonObject obj = element.getAsJsonObject();
                softwareRelease.setBigCode(bigCode);
                softwareRelease.setArea("area" + bigCode.split("-")[1]);
                release.setDate(obj.getAsJsonPrimitive("closed_on").getAsString());
                release.setAuthor(obj.getAsJsonObject("author").get("name").getAsString());
                release.setMergeRequestType(obj.getAsJsonObject("author").get("name").getAsString());
                JsonObject urlNexusObj = obj.getAsJsonArray("custom_fields").get(6).getAsJsonObject();
                String urlNexus = urlNexusObj.get("value").getAsString().trim();
                String[] item = urlNexus.split("\r\n");
                List<String> nexusurls = new ArrayList<>();
                for (String line : item) {
                    if (line.startsWith("http:")) {
                        nexusurls.add(line.replace(",", "") + "\n");
                    }
                }
                release.setNexusUrls(nexusurls);
                JsonObject tipoMRObj = obj.getAsJsonArray("custom_fields").get(3).getAsJsonObject();
                String tipoMR = tipoMRObj.get("value").getAsString().trim();
                release.setMergeRequestType(Release.decodeTipoMergeRequest.get(tipoMR));
                JsonObject dettagliRilascioObj = obj.getAsJsonArray("custom_fields").get(10).getAsJsonObject();
                String dettagliRilascio = dettagliRilascioObj.get("value").getAsString().trim();
                for (String dettaglio : dettagliRilascio.split("\r\n")) {
                    if (dettaglio != null && !dettaglio.equals("")) {
                        ReleaseItem releaseItem = new ReleaseItem();
                        String[] elements = dettaglio.split(":");
                        releaseItem.setSha1sum(elements[1]);

                        Matcher matcher = pattern.matcher(elements[0]);
                        if (matcher.find()) {
                            releaseItem.setRepository(matcher.group(2));
                            releaseItem.setFilename(matcher.group(3));

                            if (Release.decodeTipoMergeRequest.get(tipoMR).equals("ESERCIZIO")) {
                                releaseItem.setPathMef(ReleaseItem.pathMefMapEsercizio.getOrDefault(matcher.group(1) + "/" + matcher.group(2), ""));
                            } else {
                                releaseItem.setPathMef(ReleaseItem.pathMefMapCollaudo.getOrDefault(matcher.group(1) + "/" + matcher.group(2), ""));
                            }
                        }
                        releaseItems.add(releaseItem);
                    }
                }
                release.setReleaseItems(releaseItems);
                releases.add(release);
            }
            softwareRelease.setReleases(releases);

            if (releases.size() > 0) {
                softwareReleaseDAO.CreateOrUpdateDeliveryCheck(softwareRelease);
            }

        } catch (Exception ex) {
            ex.printStackTrace();
            System.out.println("ERROR: " + ex.getMessage());
        }
        Comparator<Release> compareDate = Comparator
                .comparing(Release::getDate);


        return releases.stream()
                .sorted(compareDate)
                .collect(Collectors.toList());
    }

    public static void main(String[] args) {
        SoftwareReleaseServiceImpl service = new SoftwareReleaseServiceImpl();
        try {
            service.getLOS("R116-0196-E-000143");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


}