package com.example.app.configurations;

import com.example.app.filters.AuthorizationFilter;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration @EnableWebSecurity @RequiredArgsConstructor
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.csrf().disable();
        http.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);
        http.cors().configurationSource(request -> new CorsConfiguration().applyPermitDefaultValues());
        http.authorizeRequests().antMatchers(HttpMethod.DELETE, "/bookshelf/bookstorage/books/**").hasAuthority("ADMIN");
        http.authorizeRequests().antMatchers(HttpMethod.DELETE, "/bookshelf/bookstorage/authors/**").hasAuthority("ADMIN");
        http.authorizeRequests().antMatchers(HttpMethod.GET, "/bookshelf/bookstorage/books").permitAll();
        http.authorizeRequests().antMatchers("/bookshelf/bookstorage/books/**").hasAnyAuthority("USER", "ADMIN");
        http.authorizeRequests().antMatchers("/bookshelf/bookstorage/authors/**").hasAnyAuthority("USER", "ADMIN");
        http.addFilterBefore(new AuthorizationFilter(), BasicAuthenticationFilter.class);
    }

    @Bean
    public WebMvcConfigurer corsConfigurer() {
        return new WebMvcConfigurer() {
            @Override
            public void addCorsMappings(CorsRegistry registry) {
                registry.addMapping("/**")
                        .allowedOrigins("*")
                        .allowedMethods("GET", "PUT", "POST", "PATCH", "DELETE", "OPTIONS");
            }
        };
    }
}
