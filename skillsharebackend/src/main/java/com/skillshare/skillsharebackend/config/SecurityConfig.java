package com.skillshare.skillsharebackend.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.io.File;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .cors(Customizer.withDefaults())
                .csrf(csrf -> csrf.disable())
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers(HttpMethod.OPTIONS, "/**").permitAll()
                        .requestMatchers("/uploads/**").permitAll() // 👈 Allow access to images
                        .requestMatchers("/api/posts/upload").permitAll()
                        .requestMatchers("/api/posts/all").permitAll()
                        .requestMatchers("/api/auth/send-code").permitAll()
                        .requestMatchers("/api/auth/reset-password").permitAll()
                        .requestMatchers("/api/auth/verify-code").permitAll()
                        .requestMatchers("/api/reactions").permitAll()
                        .requestMatchers("/api/comments").permitAll()
                        .requestMatchers("/api/comments/count").permitAll()
                        .requestMatchers("/api/comments/update/**").permitAll()
                        .requestMatchers("/api/comments/delete/**").permitAll()
                        .requestMatchers("/api/notifications").permitAll()
                        .requestMatchers("/api/notifications/unread-count").permitAll()
                        .requestMatchers("/api/notifications/mark-read").permitAll()
                        .requestMatchers("/api/follow").permitAll()
                        .requestMatchers("/api/follow/follow").permitAll()
                        .requestMatchers("/api/follow/following").permitAll()
                        .requestMatchers("/api/follow/followers").permitAll()
                        .requestMatchers("/api/follow/unfollow").permitAll()
                        .requestMatchers("api/user/search").permitAll()
                        .requestMatchers("api/user/profile").permitAll()
                        .requestMatchers("api/user/update").permitAll()
                        .requestMatchers("/api/posts/user").permitAll()
                        .requestMatchers("/api/posts/update").permitAll()
                        .requestMatchers("/api/posts/delete").permitAll()
                        .requestMatchers("/api/user/login", "/api/user/register", "/api/user/send-otp","/api/user/session","/login/oauth2/code/google",
                                "/oauth2/**").permitAll() // 👈 Add this!
                        .anyRequest().authenticated()
                )
                .sessionManagement(session -> session
                        .sessionCreationPolicy(SessionCreationPolicy.IF_REQUIRED)
                )
//                .oauth2Login(Customizer.withDefaults()) // 👈 Add this to enable OAuth2 login
                .oauth2Login(oauth2 -> oauth2
                        .defaultSuccessUrl("http://localhost:3000/home", true) // 👈 Redirect to React
                )
                .httpBasic(Customizer.withDefaults()); // Optional if using session login

        return http.build();
    }

    @Bean
    public WebMvcConfigurer corsConfigurer() {
        return new WebMvcConfigurer() {
            @Override
            public void addCorsMappings(CorsRegistry registry) {
                registry.addMapping("/api/**")
                        .allowedOrigins("http://localhost:3000")
                        .allowedMethods("GET", "POST", "PUT", "DELETE", "OPTIONS")
                        .allowedHeaders("*")
                        .allowCredentials(true);
            }

            @Override
            public void addResourceHandlers(ResourceHandlerRegistry registry) {
                String uploadPath = System.getProperty("user.dir") + File.separator + "uploads" + File.separator;
                registry.addResourceHandler("/uploads/**")
                        .addResourceLocations("file:" + uploadPath);
            }

        };
    }

//    public CorsConfigurationSource corsConfigurationSource() {
//        CorsConfiguration configuration = new CorsConfiguration();
//        configuration.setAllowCredentials(true);
//        configuration.addAllowedOrigin("http://localhost:3000");
//        configuration.addAllowedHeader("*");
//        configuration.addAllowedMethod("*");
//
//        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
//        source.registerCorsConfiguration("/**", configuration); // Match all endpoints
//
//        return source;
//    }
}

