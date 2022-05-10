package com.cm.dev.service;

import com.cm.dev.domain.ProjectInfo;
import com.cm.dev.exception.ServiceException;

import java.util.List;

/**
 * Interface that exposes methods to handle ProjectInfos business logic
 */
public interface ProjectInfoService {
    List<ProjectInfo> getAllProjectInfos() throws ServiceException;

    void findAndModifyProjectInfo(ProjectInfo projectInfo) throws ServiceException;
    void findAndModifyMavenCertified(ProjectInfo projectInfo) throws ServiceException;

}
