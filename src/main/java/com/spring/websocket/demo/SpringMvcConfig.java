package com.spring.websocket.demo;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

/**
 * 
 * @author chenrenyuan
 *
 * 2017年11月8日下午2:48:45
 */
@Import(WebSocketConfig.class)
@Configuration
@EnableWebMvc
@ComponentScan(basePackages={"com.spring.websocket.demo"})
public class SpringMvcConfig {

}
