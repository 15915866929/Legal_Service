package com.core.event;

import org.springframework.context.ApplicationEvent;

/**
 * 实体对象更新后事件
 *
 * @author Yuko
 */
public class ModelPostUpdateEvent extends ApplicationEvent {
    private static final long serialVersionUID = -6977830489141933072L;
    private Class<?> clazz;
    
    public ModelPostUpdateEvent(Object source, Class<?> clazz) {
        super(source);
        this.clazz = clazz;
    }
    
    public Class<?> getClazz() {
        return this.clazz;
    }
}
