package lk.jwtsecurity.filter;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import lk.jwtsecurity.model.userDetailsImpl;
import lk.jwtsecurity.model.userModel;
import lk.jwtsecurity.repository.userRepository;
import lk.jwtsecurity.security.JWTProperties;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class JWTAuthorizationFilter extends BasicAuthenticationFilter {

    public static final Logger log = LoggerFactory.getLogger(JWTAuthorizationFilter.class);
    private userRepository userRepository;

    public JWTAuthorizationFilter(AuthenticationManager authenticationManager, userRepository userRepository) {
        super(authenticationManager);
        this.userRepository = userRepository;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain) throws IOException, ServletException {
        String header = request.getHeader(JWTProperties.HEADER_STRING);

        if (header == null || !header.startsWith(JWTProperties.TOKEN_PREFIX)) {
            log.warn("No authorization header found");
            chain.doFilter(request, response);
            return;
        }

        log.info("Token found, authorizing..");

        Authentication authentication = getUsernamePasswordAuthentication(request);
        SecurityContextHolder.getContext().setAuthentication(authentication);

        chain.doFilter(request, response);
    }

    private Authentication getUsernamePasswordAuthentication(HttpServletRequest request){
        String token = request.getHeader(JWTProperties.HEADER_STRING)
                .replace(JWTProperties.TOKEN_PREFIX, "");

        if(token != null){
            String username = JWT.require(Algorithm.HMAC512(JWTProperties.SECRET.getBytes()))
                    .build()
                    .verify(token)
                    .getSubject();

            if(username != null){
                userModel user = userRepository.findUserByUsername(username);
                userDetailsImpl userDetails = new userDetailsImpl(user);

                return new UsernamePasswordAuthenticationToken(username, null, userDetails.getAuthorities());
            }
            return null;
        }
        return null;
    }
}
