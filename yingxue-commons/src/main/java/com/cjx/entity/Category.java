package com.cjx.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import java.util.Date;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

/**
 * @TableName category
 */
@TableName(value ="category")
@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Category implements Serializable {
    @TableId(type = IdType.AUTO)
    private Integer id;
    private String name;
    @TableField(exist = false)
    List<Category> children;
    private Integer parentId;
    private Date createdAt;
    private Date updatedAt;
    private Date deletedAt;

    private static final long serialVersionUID = 1L;
}