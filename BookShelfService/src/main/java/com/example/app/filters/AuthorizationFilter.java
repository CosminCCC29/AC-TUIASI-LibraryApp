package com.example.app.filters;

import com.example.app.configurations.SoapClientConfig;
import com.example.app.models.dtos.Token;
import com.example.app.models.dtos.TokenAndClaims;
import com.example.app.services.IdentityProviderClient;
import com.example.wsdl.*;
import org.apache.tomcat.util.http.fileupload.IOUtils;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.http.*;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;

import static org.springframework.http.HttpHeaders.AUTHORIZATION;

public class AuthorizationFilter extends OncePerRequestFilter {

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {

        SoapClientConfig soapClientConfig = new SoapClientConfig();
        IdentityProviderClient identityProviderClient = soapClientConfig.articleClient(soapClientConfig.marshaller());

        String authorizationHeader = request.getHeader(AUTHORIZATION);
        if(authorizationHeader != null && authorizationHeader.startsWith("Bearer "))
        {
            String token = authorizationHeader.substring("Bearer ".length());

            ValidateTokenResponse validateTokenResponse = null;
            try {
                ValidateTokenRequest validateTokenRequest = new ValidateTokenRequest();

                TokenData tokenData = new TokenData();
                tokenData.setToken(token);

                validateTokenRequest.setTokenData(tokenData);

                validateTokenResponse = identityProviderClient.validateToken(validateTokenRequest);

            }
            catch (Exception exception)
            {
                response.setHeader("error", "Invalid token!");
                response.sendError(HttpStatus.UNAUTHORIZED.value());
                return;
            }

            String userRole = validateTokenResponse.getTokenAndClaimsData().getRole();
            if(userRole != null && userRole.length() > 0)
            {
                Collection<SimpleGrantedAuthority> authorities = new ArrayList<>();
                authorities.add(new SimpleGrantedAuthority(userRole));
                UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken = new UsernamePasswordAuthenticationToken(null, null, authorities);

                SecurityContextHolder.getContext().setAuthentication(usernamePasswordAuthenticationToken);

                filterChain.doFilter(request, response);
            }
            else
            {
                response.setHeader("error", "No role found!");
                response.sendError(HttpStatus.INTERNAL_SERVER_ERROR.value());
            }
        }
        else
        {
            response.setHeader("error", "Token not found!");
            response.sendError(HttpStatus.UNAUTHORIZED.value());
        }
    }
}
