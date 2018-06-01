package com.example.service.template.boot;

import com.example.service.template.boot.config.JettyConfiguration;
import com.example.service.template.boot.config.Swagger2Config;
import com.example.service.template.boot.config.WebMvcConfig;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Import;

@Import({JettyConfiguration.class, WebMvcConfig.class, Swagger2Config.class})
public class BaseApplication {
    private static final Logger logger = LoggerFactory.getLogger(BaseApplication.class);
}
