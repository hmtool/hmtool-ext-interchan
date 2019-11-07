package tech.mhuang.ext.interchan.core.service;

import tech.mhuang.ext.interchan.protocol.InsertInto;
import tech.mhuang.ext.interchan.protocol.data.Page;

import java.io.Serializable;
import java.util.List;

/**
 * 通用service层
 *
 * @author mhuang
 * @since 1.0.0
 */
public interface BaseService<T extends Serializable, Id> {

    /**
     * 获取单个实例
     *
     * @param t 查询的实例对象
     * @return T 实例
     */
    T get(T t);

    /**
     * 根据Id获取单个实例
     *
     * @param id 查询的主键id
     * @return T 实例
     */
    T getById(Id id);

    /**
     * 新增单个实例
     *
     * @param t 实例对象
     * @return int 新增个数
     */
    int save(T t);

    int insert(T t);

    /**
     * 修改单个实例
     *
     * @param t 实例对象
     * @return int 修改成功的个数
     */
    int update(T t);

    /**
     * 修改单个实例所有的字段
     *
     * @param t 修改的实例
     * @return 修改成功的个数
     */
    int updateAll(T t);

    int delete(Id id);

    /**
     * 删除单个实例
     *
     * @param t 实例对象
     * @return int 删除成功的个数
     */
    int remove(T t);

    /**
     * 删除单个实例
     *
     * @param id 删除的Id
     * @return int 删除的个数
     */
    int remove(Id id);

    /**
     * 查询条数
     *
     * @param t 查询的实例
     * @return int 查询的个数
     */
    int count(T t);

    public List<T> page(Page<T> page);

    public int pageCount(Page<T> page);

    List<T> queryAll();

    List<T> query(T t);

    int insertInto(InsertInto<Id> into);
}
