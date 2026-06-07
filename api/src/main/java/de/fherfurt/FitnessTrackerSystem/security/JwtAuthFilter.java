package de.fherfurt.FitnessTrackerSystem.security;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
public class JwtAuthFilter extends OncePerRequestFilter {

    private final JwtService jwtService;
    private final UserDetailsServiceImpl userDetailsService;

    public JwtAuthFilter(JwtService jwtService, UserDetailsServiceImpl userDetailsService) {
        this.jwtService = jwtService;
        this.userDetailsService = userDetailsService;
    }

    @Override
    protected void doFilterInternal(
            HttpServletRequest request,
            HttpServletResponse response,
            FilterChain filterChain
    ) throws ServletException, IOException {

        // read Authorization Header
        String authHeader = request.getHeader("Authorization");

        // no Token → go on without authentication
        if (!hasValidAuthHeader(authHeader)) {
            filterChain.doFilter(request, response);
            return;
        }

        // call Token from Header
        String token = extractToken(authHeader);

        // read userName from Token
        String userName = jwtService.extractUserName(token);

        // User still not authenticated?
        if (userName != null && SecurityContextHolder.getContext().getAuthentication() == null) {
            authenticateUser(userName, token, request);
        }

        // call next Filter
        filterChain.doFilter(request, response);
    }

    // verifies if authentication Header exist and in correct format
    private boolean hasValidAuthHeader(String authHeader) {
        return authHeader != null && authHeader.startsWith("Bearer ");
    }

    // removes "Bearer " (7 sign) from the Header
    private String extractToken(String authHeader) {
        return authHeader.substring(7);
    }

    // loads User from DB and starts authentication in Spring Security
    private void authenticateUser(String userName, String token, HttpServletRequest request) {
        // loads User from DB
        UserDetails userDetails = userDetailsService.loadUserByUsername(userName);

        // Token valid?
        if (jwtService.isTokenValid(token)) {
            // create authentication and add it in Spring Security
            UsernamePasswordAuthenticationToken authToken =
                    new UsernamePasswordAuthenticationToken(
                            userDetails,
                            null,
                            userDetails.getAuthorities()
                    );
            // Request Details added
            authToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));

            // Spring Security says who´s logged in
            SecurityContextHolder.getContext().setAuthentication(authToken);
        }
    }
}