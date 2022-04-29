package com.cm.dev.service.impl;

import com.cm.dev.service.DependencyFileService;
import org.springframework.stereotype.Service;

import java.util.HashMap;

@Service
public class DependencyFileServiceImpl implements DependencyFileService {

    @Override
    public HashMap<Integer, String> getAllLines() throws Exception {
        return PlainFileReaderServiceImpl.getInstance().getDependenciesFileLines();
    }
}
