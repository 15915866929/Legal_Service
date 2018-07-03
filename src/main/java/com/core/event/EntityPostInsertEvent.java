package com.core.event;

import org.springframework.context.ApplicationEvent;

/**
 * 实体对象插入后事件
 *
 * @author Yuko
 */
public class EntityPostInsertEvent extends ApplicationEvent {
    private static final long serialVersionUID = 6947694159604199708L;
    private Class<?> clazz;
    
    public EntityPostInsertEvent(Object source, Class<?> clazz) {
        super(source);
        this.clazz = clazz;
    }
    
    public Class<?> getClazz() {
        return this.clazz;
    }
}
