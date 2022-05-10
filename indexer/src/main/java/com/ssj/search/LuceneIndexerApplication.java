package com.ssj.search;

import com.ssj.search.service.IndexSearchService;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.beans.factory.annotation.Value;

@SpringBootApplication
public class LuceneIndexerApplication implements CommandLineRunner {
    private static final Logger logger = LoggerFactory.getLogger(LuceneIndexerApplication.class);

    @Autowired
    IndexSearchService service;
    
    @Value("${search.index.sourcesDirectory}")
    private String sourcesDirectory;

    
    /** 
     * @param args
     */
    public static void main(String[] args) {
        logger.info("STARTING THE APPLICATION");
        SpringApplication.run(LuceneIndexerApplication.class, args);
        logger.info("APPLICATION FINISHED");
    }

    
    /** 
     * @param args
     * @throws Exception
     */
    @Override
    public void run(String... args) throws Exception {
        logger.info("EXECUTING : command line runner");
        service.indexDirectory(sourcesDirectory);
    }
}
