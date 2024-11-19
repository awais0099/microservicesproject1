package com.microservicesproj1.auth.service.AuthService.authenticationfilter;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.microservicesproj1.auth.service.AuthService.dto.ApiResponse;
import com.microservicesproj1.auth.service.AuthService.service.CustomUserDetailsService;
import com.microservicesproj1.auth.service.AuthService.service.JwtService;

import io.jsonwebtoken.ExpiredJwtException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component
public class JwtFilter extends OncePerRequestFilter {

	@Autowired
	private JwtService jwtService;

	@Autowired
	ApplicationContext context;
	
	private static final List<String> allowedUrls = new ArrayList<>();

    static {
        allowedUrls.add("/auth/login");
        allowedUrls.add("/auth/register");
        allowedUrls.add("/auth/test");
        allowedUrls.add("/auth/validate");
    }

	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {
		System.out.println("JwtFilter > doFilterInternal");

		// Bypass the filter for the allowed URLs
		if (allowedUrls.contains(request.getRequestURI())) {
			System.out.println("> allowed Urls");
            filterChain.doFilter(request, response);
            return;
        }
		
		String authHeader = request.getHeader("Authorization");
		String token = null;
		String username = null;

		if (authHeader != null && authHeader.startsWith("Bearer ")) {
			token = authHeader.substring(7);
			username = jwtService.extractUsername(token);
			jwtService.isTokenExpired(token);
			
			if (username != null && SecurityContextHolder.getContext().getAuthentication() == null) {
				UserDetails userDetails = context.getBean(CustomUserDetailsService.class).loadUserByUsername(username);

				if (jwtService.validateToken(token, userDetails.getUsername())) {
					UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(userDetails,
							null, userDetails.getAuthorities());
					authToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
					SecurityContextHolder.getContext().setAuthentication(authToken);
					filterChain.doFilter(request, response);
				}
			}
		} else {
			handleUnauthorized(response, "Authorization header is missing or invalid");
		}
	}

	private void handleUnauthorized(HttpServletResponse response, String message) throws IOException {
		ApiResponse<String> apiResponse = new ApiResponse<>(HttpServletResponse.SC_UNAUTHORIZED, false, message, null, new Date());
		response.setContentType("application/json");
		response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
		response.getWriter().write(new ObjectMapper().writeValueAsString(apiResponse));
	}
}
