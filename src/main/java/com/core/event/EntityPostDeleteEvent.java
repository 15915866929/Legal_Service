package com.core.event;

import org.springframework.context.ApplicationEvent;

/**
 * 实体对象删除后事件
 *
 * @author Yuko
 */
public class EntityPostDeleteEvent extends ApplicationEvent {
    
    private static final long serialVersionUID = 5332008017499449232L;
    private Class<?> clazz;
    
    public EntityPostDeleteEvent(Object source, Class<?> clazz) {
        super(source);
        this.clazz = clazz;
    }
    
    public Class<?> getClazz() {
        return this.clazz;
    }
}
