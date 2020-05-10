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
package dpoi.serverless.scapping.model.action;

import dpoi.serverless.scapping.model.scrap.Scrap;

import java.util.List;

/**
 * Bean for the list scraps response.
 */
public class ListScrapsResponse {

    private int count;
    private int pageLimit;
    private List<Scrap> scraps = null;

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public int getPageLimit() {
        return pageLimit;
    }

    public void setPageLimit(int pageLimit) {
        this.pageLimit = pageLimit;
    }

    public List<Scrap> getScraps() {
        return scraps;
    }

    public void setScraps(List<Scrap> scraps) {
        this.scraps = scraps;
    }
}
