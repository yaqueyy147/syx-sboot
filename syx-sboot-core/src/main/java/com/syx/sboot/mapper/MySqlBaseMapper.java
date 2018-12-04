package com.syx.sboot.mapper;

import com.syx.sboot.common.entity.DataEntity;
import com.syx.sboot.common.entity.Page;
import org.apache.ibatis.annotations.InsertProvider;
import org.apache.ibatis.annotations.SelectProvider;

import java.util.List;

/**
 * Created by Administrator on 2018/11/28 0028.
 */
public interface MySqlBaseMapper<T extends DataEntity<T>> {

    @InsertProvider(type = MySqlBaseProvider.class, method = "insert")
    int insert(T entity);

    @SelectProvider(type = MySqlBaseProvider.class, method = "getOneById")
    T getOneById(T entity);
    @SelectProvider(type = MySqlBaseProvider.class, method = "getOneByCondition")
    T getOneByCondition(T entity, String condition);

    @SelectProvider(type = MySqlBaseProvider.class, method = "getAllByParams")
    List<T> getAllByParams(T entity);

    @SelectProvider(type = MySqlBaseProvider.class, method = "getListByCondition")
    List<T> getListByCondition(T entity,String condition);

    @SelectProvider(type = MySqlBaseProvider.class, method = "getCountByCondition")
    int getCountByCondition(T entity,String condition);

    @SelectProvider(type = MySqlBaseProvider.class, method = "getPageByParams")
    List<T> getPageByParams(T entity, Page<T> page, String condition);

    @SelectProvider(type = MySqlBaseProvider.class, method = "updateById")
    List<T> updateById(T entity);

    @SelectProvider(type = MySqlBaseProvider.class, method = "deleteById")
    List<T> deleteById(T entity);

    @SelectProvider(type = MySqlBaseProvider.class, method = "fakeDeleteById")
    List<T> fakeDeleteById(T entity);
}
