package lk.jwtsecurity.security;

import lk.jwtsecurity.filter.JWTAuthenticationFilter;
import lk.jwtsecurity.filter.JWTAuthorizationFilter;
import lk.jwtsecurity.repository.userRepository;
import lk.jwtsecurity.service.userDetailsServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

@Configuration
@EnableWebSecurity
public class securityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    userRepository userRepository;

    @Autowired
    userDetailsServiceImpl userDetailsService;

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .csrf().disable()
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                .cors()
                .and()
                // add jwt filters (1. authentication, 2. authorization)
                .addFilter(new JWTAuthenticationFilter(authenticationManager()))
                .addFilter(new JWTAuthorizationFilter(authenticationManager(),  this.userRepository))
                .authorizeRequests()
                // configure access rules
                .antMatchers(HttpMethod.POST, "/login").permitAll()
                .antMatchers(HttpMethod.POST, "/users/register").permitAll()
                .anyRequest().authenticated();


//                .csrf().disable()
//                .authorizeRequests().anyRequest().authenticated()
//                .and().formLogin()
//                .and().sessionManagement().disable();

//                .csrf().disable()
//                .authorizeRequests()
//                .antMatchers("/users/register").permitAll()
//                .anyRequest().authenticated()
//                .and().httpBasic()
//                .and().sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth
                .userDetailsService(userDetailsService)
                .passwordEncoder(passwordEncoder());

//        auth.authenticationProvider(authProvider());
    }

    @Bean
    BCryptPasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder(12);
    }

//    @Bean
//    DaoAuthenticationProvider authProvider(){
//        DaoAuthenticationProvider authenticationProvider = new DaoAuthenticationProvider();
//        authenticationProvider.setUserDetailsService(this.userDetailsService);
//        authenticationProvider.setPasswordEncoder(passwordEncoder());
//
//        return authenticationProvider;
//    }

    @Bean
    public WebMvcConfigurer corsConfigurer() {
        return new WebMvcConfigurerAdapter() {
            @Override
            public void addCorsMappings(CorsRegistry registry) {
                registry.addMapping("/**");
            }
        };
    }

}

//hasintha 12 round bcrypt hash $2y$12$WvffONmAWGIqgwzZF/3SjuSWsLK5bUt.Qgw7QaRt8728JriJKMBfu
