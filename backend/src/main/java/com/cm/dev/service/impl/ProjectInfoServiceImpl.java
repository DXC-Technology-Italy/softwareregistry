package com.cm.dev.service.impl;

import com.cm.dev.dao.ProjectInfoDAO;
import com.cm.dev.domain.ProjectInfo;
import com.cm.dev.exception.ServiceException;
import com.cm.dev.service.ProjectInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;


/**
 * Business Logic for ProjectInfo Objects
 * 
 */
@Service
public class ProjectInfoServiceImpl implements ProjectInfoService {
    @Autowired
    private ProjectInfoDAO projectInfoDAO;

    
    /** 
     * @return List<ProjectInfo>
     * @throws ServiceException
     */
    @Override
    public List<ProjectInfo> getAllProjectInfos() throws ServiceException {
        return projectInfoDAO.getAll();
    }

    
    /** 
     * @param projectInfo
     * @throws ServiceException
     */
    @Override
    public void findAndModifyProjectInfo(ProjectInfo projectInfo) throws ServiceException {


        projectInfoDAO.findAndModifyProjectInfo(projectInfo);
    }

    
    /** 
     * @param projectInfo
     * @throws ServiceException
     */
    @Override
    public void findAndModifyMavenCertified(ProjectInfo projectInfo) throws ServiceException {

        projectInfoDAO.findAndModifyMavenCertified(projectInfo);
    }


}
