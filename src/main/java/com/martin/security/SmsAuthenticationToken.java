package com.martin.security;

import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;

import java.util.Collection;

public class SmsAuthenticationToken extends AbstractAuthenticationToken {

    private final Object principal;
    private Object sms;

    public SmsAuthenticationToken(Object principal, Object credentials) {
        super(null);
        System.out.println("SmsAuthenticationToken false");
        this.principal = principal;
        this.sms = credentials;
        super.setAuthenticated(false);
    }

    public SmsAuthenticationToken(Object principal, Object credentials, Collection<? extends GrantedAuthority> authorities) {
        super(authorities);
        System.out.println("SmsAuthenticationToken true");
        this.principal = principal;
        this.sms = credentials;
        super.setAuthenticated(true);
    }


    @Override
    public Object getCredentials() {
        return sms;
    }

    @Override
    public Object getPrincipal() {
        return principal;
    }
}
