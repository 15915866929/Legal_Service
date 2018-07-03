package com.core.event;

import org.springframework.context.ApplicationEvent;

/**
 * 实体对象更新后事件
 *
 * @author Yuko
 */
public class EntityPostUpdateEvent extends ApplicationEvent {
    private static final long serialVersionUID = -284992404132170068L;
    private Class<?> clazz;
    
    public EntityPostUpdateEvent(Object source, Class<?> clazz) {
        super(source);
        this.clazz = clazz;
    }
    
    public Class<?> getClazz() {
        return this.clazz;
    }
}
