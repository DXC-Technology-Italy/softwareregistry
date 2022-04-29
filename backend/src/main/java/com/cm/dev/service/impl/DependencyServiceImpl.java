package com.cm.dev.service.impl;

import com.cm.dev.dao.DependencyDAO;
import com.cm.dev.domain.Dependency;
import com.cm.dev.service.DependencyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DependencyServiceImpl implements DependencyService {

    @Autowired
    private DependencyDAO dependencyDAO;

    @Override
    public List<Dependency> getByName(String name) throws Exception {
        return dependencyDAO.getByName(name);
    }
}
