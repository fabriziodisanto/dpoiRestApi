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

import com.amazonaws.services.dynamodbv2.AmazonDynamoDBClient;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapper;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBScanExpression;
import dpoi.serverless.scapping.configuration.DynamoDBConfiguration;
import dpoi.serverless.scapping.exception.DAOException;

import java.util.List;

import static dpoi.serverless.scapping.Utils.isEmpty;
import static dpoi.serverless.scapping.Utils.nextId;

/** DynamoDB implementation of the ScrapDAO interface. */
public class DynamoScrapDAO implements ScrapDAO {

    /* Credentials for the client come from the environment variables pre-configured by Lambda.
    These are tied to the Lambda function execution role. */
    private static final AmazonDynamoDBClient ddbClient = new AmazonDynamoDBClient();

    private static class InstanceHolder {
        private static final DynamoScrapDAO instance = new DynamoScrapDAO();
    }

    /**
     * Returns an initialized instance of the DDBUserDAO object. DAO objects should be retrieved through the DAOFactory
     * class
     *
     * @return An initialized instance of the DDBUserDAO object
     */
    public static DynamoScrapDAO getInstance() { return InstanceHolder.instance; }

    private DynamoScrapDAO() { }

    /** Queries DynamoDB to find a scrap by its id */
    @Override public Scrap getScrapById(String uuid) throws DAOException {
        if (isEmpty(uuid)) throw new DAOException("Cannot lookup null or empty scrap");
        return getMapper().load(Scrap.class, uuid);
    }

    /** Inserts a new row in the DynamoDB scrap table. */
    @Override public String createScrap(Scrap scrap) throws DAOException {
        if (isEmpty(scrap.getContent())) throw new DAOException("Cannot create scrap with empty content");

        if(isEmpty(scrap.getUuid())) scrap.setUuid(nextId());

        if (getScrapById(scrap.getUuid()) != null) {
            throw new DAOException("Scrap id must be unique");
        }

        getMapper().save(scrap);

        return scrap.getUuid();
    }

    /** List scraps in the data store */
    @Override public List<Scrap> getScraps(int l) {
        int limit = l;

        if (limit <= 0 || limit > DynamoDBConfiguration.SCAN_LIMIT)
            limit = DynamoDBConfiguration.SCAN_LIMIT;

        final DynamoDBScanExpression expression = new DynamoDBScanExpression();
        expression.setLimit(limit);
        return getMapper().scan(Scrap.class, expression);
    }

    /** Returns a DynamoDBMapper object initialized with the default DynamoDB client */
    private DynamoDBMapper getMapper() { return new DynamoDBMapper(ddbClient); }
}
