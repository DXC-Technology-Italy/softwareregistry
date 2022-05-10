package com.cm.dev.dao.impl;

import com.cm.dev.dao.AreaDAO;
import com.cm.dev.dao.RepositoryDAO;
import com.cm.dev.domain.Area;
import com.cm.dev.domain.Repository;
import com.cm.dev.exception.NotImplementedException;
import com.mongodb.MongoException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;

import java.util.Collection;
import java.util.List;

/**
 * Manages the persistence of Repository Objects
 * 
 */

@org.springframework.stereotype.Repository
public class RepositoryDAOImpl implements RepositoryDAO {

    @Autowired
    private final MongoTemplate mongoTemplate;

    private final AreaDAO areaDAO;

    public RepositoryDAOImpl(MongoTemplate mongoTemplate, AreaDAO areaDAO) {
        this.mongoTemplate = mongoTemplate;
        this.areaDAO = areaDAO;
    }

    
    /** 
     * @param code
     * @return List<Repository>
     * @throws MongoException
     */
    @Override
    public List<Repository> getRepositoriesFromArea(String code) throws MongoException {
        Area area = areaDAO.getByCode(code);
        return mongoTemplate.find(
                Query.query(Criteria.where("area.$id").is(area.getId())),
                Repository.class);
    }

    
    /** 
     * @param kind
     * @return List<String>
     */
    @Override
    public List<String> getRepositoriesByKind(String kind) {
        return mongoTemplate.findDistinct(
                Query.query(Criteria.where("kind").is(kind)), "name",
                Repository.class, java.lang.String.class);
    }

    
    /** 
     * @return List<String>
     */
    @Override
    public List<String> getDistinctRepositories() {
        return mongoTemplate.findDistinct("name", Repository.class, java.lang.String.class);
    }

    
    /** 
     * @param entity
     * @return Repository
     * @throws MongoException
     */
    @Override
    public Repository create(Repository entity) throws MongoException {
        throw new NotImplementedException();
    }

    
    /** 
     * @param entities
     * @return Collection<Repository>
     * @throws MongoException
     */
    @Override
    public Collection<Repository> createMany(List<Repository> entities) throws MongoException {
        return mongoTemplate.insertAll(entities);
    }

    
    /** 
     * @param entity
     * @throws MongoException
     */
    @Override
    public void delete(Repository entity) throws MongoException {
        throw new NotImplementedException();
    }

    
    /** 
     * @param entity
     * @return Repository
     * @throws MongoException
     */
    @Override
    public Repository save(Repository entity) throws MongoException {
        throw new NotImplementedException();
    }

    
    /** 
     * @throws MongoException
     */
    @Override
    public void deleteAll() throws MongoException {
        mongoTemplate.dropCollection(Repository.class);
    }

    
    /** 
     * @return List<Repository>
     * @throws MongoException
     */
    @Override
    public List<Repository> getAll() throws MongoException {
        return mongoTemplate.findAll(Repository.class);
    }

    
    /** 
     * @param orderFields
     * @return List<Repository>
     */
    @Override
    public List<Repository> getAllOrdered(List<String> orderFields) {
        Query query = new Query();

        for (String orderField : orderFields) {
            query.with(Sort.by(Sort.Direction.ASC, orderField));
        }
        return mongoTemplate.find(query, Repository.class);
    }
}
