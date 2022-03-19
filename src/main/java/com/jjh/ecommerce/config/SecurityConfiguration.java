package com.jjh.ecommerce.config;

import com.okta.spring.boot.oauth.Okta;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

@Configuration
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        // /api/orders를 위한 endpoint를 보호한다.
        http.authorizeRequests()
                .antMatchers("/api/orders/**")
                .authenticated()// only for authenticated users
                .and()
                .oauth2ResourceServer()
                .jwt();//for processing jwt bearer token

        //add CORS filters
        http.cors();

        //force a non-empty response body 401's to make the response more friendly
        Okta.configureResourceServer401ResponseBody(http);//pom.xml에 추가해준 okta dependency 추가로 사용 가능한 기능이다

    }
}
