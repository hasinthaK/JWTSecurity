package lk.jwtsecurity.filter;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.fasterxml.jackson.databind.ObjectMapper;
import lk.jwtsecurity.model.loginUser;
import lk.jwtsecurity.model.userDetailsImpl;
import lk.jwtsecurity.security.JWTProperties;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Date;


public class JWTAuthenticationFilter extends UsernamePasswordAuthenticationFilter {
    public static final Logger log = LoggerFactory.getLogger(JWTAuthenticationFilter.class);
    private AuthenticationManager AuthenticationManager;

    public JWTAuthenticationFilter(AuthenticationManager authenticationManager) {
        AuthenticationManager = authenticationManager;
        setFilterProcessesUrl("/login");
    }

    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException {
        loginUser credentials = null;
        try {
            credentials = new ObjectMapper().readValue(request.getInputStream(), loginUser.class);
        } catch (IOException e) {
            e.printStackTrace();
        }

        UsernamePasswordAuthenticationToken AuthToken = new UsernamePasswordAuthenticationToken(
                credentials.getUsername(),
                credentials.getPassword(),
                new ArrayList<>());
        return this.AuthenticationManager.authenticate(AuthToken);
    }

    @Override
    protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain, Authentication authResult) throws IOException, ServletException {
        userDetailsImpl user = (userDetailsImpl) authResult.getPrincipal();

        String token = JWT.create()
                .withSubject(user.getUsername())
                .withExpiresAt(new Date(System.currentTimeMillis() + JWTProperties.EXPIRATION_TIME))
                .sign(Algorithm.HMAC512(JWTProperties.SECRET.getBytes()));

        log.info("JWT created and returned -> " + JWTProperties.TOKEN_PREFIX + token);

// add token as json body of the response
        PrintWriter out = response.getWriter();
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        out.print(JWTProperties.TOKEN_PREFIX + token);
        out.flush();

        response.addHeader(JWTProperties.HEADER_STRING, JWTProperties.TOKEN_PREFIX + token);
//        response. ("token: " + JWTProperties.TOKEN_PREFIX + " " + token);


    }

}
