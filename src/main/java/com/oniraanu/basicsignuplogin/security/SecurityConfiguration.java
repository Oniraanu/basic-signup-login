package com.oniraanu.basicsignuplogin.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity( prePostEnabled = true)
public class SecurityConfiguration {

    private UnAuthorizedEntryPoint unAuthorizedEntryPoint;

/*    public SecurityFilterChain filterChain (HttpSecurity http) throws Exception {
        return http.csrf(AbstractHttpConfigurer::disable)
                .authorizeRequests(auth -> {
                    auth.antMatchers("/api/v1/home", "api/v1/register").permitAll();
                })
                .httpBasic(Customizer.withDefaults())
                .build();
    }*/
@Bean
public SecurityFilterChain filterChain (HttpSecurity httpSecurity) throws Exception {
    httpSecurity
            .cors()
            .and()
            .csrf()
            .disable()
            .authorizeHttpRequests(authorize -> {
                try {
                    authorize.antMatchers("/", "/register").permitAll()
                            .anyRequest()
                            .authenticated()
                            .and()
                            .sessionManagement()
                            .sessionCreationPolicy(SessionCreationPolicy.STATELESS);
                } catch (Exception e) {
                    throw new RuntimeException(e);
                }
            });
        return httpSecurity.build();
    }


    @Bean
    public BCryptPasswordEncoder bCryptPasswordEncoder(){
    return new BCryptPasswordEncoder(7);
    }
}
