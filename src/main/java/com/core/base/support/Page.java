package com.core.base.support;

import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * 分页对象
 *
 * @author lzt 2013-8-15
 */
public class Page<T> {
    /**
     * 当前页数据
     */
    private final List<T> entities;
    /**
     * 总记录数
     */
    private final int entityCount;
    /**
     * 当前页码
     */
    private final int pageNo;
    /**
     * 每页大小
     */
    private final int pageSize;
    /**
     * 当前总页数
     */
    private final int pageCount;
    
    /**
     * 数据库分页
     *
     * @param pageNo      当前页码
     * @param pageSize    当前页最大的数据量
     * @param totalRecord 总共的数据条数
     * @param entities    当前页的数据
     */
    public Page(int pageNo, int pageSize, int totalRecord, List<T> entities) {
        if (pageSize <= 0) {
            throw new RuntimeException("当前页最大数据量必须大于0");
        }
        this.pageSize = pageSize; // 当前页最大的数据量
        this.entityCount = totalRecord; // 总共的数据条数
        this.entities = entities; // 当前页的数据
        if (pageNo < 1) { // 当前页码
            this.pageNo = 1;
        } else {
            this.pageNo = pageNo;
        }
        int pageCount = this.entityCount / this.pageSize; // 总页数
        if ((this.pageSize * pageCount) < this.entityCount) {
            pageCount++;
        }
        this.pageCount = pageCount;
    }
    
    /**
     * 程序分页
     *
     * @param pageNo   当前页码
     * @param pageSize 当前页最大的数据量
     * @param entities 所有页数据
     */
    public Page(int pageNo, int pageSize, List<T> entities) {
        if (pageSize <= 0) {
            throw new RuntimeException("当前页最大数据量必须大于0");
        }
        this.pageSize = pageSize; // 当前页最大的数据量
        this.entityCount = entities.size(); // 总共的数据条数
        if (pageNo < 1) { // 当前页码
            this.pageNo = 1;
        } else {
            this.pageNo = pageNo;
        }
        int pageCount = this.entityCount / this.pageSize; // 总页数
        if ((this.pageSize * pageCount) < this.entityCount) {
            pageCount++;
        }
        this.pageCount = pageCount;
        // 开始分割当前页数据
        int start = (this.pageNo - 1) * this.pageSize;
        int end = start + this.pageSize;
        if (end > this.entityCount) {
            end = this.entityCount;
        }
        List<T> list = new ArrayList<>(this.getPageSize());
        for (int i = start; i < end; i++) {
            if (i < entities.size()) {
                list.add(entities.get(i));
            }
        }
        this.entities = list;
    }
    
    /**
     * 当前页的最大数据量
     *
     * @return 当前页的最大数据量
     */
    public int getPageSize() {
        return this.pageSize;
    }
    
    /**
     * 当前页数据的迭代器
     *
     * @return 当前页数据的迭代器
     */
    public Iterator<T> iterator() {
        if (this.entities != null) {
            return this.entities.iterator();
        }
        return null;
    }
    
    @Override
    public String toString() {
        return MessageFormat.format("当前页:{0}\n是否第一页:{1},第一页页码:{9}\n是否有上一页:{2},上一页页码:{8}\n是否下一页:{3},下一页页码:{10}\n是否最后页:{4},最后页页码:{11}\n最大页:{5}\n总数据量:{6}\n当前页数据量:{7}", this.getPageNo(), this.getIsFirst() ? "是" : "否", this.getHasPrev() ? "有" : "无", this.getHasNext() ? "有" : "无", this.getIsLast() ? "是" : "否", this.getPageCount(), this.getEntityCount(), this.getEntitySize(), this.getPrevPageNo(), this.getFirstPageNo(), this.getNextPageNo(), this.getLastPageNo());
    }
    
    /**
     * 当前页页码
     *
     * @return 当前页页码
     */
    public int getPageNo() {
        return this.pageNo;
    }
    
    /**
     * 是否为第一页
     *
     * @return 是否为第一页
     */
    public boolean getIsFirst() {
        return this.pageNo <= 1;
    }
    
    /**
     * 是否有前一页
     *
     * @return 是否有前一页
     */
    public boolean getHasPrev() {
        return this.pageNo > 1;
    }
    
    /**
     * 是否有上一页
     *
     * @return 是否有上一页
     */
    public boolean getHasNext() {
        return this.pageNo < this.pageCount;
    }
    
    /**
     * 是否为最后一页
     *
     * @return 是否为最后一页
     */
    public boolean getIsLast() {
        return this.pageNo >= this.pageCount;
    }
    
    /**
     * 总页数
     *
     * @return 总页数
     */
    public int getPageCount() {
        return this.pageCount;
    }
    
    /**
     * 总记录数
     *
     * @return 总记录数
     */
    public int getEntityCount() {
        return this.entityCount;
    }
    
    /**
     * 获取当前页的数据量
     *
     * @return 获取当前页的数据量
     */
    public int getEntitySize() {
        return this.getEntities().size();
    }
    
    /**
     * 上一页页码
     *
     * @return 上一页页码
     */
    public int getPrevPageNo() {
        return this.pageNo - 1;
    }
    
    /**
     * 第一页页码
     *
     * @return 第一页页码
     */
    public int getFirstPageNo() {
        return 1;
    }
    
    /**
     * 下一页页码
     *
     * @return 下一页页码
     */
    public int getNextPageNo() {
        return this.pageNo + 1;
    }
    
    /**
     * 最后一页页码
     *
     * @return 最后一页页码
     */
    public int getLastPageNo() {
        return this.pageCount;
    }
    
    /**
     * 当前页数据
     *
     * @return 当前页数据
     */
    public List<T> getEntities() {
        return this.entities;
    }
}
