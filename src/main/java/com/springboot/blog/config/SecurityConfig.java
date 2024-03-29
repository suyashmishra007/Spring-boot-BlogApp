package com.springboot.blog.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
// this class become java based configuration , and within this java based configuration, we can define all the spring bean definations,// This annotation marks the class as a Java-based configuration for the Spring application. It means that this class will contain configuration settings for the application.
@EnableMethodSecurity
// used in Spring Security to enable method-level security. When you annotate a configuration class with @EnableMethodSecurity, it indicates that Spring Security should process method-level security annotations such as @PreAuthorize, @PostAuthorize, @Secured, etc., and enforce security rules accordingly.
public class SecurityConfig {

    private UserDetailsService userDetailsService;

    public SecurityConfig(UserDetailsService userDetailsService) {
        this.userDetailsService = userDetailsService;
    }

    @Bean
        // This annotation indicates that the method annotated with it will produce a bean to be managed by the Spring container.
    SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception { //  SecurityFilterChain : This method returns an instance of SecurityFilterChain, which is a filter chain that Spring Security uses to filter incoming HTTP requests based on defined security rules.
        http
                .csrf(csrf -> csrf.disable())
                .authorizeHttpRequests(authorize ->
                        authorize.requestMatchers(HttpMethod.GET, "/api/**").permitAll()
                                .requestMatchers("/api/auth/**").permitAll()
                )
                .httpBasic(Customizer.withDefaults());
        return http.build();
    }

    @Bean
    public AuthenticationManager authenticationManager(
            AuthenticationConfiguration configuration
    ) throws Exception {
        return configuration.getAuthenticationManager();
    }

    @Bean
    public static PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
