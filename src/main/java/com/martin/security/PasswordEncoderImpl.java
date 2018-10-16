package com.martin.security;

import com.martin.service.UserJpaService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
public class PasswordEncoderImpl implements PasswordEncoder {

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
}
