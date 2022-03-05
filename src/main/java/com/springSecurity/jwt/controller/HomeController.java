package com.springSecurity.jwt.controller;

import com.springSecurity.jwt.model.JwtRequest;
import com.springSecurity.jwt.model.JwtResponse;
import com.springSecurity.jwt.service.UserService;
import com.springSecurity.jwt.utility.JWTUtility;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HomeController 
{

    @Autowired
    private JWTUtility jwtUtility;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private UserService userService;

    @GetMapping("/getName")
    public String home() 
    {
        return "welcome Admin. confidential page only Valid user can see this page";
    }
    
    @PostMapping("/authenticate")
    public JwtResponse authenticate(@RequestBody JwtRequest jwtRequest) throws Exception
    {

        try 
        {
        	// for authentication user is valid or not
        	// authenticate method call loadUserByUsername Method
            authenticationManager.authenticate( new UsernamePasswordAuthenticationToken(jwtRequest.getUsername(), jwtRequest.getPassword()) );
           // authentication complete
        }
         catch (BadCredentialsException e) 
        {
            throw new Exception("INVALID_CREDENTIALS", e);
        }

        // Create token and return to user
        // userDetails Contains username, password, GrantedAuthority Collection 
    	System.out.println("Reached Here...006");
 
    	// get user details
        final UserDetails userDetails  = userService.loadUserByUsername(jwtRequest.getUsername());
    	System.out.println("Reached Here...007");

        // Pass userDetails and take token
        final String token = jwtUtility.generateToken(userDetails);
        return  new JwtResponse(token);
    }
}
