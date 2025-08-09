package com.ashare.auth.jwt;

import com.ashare.model.User;
import com.ashare.repository.UserRepository;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
@RequiredArgsConstructor
public class JwtAuthFilter extends OncePerRequestFilter {

    private final JwtService jwtService;
    private final UserRepository userRepository;

    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain filterChain) throws ServletException, IOException {

        System.out.println("🔥 JwtAuthFilter triggered for: " + request.getRequestURI()
                + " [Thread: " + Thread.currentThread().getId() + "]");

        final String authHeader = request.getHeader("Authorization");

        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            System.out.println("⛔️ No Bearer token for: " + request.getRequestURI());
            filterChain.doFilter(request, response);
            return;
        }

        String token = authHeader.substring(7);
        String email = jwtService.extractEmail(token);

        System.out.println("Extracted email: " + email);

        if (email != null && SecurityContextHolder.getContext().getAuthentication() == null) {
            User user = userRepository.findByEmail(email).orElse(null);
            if (user != null) {
                UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(
                        user, null, user.getAuthorities()
                );
                authToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));

                System.out.println("✅ Setting authentication for user: " + email);
                SecurityContextHolder.getContext().setAuthentication(authToken);
                System.out.println("🧠 Current auth: " + SecurityContextHolder.getContext().getAuthentication());

            }
        }

        filterChain.doFilter(request, response);
    }
}
