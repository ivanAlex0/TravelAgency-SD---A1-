package view;

import controller.DestinationController;
import controller.PackageController;
import controller.UserController;
import model.Destination;
import model.Package;
import model.User;
import repository.Repository;
import service.DestinationService;
import service.PackageService;
import service.UserService;

public class DIController {

    public static final Repository<User> userRepository = new Repository<>(User.class);
    public static final UserService userService = new UserService(userRepository);
    public static final UserController userController = new UserController(userService);

    public static final Repository<Destination> destinationRepository = new Repository<>(Destination.class);
    public static final DestinationService destinationService = new DestinationService(destinationRepository);
    public static final DestinationController destinationController = new DestinationController(destinationService);

    public static final Repository<Package> packageRepository = new Repository<>(Package.class);
    public static final PackageService packageService = new PackageService(packageRepository);
    public static final PackageController packageController = new PackageController(packageService);

    public static User user = null;
}
