package com.cm.dev.dao;

import com.cm.dev.domain.Dependency;

import java.util.List;

public interface DependencyDAO extends GenericDAO<Dependency> {

    public List<Dependency> getByName(String name) throws Exception;
    public List<Dependency> getByNameAndGroupId(String name, String groupId) throws Exception;

}