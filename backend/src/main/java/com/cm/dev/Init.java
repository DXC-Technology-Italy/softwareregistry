package com.cm.dev;

import com.cm.dev.service.DependencyFileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

@Component
public class Init implements ApplicationListener<ApplicationReadyEvent> {

    @Autowired
    private DependencyFileService lineService;

    @Override
    @EventListener(ApplicationReadyEvent.class)
    public void onApplicationEvent(ApplicationReadyEvent applicationReadyEvent) {
        try {
            if (applicationReadyEvent.getApplicationContext().getParent() == null) {

            }
        } catch (Exception e) {
            e.printStackTrace();
        }


    }


}
