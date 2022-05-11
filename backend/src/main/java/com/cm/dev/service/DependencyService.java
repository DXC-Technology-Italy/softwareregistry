package com.cm.dev.service;

import com.cm.dev.domain.Dependency;
import com.cm.dev.exception.ServiceException;

import java.util.List;

/**
 * Interface that exposes methods to handle Dependencies business logic
 */
public interface DependencyService {

    List<Dependency> getByName(String name) throws ServiceException;

}
