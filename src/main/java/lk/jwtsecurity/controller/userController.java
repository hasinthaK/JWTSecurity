package lk.jwtsecurity.controller;


import lk.jwtsecurity.model.userModel;
import lk.jwtsecurity.repository.userRepository;
import org.bson.types.ObjectId;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("users")
@CrossOrigin
public class userController {

    private userRepository userRepo;

    public userController(userRepository userRepo) {
        this.userRepo = userRepo;
    }

    @RequestMapping(value = "getusers", method = RequestMethod.GET)
    public List<userModel> getUsers(){
       return userRepo.findAll();
    }

    @RequestMapping(value = "register", method = RequestMethod.POST)
    public userModel register(@RequestBody @Valid userModel newUser){
        newUser.set_id(ObjectId.get()); // set new ObjectId to the user
        userRepo.save(newUser);
        System.out.println("New user saved to Database with username -> "+ newUser.getUsername());
        return newUser;
    }

}
