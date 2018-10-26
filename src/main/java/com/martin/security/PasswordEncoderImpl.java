package com.martin.security;

import com.martin.service.UserDetailsServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class PasswordEncoderImpl implements PasswordEncoder {

    @Override
    public String encode(CharSequence rawPassword) {
        return UserDetailsServiceImpl.encode(rawPassword.toString());
    }

    @Override
    public boolean matches(CharSequence rawPassword, String encodedPasswordFromStorage) {
        log.info("RawPassword: " + rawPassword + " encodedPasswordFromStorage: " + encodedPasswordFromStorage);
        return encode(rawPassword).equals(encodedPasswordFromStorage);
    }
}
