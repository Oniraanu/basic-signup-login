package com.oniraanu.basicsignuplogin.security;

import lombok.AllArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@AllArgsConstructor
@EnableWebSecurity
@EnableGlobalMethodSecurity( prePostEnabled = true)
public class SecurityConfiguration {

    private UnAuthorizedEntryPoint unAuthorizedEntryPoint;

    public SecurityFilterChain filter(HttpSecurity httpSecurity) throws Exception {
        httpSecurity
                .cors()
                .and()
                .csrf()
                .disable()
                .authorizeHttpRequests(authorize -> {
                    try {
                        authorize
                                .antMatchers("/api/v1/register/", "/api/v1/login")
                                .permitAll()
                                .anyRequest()
                                .authenticated()
                                .and()
                                .exceptionHandling()
                                .authenticationEntryPoint(unAuthorizedEntryPoint)
                                .and()
                                .sessionManagement()
                                .sessionCreationPolicy(SessionCreationPolicy.STATELESS);
                    } catch (Exception e) {
                        throw new RuntimeException(e);
                    }
                });
        httpSecurity.addFilterBefore(jwtAuthenticationFilterBean(), UsernamePasswordAuthenticationFilter.class);
        httpSecurity.addFilterBefore(exceptionHandlerFilterBean(), JwtAuthenticationFilter.class);
        return httpSecurity.build();
    }
        @Bean
        public JwtAuthenticationFilter jwtAuthenticationFilterBean(){
            return new JwtAuthenticationFilter();
        }
        @Bean
        public ExceptionHandlerFilter exceptionHandlerFilterBean(){
            return new ExceptionHandlerFilter();
        }
}
