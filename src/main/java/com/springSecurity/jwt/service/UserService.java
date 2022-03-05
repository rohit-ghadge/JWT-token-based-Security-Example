package com.springSecurity.jwt.service;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collection;

@Service
public class UserService implements UserDetailsService 
{
    @Override
    public UserDetails loadUserByUsername(String userName) throws UsernameNotFoundException
    {
         //  to get the user form the Database
        // User Class Constructor take three argument 
        // username, password, GrantedAuthority Collection
    	// BCryptPasswordEncoder used to convert password  below password = rohit@123
    	// https://bcrypt-generator.com/
    	System.out.println("Reached Here...005");
        return new User("rohit123@gmail.com",
        		"$2a$12$7rQ9OqU3zP38a4BjuYtHx.i1PDANWVrOpjKTrBfF1l2n.bLzlKkMa"
                ,new ArrayList<>());
    }
}

