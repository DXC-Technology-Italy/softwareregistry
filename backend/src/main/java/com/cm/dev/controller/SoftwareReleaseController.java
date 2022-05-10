package com.cm.dev.controller;

import com.cm.dev.bean.DevelopmentItem;
import com.cm.dev.bean.Release;
import com.cm.dev.service.SoftwareReleaseService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;


/**
 * Controller for Software Release Objects
 * 
 */

@RestController
@CrossOrigin
public class SoftwareReleaseController {

    private static final Logger LOGGER = LoggerFactory.getLogger(SoftwareReleaseController.class);

    @Autowired
    private SoftwareReleaseService softwareReleaseService;

    
    /** 
     * @param bigcode
     * @return List<DevelopmentItem>
     */
    @PostMapping(value = "/los/{bigcode}")
    public List<DevelopmentItem> getLOS(@PathVariable(value = "bigcode") String bigcode) {
        List<DevelopmentItem> matches = new ArrayList<>();
        try {
            matches = softwareReleaseService.getLOS(bigcode);
        } catch (Exception e) {
            LOGGER.error(String.valueOf(e));
        }
        return matches;
    }

    
    /** 
     * @param bigcode
     * @return List<Release>
     */
    @PostMapping(value = "/deliverycheck/{bigcode}")
    public List<Release> getDeliveryCheck(@PathVariable(value = "bigcode") String bigcode) {
        List<Release> releases = new ArrayList<>();
        try {
            releases = softwareReleaseService.getDeliveryCheck(bigcode);
        } catch (Exception e) {
            LOGGER.error(String.valueOf(e));
        }
        return releases;
    }

}
