package com.cjx.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.extension.conditions.update.LambdaUpdateChainWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.cjx.entity.User;
import com.cjx.service.UserService;
import com.cjx.mapper.UserMapper;
import com.cjx.vo.MsgVo;
import org.apache.commons.lang3.ObjectUtils;
import org.springframework.stereotype.Service;

/**
* @author chenxin
* @description 针对表【user(用户)】的数据库操作Service实现
* @createDate 2023-03-20 19:40:43
*/
@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User>
    implements UserService{
    @Override
    public User updateUserById(MsgVo msgVo,Integer uid) {
        User user = new User();
        UpdateWrapper<User> queryWrapper=new UpdateWrapper<>();
        queryWrapper.set(ObjectUtils.isNotEmpty(msgVo.getPhone()), "phone", msgVo.getPhone())
                .set(ObjectUtils.isNotEmpty(msgVo.getName()), "name", msgVo.getName())
                .set(ObjectUtils.isNotEmpty(msgVo.getIntro()), "intro", msgVo.getIntro())
                .eq("id", uid);
        int update = baseMapper.update(null, queryWrapper);
        if (update > 0) {

             user = baseMapper.selectById(uid);
        }
        return user;
    }
}




