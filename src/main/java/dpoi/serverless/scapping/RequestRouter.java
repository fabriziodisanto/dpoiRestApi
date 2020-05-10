
/*
 * Licensed under the Apache License, Version 2.0 (the "License"). You may not use this file except in compliance
 * with the License. A copy of the License is located at
 * http://aws.amazon.com/apache2.0/
 */
package dpoi.serverless.scapping;

import dpoi.serverless.scapping.action.ApplicationAction;
import dpoi.serverless.scapping.exception.BadRequestException;
import dpoi.serverless.scapping.exception.InternalErrorException;
import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.LambdaLogger;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import org.apache.commons.io.IOUtils;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

/**
 * This class contains the main event handler for the Lambda function.
 */
public class RequestRouter {

    private RequestRouter() { }

    //~ Methods ......................................................................................................................................

    /**
     * The main Lambda function handler. Receives the request as an input stream, parses the json
     * and looks for the "action" property to decide where to route the request. The "body" property
     * of the incoming request is passed to the DemoAction implementation as a request body.
     *
     * @param   request   The InputStream for the incoming event. This should contain an "action"
     *                    and "body" properties. The action property should contain the namespaced
     *                    name of the class that should handle the invocation. The class should
     *                    implement the DemoAction interface. The body property should contain the
     *                    full request body for the action class.
     * @param   response  An OutputStream where the response returned by the action class is written
     * @param   context   The Lambda Context object
     *
     * @throws  BadRequestException     This Exception is thrown whenever parameters are missing
     *                                  from the request or the action class can't be found
     * @throws  InternalErrorException  This Exception is thrown when an internal error occurs, for
     *                                  example when the database is not accessible
     */
    public static void lambdaHandler(InputStream request, OutputStream response, Context context)
        throws BadRequestException, InternalErrorException
    {
        final LambdaLogger logger = context.getLogger();

        final JsonParser parser   = new JsonParser();
        final JsonObject parameters = getJsonInputObject(request, logger, parser);

        final ApplicationAction action = instantiateAction(logger, parameters.get("action").getAsString());

        JsonObject body = null;
        if (parameters.get("body") != null) body = parameters.get("body").getAsJsonObject();

        final String output = action.handle(body, context);

        try {
            IOUtils.write(output, response);
        }
        catch (final IOException e) {
            logger.log("Error while writing response\n" + e.getMessage());
            throw new InternalErrorException(e.getMessage());
        }
    }  // end method lambdaHandler

    private static ApplicationAction instantiateAction(LambdaLogger logger, String actionClass)
        throws InternalErrorException, BadRequestException
    {
        final ApplicationAction action;
        try {
            action = ApplicationAction.class.cast(Class.forName(actionClass).newInstance());
        }
        catch (final InstantiationException e) {
            logger.log("Error while instantiating action class\n" + e.getMessage());
            throw new InternalErrorException(e.getMessage());
        }
        catch (final IllegalAccessException e) {
            logger.log("Illegal access while instantiating action class\n" + e.getMessage());
            throw new InternalErrorException(e.getMessage());
        }
        catch (final ClassNotFoundException e) {
            logger.log("Action class could not be found\n" + e.getMessage());
            throw new InternalErrorException(e.getMessage());
        }

        if (action == null) {
            logger.log("Action class is null");
            throw new BadRequestException("Invalid action class");
        }

        return action;
    }

    private static JsonObject getJsonInputObject(InputStream request, LambdaLogger logger, JsonParser parser)
        throws InternalErrorException, BadRequestException
    {
        final JsonObject inputObj;
        try {
            inputObj = parser.parse(IOUtils.toString(request)).getAsJsonObject();
        }
        catch (final IOException e) {
            logger.log("Error while reading request\n" + e.getMessage());
            throw new InternalErrorException(e.getMessage());
        }

        if (inputObj == null || inputObj.get("action") == null || inputObj.get("action").getAsString().trim().equals("")) {
            logger.log("Invald inputObj, could not find action parameter");
            throw new BadRequestException("Could not find action value in request");
        }
        return inputObj;
    }
}  // end class RequestRouter