package com.cm.dev;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

/**
 * Called on application start
 *  
 */
@Component
public class Init implements ApplicationListener<ApplicationReadyEvent> {

    private static final Logger LOGGER = LoggerFactory.getLogger(Init.class);
    
    /** 
     * @param applicationReadyEvent
     */
    @Override
    @EventListener(ApplicationReadyEvent.class)
    public void onApplicationEvent(ApplicationReadyEvent applicationReadyEvent) {
        try {
            applicationReadyEvent.getApplicationContext().getParent();
        } catch (Exception e) {
            LOGGER.error(String.valueOf(e));
        }


    }


}
