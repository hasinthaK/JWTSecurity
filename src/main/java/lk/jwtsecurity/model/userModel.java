package lk.jwtsecurity.model;

import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.security.core.GrantedAuthority;

import java.util.List;

public class userModel {

    @Id
    private ObjectId _id;

    private String username;
    private String password;
    private List<? extends GrantedAuthority> roles;

    public userModel(ObjectId _id, String username, String password, List<? extends GrantedAuthority> roles) {
        this._id = _id;
        this.username = username;
        this.password = password;
        this.roles = roles;
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

    public List<? extends GrantedAuthority> getRoles() {
        return roles;
    }

    public void setRoles(List<? extends GrantedAuthority> roles) {
        this.roles = roles;
    }
}
