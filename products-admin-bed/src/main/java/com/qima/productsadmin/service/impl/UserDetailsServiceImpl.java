package com.qima.productsadmin.service.impl;

import com.qima.productsadmin.model.UserDetailsImpl;
import com.qima.productsadmin.persistence.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class UserDetailsServiceImpl implements UserDetailsService {

    @Autowired
    private UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        return userRepository.findByUsername(username)
                .map(u -> {

                    UserDetailsImpl user = new UserDetailsImpl();
                    user.setUsername(u.getUsername());
                    user.setName(u.getName());
                    user.setId(u.getId());
                    user.setPassword(u.getPassword());
                    user.setRoles(List.of(new SimpleGrantedAuthority(u.getRole())));
                    return user;

                })
                .orElseThrow(() -> new UsernameNotFoundException("User not found: " + username));
    }
}
