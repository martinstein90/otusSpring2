package com.martin.service;

import com.google.common.collect.Lists;
import com.martin.domain.User;
import com.martin.repository.UserRepository;
import com.martin.security.GrantedAuthorityImpl;
import com.martin.security.Role;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class UserJpaService implements UserService {

    private final UserRepository userRepository;

    public UserJpaService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public User getUser(String username) throws UsernameNotFoundException {
        List<User> users = new ArrayList<>(Lists.newArrayList(userRepository.findByUsername(username)));

        if(users.isEmpty()) {
            System.out.println("Username not found");
            throw new UsernameNotFoundException("Username not found");
            //todo когда вводим логин  несуществующего пользователя, заходим сюда, но никаких эксепшинов, а идем в passwordEncoder
        }
        if(users.size() != 1) {
            throw new RuntimeException("Error users data base. users.size() != 1");
        }
        return users.get(0);
    }

    @Override
    public User addUser(String username, String password, String phoneNumber, GrantedAuthorityImpl... authorities) throws Exception {
        try {
            return userRepository.save(new User(
                    username, encode(password), phoneNumber,
                    new HashSet<>(Arrays.asList(authorities)),
                    true, true, true, true));
        }
        catch (Exception e) {
            throw new Exception("Error save user!");
        }
    }

    public static String encode(String password) {
        return String.valueOf(password.hashCode());
    }
}
