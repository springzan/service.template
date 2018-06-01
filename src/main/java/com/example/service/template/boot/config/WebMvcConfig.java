package com.example.service.template.boot.config;

import com.alibaba.fastjson.serializer.SerializerFeature;
import com.alibaba.fastjson.support.config.FastJsonConfig;
import com.example.service.template.boot.api.CustomHttpMessageConverter;
import com.example.service.template.boot.api.sign.SignInterceptor;
import com.example.service.template.boot.api.user.UserIdentityConfiguration;
import com.example.service.template.boot.api.user.UserIdentityInterceptor;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

import java.util.List;

@Configuration
@Import({UserIdentityConfiguration.class})
public class WebMvcConfig extends WebMvcConfigurerAdapter {

    // 内部API, 不需要没有验签和用户身份认证策略
    public static final String API_PREFIX_VIP = "/vip";

    protected String[] whitePathList = new String[]{"/error", "/healthy_check","/swagger-resources/**", API_PREFIX_VIP};

    public String[] getWhitePathList() {
        return whitePathList;
    }

    public void setWhitePathList(String[] whitePathList) {
        this.whitePathList = whitePathList;
    }

    @Bean
    public CustomHttpMessageConverter customHttpMessageConverter(){
        CustomHttpMessageConverter c=new CustomHttpMessageConverter();
        FastJsonConfig config=new FastJsonConfig();
        config.setSerializerFeatures(SerializerFeature.DisableCircularReferenceDetect,SerializerFeature.WriteMapNullValue);
        c.setFastJsonConfig(config);
        return c;
    }

    @Override
    public void configureMessageConverters(List<HttpMessageConverter<?>> converters) {
        converters.add(customHttpMessageConverter());
        super.configureMessageConverters(converters);
    }

    @Bean
    public HandlerInterceptor signInterceptor(){
        return new SignInterceptor();
    }

    @Bean
    public HandlerInterceptor userIdentityInterceptor() {
        return new UserIdentityInterceptor();
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(signInterceptor())
                .excludePathPatterns(whitePathList);
        registry.addInterceptor(userIdentityInterceptor())
                .excludePathPatterns(whitePathList);
        super.addInterceptors(registry);
    }

}
