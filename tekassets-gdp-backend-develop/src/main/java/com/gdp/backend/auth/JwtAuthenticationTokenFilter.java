package com.gdp.backend.auth;

import com.gdp.backend.exception.InvalidTokenException;
import com.gdp.backend.service.GDPUserDetailsService;
import com.gdp.backend.util.AzureADUtils;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

import static com.gdp.backend.common.Constants.HEADER_STRING;
import static com.gdp.backend.common.Constants.TOKEN_PREFIX;

@Component
public class JwtAuthenticationTokenFilter extends OncePerRequestFilter {

    private GDPUserDetailsService userDetailsService;

    private AzureADUtils azureADUtils;

    public JwtAuthenticationTokenFilter(GDPUserDetailsService userDetailsService, AzureADUtils azureADUtils) {
        super();
        this.userDetailsService = userDetailsService;
        this.azureADUtils = azureADUtils;
    }

    private int tokenType(HttpServletRequest request) {
        int tokenType = 0;
        final String requestHeader = request.getHeader(HEADER_STRING);
        if (requestHeader != null && requestHeader.startsWith(TOKEN_PREFIX)) {
            tokenType = 1;  //AZURE TOKEN
        }
        return tokenType;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain) throws ServletException, IOException {
        String requestHeader;
        String username = null;
        String authToken = null;
        int type = tokenType(request);

        if (type == 0) {
            logger.warn("Will ignore the header");
        } else if (type == 1) {
            requestHeader = request.getHeader(HEADER_STRING);
            authToken = requestHeader.substring(7);
            try {
                username = azureADUtils.getUsernameFromToken(authToken);
            } catch (InvalidTokenException e) {
                logger.warn("the token is expired and not valid anymore", e);
            }
        }

        logger.info("checking authentication for user " + username);
        if (username != null && SecurityContextHolder.getContext().getAuthentication() == null) {

            UserDetails userDetails = this.userDetailsService.loadUserByUsername(username);

            // For simple validation it is completely sufficient to just check the token integrity
            if (userDetails != null && (type == 1 && azureADUtils.validateToken(authToken))) {

                UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
                authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                logger.info("authenticated user " + username + ", setting security context");
                SecurityContextHolder.getContext().setAuthentication(authentication);
            }
        }

        chain.doFilter(request, response);
    }
}