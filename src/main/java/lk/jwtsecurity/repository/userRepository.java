package lk.jwtsecurity.repository;

import lk.jwtsecurity.model.user;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface userRepository extends MongoRepository<user, String> {

    user findUserByUsername(String username);
}
