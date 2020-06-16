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
 * 通知公告
 *
 * @author yf
 */
@Data
@TableName("rail_inform")
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
public class Inform {

    @TableId(value = "id",type = IdType.UUID)
    private String id;

    @TableField(exist = false)
    private String key;

    private String title;

    private String content;

    private Date createTime;

    /**
     * 来源
     */
    private String period;

    private String createUser;

    private Integer status;

    /**
     * 上级ID
     */
    private String parentId;
}
