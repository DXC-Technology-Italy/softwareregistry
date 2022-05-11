package com.cm.dev.service.impl;

import com.cm.dev.dao.AreaDAO;
import com.cm.dev.domain.Area;
import com.cm.dev.exception.ServiceException;
import com.cm.dev.service.AreaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Business Logic for Area Objects
 * 
 */
@Service
public class AreaServiceImpl implements AreaService {

    @Autowired
    private AreaDAO areaDAO;

    
    /** 
     * @return List<Area>
     * @throws ServiceException
     */
    @Override
    public List<Area> getAllAreas() throws ServiceException {
        return areaDAO.getAll();
    }

    
    /** 
     * @return List<String>
     * @throws ServiceException
     */
    @Override
    public List<String> getDistinctAreas() throws ServiceException {
        return areaDAO.getDistinctAreas();
    }
}
