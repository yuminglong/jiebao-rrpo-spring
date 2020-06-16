package com.jiebao.platfrom.railway.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.jiebao.platfrom.railway.domain.Address;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * @author yf
 */
public interface AddressMapper extends BaseMapper<Address> {

    //TODO: 可使用注解/xml配置来编写SQL语句

    /**
     * 查询根据部门ID所在的通讯录
     * @param parentsId 部门ID
     * @return List
     */
    //@Select("select * from rail_address r  join sys_dept d  on r.parents_id  = d.dept_id where r.parents_id = #{parentsId}")
    List<Address>  getAddressList(@Param("parentsId")String parentsId );



    /**
     * 删除部门时，其部门下的通讯录将移入公共通讯录
     * @param parentsId
     */
    void updateByIds(String parentsId);
}
