package com.cm.dev.domain;

import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.ArrayList;
import java.util.List;

/**
 * ProjectInfo domain object
 * 
 */
@Document(collection = "ProjectInfo")
public class ProjectInfo {

    @Id
    private ObjectId id;
    private String name;
    private String repository;
    private Boolean reusable;
    private String reusabilityNotes;
    private String description;
    private List<String> projectType = new ArrayList<>();
    private Boolean mavenCertified;

    
    /** 
     * @return Boolean
     */
    public Boolean getMavenCertified() {
        return mavenCertified;
    }

    
    /** 
     * @param mavenCertified
     */
    public void setMavenCertified(Boolean mavenCertified) {
        this.mavenCertified = mavenCertified;
    }

    
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
    public String getRepository() {
        return repository;
    }

    
    /** 
     * @param repository
     */
    public void setRepository(String repository) {
        this.repository = repository;
    }

    
    /** 
     * @return Boolean
     */
    public Boolean getReusable() {
        return reusable;
    }

    
    /** 
     * @param reusable
     */
    public void setReusable(Boolean reusable) {
        this.reusable = reusable;
    }

    
    /** 
     * @return String
     */
    public String getReusabilityNotes() {
        return reusabilityNotes;
    }

    
    /** 
     * @param reusabilityNotes
     */
    public void setReusabilityNotes(String reusabilityNotes) {
        this.reusabilityNotes = reusabilityNotes;
    }

    
    /** 
     * @return String
     */
    public String getDescription() {
        return description;
    }

    
    /** 
     * @param description
     */
    public void setDescription(String description) {
        this.description = description;
    }

    
    /** 
     * @return List<String>
     */
    public List<String> getProjectType() {
            return projectType;
    }

    
    /** 
     * @param projectType
     */
    public void setProjectType(List<String> projectType) {
        this.projectType = projectType;
    }

    
    /** 
     * @return String
     */
    public String toString() {
        return "ProjectInfo: [" + this.id + ", " + this.name + ", " + this.repository + ", " + this.reusable + ", " + this.description + ", " + this.reusabilityNotes + "]";
    }
}
