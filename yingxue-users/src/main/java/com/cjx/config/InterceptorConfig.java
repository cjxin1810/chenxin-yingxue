package com.cjx.config;

import com.cjx.interceptor.CheckTokenInterceptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class InterceptorConfig  implements WebMvcConfigurer {
    @Autowired
    private CheckTokenInterceptor checkTokenInterceptor;
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        //添加checkTokenInterceptor到注册机里面，该拦截器拦截所有的请求
        registry.addInterceptor(checkTokenInterceptor).addPathPatterns("/**");
    }
}