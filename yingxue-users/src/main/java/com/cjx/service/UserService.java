package com.cjx.service;

import com.cjx.entity.User;
import com.baomidou.mybatisplus.extension.service.IService;
import com.cjx.vo.MsgVo;

/**
* @author chenxin
* @description 针对表【user(用户)】的数据库操作Service
* @createDate 2023-03-20 19:40:43
*/
public interface UserService extends IService<User> {
    User updateUserById(MsgVo user, Integer id);
}
