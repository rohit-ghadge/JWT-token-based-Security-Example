package com.springSecurity.jwt.filter;

import com.springSecurity.jwt.service.UserService;
import com.springSecurity.jwt.utility.JWTUtility;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

// run before every Unauthorized API Call (single execution per request)
// Filter to check token is valid or not
@Component
public class JwtFilter extends OncePerRequestFilter 
{
    @Autowired
    private JWTUtility jwtUtility;

    @Autowired
    private UserService userService;

    // Check Token is Valid or Not
    @Override
    protected void doFilterInternal(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, FilterChain filterChain) throws ServletException, IOException 
    {
    	// Header Key Muse be = "Authorization"
        String authorization = httpServletRequest.getHeader("Authorization");
        String token = null;
        String userName = null;

        // Check Header Value start with "Bearer "
        if(null != authorization && authorization.startsWith("Bearer "))
        {
            token = authorization.substring(7);
            userName = jwtUtility.getUsernameFromToken(token);
        }

        if(null != userName && SecurityContextHolder.getContext().getAuthentication() == null)
        {
            UserDetails userDetails   = userService.loadUserByUsername(userName);

            if(jwtUtility.validateToken(token,userDetails))
            {
                UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken  = new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
                usernamePasswordAuthenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(httpServletRequest));
                SecurityContextHolder.getContext().setAuthentication(usernamePasswordAuthenticationToken);
            }

        }
        filterChain.doFilter(httpServletRequest, httpServletResponse);
    }
}
