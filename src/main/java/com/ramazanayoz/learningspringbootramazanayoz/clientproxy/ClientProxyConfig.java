package com.ramazanayoz.learningspringbootramazanayoz.clientproxy;

import org.jboss.resteasy.client.jaxrs.ResteasyClient;
import org.jboss.resteasy.client.jaxrs.ResteasyClientBuilder;
import org.jboss.resteasy.client.jaxrs.ResteasyWebTarget;
import org.jboss.resteasy.client.jaxrs.internal.ResteasyClientBuilderImpl;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ClientProxyConfig {
	
	@Value("${users.api.url.v1}")
	private String userEndpointUrl;
	
	@Bean
	public IUserResourceV1 getIUserResourceV1() {
		IUserResourceV1 proxy;
		ResteasyClient client = new ResteasyClientBuilderImpl().build();
		ResteasyWebTarget target = client.target(userEndpointUrl);
		proxy = target.proxy(IUserResourceV1.class);
		return proxy;
	}
}
