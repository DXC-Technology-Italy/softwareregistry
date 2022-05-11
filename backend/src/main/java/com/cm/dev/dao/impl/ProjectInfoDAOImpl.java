package com.cm.dev.dao.impl;

import com.cm.dev.dao.ProjectInfoDAO;
import com.cm.dev.domain.ProjectInfo;
import com.cm.dev.exception.NotImplementedException;
import com.mongodb.MongoException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Repository;

import java.util.Collection;
import java.util.List;

/**
 * Manages the persistence of ProjectInfo Objects
 * 
 */
@Repository
public class ProjectInfoDAOImpl implements ProjectInfoDAO {

    private static final String REPOSITORY = "repository";

    @Autowired
    private MongoTemplate mongoTemplate;

    
    /** 
     * @param entity
     * @return ProjectInfo
     * @throws MongoException
     */
    @Override
    public ProjectInfo create(ProjectInfo entity) throws MongoException {
        return mongoTemplate.insert(entity);
    }

    
    /** 
     * @param entities
     * @return Collection<ProjectInfo>
     * @throws MongoException
     */
    @Override
    public Collection<ProjectInfo> createMany(List<ProjectInfo> entities) throws MongoException {
        return mongoTemplate.insertAll(entities);
    }

    
    /** 
     * @param entity
     * @throws MongoException
     */
    @Override
    public void delete(ProjectInfo entity) throws MongoException {
        throw new NotImplementedException();
    }

    
    /** 
     * @throws MongoException
     */
    @Override
    public void deleteAll() throws MongoException {
        throw new NotImplementedException();
    }

    
    /** 
     * @param entity
     * @return ProjectInfo
     * @throws MongoException
     */
    @Override
    public ProjectInfo save(ProjectInfo entity) throws MongoException {
        return mongoTemplate.save(entity);
    }


    
    /** 
     * @return List<ProjectInfo>
     * @throws MongoException
     */
    @Override
    public List<ProjectInfo> getAll() throws MongoException {
        return mongoTemplate.findAll(ProjectInfo.class);
    }

    
    /** 
     * @param projectInfo
     * @return ProjectInfo
     */
    @Override
    public ProjectInfo findAndModifyProjectInfo(ProjectInfo projectInfo) {
        Query query = new Query(Criteria.where("name").is(projectInfo.getName()).and(REPOSITORY).is(projectInfo.getRepository()));
        Update update = new Update();
        update.set("reusable", projectInfo.getReusable());
        update.set("description", projectInfo.getDescription());
        update.set("reusabilityNotes", projectInfo.getReusabilityNotes());
        update.set("projectType", projectInfo.getProjectType());
        update.set("mavenCertified", projectInfo.getMavenCertified());
        return mongoTemplate.findAndModify(query, update, ProjectInfo.class);
    }

    
    /** 
     * @param projectInfo
     * @return ProjectInfo
     */
    @Override
    public ProjectInfo findAndModifyMavenCertified(ProjectInfo projectInfo) {
        Query query = new Query(Criteria.where("name").is(projectInfo.getName()).and(REPOSITORY).is(projectInfo.getRepository()));
        Update update = new Update();
        update.set("mavenCertified", projectInfo.getMavenCertified());
        return mongoTemplate.findAndModify(query, update, ProjectInfo.class);
    }
}
