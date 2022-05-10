package com.cm.dev.domain;

import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

/**
 * Area domain object
 * 
 */
@Document(collection = "Area")
public class Area {

    @Id
    private ObjectId id;
    private String name;
    private String code;

    public Area() {
        this.code = "";
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
    public String getCode() {
        return code;
    }

    
    /** 
     * @param code
     */
    public void setCode(String code) {
        this.code = code;
    }
}
