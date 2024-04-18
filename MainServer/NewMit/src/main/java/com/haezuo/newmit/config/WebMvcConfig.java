package com.haezuo.newmit.config;

import com.haezuo.newmit.config.handler.CustomHandlerInterceptor;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebMvcConfig implements WebMvcConfigurer {

    private final CustomHandlerInterceptor customHandlerInterceptor;

    public WebMvcConfig(CustomHandlerInterceptor customHandlerInterceptor) {
        this.customHandlerInterceptor = customHandlerInterceptor;
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(customHandlerInterceptor)
                .excludePathPatterns("/css/**", "/images/**", "/js/**");
    }

}