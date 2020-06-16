package com.jiebao.platfrom.railway.domain;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.util.Date;


/**
 * 地区表
 *
 * @author yf
 */
@Data
@TableName("rail_area")
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
public class Area {

    @TableId(value = "id",type = IdType.UUID)
    private String id;

    private String parentId;

    /**
     * 地区阶级1、市级 2、区级 3、街道或镇级
     */
    private Integer rank;

    /**
     * 邮编
     */
    private String areaCode;

    /**
     * 地区名
     */
    private String areaName;

    private String updateUser;


    private Date creatTime;




}
