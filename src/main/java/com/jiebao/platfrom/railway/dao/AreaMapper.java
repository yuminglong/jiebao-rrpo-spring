package com.jiebao.platfrom.railway.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.jiebao.platfrom.railway.domain.Area;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * @author yf
 */
public interface AreaMapper extends BaseMapper<Area> {
    //Mapper查询示例一
    //TODO: 可使用注解/xml配置来编写SQL语句

    @Select("SELECT * FROM rail_area")
    List<Area> getAreaList();
}
