package com.cjx.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import java.util.Date;
import lombok.Data;

/**
 * @TableName video
 */
@TableName(value ="video")
@Data
public class Video implements Serializable {
    private Integer id;

    private String title;

    private String intro;

    private Integer uid;

    private String cover;

    private String link;

    private Integer categoryId;

    private Date createdAt;

    private Date updatedAt;

    private Date deletedAt;

    private static final long serialVersionUID = 1L;
}