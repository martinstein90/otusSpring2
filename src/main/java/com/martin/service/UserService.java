package com.martin.service;

import com.martin.domain.User;
import com.martin.security.GrantedAuthorityImpl;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import java.util.Collection;

public interface UserService {
    User getUser(String login) throws UsernameNotFoundException;
    User addUser(String username, String password, String phoneNumber, GrantedAuthorityImpl... authorities) throws Exception;
    void setUserSms(String username, String sms) throws Exception;
}
