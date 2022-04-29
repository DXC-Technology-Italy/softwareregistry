package com.cm.dev.dao;

import com.cm.dev.bean.DevelopmentItem;
import com.cm.dev.domain.SoftwareRelease;

public interface SoftwareReleaseDAO extends GenericDAO<DevelopmentItem> {
    public SoftwareRelease getLosByBigCode(String bigCode);

    public void DeleteLosByBigCode(String bigCode);

    public void CreateOrUpdateLos(SoftwareRelease softwareRelease);

    public void CreateOrUpdateDeliveryCheck(SoftwareRelease softwareRelease);
}
