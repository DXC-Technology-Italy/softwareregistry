package com.cm.dev.dao.impl;

import com.cm.dev.dao.DependencyDAO;
import com.cm.dev.domain.Dependency;
import com.cm.dev.exception.NotImplementedException;
import com.mongodb.MongoException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Repository;

import java.util.Collection;
import java.util.List;


/**
 * Manages the persistence of Dependency Objects
 * 
 */
@Repository
public class DependencyDAOImpl implements DependencyDAO {

    @Autowired
    private MongoTemplate mongoTemplate;

    
    /** 
     * @param entity
     * @return Dependency
     * @throws MongoException
     */
    @Override
    public Dependency create(Dependency entity) throws MongoException {
        return mongoTemplate.insert(entity);
    }

    
    /** 
     * @param entities
     * @return Collection<Dependency>
     * @throws MongoException
     */
    @Override
    public Collection<Dependency> createMany(List<Dependency> entities) throws MongoException {
        return mongoTemplate.insertAll(entities);
    }

    
    /** 
     * @param entity
     * @throws MongoException
     */
    @Override
    public void delete(Dependency entity) throws MongoException {
        throw new NotImplementedException();
    }

    
    /** 
     * @throws MongoException
     */
    @Override
    public void deleteAll() throws MongoException {
        mongoTemplate.dropCollection(Dependency.class);
    }

    
    /** 
     * @param entity
     * @return Dependency
     * @throws MongoException
     */
    @Override
    public Dependency save(Dependency entity) throws MongoException {
        throw new NotImplementedException();
    }

    
    /** 
     * @return List<Dependency>
     * @throws MongoException
     */
    @Override
    public List<Dependency> getAll() throws MongoException {
        throw new NotImplementedException();
    }

    
    /** 
     * @param name
     * @return List<Dependency>
     */
    @Override
    public List<Dependency> getByName(String name) {
        String search = ".*" + name + ".*";
        return mongoTemplate.find(
                Query.query(Criteria.where("artifactId").regex(search, "i")),
                Dependency.class);
    }

    
    /** 
     * @param name
     * @param groupId
     * @return List<Dependency>
     */
    @Override
    public List<Dependency> getByNameAndGroupId(String name, String groupId) {
        String search = ".*" + name + ".*";
        Criteria criteria = new Criteria();
        criteria.andOperator(Criteria.where("artifactId").regex(search, "i"),
                Criteria.where("groupId").is(groupId));
        Query query = new Query(criteria);
        return mongoTemplate.find(query, Dependency.class);
    }
}
