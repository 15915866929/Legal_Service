package com.core.util;

import org.springframework.core.io.Resource;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.core.type.classreading.CachingMetadataReaderFactory;
import org.springframework.core.type.classreading.MetadataReader;
import org.springframework.core.type.classreading.MetadataReaderFactory;

import java.io.IOException;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * class工具类
 *
 * @author lzt
 */
public final class ClassUtils {
    /**
     * 单例
     */
    private ClassUtils() {
    }
    
    /**
     * 从包package中获取所有的Class
     *
     * @param pkg 包名
     * @return
     * @throws IOException
     * @throws ClassNotFoundException
     */
    public static Set<Class<?>> getClasses(String pkg, boolean containsChildren) throws IOException {
        Set<Class<?>> classes = new HashSet<>();
        String pattern = "classpath*:" + org.springframework.util.ClassUtils.convertClassNameToResourcePath(pkg) + "/**/*.class";
        PathMatchingResourcePatternResolver resourcePatternResolver = new PathMatchingResourcePatternResolver();
        Resource[] resources = resourcePatternResolver.getResources(pattern);
        MetadataReaderFactory readerFactory = new CachingMetadataReaderFactory(resourcePatternResolver);
        for (Resource resource : resources) {
            MetadataReader reader = readerFactory.getMetadataReader(resource);
            String className = reader.getClassMetadata().getClassName();
            // 检查是否能检查到类,如果不能检查则不加入到类列表中
            try {
                Class<?> clazz = resourcePatternResolver.getClassLoader().loadClass(className);
                classes.add(clazz);
            } catch (Throwable e) {
            }
        }
        if (containsChildren) {
            return classes;
        } else {
            Set<Class<?>> cs = new HashSet<>();
            for (Class<?> c : classes) {
                if (StringUtils.equals(c.getPackage().getName(), pkg)) {
                    cs.add(c);
                }
            }
            return cs;
        }
    }
    
    /**
     * 通过反射,获得Field泛型参数的实际类型. 如: public Map<String, Buyer> names;
     *
     * @param field 字段
     * @return 如果不支持泛型，直接返回null
     */
    @SuppressWarnings("rawtypes")
    public static Class getFieldGenericType(Field field) {
        return ClassUtils.getFieldGenericType(field, 0);
    }
    
    /**
     * 通过反射,获得Field泛型参数的实际类型
     *
     * @param field 字段
     * @param index 泛型参数所在索引,从0开始.
     * @return 如果不支持泛型，直接返回null
     */
    @SuppressWarnings({"rawtypes"})
    public static Class getFieldGenericType(Field field, int index) {
        Type genericFieldType = field.getGenericType();
        if (genericFieldType instanceof ParameterizedType) {
            ParameterizedType aType = (ParameterizedType) genericFieldType;
            Type[] fieldArgTypes = aType.getActualTypeArguments();
            if ((index >= fieldArgTypes.length) || (index < 0)) {
                throw new RuntimeException("你输入的索引" + (index < 0 ? "不能小于0" : "超出了参数的总数"));
            }
            return (Class) fieldArgTypes[index];
        }
        return null;
    }
    
    /**
     * 通过反射,获得方法输入参数第一个输入参数的所有泛型参数的实际类型
     *
     * @param method 方法
     * @return 如果不支持泛型，直接返回空集合
     */
    @SuppressWarnings({"rawtypes"})
    public static List<Class> getMethodGenericParameterTypes(Method method) {
        return ClassUtils.getMethodGenericParameterTypes(method, 0);
    }
    
    /**
     * 通过反射,获得方法输入参数第index个输入参数的所有泛型参数的实际类型.
     *
     * @param method 需要反射的方法
     * @param index  第几个输入参数
     * @return 如果不支持泛型，直接返回空集合
     */
    @SuppressWarnings("rawtypes")
    public static List<Class> getMethodGenericParameterTypes(Method method, int index) {
        List<Class> results = new ArrayList<>();
        Type[] genericParameterTypes = method.getGenericParameterTypes();
        if ((index >= genericParameterTypes.length) || (index < 0)) {
            throw new RuntimeException("你输入的索引" + (index < 0 ? "不能小于0" : "超出了泛型参数的总数"));
        }
        Type genericParameterType = genericParameterTypes[index];
        if (genericParameterType instanceof ParameterizedType) {
            ParameterizedType aType = (ParameterizedType) genericParameterType;
            Type[] parameterArgTypes = aType.getActualTypeArguments();
            for (Type parameterArgType : parameterArgTypes) {
                Class parameterArgClass = (Class) parameterArgType;
                results.add(parameterArgClass);
            }
            return results;
        }
        return results;
    }
    
    /**
     * 通过反射,获得方法返回值第一个泛型参数的实际类型
     *
     * @param method 需要反射的方法
     * @return 如果输入的方法的返回值不支持泛型，直接返回null
     */
    @SuppressWarnings("rawtypes")
    public static Class getMethodGenericReturnType(Method method) {
        return ClassUtils.getMethodGenericReturnType(method, 0);
    }
    
    /**
     * 通过反射,获得方法返回值泛型参数的实际类型
     *
     * @param method 需要反射的方法
     * @param index  泛型参数所在索引,从0开始.
     * @return 如果输入的方法的返回值不支持泛型，直接返回null
     */
    @SuppressWarnings({"rawtypes"})
    public static Class getMethodGenericReturnType(Method method, int index) {
        Type returnType = method.getGenericReturnType();
        if (returnType instanceof ParameterizedType) {
            ParameterizedType type = (ParameterizedType) returnType;
            Type[] typeArguments = type.getActualTypeArguments();
            if ((index >= typeArguments.length) || (index < 0)) {
                throw new RuntimeException("你输入的索引" + (index < 0 ? "不能小于0" : "超出了泛型参数的总数"));
            }
            return (Class) typeArguments[index];
        }
        return null;
    }
    
    /**
     * 通过反射,获得指定类的父类的第一个泛型参数的实际类型.
     *
     * @param clazz 需要反射的类,该类必须继承泛型父类
     * @return 如果输入类的父类不支持泛型，直接返回 null
     */
    @SuppressWarnings("rawtypes")
    public static Class getSuperClassGenricType(Class clazz) {
        return ClassUtils.getSuperClassGenricType(clazz, 0);
    }
    
    /**
     * 通过反射,获得指定类的父类的泛型参数的实际类型.
     *
     * @param clazz 需要反射的类,该类必须继承泛型父类
     * @param index 泛型参数所在索引,从0开始.
     * @return 如果输入类的父类不支持泛型，直接返回null
     */
    @SuppressWarnings({"rawtypes"})
    public static Class getSuperClassGenricType(Class clazz, int index) {
        Type genType = clazz.getGenericSuperclass();
        if (!(genType instanceof ParameterizedType)) {
            return null;
        }
        Type[] params = ((ParameterizedType) genType).getActualTypeArguments();
        if ((index >= params.length) || (index < 0)) {
            throw new RuntimeException("你输入的索引" + (index < 0 ? "不能小于0" : "超出了泛型参数的总数"));
        }
        if (!(params[index] instanceof Class)) {
            return null;
        }
        return (Class) params[index];
    }
    
    /**
     * 获取输入类型的所有父类以及父接口
     *
     * @param clazz 输入类型
     * @return 父类以及父接口的集合
     */
    public static Set<Class<?>> getSupperClass(Class<?> clazz) {
        Set<Class<?>> set = new HashSet<>();
        ClassUtils.inner(set, clazz);
        set.add(clazz);
        return set;
    }
    
    /**
     * 递归获取父类与父接口
     *
     * @param set   返回的集合
     * @param clazz 输入类型
     * @return 父类以及父接口的集合
     */
    private static Set<Class<?>> inner(Set<Class<?>> set, Class<?> clazz) {
        Class<?>[] supperInterfaces = clazz.getInterfaces();// 获取父接口
        for (Class<?> supperInterface : supperInterfaces) {
            if (supperInterface != null) {
                set.add(supperInterface);
                ClassUtils.inner(set, supperInterface);
            }
        }
        Class<?> supperClass = clazz.getSuperclass(); // 获取父类
        if (supperClass != null) {
            set.add(supperClass);
            ClassUtils.inner(set, supperClass);
        }
        return set;
    }
}
