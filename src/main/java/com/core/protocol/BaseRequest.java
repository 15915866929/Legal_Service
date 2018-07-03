package com.core.protocol;

import java.io.Serializable;

/**
 * 有分页才需要继承
 */
public class BaseRequest implements Serializable{

    private Integer page;
    private Integer limit;

    public Integer getPage() {
        return page;
    }

    public void setPage(Integer page) {
        this.page = page;
    }

    public Integer getLimit() {
        return limit;
    }

    public void setLimit(Integer limit) {
        this.limit = limit;
    }
}
