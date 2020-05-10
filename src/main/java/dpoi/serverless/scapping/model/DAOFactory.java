/*
 * Copyright 2015 Amazon.com, Inc. or its affiliates. All Rights Reserved.
 *
 * Licensed under the Apache License, Version 2.0 (the "License"). You may not use this file except in compliance
 * with the License. A copy of the License is located at
 *
 * http://aws.amazon.com/apache2.0/
 *
 * or in the "license" file accompanying this file. This file is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES
 * OR CONDITIONS OF ANY KIND, either express or implied. See the License for the specific language governing permissions
 * and limitations under the License.
 */
package dpoi.serverless.scapping.model;

import dpoi.serverless.scapping.model.scrap.DynamoScrapDAO;
import dpoi.serverless.scapping.model.scrap.ScrapDAO;

/**
 * The DAO Factory object to abstract the implementation of DAO interfaces.
 */
public interface DAOFactory {

    /** Contains the implementations of the DAO objects. By default we only have a DynamoDB implementation */
    enum DAOType {
        DynamoDB
    }

    /** Returns the default ScrapDAO implementation */
    static ScrapDAO getScrapDAO() {
        return getScrapDAO(DAOType.DynamoDB);
    }

    /** Returns a ScrapDAO implementation */
    static ScrapDAO getScrapDAO(DAOType daoType) {
        ScrapDAO dao = null;
        if (daoType == DAOType.DynamoDB) dao = DynamoScrapDAO.getInstance();
        return dao;
    }
}
