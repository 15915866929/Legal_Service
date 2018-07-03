package com.core.event;

import org.springframework.context.ApplicationEvent;

/**
 * 实体对象插入后事件
 *
 * @author Yuko
 */
public class ModelPostInsertEvent extends ApplicationEvent {
    private static final long serialVersionUID = 1519732077588289911L;
    private Class<?> clazz;
    
    public ModelPostInsertEvent(Object source, Class<?> clazz) {
        super(source);
        this.clazz = clazz;
    }
    
    public Class<?> getClazz() {
        return this.clazz;
    }
}
