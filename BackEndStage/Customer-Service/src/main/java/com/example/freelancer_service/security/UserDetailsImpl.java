package com.example.freelancer_service.security;

import com.example.freelancer_service.entity.Customer;
import com.example.freelancer_service.repository.CustomerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class UserDetailsImpl implements UserDetailsService {
    @Autowired
    CustomerRepository userRep;
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Customer userOptional =userRep.findByEmail(username);
        return new MyUserDetails(userOptional);
    }
}