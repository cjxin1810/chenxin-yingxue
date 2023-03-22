package com.cjx.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.cjx.dto.CategoryDTO;
import com.cjx.entity.Category;
import com.cjx.service.CategoryService;
import com.cjx.mapper.CategoryMapper;
import org.apache.commons.lang3.ObjectUtils;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

/**
* @author chenxin
* @description 针对表【category(分类)】的数据库操作Service实现
* @createDate 2023-03-20 16:16:44
*/
@Service
public class CategoryServiceImpl extends ServiceImpl<CategoryMapper, Category>
    implements CategoryService{

    @Override
    public List<Category> getCategoryList() {
        LambdaQueryWrapper<Category> qw = new LambdaQueryWrapper<>();
        qw.isNull(Category::getParentId)
                //.eq(ObjectUtils.isNotEmpty(id),Category::getId,id)
                .select(Category::getId, Category::getName);

        List<Category> list = baseMapper.selectList(qw);
        //List<Integer> ids = list.stream().map(c -> c.getId()).collect(Collectors.toList());
        for (Category category : list) {
          /*  qw=new LambdaQueryWrapper<>();
            qw.eq(Category::getParentId, category.getId());
            List<Category> list1 = baseMapper.selectList(qw);
            category.setChildren(list1);*/
            category.setChildren(getChildren(category.getId()));
        }
        return list;
    }

    public List<Category> getChildren(Integer pid) {
        LambdaQueryWrapper<Category> qw = new LambdaQueryWrapper<>();
        qw.eq(Category::getParentId, pid).select(Category::getId, Category::getName);
        List<Category> list = baseMapper.selectList(qw);
        if (ObjectUtils.isEmpty(list)) {
            return list;
        }
        for (Category category : list) {
            category.setChildren(getChildren(category.getId()));
        }
        return list;
    }
}




