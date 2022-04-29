package com.cm.dev.dao;

import com.cm.dev.domain.Area;

import java.util.List;


public interface AreaDAO extends GenericDAO<Area> {

    public Area getByCode(String code);
    public List<String> getDistinctAreas();
}