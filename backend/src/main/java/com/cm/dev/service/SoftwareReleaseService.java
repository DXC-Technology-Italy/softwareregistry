package com.cm.dev.service;

import com.cm.dev.bean.DevelopmentItem;
import com.cm.dev.bean.Release;
import com.cm.dev.exception.ServiceException;

import java.io.IOException;
import java.util.List;

/**
 * Interface that exposes methods to handle SoftwareReleases business logic
 */
public interface SoftwareReleaseService {

    List<DevelopmentItem> getLOS(String bigcode) throws ServiceException, IOException;

    List<Release> getDeliveryCheck(String bigCode) throws ServiceException, IOException;

}
