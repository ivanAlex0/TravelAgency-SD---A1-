package controller;

import model.User;
import model.enums.PERSIST_RESPONSE;
import service.UserService;

public class UserController extends Controller<User> {

    public UserController(UserService userService) {
        super(userService);
    }

    public PERSIST_RESPONSE register(String email, String password) {
        return ((UserService) service).register(email, password);
    }

    public User authenticate(String email, String password) {
        return ((UserService) service).authenticate(email, password);
    }

    public void update(User user) {
        ((UserService) service).update(user);
    }
}
