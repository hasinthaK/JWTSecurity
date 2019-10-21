package lk.jwtsecurity.service;

import lk.jwtsecurity.model.userModel;
import lk.jwtsecurity.repository.userRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

@Component
public class userDetailsServiceImpl implements UserDetailsService {

    @Autowired
    userRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String s) throws UsernameNotFoundException {
        userModel user = userRepository.findUserByUsername(s);

        if(user == null){
            throw new UsernameNotFoundException("User not found");
        }


//        for(String role:user.getRoles()){
////            authorities.add(role);
////        }
        //Collection<? extends GrantedAuthority> authorities = Arrays.asList(new SimpleGrantedAuthority("User"));


        return new User(user.getUsername(), user.getPassword(), user.getAuthorities());
    }
}
