package com.cm.dev.service.impl;

import com.cm.dev.dao.AreaDAO;
import com.cm.dev.domain.Area;
import com.cm.dev.service.AreaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AreaServiceImpl implements AreaService {

    @Autowired
    private AreaDAO areaDAO;

    @Override
    public List<Area> getAllAreas() throws Exception {
        return areaDAO.getAll();
    }

    @Override
    public Area getByCode(String code) throws Exception {
        return areaDAO.getByCode(code);
    }

    @Override
    public List<String> getDistinctAreas() throws Exception {
        return areaDAO.getDistinctAreas();
    }
}
