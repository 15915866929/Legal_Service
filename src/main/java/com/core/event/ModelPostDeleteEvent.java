package com.core.event;

import org.springframework.context.ApplicationEvent;

/**
 * 实体对象删除后事件
 *
 * @author Yuko
 */
public class ModelPostDeleteEvent extends ApplicationEvent {
    
    private static final long serialVersionUID = 1327119519631981867L;
    private Class<?> clazz;
    
    public ModelPostDeleteEvent(Object source, Class<?> clazz) {
        super(source);
        this.clazz = clazz;
    }
    
    public Class<?> getClazz() {
        return this.clazz;
    }
}
