package com.cjx.service;

import com.cjx.dto.CategoryDTO;
import com.cjx.entity.Category;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
* @author chenxin
* @description 针对表【category(分类)】的数据库操作Service
* @createDate 2023-03-20 16:16:44
*/
public interface CategoryService extends IService<Category> {
    //多级分类展示
    List<Category> getCategoryList();
}
