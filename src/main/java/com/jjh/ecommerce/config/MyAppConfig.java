package com.jjh.ecommerce.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

//컴포넌트 스캐닝 때 스캔 돼서 여기에 작성한 설정들이 적용될 수 있게 한다.
@Configuration
public class MyAppConfig implements WebMvcConfigurer {

    @Value("${allowed.origins}")
    private String[] theAllowedOrigin;

    @Value("${spring.data.rest.base-path}")
    private String basePath;

    @Override
    public void addCorsMappings(CorsRegistry cors) {
        //set up cors mapping
        cors.addMapping(basePath+"/**").allowedOrigins(theAllowedOrigin);
    }
}
