package com.cm.dev.bean;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Release {

    public static final Map<String, String> decodeTipoMergeRequest = Stream.of(new String[][]{
            {"29", "COLLAUDO"},
            {"30", "ESERCIZIO"}
    }).collect(Collectors.toMap(data -> data[0], data -> data[1]));

    private String author;
    private String date;
    private String mergeRequestType;
    private List<String> nexusUrls;
    private List<ReleaseItem> releaseItems;

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getMergeRequestType() {
        return mergeRequestType;
    }

    public void setMergeRequestType(String mergeRequestType) {
        this.mergeRequestType = mergeRequestType;
    }

    public List<String> getNexusUrls() {
        return nexusUrls;
    }

    public void setNexusUrls(List<String> nexusUrls) {
        this.nexusUrls = nexusUrls;
    }

    public List<ReleaseItem> getReleaseItems() {
        return releaseItems;
    }

    public void setReleaseItems(List<ReleaseItem> releaseItems) {
        this.releaseItems = releaseItems;
    }
}
