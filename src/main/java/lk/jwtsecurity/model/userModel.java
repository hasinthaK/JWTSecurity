package lk.jwtsecurity.model;

import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.security.core.GrantedAuthority;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;

@Document("user")
public class userModel {

    @Id
    private ObjectId _id;

    private String username;
    private String password;
    private String roles;
//    private Collection<? extends GrantedAuthority> authorities;

    public userModel(ObjectId _id, String username, String password, String roles) {
        this._id = _id;
        this.username = username;
        this.password = password;
        this.roles = roles;
//        this.authorities = authorities;
    }

    public ObjectId get_id() {
        return _id;
    }

    public void set_id(ObjectId _id) {
        this._id = _id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public List<String> getRoles(){
        if(this.roles.length() > 0){
            return Arrays.asList(this.roles.split(","));
        }
        return new ArrayList<>();
    }

    //Only list of strings or list of grantedauthority will work
    //else roles will not returned by the server

//    public Collection<? extends GrantedAuthority>getAuthorities(){
//        return this.authorities;
//    }

}
