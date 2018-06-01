package com.example.service.template.boot.api.user;

import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConditionalOnMissingBean(UserIdentityService.class)
public class UserIdentityConfiguration {

    @Bean
    public UserIdentityService userIdentityService(){
        return new DefaultUserIdentityServiceImpl();
    }
}
