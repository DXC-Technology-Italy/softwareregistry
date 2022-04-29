package com.cm.dev.domain;

import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.ArrayList;

@Document(collection = "ProjectInfo")
public class ProjectInfo {

    @Id
    private ObjectId id;
    private String name;
    private String repository;
    private Boolean reusable;
    private String reusabilityNotes;
    private String description;
    private ArrayList<String> projectType;
    private Boolean mavenCertified;

    public Boolean getMavenCertified() {
        return mavenCertified;
    }

    public void setMavenCertified(Boolean mavenCertified) {
        this.mavenCertified = mavenCertified;
    }

    public ObjectId getId() {
        return id;
    }

    public void setId(ObjectId id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getRepository() {
        return repository;
    }

    public void setRepository(String repository) {
        this.repository = repository;
    }

    public Boolean getReusable() {
        return reusable;
    }

    public void setReusable(Boolean reusable) {
        this.reusable = reusable;
    }

    public String getReusabilityNotes() {
        return reusabilityNotes;
    }

    public void setReusabilityNotes(String reusabilityNotes) {
        this.reusabilityNotes = reusabilityNotes;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public ArrayList<String> getProjectType() {
        return projectType;
    }

    public void setProjectType(ArrayList<String> projectType) {
        this.projectType = projectType;
    }

    public String toString() {
        return "ProjectInfo: [" + this.id + ", " + this.name + ", " + this.repository + ", " + this.reusable + ", " + this.description + ", " + this.reusabilityNotes + "]";
    }
}
