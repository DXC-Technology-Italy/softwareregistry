package com.cm.dev.service;

import com.cm.dev.exception.ServiceException;

import java.io.IOException;
import java.util.HashMap;

/**
 * Interface that exposes methods to handle DependencyFiles business logic
 */
public interface DependencyFileService {

    HashMap<Integer, String> getAllLines() throws ServiceException, IOException;

}
