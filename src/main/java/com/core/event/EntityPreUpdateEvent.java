package com.core.event;

import org.springframework.context.ApplicationEvent;

/**
 * 实体对象更新前事件
 *
 * @author Yuko
 */
public class EntityPreUpdateEvent extends ApplicationEvent {
    
    private static final long serialVersionUID = 5128515698316527670L;
    private Class<?> clazz;
    /**
     * 是否落实最终落实到数据库,默认值为true
     */
    private boolean execute = true;
    
    public EntityPreUpdateEvent(Object source, Class<?> clazz) {
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
