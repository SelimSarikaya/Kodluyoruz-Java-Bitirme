package com.ticket.configuration;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Component;

import com.ticket.exception.UserNotFoundException;
import com.ticket.model.User;
import com.ticket.repository.UserRepository;

@Component
public class CustomUserDetailsService implements UserDetailsService {

    @Autowired
    UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String name) throws UserNotFoundException {
        User user = userRepository.findByName(name);
        return new CustomUserDetail(user);
    }
}