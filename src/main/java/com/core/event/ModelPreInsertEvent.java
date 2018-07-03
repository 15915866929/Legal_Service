package com.core.event;

import org.springframework.context.ApplicationEvent;

/**
 * 实体对象插入前事件
 *
 * @author Yuko
 */
public class ModelPreInsertEvent extends ApplicationEvent {
    
    private static final long serialVersionUID = 1544487771051360856L;
    private Class<?> clazz;
    /**
     * 是否落实最终落实到数据库,默认值为true
     */
    private boolean execute = true;
    
    public ModelPreInsertEvent(Object source, Class<?> clazz) {
        super(source);
        this.clazz = clazz;
    }
    
    public Class<?> getClazz() {
        return this.clazz;
    }
    
    /**
     * 是否落实最终落实到数据库,默认值为true
     *
     * @return
     */
    public boolean isExecute() {
        return this.execute;
    }
    
    /**
     * 是否落实最终落实到数据库,默认值为true
     *
     * @param execute
     */
    public void setExecute(boolean execute) {
        this.execute = execute;
    }
    
}
