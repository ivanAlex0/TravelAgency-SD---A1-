package service;

import model.User;
import model.enums.PERSIST_RESPONSE;
import repository.Repository;

import javax.persistence.PersistenceException;

import static service.Encryptor.checkPass;
import static service.Encryptor.hashPassword;

public class UserService extends Service<User> {

    public UserService(Repository<User> userRepository) {
        super(userRepository);
    }

    //
    //delete and getAll are in Service
    //

    public PERSIST_RESPONSE register(String email, String password) {
        if (email != null && password != null) {
            try {
                repository.persist(new User(email, hashPassword(password)));
                return PERSIST_RESPONSE.SUCCESS;
            } catch (PersistenceException e) {
                return PERSIST_RESPONSE.DUPLICATE_ENTITY;
            }
        } else {
            return PERSIST_RESPONSE.INVALID_INPUT;
        }
    }

    public User authenticate(String email, String password) {
        User user = repository.findByField("email", email);
        if (user != null && checkPass(password, user.getPassword())) {
            return user;
        } else return null;
    }

    public void update(User user) {
        repository.update(user);
    }
}
