package com.cm.dev.domain;

import org.springframework.data.mongodb.core.mapping.Document;

/**
 * User Access Log domain object
 * 
 */
@Document("UserAccessLog")
public class UserAccessLog {
    private String username;
    private String environment;

    public UserAccessLog() {

    }

    public UserAccessLog(String username, String environment) {
        this.username = username;
        this.setEnvironment(environment);

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
     * @return String
     */
    public String getEnvironment() {
        return environment;
    }

    
    /** 
     * @param environment
     */
    public void setEnvironment(String environment) {
        this.environment = environment;
    }

}
