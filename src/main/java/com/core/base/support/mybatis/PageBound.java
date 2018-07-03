package com.core.base.support.mybatis;

import org.apache.ibatis.session.RowBounds;

/**
 * 自定义RowBounds
 */
public class PageBound extends RowBounds {
    /**
     * 偏移量
     */
    private int offset;
    /**
     * 限制量
     */
    private int limit;
    /**
     * 页码
     */
    private int pageNo;
    /**
     * 每页行数
     */
    private int pageSize;
    /**
     * 总页数
     */
    private long pagecount;
    /**
     * 总行数
     */
    private long totalcount;
    /**
     * 是否计算总行数
     */
    private Boolean checkTotal = true;
    
    /**
     * @param pageNo   页码
     * @param pageSize 每页行数
     */
    public PageBound(int pageNo, int pageSize) {
        this.pageNo = pageNo;
        this.pageSize = pageSize;
        this.offset = (pageNo - 1) * pageSize;
        this.limit = pageSize;
    }
    
    /**
     * 偏移量
     *
     * @return
     */
    @Override
    public int getOffset() {
        return offset;
    }
    
    /**
     * 限制量
     *
     * @return
     */
    @Override
    public int getLimit() {
        return limit;
    }
    
    /**
     * 总行数
     *
     * @return
     */
    public long getTotalcount() {
        return totalcount;
    }
    
    /**
     * 总行数
     *
     * @param totalcount
     */
    public void setTotalcount(long totalcount) {
        this.totalcount = totalcount;
    }
    
    /**
     * 页码
     *
     * @return
     */
    public int getPageNo() {
        return pageNo;
    }
    
    /**
     * 页码
     *
     * @param pageNo
     */
    public void setPageNo(int pageNo) {
        this.pageNo = pageNo;
    }
    
    /**
     * 每页行数
     *
     * @return
     */
    public int getPageSize() {
        return pageSize;
    }
    
    /**
     * 每页行数
     *
     * @param pageSize
     */
    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }
    
    /**
     * 总页数
     *
     * @return
     */
    public long getPagecount() {
        return pagecount;
    }
    
    /**
     * 总页数
     *
     * @param pagecount
     */
    public void setPagecount(long pagecount) {
        this.pagecount = pagecount;
    }
    
    /**
     * 是否计算总行数
     *
     * @return
     */
    public Boolean getCheckTotal() {
        return checkTotal;
    }
    
    /**
     * 是否计算总行数
     *
     * @param checkTotal
     */
    public void setCheckTotal(Boolean checkTotal) {
        if (checkTotal == null) {
            throw new NullPointerException();
        } else {
            this.checkTotal = checkTotal;
        }
    }
}