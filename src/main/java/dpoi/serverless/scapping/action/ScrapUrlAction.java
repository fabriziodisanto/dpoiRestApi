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
import dpoi.serverless.scapping.configuration.ExceptionMessages;
import dpoi.serverless.scapping.exception.BadRequestException;
import dpoi.serverless.scapping.exception.DAOException;
import dpoi.serverless.scapping.exception.InternalErrorException;
import dpoi.serverless.scapping.model.DAOFactory;
import dpoi.serverless.scapping.model.action.CreateScrapResponse;
import dpoi.serverless.scapping.model.action.ScrapUrlRequest;
import dpoi.serverless.scapping.model.scrap.Scrap;
import dpoi.serverless.scapping.model.scrap.ScrapDAO;
import dpoi.serverless.scapping.myScrapper.FravegaScrapper;
import dpoi.serverless.scapping.myScrapper.LaNacionScrapper;
import dpoi.serverless.scapping.myScrapper.model.NewsArticleSchema;
import dpoi.serverless.scapping.myScrapper.model.ProductSchema;

import java.util.Date;

import static dpoi.serverless.scapping.Utils.*;
import static dpoi.serverless.scapping.configuration.ExceptionMessages.EX_INVALID_INPUT;

/**
 * Action that scraps an url and posts result to 'scraps' table
 */
public class ScrapUrlAction implements ApplicationAction {

  public String handle(JsonObject request, Context lambdaContext)
          throws BadRequestException, InternalErrorException {
    final LambdaLogger logger = lambdaContext.getLogger();

    final ScrapUrlRequest input = getGson().fromJson(request, ScrapUrlRequest.class);

    if (input == null || isEmpty(input.getUrl())) {
      throw new BadRequestException(EX_INVALID_INPUT);
    }

    final ScrapDAO dao = DAOFactory.getScrapDAO();

    final Scrap scrap = new Scrap();

    scrap.setUuid(nextId());
    scrap.setDate(new Date());
    scrap.setContent(fetchStructuredData(input, logger));

    try {
      dao.createScrap(scrap);
    } catch (final DAOException e) {
      logger.log("Error while creating new scrap\n" + e.getMessage());
      throw new InternalErrorException(ExceptionMessages.EX_DAO_ERROR);
    }

    final CreateScrapResponse output = new CreateScrapResponse();
    output.setScapId(scrap.getUuid());

    return getGson().toJson(output);
  }

  private String fetchStructuredData(ScrapUrlRequest request, LambdaLogger logger) {
      String url = request.getUrl();
      if(url.contains("www.fravega.com")){
          ProductSchema schema = FravegaScrapper.scrapp(url);
          return schema.toString();
      }
      else if(url.contains("www.lanacion.com")){
          NewsArticleSchema schema = LaNacionScrapper.scrapp(url);
          if (schema != null) {
              return schema.toString();
          }
      }
      return null; // Return jsonld for given request
  }
}
