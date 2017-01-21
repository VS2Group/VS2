package de.hska.exablog.GUI.Config;

import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.simp.config.MessageBrokerRegistry;
import org.springframework.web.socket.config.annotation.AbstractWebSocketMessageBrokerConfigurer;
import org.springframework.web.socket.config.annotation.EnableWebSocketMessageBroker;
import org.springframework.web.socket.config.annotation.StompEndpointRegistry;

@Configuration
@EnableWebSocketMessageBroker
public class WebSocketConfig extends AbstractWebSocketMessageBrokerConfigurer {

	static private StompEndpointRegistry stompEndpointRegistry;

	public static StompEndpointRegistry getStompEndpointRegistry() {
		return stompEndpointRegistry;
	}

	@Override
	public void configureMessageBroker(MessageBrokerRegistry config) {
        config.enableSimpleBroker("/topic"); // für jeden Benutzer eine Warteschlange
        config.setApplicationDestinationPrefixes("/app");
    }

    @Override
    public void registerStompEndpoints(StompEndpointRegistry registry) {
		stompEndpointRegistry = registry;

		registry.addEndpoint("/mysocket").withSockJS(); // Für initiale Verbindung
		registry.addEndpoint("/stomp/new-post-request").withSockJS(); // für jede Funktionalität eine Warteschlange
		registry.addEndpoint("/stomp/search-attempt").withSockJS();
		registry.addEndpoint("/stomp/search-suggestion").withSockJS();
		registry.addEndpoint("/stomp/timeline-update").withSockJS();
	}

}