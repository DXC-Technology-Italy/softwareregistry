package com.cm.dev.bean;

public class MatchedSearch {
    private String filepath;
    private String area;
    private String kind;
    private String project;
    private String repository;

    public String getRepository() {
        return repository;
    }

    public void setRepository(String repository) {
        this.repository = repository;
    }

    public String getFilepath() {
        return filepath;
    }

    public void setFilepath(String filepath) {
        this.filepath = filepath;
    }

    public String getArea() {
        return area;
    }

    public void setArea(String area) {
        this.area = area;
    }

    public String getKind() {
        return kind;
    }

    public void setKind(String kind) {
        this.kind = kind;
    }

    public String getProject() {
        return project;
    }

    public void setProject(String project) {
        this.project = project;
    }

    @Override
    public String toString(){
        return this.getRepository() + " - " + this.getProject() + " - " + this.getArea()  + " - " + this.getFilepath();
    }
}
