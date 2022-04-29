package com.cm.dev.domain;

import com.cm.dev.bean.DevelopmentItem;
import com.cm.dev.bean.Release;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;

@Document(collection = "SoftwareRelease")
public class SoftwareRelease {
    @Id
    private ObjectId id;
    private String bigCode;
    private String Area;
    private List<DevelopmentItem> developmentItemList;
    private List<Release> releases;

    public ObjectId getId() {
        return id;
    }

    public void setId(ObjectId id) {
        this.id = id;
    }

    public String getBigCode() {
        return bigCode;
    }

    public void setBigCode(String bigCode) {
        this.bigCode = bigCode;
    }


    public List<DevelopmentItem> getDevelopmentItemList() {
        return developmentItemList;
    }

    public void setDevelopmentItemList(List<DevelopmentItem> developmentItemList) {
        this.developmentItemList = developmentItemList;
    }

    public String getArea() {
        return Area;
    }

    public void setArea(String area) {
        Area = area;
    }

    public List<Release> getReleases() {
        return releases;
    }

    public void setReleases(List<Release> releases) {
        this.releases = releases;
    }
}
