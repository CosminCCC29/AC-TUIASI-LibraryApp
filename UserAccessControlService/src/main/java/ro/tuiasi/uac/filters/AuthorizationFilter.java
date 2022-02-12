package ro.tuiasi.uac.filters;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;
import ro.tuiasi.uac.interfaces.JwtParserServiceInterface;
import ro.tuiasi.uac.models.HSKeyProperties;
import ro.tuiasi.uac.services.HS256JwtParserService;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;

import static org.springframework.http.HttpHeaders.AUTHORIZATION;
import static org.springframework.http.HttpStatus.FORBIDDEN;
import static org.springframework.http.HttpStatus.UNAUTHORIZED;

public class AuthorizationFilter extends OncePerRequestFilter {

    private HSKeyProperties hs256KeyProperties;
    private JwtParserServiceInterface<String> jwtParserService;

    public AuthorizationFilter()
    {
        hs256KeyProperties = new HSKeyProperties();
        hs256KeyProperties.setSecretKey("2f302a29749692057d15");
        jwtParserService = new HS256JwtParserService();
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {

        if(
                request.getServletPath().equals("/identity-provider")
        )
        {
            filterChain.doFilter(request, response);
            return;
        }

        String authorizationHeader = request.getHeader(AUTHORIZATION);
        if(authorizationHeader != null && authorizationHeader.startsWith("Bearer "))
        {
            String token = authorizationHeader.substring("Bearer ".length());

            try {
                Jws<Claims> claims = jwtParserService.validateSignatureAndGetTokenParser(token, hs256KeyProperties.getSecretKey());
                String role = claims.getBody().get("role", String.class);

                Collection<SimpleGrantedAuthority> authorities = new ArrayList<>();
                authorities.add(new SimpleGrantedAuthority(role));
                UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken = new UsernamePasswordAuthenticationToken(null, null, authorities);

                SecurityContextHolder.getContext().setAuthentication(usernamePasswordAuthenticationToken);
                filterChain.doFilter(request, response);
            }
            catch (Exception exception)
            {
                response.setHeader("error", exception.getMessage());
                response.sendError(FORBIDDEN.value());
            }
        }
        else
        {
            response.setHeader("error", "Token not found or expired");
            response.sendError(UNAUTHORIZED.value());
        }
    }

}
