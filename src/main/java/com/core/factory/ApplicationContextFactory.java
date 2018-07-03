package com.core.factory;

import com.alibaba.druid.util.StringUtils;
import com.core.event.ApplicationStartupEvent;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Component;

/**
 * Application工厂
 *
 * @author lzt
 * @date 2017年10月11日
 */
@Component
@Slf4j
public class ApplicationContextFactory implements ApplicationListener<ContextRefreshedEvent> {
    
    /**
     * ApplicationContext
     */
    private static ApplicationContext applicationContext;
    @Value("${spring.application.name}")
    private String applicationName;
    @Value("${server.port}")
    private String applicationPort;
    
    /**
     * 获取ApplicationContext
     *
     * @return
     */
    public static ApplicationContext get() {
        if (applicationContext == null) {
            throw new RuntimeException("Spring容器未完成加载...");
        }
        return ApplicationContextFactory.applicationContext;
    }
    
    @Override
    public void onApplicationEvent(ContextRefreshedEvent event) {
        ApplicationContext applicationContext = event.getApplicationContext();
        if (!StringUtils.equalsIgnoreCase(applicationName + ":" + applicationPort, applicationContext.getId())) {
            return;
        }
        if (ApplicationContextFactory.applicationContext != null) {
            return;
        }
        ApplicationContextFactory.applicationContext = applicationContext;
        log.debug("成功接收ApplicationContext");
        // 发布启动完成的事件
        applicationContext.publishEvent(new ApplicationStartupEvent(applicationContext));
        log.debug("共扫描" + BeanFactory.getBeanNames().size() + "个SpringBean");
    }
    
}
