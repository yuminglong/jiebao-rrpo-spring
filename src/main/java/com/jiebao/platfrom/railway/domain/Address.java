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
 * 通讯录
 *
 * @author yf
 */
@Data
@TableName("rail_address")
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
public class Address {

    @TableId(value = "id",type = IdType.UUID)
    private String id;

    private String parentsId;

    private Date creatTime;

    private String userName;

    private  String phone;

    private String telPhone;

    private String weiXin;

    private String email;

    /**
     * 序号
     */
    private Double numbers;

    @TableField(exist = false)
   // @JsonProperty("title")
    private String deptName;

    private String userId;

    /**
     * 状态1正常
     */
    private Integer status;

}
