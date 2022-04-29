package com.cm.dev.dao.impl;

import com.cm.dev.dao.AreaDAO;
import com.cm.dev.dao.GenericDAO;
import com.cm.dev.domain.Area;
import com.cm.dev.domain.Project;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Repository;

import java.util.Collection;
import java.util.List;
@Repository
public class AreaDAOImpl implements AreaDAO {

    @Autowired
    private MongoTemplate mongoTemplate;

    @Override
    public Area create(Area entity) throws Exception {
       Area area = mongoTemplate.insert(entity);
       return area;
    }

    @Override
    public Collection<Area> createMany(List<Area> entities) throws Exception {
        Collection<Area> areas =  mongoTemplate.insertAll(entities);
        return areas;
    }

    @Override
    public void delete(Area entity) throws Exception {

    }

    @Override
    public void deleteAll() throws Exception {
        mongoTemplate.dropCollection(Area.class);
    }

    @Override
    public Area save(Area entity) throws Exception {
        return null;
    }

    @Override
    public List<Area> getAll() throws Exception {
        return mongoTemplate.findAll(Area.class);
    }

    @Override
    public Area getByCode(String code) {
        return mongoTemplate.findOne(Query.query(Criteria.where("code").is(code)), Area.class);
    }

    @Override
    public List<String> getDistinctAreas() {
        return mongoTemplate.findDistinct("code",Area.class, java.lang.String.class);
    }
}
