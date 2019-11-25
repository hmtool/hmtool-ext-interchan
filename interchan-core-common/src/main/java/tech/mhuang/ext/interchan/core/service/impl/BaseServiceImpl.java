package tech.mhuang.ext.interchan.core.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import tech.mhuang.ext.interchan.core.mapper.BaseMapper;
import tech.mhuang.ext.interchan.core.service.BaseService;
import tech.mhuang.ext.interchan.protocol.InsertInto;
import tech.mhuang.ext.interchan.protocol.data.Page;

import java.io.Serializable;
import java.util.List;

/**
 * 通用service
 *
 * @author mhuang
 * @since 1.0.0
 */
public abstract class BaseServiceImpl<T extends Serializable, Id> implements BaseService<T, Id> {

    @Autowired
    private BaseMapper<T, Id> baseMapper;

    public void setBaseMapper(BaseMapper<T, Id> baseMapper) {
        this.baseMapper = baseMapper;
    }

    /**
     * 获取单个
     *
     * @param t 查询的实例对象
     * @return T
     */
    @Override
    public T get(T t) {
        return baseMapper.get(t);
    }

    /**
     * @param id 查询的主键id
     * @return T
     */
    @Override
    public T getById(Id id) {
        return baseMapper.getById(id);
    }

    /**
     * @param t 实例对象
     * @return 新增个数
     */
    @Override
    public int save(T t) {
        return baseMapper.save(t);
    }

    /**
     * @param t 实例对象
     * @return 修改个数
     */
    @Override
    public int update(T t) {
        return baseMapper.update(t);
    }

    @Override
    public int updateAll(T t) {
        return baseMapper.updateAll(t);
    }

    /**
     * 删除单个实例
     *
     * @param t 实例对象
     * @return 删除的个数
     */
    @Override
    public int remove(T t) {
        return baseMapper.remove(t);
    }

    /**
     * 删除单个实例
     *
     * @param id 删除实例的id
     * @return int 删除的个数
     */
    @Override
    public int remove(Id id) {
        return baseMapper.remove(id);
    }

    /**
     * 查询实例的总数
     *
     * @param t 查询实例
     * @return 查询的个数
     */
    @Override
    public int count(T t) {
        return baseMapper.count(t);
    }

    @Override
    public int insert(T t) {
        return baseMapper.insert(t);
    }

    @Override
    public List<T> page(Page<T> page) {
        return baseMapper.page(page);
    }

    @Override
    public int pageCount(Page<T> page) {
        return baseMapper.pageCount(page);
    }

    @Override
    public List<T> queryAll() {
        return baseMapper.queryAll();
    }

    @Override
    public List<T> query(T t) {
        return baseMapper.query(t);
    }

    @Override
    public int delete(Id id) {
        return baseMapper.delete(id);
    }

    @Override
    public int insertInto(InsertInto<Id> into) {
        return baseMapper.insertInto(into);
    }
}
