package com.martin.security;

import com.martin.service.UserDetailsServiceImpl;
import com.martin.service.UserJpaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;

@EnableWebSecurity
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {

    @Override
    public void configure(WebSecurity web) {
        web.ignoring().antMatchers("/");
    }

    @Override
    public void configure(HttpSecurity http) throws Exception {
        http.csrf().disable()
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.ALWAYS)
                .and()
                .authorizeRequests().antMatchers("/public").permitAll()
                .and()
                .authorizeRequests().antMatchers("/authenticated", "/success").authenticated()
                .and()
                .formLogin().loginPage("/login").successForwardUrl("/success");
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new PasswordEncoder() {
            @Override
            public String encode(CharSequence rawPassword) {
                return UserJpaService.encode(rawPassword.toString());
            }

            @Override
            public boolean matches(CharSequence rawPassword, String encodedPasswordFromStorage) {
                System.out.println("matches rawPassword = " + rawPassword +
                        " encodedPasswordFromStorage = " + encodedPasswordFromStorage);
                return encode(rawPassword).equals(encodedPasswordFromStorage);
            }
        };
    }

    @Autowired
    private UserDetailsServiceImpl userDetailsService;

    @Override
    public void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userDetailsService);
    }


//    @Override
//    protected UserDetailsService userDetailsService() {
//        return new UserDetailsServiceImpl();
//    }
//
//    @Bean(name = "userDetailsService")
//    @Override
//    public UserDetailsService userDetailsServiceBean() throws Exception {
//        return super.userDetailsServiceBean();
//    }
}
