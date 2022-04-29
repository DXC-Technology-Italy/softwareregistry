package com.cm.dev.service;

import com.cm.dev.domain.ProjectInfo;

import java.util.List;

public interface ProjectInfoService {
    public List<ProjectInfo> getAllProjectInfos() throws Exception;
    public ProjectInfo findAndModifyProjectInfo(ProjectInfo projectInfo) throws Exception;
    public ProjectInfo findAndModifyMavenCertified(ProjectInfo projectInfo) throws Exception;
    public ProjectInfo update(ProjectInfo projectInfo) throws Exception;
}
