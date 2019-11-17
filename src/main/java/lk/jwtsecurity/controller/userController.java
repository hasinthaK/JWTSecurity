package lk.jwtsecurity.controller;


import lk.jwtsecurity.model.userModel;
import lk.jwtsecurity.repository.userRepository;
import org.bson.types.ObjectId;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/users")
@CrossOrigin(origins = "http://localhost:4200")
public class userController {

    private static final Logger log = LoggerFactory.getLogger(userController.class);

    @Autowired
    BCryptPasswordEncoder passwordEncoder;

    @Autowired
    private userRepository userRepo;

    @RequestMapping(value = "/getusers", method = RequestMethod.POST)
    public List<userModel> getUsers(){
        log.info("Current saved users returned");
        return userRepo.findAll();
    }

    @RequestMapping(value = "/register", method = RequestMethod.POST)
    public userModel register(@RequestBody @Valid userModel newUser){
        newUser.set_id(ObjectId.get()); // set new ObjectId to the user
        String pass = passwordEncoder.encode(newUser.getPassword());
        newUser.setPassword(pass);
        userRepo.save(newUser);
        String logString = "New user saved to Database with username -> "+ newUser.getUsername();
        log.info(logString);
        return newUser;
    }

    @RequestMapping(value = "/loggeduser", method = RequestMethod.POST)
    @ResponseBody
    public String getLoggedinUser(){
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if(!(auth instanceof AnonymousAuthenticationToken)){
            String username = auth.getName();
            return username;
        }

        return null;
    }

}
