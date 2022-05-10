package com.cm.dev.domain;

import com.cm.dev.bean.DevelopmentItem;
import com.cm.dev.bean.Release;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;

/**
 * SoftwareRelease domain object
 * 
 */
@Document(collection = "SoftwareRelease")
public class SoftwareRelease {
    @Id
    private ObjectId id;
    private String bigCode;
    private String area;
    private List<DevelopmentItem> developmentItemList;
    private List<Release> releases;

    
    /** 
     * @return ObjectId
     */
    public ObjectId getId() {
        return id;
    }

    
    /** 
     * @param id
     */
    public void setId(ObjectId id) {
        this.id = id;
    }

    
    /** 
     * @return String
     */
    public String getBigCode() {
        return bigCode;
    }

    
    /** 
     * @param bigCode
     */
    public void setBigCode(String bigCode) {
        this.bigCode = bigCode;
    }


    
    /** 
     * @return List<DevelopmentItem>
     */
    public List<DevelopmentItem> getDevelopmentItemList() {
        return developmentItemList;
    }

    
    /** 
     * @param developmentItemList
     */
    public void setDevelopmentItemList(List<DevelopmentItem> developmentItemList) {
        this.developmentItemList = developmentItemList;
    }

    
    /** 
     * @return String
     */
    public String getArea() {
        return area;
    }

    
    /** 
     * @param area
     */
    public void setArea(String area) {
        this.area = area;
    }

    
    /** 
     * @return List<Release>
     */
    public List<Release> getReleases() {
        return releases;
    }

    
    /** 
     * @param releases
     */
    public void setReleases(List<Release> releases) {
        this.releases = releases;
    }
}
