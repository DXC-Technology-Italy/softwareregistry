package com.cm.dev.dao.impl;

import com.cm.dev.bean.DevelopmentItem;
import com.cm.dev.dao.SoftwareReleaseDAO;
import com.cm.dev.domain.SoftwareRelease;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Repository;

import java.util.Collection;
import java.util.List;

@Repository
public class SoftwareReleaseDAOImpl implements SoftwareReleaseDAO {


    @Autowired
    private MongoTemplate mongoTemplate;

    @Override
    public DevelopmentItem create(DevelopmentItem entity) throws Exception {
        DevelopmentItem developmentItem = mongoTemplate.insert(entity);
        return developmentItem;
    }

    @Override
    public Collection<DevelopmentItem> createMany(List<DevelopmentItem> entities) throws Exception {
        Collection<DevelopmentItem> developmentItemCollection = mongoTemplate.insertAll(entities);
        return developmentItemCollection;
    }

    @Override
    public void delete(DevelopmentItem entity) throws Exception {

    }

    @Override
    public void deleteAll() throws Exception {
        mongoTemplate.dropCollection(DevelopmentItem.class);
    }

    @Override
    public DevelopmentItem save(DevelopmentItem entity) throws Exception {
        return null;
    }

    @Override
    public List<DevelopmentItem> getAll() throws Exception {
        return mongoTemplate.findAll(DevelopmentItem.class);
    }

    @Override
    public void CreateOrUpdateLos(SoftwareRelease softwareRelease) {
        Query query = new Query();
        query.addCriteria(Criteria.where("bigCode").is(softwareRelease.getBigCode()));
        Update update = new Update();
        update.set("developmentItemList", softwareRelease.getDevelopmentItemList());
        update.set("area", softwareRelease.getArea());
        update.set("bigCode", softwareRelease.getBigCode());
        mongoTemplate.upsert(query, update, SoftwareRelease.class);
    }

    @Override
    public SoftwareRelease getLosByBigCode(String bigCode) {
        Query query = new Query();
        query.addCriteria(Criteria.where("bigCode").is(bigCode));
        query.fields().include("developmentItemList");

        return mongoTemplate.findOne(query, SoftwareRelease.class);
    }

    @Override
    public void CreateOrUpdateDeliveryCheck(SoftwareRelease softwareRelease) {
        Query query = new Query();
        query.addCriteria(Criteria.where("bigCode").is(softwareRelease.getBigCode()));
        Update update = new Update();
        update.set("releases", softwareRelease.getReleases());
        update.set("bigCode", softwareRelease.getBigCode());
        update.set("area", softwareRelease.getArea());
        mongoTemplate.upsert(query, update, SoftwareRelease.class);
    }

    @Override
    public void DeleteLosByBigCode(String bigCode) {
        mongoTemplate.findAllAndRemove(Query.query(Criteria.where("bigCode").is(bigCode)), DevelopmentItem.class);
    }
}
