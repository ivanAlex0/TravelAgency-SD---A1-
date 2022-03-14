package view;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.DatePicker;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;
import model.Destination;
import model.Package;
import model.enums.PACKAGE_STATUS;

import java.sql.Date;
import java.util.ArrayList;
import java.util.stream.Collectors;

public class UserPage extends DIController implements SceneController {

    @FXML
    public ListView<String> availablePackageList;

    @FXML
    public ListView<Destination> availableDestinationList;

    @FXML
    public ListView<String> bookedPackageList;

    @FXML
    private Text errorText;

    @FXML
    private TextField destinationNameField;

    @FXML
    private TextField packageNameField;

    @FXML
    private TextField priceField;

    @FXML
    private DatePicker fromDateField;

    @FXML
    private DatePicker toDateField;

    @FXML
    public void initialize() {
        destinationNameField.textProperty().addListener((obs, oldText, newText) -> {
            filterDestinationName();
        });
        packageNameField.textProperty().addListener((obs, oldText, newText) -> {
            filterPackageName();
        });
        priceField.textProperty().addListener((obs, oldText, newText) -> {
            filterPrice();
        });
        fromDateField.valueProperty().addListener((observable, oldValue, newValue) -> {
            filterByDate();
        });
        toDateField.valueProperty().addListener((observable, oldValue, newValue) -> {
            filterByDate();
        });
    }

    @FXML
    public void bookPackage() {
        String packName = availablePackageList.getSelectionModel().getSelectedItem();
        if (packName != null) {
            Package pack = packageController.getAll()
                    .stream()
                    .filter(p -> packName.split("\\[")[0].equals(p.getName()))
                    .findFirst()
                    .orElse(null);
            System.out.println(packName.split("\\[")[0]);
            if (pack != null) {
                System.out.println(pack.getPackage_status() + " " + pack.getName());
                if (pack.getPackage_status() != PACKAGE_STATUS.BOOKED) {
                    pack.setAvailableSpots(pack.getAvailableSpots() - 1);
                    if (pack.getAvailableSpots() == 0) {
                        pack.setPackage_status(PACKAGE_STATUS.BOOKED);
                    } else if (pack.getPackage_status() == PACKAGE_STATUS.NOT_BOOKED) {
                        pack.setPackage_status(PACKAGE_STATUS.IN_PROGRESS);
                    }
                    packageController.updateStatus(pack);

                    pack.getUsers().add(user);
                    user.getBookedPackages().add(pack);
                    userController.update(user);

                    listBookedPackages();
                    listAvailablePackages();
                } else {
                    logInfo(errorText, "Package not available");
                }

            } else {
                logInfo(errorText, "Please select a package first");
            }
        } else {
            logInfo(errorText, "Please select a package first");
        }
    }

    @FXML
    public void listAvailablePackages() {
        ArrayList<Package> packages = new ArrayList<>(user.getBookedPackages());
        availablePackageList.setItems(FXCollections.observableArrayList(
                packageController.getAll()
                        .stream()
                        .filter(
                                p -> packages.stream().noneMatch(pack -> pack.getId().equals(p.getId()))
                        )
                        .filter(
                                p -> p.getPackage_status() != PACKAGE_STATUS.BOOKED
                        )
                        .map(Package::toStringUserLevel)
                        .collect(Collectors.toList())
        ));
    }

    @FXML
    public void listPackagesForDestination() {
        Destination destination = availableDestinationList.getSelectionModel().getSelectedItem();
        ArrayList<Package> packages = new ArrayList<>(user.getBookedPackages());
        if (destination != null) {
            availablePackageList.setItems(FXCollections.observableArrayList(
                    packageController.getAll()
                            .stream()
                            .filter(pack -> pack.getDestination().getId().equals(destination.getId())
                                    && pack.getPackage_status() != PACKAGE_STATUS.BOOKED
                                    && packages.stream().noneMatch(p -> p.getId().equals(pack.getId())))
                            .map(Package::toStringUserLevel)
                            .collect(Collectors.toList())
            ));
        }
    }

    @FXML
    public void listBookedPackages() {
        ArrayList<Package> bookedPackages = new ArrayList<>(user.getBookedPackages());
        ObservableList<String> items = FXCollections.observableArrayList();
        for (Package pack : bookedPackages) {
            items.add(packageController.findById(pack.getId()).toStringUserLevel());
        }
        bookedPackageList.setItems(items);
    }

    @FXML
    public void listDestinations() {
        availableDestinationList.setItems(FXCollections.observableArrayList(destinationController.getAll()));
    }

    public ArrayList<Package> getAllAvailablePackagesForUser() {
        ArrayList<Package> bookedPackages = new ArrayList<>(user.getBookedPackages());
        return packageController.getAll()
                .stream()
                .filter(pack -> bookedPackages.stream().noneMatch(p -> p.getId().equals(pack.getId()))
                        && pack.getPackage_status() != PACKAGE_STATUS.BOOKED)
                .collect(Collectors.toCollection(ArrayList::new));
    }

    @FXML
    public void filterDestinationName() {
        String destinationName = destinationNameField.getText();
        if (destinationName != null) {
            availableDestinationList.setItems(FXCollections.observableArrayList(
                    destinationController.getAll()
                            .stream()
                            .filter(destination -> destination.getName().contains(destinationName))
                            .collect(Collectors.toList())
            ));
        }
    }

    @FXML
    public void filterPackageName() {
        String packageName = packageNameField.getText();
        if (packageName != null) {
            availablePackageList.setItems(FXCollections.observableArrayList(
                    getAllAvailablePackagesForUser()
                            .stream()
                            .filter(pack -> pack.getName().contains(packageName))
                            .map(Package::toStringUserLevel)
                            .collect(Collectors.toList())
            ));
        }
    }

    @FXML
    public void filterPrice() {
        try {
            Integer price = Integer.parseInt(priceField.getText());
            availablePackageList.setItems(FXCollections.observableArrayList(
                    getAllAvailablePackagesForUser()
                            .stream()
                            .filter(pack -> pack.getPrice() <= price)
                            .map(Package::toStringUserLevel)
                            .collect(Collectors.toList())
            ));
        } catch (Exception e) {
            logInfo(errorText, "Invalid price");
        }
    }

    @FXML
    public void filterByDate() {
        if (fromDateField.getValue() != null && toDateField.getValue() != null) {
            Date to = java.sql.Date.valueOf(toDateField.getValue());
            Date from = java.sql.Date.valueOf(fromDateField.getValue());
            if (from.before(to)) {
                availablePackageList.setItems(FXCollections.observableArrayList(
                        getAllAvailablePackagesForUser()
                                .stream()
                                .filter(pack -> pack.getFromDate().after(from)
                                        && pack.getToDate().before(to))
                                .map(Package::toStringUserLevel)
                                .collect(Collectors.toList())
                ));
            } else logInfo(errorText, "Invalid date interval");
        } else if (fromDateField.getValue() != null) {
            Date from = java.sql.Date.valueOf(fromDateField.getValue());
            availablePackageList.setItems(FXCollections.observableArrayList(
                    getAllAvailablePackagesForUser()
                            .stream()
                            .filter(pack -> pack.getFromDate().after(from))
                            .map(Package::toStringUserLevel)
                            .collect(Collectors.toList())
            ));
        } else if (toDateField.getValue() != null) {
            Date to = java.sql.Date.valueOf(toDateField.getValue());
            availablePackageList.setItems(FXCollections.observableArrayList(
                    getAllAvailablePackagesForUser()
                            .stream()
                            .filter(pack -> pack.getFromDate().before(to))
                            .map(Package::toStringUserLevel)
                            .collect(Collectors.toList())
            ));
        }
    }

    @FXML
    public void goBack(ActionEvent actionEvent) {
        changeScene(actionEvent, "/login.fxml");
    }

}
