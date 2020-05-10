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
package dpoi.serverless.scapping.model.scrap;

import dpoi.serverless.scapping.exception.DAOException;

import java.util.List;

/**
 * This interface defines the methods required for an implementation of a ScrapsDAO object
 */
public interface ScrapDAO {

    /** Find a scrap by its uuid */
    Scrap getScrapById(String uuid) throws DAOException;

    /** Creates a new scrap in the data store */
    String createScrap(Scrap scrap) throws DAOException;

    /** List scraps in the data store */
    List<Scrap> getScraps(int limit);
}
