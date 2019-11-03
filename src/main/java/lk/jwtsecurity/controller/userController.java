package lk.jwtsecurity.controller;


import lk.jwtsecurity.model.userModel;
import lk.jwtsecurity.repository.userRepository;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/users")
@CrossOrigin
public class userController {

    @Autowired
    private userRepository userRepo;

    @RequestMapping(value = "/getusers", method = RequestMethod.GET)
    public List<userModel> getUsers(){
       return userRepo.findAll();
    }

    @RequestMapping(value = "/register", method = RequestMethod.POST)
    public userModel register(@RequestBody userModel newUser){
        newUser.set_id(ObjectId.get()); // set new ObjectId to the user
        userRepo.save(newUser);
        System.out.println("New user saved to Database with username -> "+ newUser.getUsername());
        return newUser;
    }

}
