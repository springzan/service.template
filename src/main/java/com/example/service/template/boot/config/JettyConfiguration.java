package com.example.service.template.boot.config;

import org.eclipse.jetty.server.Slf4jRequestLog;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.context.embedded.EmbeddedServletContainerCustomizer;
import org.springframework.boot.context.embedded.jetty.JettyEmbeddedServletContainerFactory;
import org.springframework.boot.context.embedded.jetty.JettyServerCustomizer;
import org.springframework.context.annotation.Bean;

public class JettyConfiguration {
    private static final Logger logger = LoggerFactory.getLogger(JettyConfiguration.class);

    private static final JettyServerCustomizer USE_SLF4J_REQUEST_LOG =
            server -> server.setRequestLog(new Slf4jRequestLog() {{
                super.setPreferProxiedForAddress(true);
                super.setLogLatency(true);
                super.setLogServer(true);
                super.setLogTimeZone("GMT+8");
            }});

    @Bean
    public EmbeddedServletContainerCustomizer customizer() {
        return container -> {
            if (container instanceof JettyEmbeddedServletContainerFactory) {
                ((JettyEmbeddedServletContainerFactory) container)
                        .addServerCustomizers(USE_SLF4J_REQUEST_LOG);
            } else {
                throw new IllegalArgumentException(
                        "Expected a Jetty container factory but encountered "
                                + container.getClass());
            }
        };
    }
}
