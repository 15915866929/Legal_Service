package com.core.base.service;

import com.baomidou.mybatisplus.entity.TableInfo;
import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.mapper.Wrapper;
import com.baomidou.mybatisplus.toolkit.TableInfoHelper;
import com.core.base.entity.IBaseModel;
import com.core.base.support.Condition;
import com.core.base.support.ConditionAdapter;
import com.core.base.support.Page;
import com.core.event.*;
import com.core.factory.ApplicationContextFactory;
import com.core.util.ClassUtils;
import org.apache.commons.beanutils.PropertyUtils;
import org.apache.ibatis.session.RowBounds;
import org.mybatis.spring.SqlSessionTemplate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.PostConstruct;
import java.io.Serializable;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Set;

/**
 * ModelService基类
 *
 * @param <M>
 * @param <T>
 * @author 何健锋
 */
@Transactional
public abstract class BaseModelService<M extends BaseMapper<T>, T extends IBaseModel> {
    /**
     * 日志记录器
     */
    protected final Logger log = LoggerFactory.getLogger(this.getClass());
    /**
     * mapper
     */
    @Autowired
    protected M baseMapper;
    /**
     * 当前的实体类型
     */
    private Class<?> entityClass;
    /**
     * 当前的mapper类型
     */
    private Class<?> mapperClass;
    /**
     * sqlSessionTemplate
     */
    @Autowired
    private SqlSessionTemplate sqlSessionTemplate;
    /**
     * 数据库反射信息
     */
    private TableInfo tableInfo;
    
    /**
     * 删除数据
     *
     * @param entitys
     */
    public void delete(Collection<T> entitys) {
        for (T entity : entitys) {
            this.delete(entity);
        }
    }
    
    /**
     * 删除数据
     *
     * @param entity
     */
    public void delete(T entity) {
        Object pkVal;
        try {
            pkVal = PropertyUtils.getProperty(entity, this.tableInfo.getKeyProperty());
        } catch (IllegalAccessException | InvocationTargetException | NoSuchMethodException e) {
            throw new RuntimeException(e);
        }
        if (pkVal == null) {
            throw new RuntimeException(entity.getClass().getName() + "主键属性值为空");
        }
        this.deleteById((Serializable) pkVal);
    }
    
    /**
     * 根据主键删除数据
     *
     * @param id
     */
    public void deleteById(Serializable id) {
        T obj = this.select(id);
        if (obj == null) {
            return;
        }
        ModelPreDeleteEvent event = new ModelPreDeleteEvent(obj, obj.getClass());
        ApplicationContextFactory.get().publishEvent(event);
        if (event.isExecute()) {
            this.baseMapper.deleteById(id);
            ApplicationContextFactory.get().publishEvent(new ModelPostDeleteEvent(obj, obj.getClass()));
        }
    }
    
    /**
     * 根据主键查询
     *
     * @param id 主键值
     * @return
     */
    public T select(Serializable id) {
        return this.baseMapper.selectById(id);
    }
    
    /**
     * 删除数据
     *
     * @param entitys
     */
    public void delete(T[] entitys) {
        for (T entity : entitys) {
            this.delete(entity);
        }
    }
    
    /**
     * 根据主键删除数据
     *
     * @param ids
     */
    public void deleteByIds(Collection<? extends Serializable> ids) {
        for (Serializable id : ids) {
            this.deleteById(id);
        }
    }
    
    /**
     * 根据主键删除数据
     *
     * @param ids
     */
    public void deleteByIds(Serializable[] ids) {
        for (Serializable id : ids) {
            this.deleteById(id);
        }
    }
    
    /**
     * 获取数据库映射信息
     *
     * @return
     */
    protected TableInfo getTableInfo() {
        return this.tableInfo;
    }
    
    /**
     * 初始化
     */
    @PostConstruct
    private void init() {
        Set<Class<?>> supperClass = ClassUtils.getSupperClass(this.getClass());
        // 找model
        Class<?> target = null;
        boolean flag = false;
        for (Class<?> clazz : supperClass) {
            Type genType = clazz.getGenericSuperclass();
            if (!(genType instanceof ParameterizedType)) {
                continue;
            }
            Type[] params = ((ParameterizedType) genType).getActualTypeArguments();
            for (int i = 0; i < params.length; i++) {
                Class<?> type = ClassUtils.getSuperClassGenricType(this.getClass(), i);
                if (ClassUtils.getSupperClass(type).contains(IBaseModel.class)) {
                    target = type;
                    flag = true;
                    break;
                }
            }
            if (flag) {
                break;
            }
        }
        this.entityClass = target;
        if (this.entityClass == null) {
            this.entityClass = this.setEntityClass();
        }
        if (this.entityClass == null) {
            throw new RuntimeException("无法找到对应的实体类型,建议你在" + this.getClass().getName() + "重写setEntityClass()并返回正确的类型");
        }
        this.tableInfo = TableInfoHelper.getTableInfo(target);
        if (this.tableInfo == null) {
            throw new RuntimeException("无法找到" + this.entityClass.getName() + "对应的数据库映射信息");
        }
        // 找mapper
        target = null;
        flag = false;
        for (Class<?> clazz : supperClass) {
            Type genType = clazz.getGenericSuperclass();
            if (!(genType instanceof ParameterizedType)) {
                continue;
            }
            Type[] params = ((ParameterizedType) genType).getActualTypeArguments();
            for (int i = 0; i < params.length; i++) {
                Class<?> type = ClassUtils.getSuperClassGenricType(this.getClass(), i);
                if (ClassUtils.getSupperClass(type).contains(BaseMapper.class)) {
                    target = type;
                    flag = true;
                    break;
                }
            }
            if (flag) {
                break;
            }
        }
        this.mapperClass = target;
        if (this.mapperClass == null) {
            this.mapperClass = this.setMapperClass();
        }
        if (this.mapperClass == null) {
            throw new RuntimeException("无法找到对应的Mapper类型,建议你在" + this.getClass().getName() + "重写setMapperClass()并返回正确的类型");
        }
    }
    
    /**
     * 强制设置实体类的类型
     *
     * @return
     */
    protected Class<?> setEntityClass() {
        return null;
    }
    
    /**
     * 强制设置Mapper的类型
     *
     * @return
     */
    protected Class<?> setMapperClass() {
        return null;
    }
    
    /**
     * 新增数据
     *
     * @param entitys
     */
    public void insert(Collection<T> entitys) {
        for (T entity : entitys) {
            this.insert(entity);
        }
    }
    
    /**
     * 新增数据
     *
     * @param entity
     */
    public void insert(T entity) {
        ModelPreInsertEvent event = new ModelPreInsertEvent(entity, entity.getClass());
        ApplicationContextFactory.get().publishEvent(event);
        if (event.isExecute()) {
            this.baseMapper.insert(entity);
            ApplicationContextFactory.get().publishEvent(new ModelPostInsertEvent(entity, entity.getClass()));
        }
    }
    
    /**
     * 新增数据
     *
     * @param entitys
     */
    public void insert(T[] entitys) {
        for (T entity : entitys) {
            this.insert(entity);
        }
    }
    
    /**
     * 新增或者修改数据
     *
     * @param entitys
     */
    public void insertOrUpdate(Collection<T> entitys) {
        for (T entity : entitys) {
            this.insertOrUpdate(entity);
        }
    }
    
    /**
     * 新增或者修改数据
     *
     * @param entity
     */
    public void insertOrUpdate(T entity) {
        Object pkVal;
        try {
            pkVal = PropertyUtils.getProperty(entity, this.tableInfo.getKeyProperty());
        } catch (IllegalAccessException | InvocationTargetException | NoSuchMethodException e) {
            throw new RuntimeException(e);
        }
        if (pkVal == null) { // 没有设置主键，进行新增操作
            this.insert(entity);
            return;
        }
        T obj = this.select((Serializable) pkVal);
        if (obj == null) { // 数据库中不存在数据，进行新增操作
            this.insert(entity);
            return;
        }
        this.updateById(entity);
    }
    
    /**
     * 保存数据
     *
     * @param entity
     */
    public void updateById(T entity) {
        Object pkVal;
        try {
            pkVal = PropertyUtils.getProperty(entity, this.tableInfo.getKeyProperty());
        } catch (IllegalAccessException | InvocationTargetException | NoSuchMethodException e) {
            throw new RuntimeException(e);
        }
        if (pkVal == null) {
            throw new RuntimeException(entity.getClass().getName() + "主键属性值为空");
        }
        T obj = this.select((Serializable) pkVal);
        if (obj == null) {
            return;
        }
        ModelPreUpdateEvent event = new ModelPreUpdateEvent(entity, entity.getClass());
        ApplicationContextFactory.get().publishEvent(event);
        if (event.isExecute()) {
            this.baseMapper.updateById(entity);
            ApplicationContextFactory.get().publishEvent(new ModelPostUpdateEvent(entity, entity.getClass()));
        }
    }
    
    /**
     * 新增或者修改数据
     *
     * @param entitys
     */
    public void insertOrUpdate(T[] entitys) {
        for (T entity : entitys) {
            this.insertOrUpdate(entity);
        }
    }
    
    /**
     * 查询所有的数据
     *
     * @return
     */
    public List<T> selectAll() {
        EntityWrapper<T> ew = new EntityWrapper<>();
        return this.baseMapper.selectList(ew);
    }
    
    /**
     * 根据查询条件获取对象集合
     *
     * @param condition 查询条件
     * @return
     */
    @SuppressWarnings({"unchecked", "rawtypes"})
    public List<T> selectAll(Condition condition) {
        Wrapper wrapper = ConditionAdapter.getWrapper(condition, (Class<T>) this.entityClass).isWhere(false);
        return this.baseMapper.selectList(wrapper);
    }
    
    /**
     * 按指定字段的值查找数据，返回一个唯一的对象
     *
     * @param column 字段名称
     * @param value  属性值
     * @return
     */
    public T selectByUniqueColumn(String column, Object value) {
        List<T> list = this.selectByColumn(column, value);
        if ((list == null) || (list.size() == 0)) {
            return null;
        } else if (list.size() > 1) {
            throw new RuntimeException("查询的字段属性对应的值不是一个唯一对象！");
        } else {
            return list.get(0);
        }
    }
    
    /**
     * 按指定字段的值查找数据，返回一个list集合
     *
     * @param column 字段名称
     * @param value  属性值
     * @return
     */
    public List<T> selectByColumn(String column, Object value) {
        EntityWrapper<T> ew = new EntityWrapper<>();
        ew.eq(column, value);
        return this.baseMapper.selectList(ew);
    }
    
    /**
     * 获取分页数据
     *
     * @param condition 查询条件
     * @return
     */
    @SuppressWarnings("unchecked")
    public Page<T> selectPage(Condition condition) {
        int totalRecord = this.baseMapper.selectCount(ConditionAdapter.getWrapper(condition, false, (Class<T>) this.entityClass).isWhere(false));
        if (condition.getPageNo() <= 0) { // 页码必须大于等于1
            condition.setPageNo(Condition.PAGE_NO);
        }
        if (condition.getPageSize() <= 0) { // 记录数默认为25
            condition.setPageSize(Condition.PAGE_SIZE);
        }
        if (totalRecord <= 0) {
            return new Page<>(condition.getPageNo(), condition.getPageSize(), 0, new ArrayList<T>());
        }
        int start = (condition.getPageNo() - 1) * condition.getPageSize();
        if (start < 0) {
            start = 0;
        }
        if (start >= totalRecord) {
            return new Page<>(condition.getPageNo(), condition.getPageSize(), totalRecord, new ArrayList<T>());
        }
        RowBounds rowBounds = new RowBounds(start, condition.getPageSize());
        List<T> datas = this.baseMapper.selectPage(rowBounds, ConditionAdapter.getWrapper(condition, (Class<T>) this.entityClass).isWhere(false));
        return new Page<>(condition.getPageNo(), condition.getPageSize(), totalRecord, datas);
    }

    public <E> Page<E> selectPage(Condition condition, Class<E> clazz) {
        try {
            Page<T> page = this.selectPage(condition);
            List<T> entities = page.getEntities();
            List<E> retlist = new ArrayList<>();
            for (T entity : entities) {
                E e = clazz.newInstance();
                PropertyUtils.copyProperties(e, entity);
                retlist.add(e);
            }
            Page<E> ret = new Page<>(page.getPageNo(), page.getPageSize(), page.getEntityCount(), retlist);
            return ret;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * 修改数据
     *
     * @param entitys
     */
    public void updateById(Collection<T> entitys) {
        for (T entity : entitys) {
            this.updateById(entity);
        }
    }
    
    /**
     * 修改数据
     *
     * @param entitys
     */
    public void updateById(T[] entitys) {
        for (T entity : entitys) {
            this.updateById(entity);
        }
    }
}
