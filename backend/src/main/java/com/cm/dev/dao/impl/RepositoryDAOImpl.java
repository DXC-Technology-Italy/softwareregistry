package com.cm.dev.dao.impl;

import com.cm.dev.dao.AreaDAO;
import com.cm.dev.dao.RepositoryDAO;
import com.cm.dev.domain.Area;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.aggregation.Aggregation;
import org.springframework.data.mongodb.core.aggregation.LookupOperation;
import org.springframework.data.mongodb.core.query.Criteria;
import com.cm.dev.domain.Repository;
import org.springframework.data.mongodb.core.query.Query;

import java.util.Collection;
import java.util.List;

@org.springframework.stereotype.Repository
public class RepositoryDAOImpl implements RepositoryDAO {

    @Autowired
    private MongoTemplate mongoTemplate;

    @Autowired
    private AreaDAO areaDAO;

    @Override
    public List<Repository> getRepositoriesFromArea(String code) {
        Area area = areaDAO.getByCode(code);
        return mongoTemplate.find(
                Query.query(Criteria.where("area.$id").is(area.getId())),
                Repository.class);
    }

    @Override
    public List<String> getRepositoriesByKind(String kind) {
        return mongoTemplate.findDistinct(
                Query.query(Criteria.where("kind").is(kind)),"name",
                Repository.class, java.lang.String.class);
    }

    @Override
    public List<String> getDistinctRepositories() {
        return mongoTemplate.findDistinct("name",Repository.class, java.lang.String.class);
    }

    @Override
    public Repository create(Repository entity) throws Exception {
        throw new Exception("Not implemented");
    }

    @Override
    public Collection<Repository> createMany(List<Repository> entities) throws Exception {
        Collection<Repository> repositories = mongoTemplate.insertAll(entities);
        return repositories;
    }

    @Override
    public void delete(Repository entity) throws Exception {
        throw new Exception("Not implemented");
    }

    @Override
    public Repository save(Repository entity) throws Exception {
        throw new Exception("Not implemented");
    }

    @Override
    public void deleteAll() throws Exception {
        mongoTemplate.dropCollection(Repository.class);
    }

    @Override
    public List<Repository> getAll() {
        return mongoTemplate.findAll(Repository.class);
    }

    @Override
    public List<Repository> getAllOrdered(List<String> orderFields) {
        Query query = new Query();

        for (String orderField: orderFields) {
            query.with(Sort.by(Sort.Direction.ASC, orderField));
        }
        return mongoTemplate.find(query, Repository.class);
    }
}
