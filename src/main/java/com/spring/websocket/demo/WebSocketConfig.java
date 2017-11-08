package com.spring.websocket.demo;

import javax.servlet.ServletContext;

import org.eclipse.jetty.websocket.api.WebSocketBehavior;
import org.eclipse.jetty.websocket.api.WebSocketPolicy;
import org.eclipse.jetty.websocket.server.WebSocketServerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.context.ServletContextAware;
import org.springframework.web.socket.WebSocketHandler;
import org.springframework.web.socket.config.annotation.EnableWebSocket;
import org.springframework.web.socket.config.annotation.WebSocketConfigurer;
import org.springframework.web.socket.config.annotation.WebSocketHandlerRegistry;
import org.springframework.web.socket.server.jetty.JettyRequestUpgradeStrategy;
import org.springframework.web.socket.server.standard.ServletServerContainerFactoryBean;
import org.springframework.web.socket.server.support.DefaultHandshakeHandler;
import org.springframework.web.socket.server.support.HttpSessionHandshakeInterceptor;

/**
 * 
 * @author chenrenyuan
 *
 * 2017年11月8日下午2:48:30
 */
@Configuration
@EnableWebSocket
public class WebSocketConfig implements WebSocketConfigurer, ServletContextAware {

	ServletContext servletContext;

	public void registerWebSocketHandlers(WebSocketHandlerRegistry registry) {
		registry.addHandler(myHandler(), "/ws").addInterceptors(new HttpSessionHandshakeInterceptor())
				.setHandshakeHandler(handshakeHandler());
		//.withSockJS();
	}

	@Bean
	public WebSocketHandler myHandler() {
		return new MyWebSocketHandler();
	}

	/**
	 * jetty
	 * 
	 * @return
	 */
	@Bean
	public DefaultHandshakeHandler handshakeHandler() {
		WebSocketPolicy policy = new WebSocketPolicy(WebSocketBehavior.SERVER);
		policy.setInputBufferSize(8192);
		policy.setIdleTimeout(600000);
		return new DefaultHandshakeHandler(new JettyRequestUpgradeStrategy(new WebSocketServerFactory(this.servletContext, policy)));
	}

	@Override
	public void setServletContext(ServletContext servletContext) {
		// TODO Auto-generated method stub
		this.servletContext = servletContext;
	}

	/**
	 * Tomcat, WildFly, and GlassFish
	 * 
	 * @return
	 */
	@Bean
	public ServletServerContainerFactoryBean createWebSocketContainer() {
		ServletServerContainerFactoryBean container = new ServletServerContainerFactoryBean();
		container.setMaxTextMessageBufferSize(8192);
		container.setMaxBinaryMessageBufferSize(8192);
		return container;
	}
}