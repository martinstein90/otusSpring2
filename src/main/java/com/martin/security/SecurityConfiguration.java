package com.martin.security;

import com.martin.service.UserDetailsServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@EnableWebSecurity
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {

    @Override
    public void configure(WebSecurity web) {
        web.ignoring().antMatchers("/");
    }

    @Autowired
    private AuthenticationProcessingFilter authenticationProcessingFilter;

    @Override
    public void configure(HttpSecurity http) throws Exception {
        http.csrf().disable()
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.NEVER)
                .and()
                .authorizeRequests().antMatchers(  "/public", "/success").permitAll()
                .and()
                .authorizeRequests().antMatchers("/authenticated").authenticated()
                .and()
                .addFilterBefore(authenticationProcessingFilter, UsernamePasswordAuthenticationFilter.class)
                .formLogin().loginPage("/login1");
    }

//    @Autowired
//    private UserDetailsServiceImpl userDetailsService;
//
//    @Override
//    public void configure(AuthenticationManagerBuilder auth) throws Exception {
//        auth.userDetailsService(userDetailsService);
//    }
}
