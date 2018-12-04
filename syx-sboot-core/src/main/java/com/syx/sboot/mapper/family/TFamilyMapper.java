package com.syx.sboot.mapper.family;

import com.syx.sboot.entity.TFamily;
import com.syx.sboot.mapper.MySqlBaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;
import java.util.Map;

/**
 * Created by suyx on 2018/11/23 0023.
 */
@Mapper
public interface TFamilyMapper extends MySqlBaseMapper<TFamily> {

    @Select("select * from t_family limit 0,5")
    public List<Map<String,Object>> getFamily();

}
