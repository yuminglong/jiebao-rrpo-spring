package com.jiebao.platfrom.railway.domain;


import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.util.Date;

/**
 * 文件表
 *
 * @author yf
 */
@Data
@TableName("rail_file")
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
public class Files {

    @TableId(value = "id",type = IdType.UUID)
    private String id;

    private String title;

    private Date createTime;

    private String url;

    /**
     * 来源
     */
    private String period;

    private String createUser;

    private Integer status;


}
