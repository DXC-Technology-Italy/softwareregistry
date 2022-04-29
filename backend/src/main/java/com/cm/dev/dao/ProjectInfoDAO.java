package com.cm.dev.dao;

import com.cm.dev.domain.ProjectInfo;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;

public interface ProjectInfoDAO extends GenericDAO<ProjectInfo>{

    public ProjectInfo findAndModifyProjectInfo(ProjectInfo projectInfo);
    public ProjectInfo findAndModifyMavenCertified(ProjectInfo projectInfo);
}
