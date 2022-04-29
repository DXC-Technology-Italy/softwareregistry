package com.cm.dev.dao.impl;

import com.cm.dev.dao.DependencyDAO;
import com.cm.dev.domain.Dependency;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Repository;

import java.util.Collection;
import java.util.List;
import java.util.regex.Pattern;

@Repository
public class DependencyDAOImpl implements DependencyDAO {

    @Autowired
    private MongoTemplate mongoTemplate;

    @Override
    public Dependency create(Dependency entity) throws Exception {
        Dependency dependency = mongoTemplate.insert(entity);
        return dependency;
    }

    @Override
    public Collection<Dependency> createMany(List<Dependency> entities) throws Exception {
        Collection<Dependency> dependencies = mongoTemplate.insertAll(entities);
        return dependencies;
    }

    @Override
    public void delete(Dependency entity) throws Exception {

    }

    @Override
    public void deleteAll() throws Exception {
        mongoTemplate.dropCollection(Dependency.class);
    }

    @Override
    public Dependency save(Dependency entity) throws Exception {
        return null;
    }

    @Override
    public List<Dependency> getAll() throws Exception {
        return null;
    }

    @Override
    public List<Dependency> getByName(String name) throws Exception {
        String search = ".*" + name + ".*";
        return mongoTemplate.find(
                Query.query( Criteria.where("artifactId").regex(search, "i") ),
                Dependency.class);
    }
    @Override
    public List<Dependency> getByNameAndGroupId(String name, String groupId) throws Exception {
        String search = ".*" + name + ".*";
        Criteria criteria = new Criteria();
        criteria.andOperator(Criteria.where("artifactId").regex(search, "i"),
                Criteria.where("groupId").is(groupId)  );
        Query query = new Query(criteria);
        return mongoTemplate.find (query, Dependency.class);
    }
}
