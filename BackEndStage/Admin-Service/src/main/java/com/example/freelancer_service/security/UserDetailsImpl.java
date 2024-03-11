package com.example.freelancer_service.security;

import com.example.freelancer_service.entity.Admin;
import com.example.freelancer_service.repository.AdminRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class UserDetailsImpl implements UserDetailsService {
    @Autowired
    AdminRepository userRep;
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Admin userOptional =userRep.findByEmail(username);
        return new MyUserDetails(userOptional);
    }
}