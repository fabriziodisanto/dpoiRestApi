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
package dpoi.serverless.scapping.action;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.LambdaLogger;
import com.google.gson.JsonObject;
import dpoi.serverless.scapping.configuration.DynamoDBConfiguration;
import dpoi.serverless.scapping.exception.BadRequestException;
import dpoi.serverless.scapping.exception.InternalErrorException;
import dpoi.serverless.scapping.model.DAOFactory;
import dpoi.serverless.scapping.model.action.ListScrapsResponse;
import dpoi.serverless.scapping.model.scrap.Scrap;
import dpoi.serverless.scapping.model.scrap.ScrapDAO;

import java.util.List;

import static dpoi.serverless.scapping.Utils.getGson;

/** Action to return a list of scraps from the data store */
public class ListScrapsAction implements ApplicationAction {

    public String handle(JsonObject request, Context lambdaContext) throws BadRequestException, InternalErrorException {
        final LambdaLogger logger = lambdaContext.getLogger();
        final ScrapDAO dao = DAOFactory.getScrapDAO();

        final List<Scrap> scraps = dao.getScraps(DynamoDBConfiguration.SCAN_LIMIT);

        final ListScrapsResponse output = new ListScrapsResponse();
        output.setCount(scraps.size());
        output.setPageLimit(DynamoDBConfiguration.SCAN_LIMIT);
        output.setScraps(scraps);

        return getGson().toJson(output);
    }
}
