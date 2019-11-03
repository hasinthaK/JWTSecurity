package lk.jwtsecurity.repository;

import lk.jwtsecurity.model.userModel;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface userRepository extends MongoRepository<userModel, String> {

    userModel findUserByUsername(String username);

}
