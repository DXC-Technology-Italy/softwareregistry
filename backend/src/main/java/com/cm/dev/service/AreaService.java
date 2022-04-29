package com.cm.dev.service;

import com.cm.dev.domain.Area;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;


public interface AreaService {

    public List<Area> getAllAreas() throws Exception;
    public Area getByCode(String code) throws Exception;
    public List<String> getDistinctAreas() throws Exception;;
}
