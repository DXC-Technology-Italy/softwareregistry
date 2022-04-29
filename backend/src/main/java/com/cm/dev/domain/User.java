package com.cm.dev.domain;

import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Date;
import java.util.List;

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

    private List<String> authorizedPages;

    public User() {

    }

    public User(String username, Date lastLogin, String role, String theme, List<String> authorizedPages, boolean accessGranted) {
        this.username = username;
        this.role = role;
        this.lastLogin = lastLogin;
        this.theme = theme;
        this.authorizedPages = authorizedPages;
        this.accessGranted = accessGranted;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public Date getLastLogin() {
        return lastLogin;
    }

    public void setLastLogin(Date lastLogin) {
        this.lastLogin = lastLogin;
    }

    public String getTheme() {
        return theme;
    }

    public void setTheme(String theme) {
        this.theme = theme;
    }

    public List<String> getAuthorizedPages() {
        return authorizedPages;
    }

    public void setAuthorizedPages(List<String> authorizedPages) {
        this.authorizedPages = authorizedPages;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public ObjectId getId() {
        return id;
    }

    public void setId(ObjectId id) {
        this.id = id;
    }

    public boolean isAuthenticated() {
        return authenticated;
    }

    public void setAuthenticated(boolean authenticated) {
        this.authenticated = authenticated;
    }

    public boolean isAccessGranted() {
        return accessGranted;
    }

    public void setAccessGranted(boolean accessGranted) {
        this.accessGranted = accessGranted;
    }
}
