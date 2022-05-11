package com.cm.dev.service.impl;

import com.cm.dev.dao.DependencyDAO;
import com.cm.dev.domain.Dependency;
import com.cm.dev.exception.ServiceException;
import com.cm.dev.service.DependencyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Business Logic for Dependency Objects
 * 
 */
@Service
public class DependencyServiceImpl implements DependencyService {

    @Autowired
    private DependencyDAO dependencyDAO;

    
    /** 
     * @param name
     * @return List<Dependency>
     * @throws ServiceException
     */
    @Override
    public List<Dependency> getByName(String name) throws ServiceException {
        return dependencyDAO.getByName(name);
    }
}
