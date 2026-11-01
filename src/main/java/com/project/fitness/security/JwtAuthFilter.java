package com.project.fitness.security;

import io.jsonwebtoken.Claims;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.List;

@Component
public class JwtAuthFilter extends OncePerRequestFilter {

    @Autowired
    JwtUtils jwtUtils;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {

        System.out.println("AuthTokenFilter called");

        try{
            String jwt = parseJwt(request);
            if(jwt != null && jwtUtils.validateJwtToken(jwt)){
                String userId = jwtUtils.getUserIdFromToken(jwt);

                Claims claims = jwtUtils.getClaims(jwt);
                Object roleClaim = claims.get("roles");
                if (roleClaim == null) {
                    roleClaim = claims.get("role");
                }

                java.util.List<GrantedAuthority> authorities = new java.util.ArrayList<>();
                if (roleClaim instanceof java.util.List<?> list) {
                    for (Object item : list) {
                        String r = item.toString();
                        authorities.add(new SimpleGrantedAuthority(r));
                        if (!r.startsWith("ROLE_")) {
                            authorities.add(new SimpleGrantedAuthority("ROLE_" + r));
                        }
                    }
                } else if (roleClaim != null) {
                    String r = roleClaim.toString();
                    authorities.add(new SimpleGrantedAuthority(r));
                    if (!r.startsWith("ROLE_")) {
                        authorities.add(new SimpleGrantedAuthority("ROLE_" + r));
                    }
                }

                UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(userId, null, authorities);
                authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                SecurityContextHolder.getContext().setAuthentication(authentication);
            }
        } catch (Exception e) {
            System.err.println("Could not set user authentication in security context: " + e.getMessage());
        }

        filterChain.doFilter(request,response);
    }

    private String parseJwt(HttpServletRequest request){
        String jwt = jwtUtils.getJwtFromHeader(request);
        System.out.println(jwt);
        return jwt;
    }
}
