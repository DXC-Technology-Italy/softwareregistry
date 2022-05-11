package com.cm.dev.service.impl;

import com.cm.dev.exception.ServiceException;
import com.cm.dev.service.DependencyFileService;
import com.cm.dev.service.PlainFileReaderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.HashMap;

/**
 * Business Logic for DependencyFile Objects
 * 
 */
@Service
public class DependencyFileServiceImpl implements DependencyFileService {

    @Autowired PlainFileReaderService service;

    
    /** 
     * @return HashMap<Integer, String>
     * @throws ServiceException
     * @throws IOException
     */
    @Override
    public HashMap<Integer, String> getAllLines() throws ServiceException, IOException {
        return (HashMap<Integer, String>) service.getDependenciesFileLines();
    }
}
