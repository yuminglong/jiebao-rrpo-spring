package com.jiebao.platfrom.railway.domain;


import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @author yf
 */
@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class AreaTree<T> {
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

    private List<AreaTree<T>> children;

    private String key;

    private boolean hasParent = false;

    private boolean hasChildren = false;

    private String text;


    public void initChildren(){
        this.children = new ArrayList<>();
    }


}
