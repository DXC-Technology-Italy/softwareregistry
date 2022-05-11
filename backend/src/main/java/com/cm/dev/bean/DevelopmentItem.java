package com.cm.dev.bean;

/**
 * Software item developed within a Release
 */
public class DevelopmentItem {

    private String extension;
    private String name;
    private String path;
    private String repository;
    private String action;

    
    /** 
     * @return String
     */
    public String getExtension() {
        return extension;
    }

    
    /** 
     * @param extension the file extension of this software item
     */
    public void setExtension(String extension) {
        this.extension = extension;
    }

    
    /** 
     * @return String
     */
    public String getName() {
        return name;
    }

    
    /** 
     * @param name the name of the software item
     */
    public void setName(String name) {
        this.name = name;
    }

    
    /** 
     * @return String
     */
    public String getPath() {
        return path;
    }

    
    /** 
     * @param path the path where the software item is deployed
     */
    public void setPath(String path) {
        this.path = path;
    }

    
    /** 
     * @return String
     */
    public String getRepository() {
        return repository;
    }

    
    /** 
     * @param repository the git repository where the software item is placed
     */
    public void setRepository(String repository) {
        this.repository = repository;
    }
    
    /** 
     * @return String
     */
    public String getAction() {
        return action;
    }

    
    /** 
     * @param action
     */
    public void setAction(String action) {
        this.action = action;
    }
}
