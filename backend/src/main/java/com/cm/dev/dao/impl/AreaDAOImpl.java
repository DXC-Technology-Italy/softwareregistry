package com.cm.dev.dao.impl;

import com.cm.dev.dao.AreaDAO;
import com.cm.dev.domain.Area;
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
 * Manages the persistence of Area Objects
 * 
 */
@Repository
public class AreaDAOImpl implements AreaDAO {

    @Autowired
    private MongoTemplate mongoTemplate;

    
    /** 
     * @param entity
     * @return Area
     * @throws MongoException
     */
    @Override
    public Area create(Area entity) throws MongoException {
        return mongoTemplate.insert(entity);
    }

    
    /** 
     * @param entities
     * @return Collection<Area>
     * @throws MongoException
     */
    @Override
    public Collection<Area> createMany(List<Area> entities) throws MongoException {
        return mongoTemplate.insertAll(entities);
    }

    
    /** 
     * @param entity
     * @throws NotImplementedException
     */
    @Override
    public void delete(Area entity) throws NotImplementedException {
        throw new NotImplementedException();
    }

    
    /** 
     * @throws MongoException
     */
    @Override
    public void deleteAll() throws MongoException {
        mongoTemplate.dropCollection(Area.class);
    }

    
    /** 
     * @param entity
     * @return Area
     * @throws MongoException
     */
    @Override
    public Area save(Area entity) throws MongoException {
        throw new NotImplementedException();
    }

    
    /** 
     * @return List<Area>
     * @throws MongoException
     */
    @Override
    public List<Area> getAll() throws MongoException {
        return mongoTemplate.findAll(Area.class);
    }

    
    /** 
     * @param code
     * @return Area
     * @throws MongoException
     */
    @Override
    public Area getByCode(String code) throws MongoException{
        return mongoTemplate.findOne(Query.query(Criteria.where("code").is(code)), Area.class);
    }

    
    /** 
     * @return List<String>
     * @throws MongoException
     */
    @Override
    public List<String> getDistinctAreas() throws MongoException{
        return mongoTemplate.findDistinct("code", Area.class, java.lang.String.class);
    }
}
