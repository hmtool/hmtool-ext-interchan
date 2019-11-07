package tech.mhuang.ext.interchan.core.mapper;

import tech.mhuang.ext.interchan.protocol.InsertInto;
import tech.mhuang.ext.interchan.protocol.data.Page;

import java.io.Serializable;
import java.util.List;

/**
 * 公共Mapper层
 *
 * @author mhuang
 * @since 1.0.0
 */
public interface BaseMapper<T extends Serializable, Id> {

    /**
     * 获取单个实例
     *
     * @param t 查询的实例对象
     * @return T
     */
    T get(T t);

    /**
     * 根据Id获取单个实例
     *
     * @param id 查询的主键id
     * @return T
     */
    T getById(Id id);

    /**
     * 增单个实例
     *
     * @param t 实例对象
     * @return 新增个数
     */
    int save(T t);

    /**
     * 修改单个实例
     *
     * @param t 实例对象
     * @return 修改个数
     */
    int update(T t);

    /**
     * 修改单个实例所有字段
     *
     * @param t 实例对象
     * @return 修改个数
     */
    int updateAll(T t);

    /**
     * 删除单个实例
     *
     * @param t 实例对象
     * @return 删除的个数
     */
    int remove(T t);

    /**
     * 删除单个实例
     *
     * @param id 删除的Id
     * @return 删除的个数
     */
    int remove(Id id);

    /**
     * 删除
     *
     * @param t 删除实例
     * @return 删除的个数
     */
    int delete(T t);

    /**
     * 删除
     *
     * @param id 删除的id
     * @return 删除的个数
     */
    int delete(Id id);

    /**
     * 查询条数
     *
     * @param t 查询的实例
     * @return 查询的个数
     */
    int count(T t);

    /**
     * 根据实例进行查询
     *
     * @param t 实例
     * @return 查询出的列表
     */
    List<T> query(T t);

    /**
     * 查询所有实例
     *
     * @return 所有实例
     */
    List<T> queryAll();

    /**
     * 分页查询
     *
     * @param page 分页条件
     * @return 分页查询出的结果
     */
    List<T> page(Page<T> page);

    /**
     * 分页查询个数
     *
     * @param page 分页条件
     * @return 查询的个数
     */
    int pageCount(Page<T> page);

    /**
     * 历史表记录
     *
     * @param insertInto 记录历史表的条件
     * @return 记录后的条数
     */
    int insertInto(InsertInto<Id> insertInto);

    /**
     * 插入
     *
     * @param t 插入的实例
     * @return 插入的条数
     */
    int insert(T t);
}
