package com.cm.dev.dao;

import com.cm.dev.domain.ProjectInfo;
import com.mongodb.MongoException;

/**
 * Interface for ProjectInfo Data Access Objects. 
 * The GenericDAO interface already exposes basic CRUD operations
 */
public interface ProjectInfoDAO extends GenericDAO<ProjectInfo> {

    ProjectInfo findAndModifyProjectInfo(ProjectInfo projectInfo) throws MongoException;

    ProjectInfo findAndModifyMavenCertified(ProjectInfo projectInfo) throws MongoException;
}
