package com.cm.dev.service;

import com.cm.dev.domain.Dependency;
import com.cm.dev.domain.Project;

import java.util.List;

public interface DependencyService {

    public List<Dependency> getByName(String name) throws Exception;

}
