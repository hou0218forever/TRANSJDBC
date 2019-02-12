package com.hzb.base;

import org.apache.ibatis.session.SqlSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.CollectionUtils;

import java.lang.reflect.ParameterizedType;
import java.util.Collections;
import java.util.List;
import java.util.Map;

public abstract class BaseDao<T> {

    protected Logger logger = LoggerFactory.getLogger(getClass());

    /**
     * 添加
     */
    protected static final String INSERT = "insert";

    /**
     * 批量添加
     */
    protected static final String BATCH_INSERT = "batchInsert";

    /**
     * 删除
     */
    protected static final String DELETE = "delete";

    /**
     * 批量删除
     */
    protected static final String BATCH_DELETE = "batchDelete";

    /**
     * 更新
     */
    protected static final String UPDATE = "update";

    /**
     * 单个主键查询对象
     */
    protected static final String SELECT_BY_ID = "selectById";

    /**
     * 主键列表查询对象列表
     */
    protected static final String SELECT_BY_IDS = "selectByIds";

    /**
     * 列表条件查询
     */
    protected static final String SELECT_LIST = "selectList";

    protected static final String SELECT_ALL = "selectListAll";

    @Autowired
    protected SqlSession sqlSession;


    public final String nameSpace;

    @SuppressWarnings("unchecked")
    public BaseDao() {
        if (getClass().getGenericSuperclass() instanceof ParameterizedType) {
            nameSpace = ((Class<T>) ((ParameterizedType) getClass().getGenericSuperclass())
                    .getActualTypeArguments()[0]).getSimpleName();
        } else {
            //解决cglib实现aop时转换异常
            nameSpace = ((Class<T>) ((ParameterizedType) getClass().getSuperclass().getGenericSuperclass())
                    .getActualTypeArguments()[0]).getSimpleName();
        }
    }

    /**
     * sql语句的id
     *
     * @param id sql id
     * @return "nameSpace.id"
     */
    protected String sqlId(String id) {
        return nameSpace + "." + id;
    }


    /**
     * 添加对象
     *
     * @param t 范型对象
     * @return 增加记录数
     */
    public Integer insert(T t) {
        return sqlSession.insert(sqlId(INSERT), t);
    }

    /**
     * 批量添加对象
     *
     * @param ts 范型对象
     * @return 增加记录数
     */
    public Integer batchInsert(List<T> ts) {
        return sqlSession.insert(sqlId(BATCH_INSERT), ts);
    }


    /**
     * 删除
     *
     * @param id 主键
     * @return 删除记录数
     */
    public Integer delete(Long id) {
        return sqlSession.delete(sqlId(DELETE), id);
    }

    /**
     * 批量删除
     *
     * @param ids 主键列表
     * @return 删除记录数
     */
    public Integer batchDelete(List<Long> ids) {
        return sqlSession.delete(sqlId(BATCH_DELETE), ids);
    }


    /**
     * 更新对象
     *
     * @param t 范型对象
     * @return 更新记录数
     */
    public Integer update(T t) {
        return sqlSession.update(sqlId(UPDATE), t);
    }


    /**
     * 查询单个对象
     *
     * @param id 主键
     * @return 对象
     */
    public T selectById(Integer id) {
        return selectById(Long.valueOf(id));
    }

    /**
     * 查询单个对象
     *
     * @param id 主键
     * @return 对象
     */
    public T selectById(Long id) {
        return sqlSession.selectOne(sqlId(SELECT_BY_ID), id);
    }

    /**
     * 计数
     */
    protected static final String COUNT = "count";

    /**
     * 分页查询
     */
    protected static final String SELECT_PAGING = "selectPaging";

    /**
     * 根据 ids 批量查询 对象
     *
     * @param ids
     * @return
     */
    public List<T> selectByIds(List<Long> ids) {
        if (CollectionUtils.isEmpty(ids)) {
            return Collections.emptyList();
        }
        return sqlSession.selectList(sqlId(SELECT_BY_IDS), ids);
    }

    /**
     * 查询对象列表
     *
     * @param t 范型对象
     * @return 查询到的对象列表
     */
    public List<T> selectList(T t) {
        return sqlSession.selectList(sqlId(SELECT_LIST), t);
    }

    public List<T> selectListAll() {
        return sqlSession.selectList(sqlId(SELECT_ALL));
    }

}
