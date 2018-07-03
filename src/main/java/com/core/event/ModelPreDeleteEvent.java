package com.core.event;

import org.springframework.context.ApplicationEvent;

/**
 * 实体对象删除前事件
 *
 * @author Yuko
 */
public class ModelPreDeleteEvent extends ApplicationEvent {
    
    private static final long serialVersionUID = 3818949223328124003L;
    private Class<?> clazz;
    /**
     * 是否落实最终落实到数据库,默认值为true
     */
    private boolean execute = true;
    
    public ModelPreDeleteEvent(Object source, Class<?> clazz) {
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
