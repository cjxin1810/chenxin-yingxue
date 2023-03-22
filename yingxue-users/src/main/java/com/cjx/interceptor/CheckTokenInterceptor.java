package com.cjx.interceptor;

import com.cjx.annotation.RequiredToken;
import com.cjx.entity.User;
import com.cjx.utils.RedisPrefix;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.concurrent.TimeUnit;

@Component
@Slf4j
public class CheckTokenInterceptor implements HandlerInterceptor {
    @Autowired
    private RedisTemplate<String,Object> redisTemplate;
    @Override
    public boolean preHandle(HttpServletRequest request,
                             HttpServletResponse response,
                             Object handler) throws Exception {
        if(handler instanceof HandlerMethod) {
            HandlerMethod handlerMethod = (HandlerMethod) handler;
            //1.获取当前请求方法上是否存在RequiredToken注解
            boolean requiredToken = handlerMethod.getMethod().isAnnotationPresent(RequiredToken.class);
            //2.存在该注解
            if (requiredToken) {
                //1.获取token信息
                String token = request.getParameter("token");
                log.info("当前传递的token为: {}", token);
                //2.拼接前缀-+
                String tokenKey = RedisPrefix.TOKEN_KEY + token;
                //3.根据tokenKey获取用户信息
                User o = (User) redisTemplate.opsForValue().get(tokenKey);
//                redisTemplate.opsForValue().set(tokenKey, 30, TimeUnit.MINUTES);
                if (o == null) throw new RuntimeException("提示: 令牌无效,无效token!");
                redisTemplate.expire(tokenKey, 30, TimeUnit.MINUTES);
                //4.存储到当前请求的上下文中
                request.setAttribute("token", token);
                request.setAttribute("user", o);
            }
        }
        //放行请求
        return true;
    }
}