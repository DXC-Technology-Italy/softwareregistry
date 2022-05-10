package com.cm.dev.controller;

import com.cm.dev.domain.Area;
import com.cm.dev.service.AreaService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

/**
 * Controller for Area Objects
 * 
 */

@RestController
@CrossOrigin
public class AreaController {

    private static final Logger LOGGER = LoggerFactory.getLogger(AreaController.class);

    @Autowired
    private AreaService areaService;

    /**
     * Returns all Area from the collection
     *
     * @return List<Area>
     */
    @RequestMapping("/area")
    public List<Area> getAllArea() {
        List<Area> areas = new ArrayList<>();
        try {
            areas = areaService.getAllAreas();
        } catch (Exception e) {
            LOGGER.error(String.valueOf(e));
        }
        return areas;

    }
    /**
     * Returns all different area codes in the collection
     *
     * @return List<String>
     */
    @RequestMapping("/distinctAreas")
    public List<String> getDistinctAreas() {
        List<String> codes = new ArrayList<>();
        try {
            codes = areaService.getDistinctAreas();
        } catch (Exception e) {
            LOGGER.error(String.valueOf(e));
        }
        return codes;
    }
}
