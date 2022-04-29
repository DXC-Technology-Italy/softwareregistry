package com.cm.dev.dao.impl;

import com.cm.dev.dao.ProjectDAO;
import com.cm.dev.domain.Project;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Repository;

import java.util.Collection;
import java.util.List;
@Repository
public class ProjectDAOImpl implements ProjectDAO {

    @Autowired
    private MongoTemplate mongoTemplate;

    @Override
    public Project create(Project entity) throws Exception {
        throw new Exception("Not implemented");
    }

    @Override
    public Collection<Project> createMany(List<Project> entities) throws Exception {
        Collection<Project> projects = mongoTemplate.insertAll(entities);
        return projects;
    }

    @Override
    public void delete(Project entity) throws Exception {
        throw new Exception("Not implemented");
    }

    @Override
    public Project save(Project entity) throws Exception {
        throw new Exception("Not implemented");
    }

    @Override
    public List<Project> getAll() throws Exception {
        Query query = new Query();
        query.with(Sort.by(Sort.Direction.ASC, "area")).with(Sort.by(Sort.Direction.ASC, "repository")).with(Sort.by(Sort.Direction.ASC, "name"));
        return mongoTemplate.find(query, Project.class);
    }

    @Override
    public void deleteAll() throws Exception {
        mongoTemplate.dropCollection(Project.class);
    }

    @Override
    public List<Project> getByRepository(String repository) throws Exception {
        return mongoTemplate.find(
                Query.query(Criteria.where("repository").is(repository)),
                Project.class);
    }

    @Override
    public List<Project> getAllProjectsWithoutDependencies() throws Exception {
        Query query = new Query();
        query.fields().exclude("dependencies");
        query.addCriteria(Criteria.where("name").not().regex("EAR$"));
        query.with(Sort.by(Sort.Direction.ASC, "area")).with(Sort.by(Sort.Direction.ASC, "repository")).with(Sort.by(Sort.Direction.ASC, "name"));
        return mongoTemplate.find(
                query,
                Project.class);
    }

    @Override
    public List<String> getDistinctProjects() throws Exception {
        return mongoTemplate.findDistinct("name",Project.class, java.lang.String.class);
    }

    @Override
    public Project getByName(String name) throws Exception {
        String search = "^" + name + "$";
        return mongoTemplate.findOne(
                Query.query( Criteria.where("name").regex(search, "i") ),
                Project.class);
    }
}
