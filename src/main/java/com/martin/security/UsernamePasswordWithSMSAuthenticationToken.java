package com.martin.security;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;

import java.util.Collection;

public class UsernamePasswordWithSMSAuthenticationToken extends UsernamePasswordAuthenticationToken {

    private final Object sms;

    public UsernamePasswordWithSMSAuthenticationToken(Object principal, Object credentials, Object sms) {
        super(principal, credentials);
        this.sms = sms;
    }

    public UsernamePasswordWithSMSAuthenticationToken(Object principal, Object credentials, Collection<? extends GrantedAuthority> authorities, Object sms) {
        super(principal, credentials, authorities);
        this.sms = sms;
    }

    public Object getSms() {
        return sms;
    }
}
