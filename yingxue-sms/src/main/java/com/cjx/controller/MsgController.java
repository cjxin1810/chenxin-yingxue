package com.cjx.controller;

import com.cjx.utils.RedisPrefix;

import com.cjx.utils.SmsUtil;
import com.cjx.vo.MsgVo;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.RandomStringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import java.util.concurrent.TimeUnit;
@RestController
@RequestMapping("/msg")
@Slf4j
public class MsgController {
   @Autowired
   private RedisTemplate<Object,Object> redisTemplate;
    @Autowired
    private SmsUtil smsUtil;
    @PostMapping("/captchas")
    public void sendMsg(@RequestBody MsgVo msgVO) {
        //一个手机号码有三次发短信的机会
        Integer count = (Integer) redisTemplate.opsForValue().get(RedisPrefix.PHONE_COUNT_KEY + msgVO.getPhone());
        if (count == null || count < 3) {
            //        1.生成四位验证码
            String varify = RandomStringUtils.randomNumeric(4);
            log.info("手机验证码：" + varify);
            //2.把手机号码和验证码放入到redis里面
            redisTemplate.opsForValue().set(RedisPrefix.PHONE_KEY + msgVO.getPhone(), varify, 5, TimeUnit.MINUTES);
            redisTemplate.opsForValue().increment(RedisPrefix.PHONE_COUNT_KEY + msgVO.getPhone());
            //3.使用发送手机短信的工具类进行手机验证码的发送
            //todo 开发环境需要把下面的打开
//            smsUtil.sendMsg(msgVO.getPhone(),varify);
           /* if(count==null){
                redisTemplate.expire(RedisPrefix.PHONE_COUNT_KEY + msgVO.getPhone(),24,TimeUnit.HOURS);
            }*/
        }/*else{
            throw new RuntimeException("手机发送次数超过三次，明天可以重试！！！");
        }*/

    }

}
