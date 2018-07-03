package com.core.factory;

import java.util.HashMap;
import java.util.Map;

/**
 * BeanFactory
 *
 * @author 梁湛桐
 */
public final class BeanFactory {
    /**
     * 此类为单例
     */
    private BeanFactory() {
    }
    
    /**
     * 获取spring bean
     *
     * @param beanName
     * @return spring bean
     */
    public static Object get(String beanName) {
        return ApplicationContextFactory.get().getBean(beanName);
    }
    
    /**
     * 获取spring bean
     *
     * @param <T>      spring bean class类型
     * @param beanName spring bean 的名称
     * @param clazz    spring bean class类型
     * @return spring bean
     */
    public static <T> T get(String beanName, Class<T> clazz) {
        return ApplicationContextFactory.get().getBean(beanName, clazz);
    }
    
    /**
     * 获取sping bean
     *
     * @param clazz
     * @param <T>
     * @return
     */
    public static <T> T get(Class<T> clazz) {
        return ApplicationContextFactory.get().getBean(clazz);
    }
    
    /**
     * 获取spring bean
     *
     * @param <T>   spring bean class类型
     * @param clazz spring bean class类型
     * @return spring bean
     */
    public static <T> Map<String, T> getBeans(Class<T> clazz) {
        return ApplicationContextFactory.get().getBeansOfType(clazz);
    }
    
    /**
     * 获取spring的所有bean以及bean对应的class
     *
     * @return spring的所有bean以及bean对应的class
     */
    public static Map<String, Class<?>> getBeanNames() {
        Map<String, Class<?>> map = new HashMap<>();
        for (String beanName : ApplicationContextFactory.get().getBeanDefinitionNames()) {
            map.put(beanName, ApplicationContextFactory.get().getType(beanName));
        }
        return map;
    }
    
}
