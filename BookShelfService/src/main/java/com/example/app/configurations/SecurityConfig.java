package com.example.app.configurations;

import com.example.app.filters.AuthorizationFilter;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

@Configuration @EnableWebSecurity @RequiredArgsConstructor
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.csrf().disable();
        http.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);
        http.authorizeRequests().antMatchers(HttpMethod.DELETE, "/bookshelf/bookstorage/books/**").hasAuthority("ADMIN");
        http.authorizeRequests().antMatchers(HttpMethod.DELETE, "/bookshelf/bookstorage/authors/**").hasAuthority("ADMIN");
        http.authorizeRequests().antMatchers("/bookshelf/bookstorage/books/**").hasAnyAuthority("USER", "ADMIN");
        http.authorizeRequests().antMatchers("/bookshelf/bookstorage/authors/**").hasAnyAuthority("USER", "ADMIN");
        http.addFilterBefore(new AuthorizationFilter(), BasicAuthenticationFilter.class);
    }
}
