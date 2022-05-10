package com.cm.dev.dao.impl;

import com.cm.dev.dao.ProjectDAO;
import com.cm.dev.domain.Project;
import com.cm.dev.exception.NotImplementedException;
import com.mongodb.MongoException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Repository;

import java.util.Collection;
import java.util.List;

/**
 * Manages the persistence of Project Objects
 * 
 */
@Repository
public class ProjectDAOImpl implements ProjectDAO {

    private static final String REPOSITORY = "repository";

    @Autowired
    private MongoTemplate mongoTemplate;

    
    /** 
     * @param entity
     * @return Project
     * @throws MongoException
     */
    @Override
    public Project create(Project entity) throws MongoException {
        throw new NotImplementedException();
    }

    
    /** 
     * @param entities
     * @return Collection<Project>
     * @throws MongoException
     */
    @Override
    public Collection<Project> createMany(List<Project> entities) throws MongoException {
        return mongoTemplate.insertAll(entities);
    }

    
    /** 
     * @param entity
     * @throws MongoException
     */
    @Override
    public void delete(Project entity) throws MongoException {
        throw new NotImplementedException();
    }

    
    /** 
     * @param entity
     * @return Project
     * @throws MongoException
     */
    @Override
    public Project save(Project entity) throws MongoException {
        throw new NotImplementedException();
    }

    
    /** 
     * @return List<Project>
     * @throws MongoException
     */
    @Override
    public List<Project> getAll() throws MongoException {
        Query query = new Query();
        query.with(Sort.by(Sort.Direction.ASC, "area")).with(Sort.by(Sort.Direction.ASC, REPOSITORY)).with(Sort.by(Sort.Direction.ASC, "name"));
        return mongoTemplate.find(query, Project.class);
    }

    
    /** 
     * @throws MongoException
     */
    @Override
    public void deleteAll() throws MongoException {
        mongoTemplate.dropCollection(Project.class);
    }

    
    /** 
     * @param repository
     * @return List<Project>
     */
    @Override
    public List<Project> getByRepository(String repository) {
        return mongoTemplate.find(
                Query.query(Criteria.where(REPOSITORY).is(repository)),
                Project.class);
    }

    
    /** 
     * @return List<Project>
     */
    @Override
    public List<Project> getAllProjectsWithoutDependencies() {
        Query query = new Query();
        query.fields().exclude("dependencies");
        query.addCriteria(Criteria.where("name").not().regex("EAR$"));
        query.with(Sort.by(Sort.Direction.ASC, "area")).with(Sort.by(Sort.Direction.ASC, REPOSITORY)).with(Sort.by(Sort.Direction.ASC, "name"));
        return mongoTemplate.find(
                query,
                Project.class);
    }

    
    /** 
     * @return List<String>
     */
    @Override
    public List<String> getDistinctProjects() {
        return mongoTemplate.findDistinct("name", Project.class, java.lang.String.class);
    }

    
    /** 
     * @param name
     * @return Project
     */
    @Override
    public Project getByName(String name) {
        String search = "^" + name + "$";
        return mongoTemplate.findOne(
                Query.query(Criteria.where("name").regex(search, "i")),
                Project.class);
    }
}
