package com.cm.dev.service.impl;

import com.cm.dev.dao.ProjectInfoDAO;
import com.cm.dev.domain.ProjectInfo;
import com.cm.dev.service.ProjectInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProjectInfoServiceImpl implements ProjectInfoService {
    @Autowired
    ProjectInfoDAO projectInfoDAO;

    @Override
    public List<ProjectInfo> getAllProjectInfos() throws Exception {
        return projectInfoDAO.getAll();
    }

    @Override
    public ProjectInfo findAndModifyProjectInfo(ProjectInfo projectInfo) throws Exception {


        return projectInfoDAO.findAndModifyProjectInfo(projectInfo);
    }

    @Override
    public ProjectInfo findAndModifyMavenCertified(ProjectInfo projectInfo) throws Exception {

        return projectInfoDAO.findAndModifyMavenCertified(projectInfo);
    }




    @Override
    public ProjectInfo update(ProjectInfo projectInfo) throws Exception {
        return projectInfoDAO.save(projectInfo);
    }


}
