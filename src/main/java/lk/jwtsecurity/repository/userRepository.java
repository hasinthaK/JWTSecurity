package lk.jwtsecurity.repository;

import lk.jwtsecurity.model.userModel;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface userRepository extends MongoRepository<userModel, String> {

    userModel findUserByUsername(String username);
}
