package com.core.event;

import org.springframework.context.ApplicationEvent;

/**
 * 应用程序启动完成时的事件
 *
 * @author lzt
 * @date 2017年10月25日
 */
public class ApplicationStartupEvent extends ApplicationEvent {
    private static final long serialVersionUID = 4541584828997315058L;
    
    public ApplicationStartupEvent(Object source) {
        super(source);
    }
    
}
