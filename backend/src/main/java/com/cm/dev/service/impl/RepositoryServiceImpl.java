package com.cm.dev.service.impl;

import com.cm.dev.dao.ProjectDAO;
import com.cm.dev.dao.RepositoryDAO;
import com.cm.dev.domain.Project;
import com.cm.dev.domain.Repository;
import com.cm.dev.exception.ServiceException;
import com.cm.dev.service.PlainFileReaderService;
import com.cm.dev.service.RepositoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;


/**
 * Business Logic for Repository Objects
 * 
 */
@Service
public class RepositoryServiceImpl implements RepositoryService {

    @Autowired
    private RepositoryDAO repositoryDAO;

    @Autowired
    private ProjectDAO projectDAO;

    @Autowired
    PlainFileReaderService plainFileReaderService;

    
    /** 
     * @return List<Repository>
     * @throws ServiceException
     */
    @Override
    public List<Repository> getAllRepositories() throws ServiceException {
        return repositoryDAO.getAll();
    }

    
    /** 
     * @param orderFields
     * @return List<Repository>
     * @throws ServiceException
     */
    @Override
    public List<Repository> getAllOrdered(List<String> orderFields) throws ServiceException {
        return repositoryDAO.getAllOrdered(orderFields);
    }

    
    /** 
     * @param code
     * @return List<Repository>
     * @throws ServiceException
     */
    @Override
    public List<Repository> getRepositoriesFromArea(String code) throws ServiceException {
        return repositoryDAO.getRepositoriesFromArea(code);
    }

    
    /** 
     * @param kind
     * @return List<String>
     * @throws ServiceException
     */
    @Override
    public List<String> getRepositoriesByKind(String kind) throws ServiceException {
        return repositoryDAO.getRepositoriesByKind(kind);
    }

    
    /** 
     * @throws ServiceException
     * @throws IOException
     */
    @Override
    public void reload() throws ServiceException, IOException {
        List<Repository> repositories = new ArrayList<>();

        for (Repository r : plainFileReaderService.getRepositories().values()) {

            List<Project> projects = projectDAO.getByRepository(r.getName());
            r.setProjects(projects);

            repositories.add(r);
        }
        repositoryDAO.deleteAll();
        repositoryDAO.createMany(repositories);
    }

    
    /** 
     * @return List<String>
     * @throws ServiceException
     */
    @Override
    public List<String> getDistinctRepositories() throws ServiceException {
        return repositoryDAO.getDistinctRepositories();
    }


}
