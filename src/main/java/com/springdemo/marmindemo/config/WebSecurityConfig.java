package com.springdemo.marmindemo.config;

import com.springdemo.marmindemo.security.JwtAuthenticationFilter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.web.filter.CorsFilter;


@Configuration
@EnableWebSecurity
@Slf4j
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class WebSecurityConfig{
    @Autowired
    protected JwtAuthenticationFilter jwtAuthenticationFilter;

    @Bean
    protected SecurityFilterChain configure(HttpSecurity http) throws Exception{
        //httpSecurityBuilder
        http.cors() //WebMvcConfig에서 이미 설정했으므로 기본 Cors설정
                .and().csrf().disable()//csrf는 사용하지 않으므로 disable
                .httpBasic().disable()//token을 사용하지 않으므로 disable
                .sessionManagement()//session기반이 아님을 선언
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                .authorizeRequests() // /와 /auth/**경로는 인증 안해도됨
                .antMatchers("/","/auth/**").permitAll()
                .anyRequest() //그외 모든 것은 인증해야됨
                .authenticated();
        //filter 등록
        //매 요청마다
        //CorsFilter 실행 후 에
        //jwtAuthenticatonFilter 실행
        return http.addFilterAfter(
                jwtAuthenticationFilter,
                CorsFilter.class
        ).build();
//        return http.build();
    }
}
