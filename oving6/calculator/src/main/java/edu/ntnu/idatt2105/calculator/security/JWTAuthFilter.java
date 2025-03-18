package edu.ntnu.idatt2105.calculator.security;

import edu.ntnu.idatt2105.calculator.model.User;
import edu.ntnu.idatt2105.calculator.service.JWTService;
import edu.ntnu.idatt2105.calculator.service.UserService;
import io.jsonwebtoken.io.IOException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

@Component
@RequiredArgsConstructor
public class JWTAuthFilter extends OncePerRequestFilter{

    private final JWTService jwtService;
    private final UserService userService;
    private final Logger logger = LoggerFactory.getLogger(JWTAuthFilter.class);


    @Override
    protected void doFilterInternal(@SuppressWarnings("null") @NonNull HttpServletRequest request, @SuppressWarnings("null") @NonNull HttpServletResponse response, @SuppressWarnings("null") @NonNull FilterChain filterChain)
    throws ServletException, IOException, java.io.IOException {
        final String jwt, username;
        final String authHeader = request.getHeader("Authorization");

        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            filterChain.doFilter(request, response);
            return;
        }

        jwt = authHeader.substring(7);
        logger.info("JWT recived through the http filter: " + jwt);

        username = jwtService.extractUsername(jwt);
        logger.info("Username found using token: " + username);

        if (username != null && SecurityContextHolder.getContext().getAuthentication() == null) {
            logger.info("User found with token: " + username);
            User user = this.userService.loadUserByUsername(username);
            if (jwtService.isTokenValid(jwt, user)) {
                UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(
                    user, null, user.getAuthorities());

                authToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                SecurityContextHolder.getContext().setAuthentication(authToken);
            }
        }
        filterChain.doFilter(request, response);
    }
}
