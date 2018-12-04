package com.syx.sboot.service;

import com.syx.sboot.common.entity.DataEntity;
import com.syx.sboot.common.entity.Page;
import com.syx.sboot.mapper.MySqlBaseMapper;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

/**
 * Created by suyx on 2018/11/29 0029.
 */
public abstract class MySqlBaseService<T extends DataEntity<T>> {
    @Autowired
    private MySqlBaseMapper mySqlBaseMapper;

    public Page<T> findPageByCondition(T entity, Page<T> page, String condition){
        try {
            int total = mySqlBaseMapper.getCountByCondition(entity,condition);
            page.setCount(total);
            List<T> list = mySqlBaseMapper.getPageByParams(entity, page, condition);
            page.setList(list);
        }catch (Exception e){
            e.printStackTrace();
        }
        return page;
    }

}
