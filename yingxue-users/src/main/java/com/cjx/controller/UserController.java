package com.cjx.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.cjx.annotation.RequiredToken;
import com.cjx.entity.User;
import com.cjx.service.UserService;
import com.cjx.utils.ImageUtils;
import com.cjx.utils.RedisPrefix;
import com.cjx.vo.MsgVo;
import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

@RestController
@RequestMapping("/user")
public class UserController {
    @Autowired
    private UserService userService;
    @Autowired
    private RedisTemplate<String, Object> redisTemplate;
    @PostMapping("/tokens")
    public Map<String, String> login(@RequestBody MsgVo msgVo, HttpSession session) {
        Map<String, String> map = new HashMap<>();
        String sessionId = session.getId();
        String msg = (String) redisTemplate.opsForValue().get(RedisPrefix.PHONE_KEY + msgVo.getPhone());//获取验证码
        if (StringUtils.isEmpty(msg)) {
            throw new RuntimeException("手机号输入错误");
        } else if (!msg.equals(msgVo.getCaptcha())) {
            throw new RuntimeException("验证码输入错误");
        } else if (msg.equals(msgVo.getCaptcha())) {
            User user = userService.getOne(new LambdaQueryWrapper<User>()
                    .eq(User::getPhone, msgVo.getPhone())
            );
            if (!ObjectUtils.isEmpty(user)) {//不为空则表示登录功能
//                map.put("token", sessionId);
            } else {
                user = new User(null, msgVo.getPhone(), ImageUtils.getPhoto(), "暂未设置", msgVo.getPhone(), 1, "暂未绑定"
                        , 0, 0, 0, new Date(), null, null);
                userService.save(user);
            }
            map.put("token", sessionId);
            redisTemplate.opsForValue().set(RedisPrefix.TOKEN_KEY + sessionId, user, 30, TimeUnit.MINUTES);
            //user为空则表示注册
        }
        return map;
    }

    @GetMapping("/user")
    @RequiredToken
    public Map<String, Object> getByToken(HttpServletRequest request) {
        String token = (String) request.getAttribute("token");
        User o = (User) request.getAttribute("user");
        Map<String, Object> map = new HashMap<>();
        /*User o = (User) redisTemplate.opsForValue().get(RedisPrefix.TOKEN_KEY + token);
        if (ObjectUtils.isEmpty(o)) {
            throw new RuntimeException("token失效");
        }*/
        map.put("avatar", o.getAvatar());
        map.put("name", o.getName());
        map.put("intro", o.getIntro());
        map.put("phone", o.getPhone());
        map.put("phone_linked", o.getPhoneLinked());
        map.put("wechat_linked", o.getWechatLinked());
        return map;
    }

    @PatchMapping("/user")
    @RequiredToken
    public Map<String, Object> updateUser(@RequestBody MsgVo msgVo, HttpServletRequest request) {
        User user = (User) request.getAttribute("user");
        String token = (String) request.getAttribute("token");
        //User user = (User) redisTemplate.opsForValue().get(RedisPrefix.TOKEN_KEY + token);
        if (!ObjectUtils.isEmpty(msgVo.getPhone())) {
            String msg = (String) redisTemplate.opsForValue().get(RedisPrefix.PHONE_KEY + msgVo.getPhone());
            if (!StringUtils.equals(msg, msgVo.getCaptcha())) {
                throw new RuntimeException("验证码错误");
            }
            if (!user.getPhone().equals(msgVo.getPhone())) {
                redisTemplate.delete(RedisPrefix.PHONE_KEY + user.getPhone());
                redisTemplate.delete(RedisPrefix.PHONE_COUNT_KEY + user.getPhone());
            }
        }
        user = userService.updateUserById(msgVo, user.getId());
        redisTemplate.opsForValue().set(RedisPrefix.TOKEN_KEY + token, user, 30, TimeUnit.MINUTES);
        System.err.println(user);
        Map<String, Object> map = new HashMap<>();
        map.put("avatar", user.getAvatar());
        map.put("name", user.getName());
        map.put("intro", user.getIntro());
        map.put("intro", user.getIntro());
        map.put("phone_linked", user.getPhoneLinked());
        map.put("wechat_linked", user.getWechatLinked());
        return map;
    }

    @DeleteMapping("/tokens")
    @RequiredToken
    public void layout(HttpServletRequest request) {
        String token = (String) request.getAttribute("token");
        Boolean delete = redisTemplate.delete(RedisPrefix.TOKEN_KEY + token);
        if (!delete) {
            throw new RuntimeException("token失效laaaa");
        }
    }

   /* @GetMapping("/played")
    public */
}
