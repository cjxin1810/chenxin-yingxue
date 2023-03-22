package com.cjx.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.cjx.dto.CategoryDTO;
import com.cjx.dto.Result;
import com.cjx.entity.Category;
import com.cjx.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/category")
public class CategoryController {
    @Autowired
    private CategoryService categoryService;

    @GetMapping("/categories/{id}")
    public List<Category> getCategoryById(@PathVariable Integer id) throws ClassNotFoundException {
        /*System.out.println(categoryService);
        Category category = categoryService.getById(id);
        System.out.println(category);
        Result result = new Result();
        result.setId(category.getId());
        result.setName(category.getName());
        return result;*/
        LambdaQueryWrapper<Category> qw=new LambdaQueryWrapper<>();
//        qw.eq(Category::getId, id).or().eq(Category::getParentId, id).select(Category::getId, Category::getName);
        qw.eq(Category::getId, id).select(Category::getId, Category::getName);
        List<Category> list = categoryService.list(qw);
//        List<Category> categoryList = categoryService.getCategoryList(id);
        return list;
    }
    @GetMapping("/categories")
    public List<Category> getCategoryList(Integer id) {
        List<Category> categoryList = categoryService.getCategoryList();
        return categoryList;
    }
}
