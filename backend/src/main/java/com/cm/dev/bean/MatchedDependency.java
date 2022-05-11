package com.cm.dev.bean;

import com.cm.dev.domain.Dependency;

/**
 * A Java Project that has been identified as the dependency of another Project
 * 
 */
public class MatchedDependency extends Dependency {

    private String actualVersion;

    
    /** 
     * @return String
     */
    public String getActualVersion() {
        return this.actualVersion;
    }

    
    /** 
     * @param actualVersion
     */
    public void setActualVersion(String actualVersion) {
        this.actualVersion = actualVersion;
    }

    
    /** 
     * @param dependency
     * @return MatchedDependency
     */
    public static MatchedDependency fromDependency(Dependency dependency) {
        MatchedDependency md = new MatchedDependency();
        md.setArtifactId(dependency.getArtifactId());
        md.setArtifactType(dependency.getArtifactType());
        md.setGroupId(dependency.getGroupId());
        md.setId(dependency.getId());
        md.setLine(dependency.getLine());
        md.setNexusUrl(dependency.getNexusUrl());
        md.setParent(dependency.getParent());
        md.setScope(dependency.getScope());
        md.setVersion(dependency.getVersion());

        return md;
    }
}