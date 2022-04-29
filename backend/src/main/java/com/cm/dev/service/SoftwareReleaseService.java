package com.cm.dev.service;

import com.cm.dev.bean.DevelopmentItem;
import com.cm.dev.bean.Release;

import java.util.List;

public interface SoftwareReleaseService {

    public List<DevelopmentItem> getLOS(String bigcode) throws Exception;

    public List<DevelopmentItem> getLosByBigCode(String bigCode) throws Exception;

    public List<Release> getDeliveryCheck(String bigCode) throws Exception;

}
