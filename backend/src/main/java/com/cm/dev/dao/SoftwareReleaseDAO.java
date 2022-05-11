package com.cm.dev.dao;

import com.cm.dev.bean.DevelopmentItem;
import com.cm.dev.domain.SoftwareRelease;
import com.mongodb.MongoException;

/**
 * Interface for SoftwareRelease Data Access Objects. 
 * The GenericDAO interface already exposes basic CRUD operations
 */
public interface SoftwareReleaseDAO extends GenericDAO<DevelopmentItem> {
    SoftwareRelease getLosByBigCode(String bigCode) throws MongoException;

    void deleteLosByBigCode(String bigCode) throws MongoException;

    void createOrUpdateLos(SoftwareRelease softwareRelease) throws MongoException;

    void createOrUpdateDeliveryCheck(SoftwareRelease softwareRelease) throws MongoException;
}
