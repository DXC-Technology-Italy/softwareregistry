package com.cm.dev.service;

import com.cm.dev.domain.Area;
import com.cm.dev.exception.ServiceException;

import java.util.List;

/**
 * Interface that exposes methods to handle Areas business logic
 */
public interface AreaService {

    List<Area> getAllAreas() throws ServiceException;

    List<String> getDistinctAreas() throws ServiceException;


}
