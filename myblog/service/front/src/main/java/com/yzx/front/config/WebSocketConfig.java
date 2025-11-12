package com.yzx.front.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.socket.server.standard.ServerEndpointExporter;

/**
 * @author yzx
 * @create 2023-01-13 10:44
 */
@Configuration
public class WebSocketConfig {
    @Bean
    //注入ServerEndpointExporter bean对象,自动注册使用了
    public ServerEndpointExporter serverEndpointExporter(){
        return new ServerEndpointExporter();
    }
}
