package com.cm.dev.domain;

import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * User domain object
 * 
 */
@Document("User")
public class User {

    @Id
    private ObjectId id;
    private String username;
    private String role;
    private Date lastLogin;
    private String theme;
    private boolean authenticated;
    private boolean accessGranted;

    private List<String> authorizedPages = new ArrayList<>();

    public User() {

    }

    public User(String username, Date lastLogin, String role, String theme, List<String> authorizedPages, boolean accessGranted) {
        this.username = username;
        this.role = role;
        this.lastLogin = (Date) lastLogin.clone();
        this.theme = theme;
        this.authorizedPages = new ArrayList<>(authorizedPages);
        this.accessGranted = accessGranted;
    }

    
    /** 
     * @return String
     */
    public String getUsername() {
        return username;
    }

    
    /** 
     * @param username
     */
    public void setUsername(String username) {
        this.username = username;
    }

    
    /** 
     * @return Date
     */
    public Date getLastLogin() {
        return (Date) lastLogin.clone();
    }

    
    /** 
     * @param lastLogin
     */
    public void setLastLogin(Date lastLogin) {
        this.lastLogin = (Date) lastLogin.clone();
    }

    
    /** 
     * @return String
     */
    public String getTheme() {
        return theme;
    }

    
    /** 
     * @param theme
     */
    public void setTheme(String theme) {
        this.theme = theme;
    }

    
    /** 
     * @return List<String>
     */
    public List<String> getAuthorizedPages() {
        return authorizedPages;
    }

    
    /** 
     * @param authorizedPages
     */
    public void setAuthorizedPages(List<String> authorizedPages) {
        this.authorizedPages = authorizedPages;
    }

    
    /** 
     * @return String
     */
    public String getRole() {
        return role;
    }

    
    /** 
     * @param role
     */
    public void setRole(String role) {
        this.role = role;
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
     * @return boolean
     */
    public boolean isAuthenticated() {
        return authenticated;
    }

    
    /** 
     * @param authenticated
     */
    public void setAuthenticated(boolean authenticated) {
        this.authenticated = authenticated;
    }

    
    /** 
     * @return boolean
     */
    public boolean isAccessGranted() {
        return accessGranted;
    }

    
    /** 
     * @param accessGranted
     */
    public void setAccessGranted(boolean accessGranted) {
        this.accessGranted = accessGranted;
    }
}
