package com.cm.dev.controller;

import com.cm.dev.domain.Area;
import com.cm.dev.service.AreaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RestController
@CrossOrigin
public class AreaController {
    @Autowired
    private AreaService areaService;

    @RequestMapping("/area")
    public List<Area> getAllArea(){
        List<Area> areas = new ArrayList<>();
        try {
            areas = areaService.getAllAreas();
        } catch (Exception e) {
            System.out.println("Exception : " + e.getMessage() );
        } finally {
            return areas;
        }
    }

    @RequestMapping("/distinctAreas")
    public List<String> getDistinctAreas(){
        List<String> codes = new ArrayList<>();
        try {
            codes = areaService.getDistinctAreas();
        } catch (Exception e) {
            System.out.println("Exception : " + e.getMessage() );
        } finally {
            return codes;
        }
    }
}
