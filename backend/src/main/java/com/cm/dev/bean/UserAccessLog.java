package com.cm.dev.bean;

import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Date;

@Document("UserAccessLog")
public class UserAccessLog {
    private String username;
    private Date accessTime;
    private String environment;

    public UserAccessLog() {

    }
    public UserAccessLog(String username, Date accessTime, String environment) {
        this.username = username;
        this.accessTime = accessTime;
        this.setEnvironment(environment);

    }
    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEnvironment() {
        return environment;
    }

    public void setEnvironment(String environment) {
        this.environment = environment;
    }
}
