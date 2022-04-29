package com.cm.dev.dao.impl;

import com.cm.dev.dao.ProjectInfoDAO;
import com.cm.dev.domain.ProjectInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Repository;

import java.util.Collection;
import java.util.List;

@Repository
public class ProjectInfoDAOImpl implements ProjectInfoDAO {
    @Autowired
    private MongoTemplate mongoTemplate;

    @Override
    public ProjectInfo create(ProjectInfo entity) throws Exception {
        ProjectInfo projectInfo = mongoTemplate.insert(entity);
        return projectInfo;
    }

    @Override
    public Collection<ProjectInfo> createMany(List<ProjectInfo> entities) throws Exception {
        Collection<ProjectInfo> projectInfos = mongoTemplate.insertAll(entities);
        return projectInfos;
    }

    @Override
    public void delete(ProjectInfo entity) throws Exception {
        throw new Exception("Not implemented");
    }

    @Override
    public void deleteAll() throws Exception {
        throw new Exception("Not implemented");
    }

    @Override
    public ProjectInfo save(ProjectInfo entity) throws Exception {
        ProjectInfo projectInfo = mongoTemplate.save(entity);
        return projectInfo;
    }


    @Override
    public List<ProjectInfo> getAll() throws Exception {
        return mongoTemplate.findAll(ProjectInfo.class);
    }

    @Override
    public ProjectInfo findAndModifyProjectInfo(ProjectInfo projectInfo) {
        Query query = new Query(Criteria.where("name").is(projectInfo.getName()).and("repository").is(projectInfo.getRepository()));
        Update update = new Update();
        update.set("reusable", projectInfo.getReusable());
        update.set("description", projectInfo.getDescription());
        update.set("reusabilityNotes", projectInfo.getReusabilityNotes());
        update.set("projectType", projectInfo.getProjectType());
        update.set("mavenCertified", projectInfo.getMavenCertified());
        return mongoTemplate.findAndModify(query, update, ProjectInfo.class);
    }

    @Override
    public ProjectInfo findAndModifyMavenCertified(ProjectInfo projectInfo) {
        Query query = new Query(Criteria.where("name").is(projectInfo.getName()).and("repository").is(projectInfo.getRepository()));
        Update update = new Update();
        update.set("mavenCertified", projectInfo.getMavenCertified());
        return mongoTemplate.findAndModify(query, update, ProjectInfo.class);
    }
}
