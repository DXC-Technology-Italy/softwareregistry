package com.cm.dev.service.impl;

import com.cm.dev.dao.AreaDAO;
import com.cm.dev.dao.ProjectDAO;
import com.cm.dev.dao.RepositoryDAO;
import com.cm.dev.domain.Area;
import com.cm.dev.domain.Project;
import com.cm.dev.domain.Repository;
import com.cm.dev.service.RepositoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class RepositoryServiceImpl implements RepositoryService {

    @Autowired
    private RepositoryDAO repositoryDAO;

    @Autowired
    private ProjectDAO projectDAO;

    @Autowired
    private AreaDAO areaDAO;

    @Override
    public List<Repository> getAllRepositories() throws Exception{
        return repositoryDAO.getAll();
    }

    @Override
    public List<Repository> getAllOrdered(List<String> orderFields) throws Exception{
        return repositoryDAO.getAllOrdered(orderFields);
    }

    @Override
    public List<Repository> getRepositoriesFromArea(String code) throws Exception {
        return repositoryDAO.getRepositoriesFromArea(code);
    }

    @Override
    public List<String> getRepositoriesByKind(String kind) throws Exception {
        return repositoryDAO.getRepositoriesByKind(kind);
    }

    @Override
    public void reload() throws Exception {
        List<Repository> repositories = new ArrayList<>();
        Area area = new Area();

        for( Repository r: PlainFileReaderServiceImpl.getInstance().getRepositories().values() ) {

            List<Project> projects = projectDAO.getByRepository(r.getName());
            r.setProjects(projects);

            repositories.add(r);
        }
        repositoryDAO.deleteAll();
        repositoryDAO.createMany(repositories);
    }

    @Override
    public List<String> getDistinctRepositories() throws Exception {
        return repositoryDAO.getDistinctRepositories();
    }


}
