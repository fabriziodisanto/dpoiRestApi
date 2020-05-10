
/*
 * Licensed under the Apache License, Version 2.0 (the "License"). You may not use this file except in compliance
 * with the License. A copy of the License is located at
 * http://aws.amazon.com/apache2.0/
 */
package dpoi.serverless.scapping.model.action;

/**
 * Bean for url scrapping request.
 */
public class ScrapUrlRequest {

    //~ Instance Fields ..............................................................................................................................

    private String url = null;

    //~ Methods ......................................................................................................................................

    public String getUrl() { return url; }

    public void setUrl(String url) { this.url = url; }
}
