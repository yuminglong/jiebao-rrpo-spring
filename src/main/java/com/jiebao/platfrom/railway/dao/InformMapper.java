package com.jiebao.platfrom.railway.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.jiebao.platfrom.demo.domain.Demo;
import com.jiebao.platfrom.railway.domain.Inform;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * @author yf
 */
public interface InformMapper extends BaseMapper<Inform> {
    //Mapper查询示例一
    //TODO: 可使用注解/xml配置来编写SQL语句
    @Select("SELECT * FROM rail_inform")
    List<Inform> getInformList();
}
