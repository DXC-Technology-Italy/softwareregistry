package com.cm.dev.dao;

import com.cm.dev.domain.Area;
import com.mongodb.MongoException;

import java.util.List;

/**
 * Interface for Area Data Access Objects. 
 * The GenericDAO interface already exposes basic CRUD operations
 */
public interface AreaDAO extends GenericDAO<Area> {

    Area getByCode(String code) throws MongoException;
    List<String> getDistinctAreas() throws MongoException ;
}