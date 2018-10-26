package com.martin.service;

import com.google.common.collect.Lists;
import com.martin.domain.User;
import com.martin.repository.UserRepository;
import com.martin.security.GrantedAuthorityImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;

@Service
@Slf4j
public class UserDetailsServiceImpl implements UserDetailsService {

    @Autowired
    private final UserRepository userRepository;

    public UserDetailsServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        log.info("Load user By " + username);
        List<User> users = new ArrayList<>(Lists.newArrayList(userRepository.findByUsername(username)));
        if(users.isEmpty()) {
            log.info(username + " not found");
            throw new UsernameNotFoundException(username + " not found");
        }
        log.info("Loaded user from storage: " + users.get(0));
        return users.get(0);
    }

    public void setUserSms(String username, String sms) throws Exception {
        User user = (User)loadUserByUsername(username);
        user.setSms(sms);
        try {
            userRepository.save(user);
        }
        catch (Exception e) {
            throw new Exception("Error save user!");
        }
    }

    public User addUser(String username, String password, String phoneNumber, GrantedAuthorityImpl... authorities) throws Exception {
        try {
            return userRepository.save(new User(username, encode(password), phoneNumber,
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
