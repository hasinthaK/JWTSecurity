package lk.jwtsecurity.service;

import lk.jwtsecurity.model.userDetailsImpl;
import lk.jwtsecurity.model.userModel;
import lk.jwtsecurity.repository.userRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

@Component
public class userDetailsServiceImpl implements UserDetailsService {

    public static final Logger log = LoggerFactory.getLogger(userDetailsServiceImpl.class);

    @Autowired
    userRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String s) throws UsernameNotFoundException {
        userModel user = userRepository.findUserByUsername(s);

        if(user == null){
            log.warn("Incorrect username or password");
            throw new UsernameNotFoundException("User not found");
        }


//        for(String role:user.getRoles()){
////            authorities.add(role);
////        }
        //Collection<? extends GrantedAuthority> authorities = Arrays.asList(new SimpleGrantedAuthority("User"));

//Spring built in userDetailsImplementation - User
        //return new User(user.getUsername(), user.getPassword(), user.getAuthorities());
        return new userDetailsImpl(user);
    }
}
