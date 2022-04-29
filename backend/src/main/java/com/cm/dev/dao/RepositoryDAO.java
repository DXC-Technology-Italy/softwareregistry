package com.cm.dev.dao;

import com.cm.dev.domain.Area;
import com.cm.dev.domain.Repository;
import org.springframework.stereotype.Component;

import java.util.List;

public interface RepositoryDAO extends GenericDAO<Repository>{

    public List<Repository> getRepositoriesFromArea(String code);
    public List<String> getRepositoriesByKind(String kind);
    public List<String> getDistinctRepositories();
    public List<Repository> getAllOrdered(List<String> orderFields);
}
