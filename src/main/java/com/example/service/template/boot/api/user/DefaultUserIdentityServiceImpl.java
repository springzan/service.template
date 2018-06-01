package com.example.service.template.boot.api.user;

import com.ebt.rpcapi.user.UserService;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

public class DefaultUserIdentityServiceImpl implements UserIdentityService<UserIdentity>{
    private static final Logger logger = LoggerFactory.getLogger(DefaultUserIdentityServiceImpl.class);

    @Autowired
    private UserService userService;

    @Override
    public UserIdentity get(String token){
        int userId=userService.getUserIdByToken(token);
        return UserIdentity.build(String.valueOf(userId),token);
    }
}
