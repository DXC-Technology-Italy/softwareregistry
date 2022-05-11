package com.cm.dev.bean;

/**
 * A file matched during a code search
 * 
 */
public class MatchedSearch {
    private String filepath;
    private String area;
    private String kind;
    private String project;
    private String repository;

    
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
     * @return String
     */
    public String getFilepath() {
        return filepath;
    }

    
    /** 
     * @param filepath
     */
    public void setFilepath(String filepath) {
        this.filepath = filepath;
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
     * @return String
     */
    public String getProject() {
        return project;
    }

    
    /** 
     * @param project
     */
    public void setProject(String project) {
        this.project = project;
    }

    
    /** 
     * @return String
     */
    @Override
    public String toString() {
        return this.getRepository() + " - " + this.getProject() + " - " + this.getArea() + " - " + this.getFilepath();
    }
}
