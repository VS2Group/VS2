package de.hska.exablog.frontend.Config;

import org.springframework.context.annotation.Configuration;
import org.springframework.http.client.ClientHttpRequestFactory;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.web.client.RestTemplate;

/**
 * Created by Angelo on 04.12.2016.
 */
@Configuration
public class RestConfig {
	private static final String BASE = "http://localhost:8090";
	private RestTemplate restTemplate = new RestTemplate(getClientHttpRequestFactory());

	public static String getUrl(String relativeUrl) {
		return BASE + relativeUrl;
	}

	private ClientHttpRequestFactory getClientHttpRequestFactory() {
		int timeout = 5000;
		HttpComponentsClientHttpRequestFactory clientHttpRequestFactory = new HttpComponentsClientHttpRequestFactory();
		clientHttpRequestFactory.setConnectTimeout(timeout);
		return clientHttpRequestFactory;
	}


	public RestTemplate getRestTemplate() {
		return restTemplate;
	}
}
