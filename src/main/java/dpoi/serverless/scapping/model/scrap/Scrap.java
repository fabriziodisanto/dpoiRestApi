
/*
 * Licensed under the Apache License, Version 2.0 (the "License"). You may not use this file except in compliance
 * with the License. A copy of the License is located at
 * http://aws.amazon.com/apache2.0/
 */
package dpoi.serverless.scapping.model.scrap;

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBAttribute;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBHashKey;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBIgnore;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBTable;
import com.google.gson.JsonObject;
import dpoi.serverless.scapping.configuration.DynamoDBConfiguration;

import java.util.Date;

import static dpoi.serverless.scapping.Utils.getGson;

/**
 * Scrap object model - this class is annotated to be used with the DynamoDB object mapper.
 */
@DynamoDBTable(tableName = DynamoDBConfiguration.SCRAPS_TABLE_NAME)
public class Scrap {

    //~ Instance Fields ..............................................................................................................................

    private String content;
    private Date   date;
    private String uuid;

    //~ Constructors .................................................................................................................................

    public Scrap() {}

    //~ Methods ......................................................................................................................................

    @DynamoDBHashKey(attributeName = "uuid")
    public String getUuid() { return uuid; }

    public void setUuid(String uuid) { this.uuid = uuid; }

    @DynamoDBAttribute(attributeName = "content")
    public String getContent() { return content; }

    public void setContent(String content) { this.content = content; }

    @DynamoDBAttribute(attributeName = "date")
    public Date getDate() { return date; }

    public void setDate(Date date) { this.date = date; }

    @DynamoDBIgnore public JsonObject getContentAsJson() { return getGson().fromJson(content, JsonObject.class); }
}
