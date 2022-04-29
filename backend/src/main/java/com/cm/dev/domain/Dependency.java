package com.cm.dev.domain;

import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection="Dependency")
public class Dependency implements Comparable<Dependency>  {

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

    public Object getId() {
        return id;
    }

    public void setId(Object id) {
        this.id = id;
    }


    public String getArtifactId() {
        return artifactId;
    }

    public void setArtifactId(String artifactId) {
        this.artifactId = artifactId;
    }

    public String getGroupId() {
        return groupId;
    }

    public void setGroupId(String groupId) {
        this.groupId = groupId;
    }

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public String getParent() {
        return parent;
    }

    public void setParent(String parent) {
        this.parent = parent;
    }

    public String getScope() {
        return scope;
    }

    public void setScope(String scope) {
        this.scope = scope;
    }


    public String getArtifactType() {
        return artifactType;
    }

    public void setArtifactType(String artifactType) {
        this.artifactType = artifactType;
    }

    public String getNexusUrl() {
        return nexusUrl;
    }

    public void setNexusUrl(String nexusUrl) {
        this.nexusUrl = nexusUrl;
    }

    public void generateIdForInsertion() {
        ObjectId id = new ObjectId();
        this.id = id;
    }

    public int getLine() {
        return line;
    }

    public void setLine(int line) {
        this.line = line;
    }

    @Override
    public int compareTo(Dependency that) {
        if(that == null)
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
        for(int i = 0; i < length; i++) {
            int thisPart = i < thisParts.length ?
                    Integer.parseInt(thisParts[i]) : 0;
            int thatPart = i < thatParts.length ?
                    Integer.parseInt(thatParts[i]) : 0;
            if(thisPart < thatPart)
                return -1;
            if(thisPart > thatPart)
                return 1;
        }
        return 0;
    }

    @Override public boolean equals(Object that) {
        if(this == that)
            return true;
        if(that == null)
            return false;
        if(this.getClass() != that.getClass())
            return false;
        return this.compareTo((Dependency) that) == 0;
    }

    @Override
    public String toString() {
        return "gav: " + this.getGroupId() + ":" + this.getArtifactId() + ":" + this.getVersion() + ", parent: " + this.getParent();
    }

}
