package com.cm.dev.dao.impl;

import com.cm.dev.bean.DevelopmentItem;
import com.cm.dev.dao.SoftwareReleaseDAO;
import com.cm.dev.domain.SoftwareRelease;
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
 * Manages the persistence of Software Release Objects
 * 
 */
@Repository
public class SoftwareReleaseDAOImpl implements SoftwareReleaseDAO {

    private static final String BIGCODE="bigCode";
    @Autowired
    private MongoTemplate mongoTemplate;

    
    /** 
     * @param entity
     * @return DevelopmentItem
     * @throws MongoException
     */
    @Override
    public DevelopmentItem create(DevelopmentItem entity) throws MongoException {
        return mongoTemplate.insert(entity);
    }

    
    /** 
     * @param entities
     * @return Collection<DevelopmentItem>
     * @throws MongoException
     */
    @Override
    public Collection<DevelopmentItem> createMany(List<DevelopmentItem> entities) throws MongoException {
        return mongoTemplate.insertAll(entities);
    }

    
    /** 
     * @param entity
     * @throws MongoException
     */
    @Override
    public void delete(DevelopmentItem entity) throws MongoException {
        throw new NotImplementedException();
    }

    
    /** 
     * @throws MongoException
     */
    @Override
    public void deleteAll() throws MongoException {
        mongoTemplate.dropCollection(DevelopmentItem.class);
    }

    
    /** 
     * @param entity
     * @return DevelopmentItem
     * @throws MongoException
     */
    @Override
    public DevelopmentItem save(DevelopmentItem entity) throws MongoException {
        return null;
    }

    
    /** 
     * @return List<DevelopmentItem>
     * @throws MongoException
     */
    @Override
    public List<DevelopmentItem> getAll() throws MongoException {
        return mongoTemplate.findAll(DevelopmentItem.class);
    }

    
    /** 
     * @param softwareRelease
     */
    @Override
    public void createOrUpdateLos(SoftwareRelease softwareRelease) {
        Query query = new Query();
        query.addCriteria(Criteria.where(BIGCODE).is(softwareRelease.getBigCode()));
        Update update = new Update();
        update.set("developmentItemList", softwareRelease.getDevelopmentItemList());
        update.set("area", softwareRelease.getArea());
        update.set(BIGCODE, softwareRelease.getBigCode());
        mongoTemplate.upsert(query, update, SoftwareRelease.class);
    }

    
    /** 
     * @param bigCode
     * @return SoftwareRelease
     */
    @Override
    public SoftwareRelease getLosByBigCode(String bigCode) {
        Query query = new Query();
        query.addCriteria(Criteria.where(BIGCODE).is(bigCode));
        query.fields().include("developmentItemList");

        return mongoTemplate.findOne(query, SoftwareRelease.class);
    }

    
    /** 
     * @param softwareRelease
     */
    @Override
    public void createOrUpdateDeliveryCheck(SoftwareRelease softwareRelease) {
        Query query = new Query();
        query.addCriteria(Criteria.where(BIGCODE).is(softwareRelease.getBigCode()));
        Update update = new Update();
        update.set("releases", softwareRelease.getReleases());
        update.set(BIGCODE, softwareRelease.getBigCode());
        update.set("area", softwareRelease.getArea());
        mongoTemplate.upsert(query, update, SoftwareRelease.class);
    }

    
    /** 
     * @param bigCode
     */
    @Override
    public void deleteLosByBigCode(String bigCode) {
        mongoTemplate.findAllAndRemove(Query.query(Criteria.where(BIGCODE).is(bigCode)), DevelopmentItem.class);
    }
}
