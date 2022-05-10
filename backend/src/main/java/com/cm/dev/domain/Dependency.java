package com.cm.dev.domain;

import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Objects;

/**
 * Dependency domain object
 * 
 */
@Document(collection = "Dependency")
public class Dependency implements Comparable<Dependency> {

    @Id
    private Object id;

    private String artifactId;
    private String groupId;
    private String version;

    private String parent;
    private String scope;

    private String artifactType;
    private String nexusUrl;

    private int line;

    
    /** 
     * @return Object
     */
    public Object getId() {
        return id;
    }

    
    /** 
     * @param id
     */
    public void setId(Object id) {
        this.id = id;
    }


    
    /** 
     * @return String
     */
    public String getArtifactId() {
        return artifactId;
    }

    
    /** 
     * @param artifactId
     */
    public void setArtifactId(String artifactId) {
        this.artifactId = artifactId;
    }

    
    /** 
     * @return String
     */
    public String getGroupId() {
        return groupId;
    }

    
    /** 
     * @param groupId
     */
    public void setGroupId(String groupId) {
        this.groupId = groupId;
    }

    
    /** 
     * @return String
     */
    public String getVersion() {
        return version;
    }

    
    /** 
     * @param version
     */
    public void setVersion(String version) {
        this.version = version;
    }

    
    /** 
     * @return String
     */
    public String getParent() {
        return parent;
    }

    
    /** 
     * @param parent
     */
    public void setParent(String parent) {
        this.parent = parent;
    }

    
    /** 
     * @return String
     */
    public String getScope() {
        return scope;
    }

    
    /** 
     * @param scope
     */
    public void setScope(String scope) {
        this.scope = scope;
    }


    
    /** 
     * @return String
     */
    public String getArtifactType() {
        return artifactType;
    }

    
    /** 
     * @param artifactType
     */
    public void setArtifactType(String artifactType) {
        this.artifactType = artifactType;
    }

    
    /** 
     * @return String
     */
    public String getNexusUrl() {
        return nexusUrl;
    }

    
    /** 
     * @param nexusUrl
     */
    public void setNexusUrl(String nexusUrl) {
        this.nexusUrl = nexusUrl;
    }

    public void generateIdForInsertion() {
        this.id = new ObjectId();
    }

    
    /** 
     * @return int
     */
    public int getLine() {
        return line;
    }

    
    /** 
     * @param line
     */
    public void setLine(int line) {
        this.line = line;
    }

    
    /** 
     * @param that
     * @return int
     */
    @Override
    public int compareTo(Dependency that) {
        if (that == null)
            return 1;
        String thisVersion = this.getVersion();
        String thatVersion = that.getVersion();

        if (thisVersion.contains("-"))
            thisVersion = thisVersion.split("-")[0];
        if (thatVersion.contains("-"))
            thatVersion = thatVersion.split("-")[0];

        String[] thisParts = thisVersion.split("\\.");
        String[] thatParts = thatVersion.split("\\.");

        int length = Math.max(thisParts.length, thatParts.length);
        for (int i = 0; i < length; i++) {
            int thisPart = i < thisParts.length ?
                    Integer.parseInt(thisParts[i]) : 0;
            int thatPart = i < thatParts.length ?
                    Integer.parseInt(thatParts[i]) : 0;
            if (thisPart < thatPart)
                return -1;
            if (thisPart > thatPart)
                return 1;
        }
        return 0;
    }

    
    /** 
     * @param that
     * @return boolean
     */
    @Override
    public boolean equals(Object that) {
        if (this == that)
            return true;
        if (that == null)
            return false;
        if (this.getClass() != that.getClass())
            return false;
        return this.compareTo((Dependency) that) == 0;
    }

    
    /** 
     * @return int
     */
    @Override
    public int hashCode() {
        return Objects.hash(id, artifactId, groupId, version, parent, scope, artifactType, nexusUrl, line);
    }

    
    /** 
     * @return String
     */
    @Override
    public String toString() {
        return "gav: " + this.getGroupId() + ":" + this.getArtifactId() + ":" + this.getVersion() + ", parent: " + this.getParent();
    }

}
