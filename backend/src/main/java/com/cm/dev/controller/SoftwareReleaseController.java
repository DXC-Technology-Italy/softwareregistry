package com.cm.dev.controller;

import com.cm.dev.bean.DevelopmentItem;
import com.cm.dev.bean.Release;
import com.cm.dev.bean.ReleaseItem;
import com.cm.dev.service.SoftwareReleaseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@CrossOrigin
public class SoftwareReleaseController {

    @Autowired
    private SoftwareReleaseService softwareReleaseService;

    @RequestMapping(value="/los/{bigcode}", method = RequestMethod.POST)
    public List<DevelopmentItem> getLOS(@PathVariable(value = "bigcode") String bigcode) {
        List<DevelopmentItem> matches = new ArrayList<>();
        try {
            matches = softwareReleaseService.getLOS(bigcode);
        } catch (Exception e) {
            System.out.println("Exception : " + e.getMessage());
        } finally {
            return matches;
        }
    }

    @RequestMapping("/los/{bigcode}/csv")
    public ResponseEntity<String> getLOSCSV(@PathVariable(value = "bigcode") String bigcode) {
        StringBuilder builder = new StringBuilder();
        String area = "area" + bigcode.split("-")[1];
        try {
            List<DevelopmentItem> matches = softwareReleaseService.getLOS(bigcode);
            builder.append("Nome File;Path;Repository;Area;Oggetto;Azione;Tipo Oggetto")
                    .append("\n");
            for (DevelopmentItem m : matches) {
                String action = "";
                if (m.getAction().equals("ADDED")) {
                    action = "Aggiunto";
                }
                if (m.getAction().equals("MODIFIED")) {
                    action = "Modificato";
                }
                if (m.getAction().equals("RENAMED")) {
                    action = "Spostato/Rinominato";
                }
                if (m.getAction().equals("DELETED")) {
                    action = "Cancellato";
                }
                builder.append(m.getName())
                        .append(";")
                        .append(m.getPath())
                        .append(";")
                        .append(m.getRepository())
                        .append(";")
                        .append(area)
                        .append(";")
                        .append(m.getRepository())
                        .append("/")
                        .append(m.getPath())
                        .append(m.getName())
                        .append(";")
                        .append(action)
                        .append(";")
                        .append(m.getExtension())
                        .append("\n");
            }
        } catch (Exception e) {
            System.out.println("Exception : " + e.getMessage());
        }
        return ResponseEntity.ok()
                .contentType(MediaType.TEXT_PLAIN)
                .header("attachment; filename=\"" + bigcode + ".csv\";")
                .body(builder.toString());
    }


    @RequestMapping(value = "/deliverycheck/{bigcode}", method = RequestMethod.POST)
    public List<Release> getDeliveryCheck(@PathVariable(value = "bigcode") String bigcode) {
        List<Release> releases = new ArrayList<>();
        try {
            releases = softwareReleaseService.getDeliveryCheck(bigcode);
        } catch (Exception e) {
            System.out.println("Exception : " + e.getMessage());
        } finally {
            return releases;
        }
    }


    @RequestMapping("/deliverycheck/{bigcode}/{i}/csv")
    public ResponseEntity<String> getDeliveryCheckCSV(@PathVariable(value = "bigcode") String bigcode, @PathVariable(value = "i") Integer index) {
        StringBuilder builder = new StringBuilder();
        try {
            List<Release> releases = softwareReleaseService.getDeliveryCheck(bigcode);
            builder.append("Nome File;Repository;Sha1;Path MEF").append("\n");
            for (ReleaseItem m : releases.get(index).getReleaseItems()) {
                builder.append(m.getFilename())
                        .append(";")
                        .append(m.getRepository())
                        .append(";")
                        .append(m.getSha1sum())
                        .append(";")
                        .append(m.getPathMef())
                        .append("\n");
            }
        } catch (Exception e) {
            System.out.println("Exception : " + e.getMessage());
        }
        return ResponseEntity.ok()
                .contentType(MediaType.TEXT_PLAIN)
                .header("attachment; filename=\"" + bigcode + ".csv\";")
                .body(builder.toString());
    }


}
