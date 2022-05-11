package com.cm.dev.domain;

import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;

/**
 * Repository domain object
 * 
 */

@Document(collection = "Repository")
public class Repository {
    @Id
    private ObjectId id;
    private String name;
    private String longName;
    private String kind;

    private String url;
    @DBRef
    private List<Project> projects;

    private String area;

    
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
    public String getName() {
        return name;
    }

    
    /** 
     * @param name
     */
    public void setName(String name) {
        this.name = name;
    }

    
    /** 
     * @return String
     */
    public String getLongName() {
        return longName;
    }

    
    /** 
     * @param longName
     */
    public void setLongName(String longName) {
        this.longName = longName;
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
     * @return String
     */
    public String getUrl() {
        return url;
    }

    
    /** 
     * @param url
     */
    public void setUrl(String url) {
        this.url = url;
    }

    
    /** 
     * @return String
     */
    public String getKind() {
        return kind;
    }

    
    /** 
     * @param kind
     */
    public void setKind(String kind) {
        this.kind = kind;
    }

    
    /** 
     * @return List<Project>
     */
    public List<Project> getProjects() {
        return projects;
    }

    
    /** 
     * @param projects
     */
    public void setProjects(List<Project> projects) {
        this.projects = projects;
    }

    
    /** 
     * @return String
     */
    public String toString() {
        return "Repository: [" + this.name + ", " + this.longName + ", " + this.url + "]";
    }
}
