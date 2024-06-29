package com.gpbitfactory.bot.api;

import com.gpbitfactory.bot.api.service.UserService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.client.ClientHttpRequestFactory;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.web.client.RestClient;

import java.time.Duration;

@Configuration
public class ApiConfig {

    @Bean(name = "middleServiceRestClient")
    public RestClient restClient(@Value("${middleService.url}") String backUrl) {
        return RestClient.builder()
                .baseUrl(backUrl)
                .defaultHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                .requestFactory(clientHttpRequestFactory())
                .build();
    }

    @Bean
    public ClientHttpRequestFactory clientHttpRequestFactory() {
        HttpComponentsClientHttpRequestFactory httpRequestFactory = new HttpComponentsClientHttpRequestFactory();
        httpRequestFactory.setConnectTimeout(Duration.ofSeconds(9));
        httpRequestFactory.setConnectionRequestTimeout(9);
        return httpRequestFactory;
    }

    @Bean
    public UserService userService(@Value("${middleService.url}") String url) {
        return new UserService(restClient(url));
    }
}
