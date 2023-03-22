package com.cjx.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;
import org.springframework.format.annotation.DateTimeFormat;

/**
 * @TableName user
 */
@TableName(value ="user")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Accessors(chain = true)
public class User implements Serializable {
    @TableId(type = IdType.AUTO)
    private Integer id;

    private String name;

    private String avatar;

    private String intro;

    private String phone;
    @JsonProperty("phone_linked")
    private Integer phoneLinked;
    private String openid;
    @JsonProperty("wechat_linked")
    private Integer wechatLinked;
    private Integer followingCount;

    private Integer followersCount;
    private Date createdAt;


    private Date updatedAt;

    private Date deletedAt;

    private static final long serialVersionUID = 1L;
}