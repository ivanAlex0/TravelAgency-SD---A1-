package view;

import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.DatePicker;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;
import model.Destination;
import model.Package;
import model.enums.DELETE_RESPONSE;
import model.enums.PERSIST_RESPONSE;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.stream.Collectors;

public class AgencyView extends DIController implements SceneController {

    @FXML
    private ListView<Package> packageList;

    @FXML
    private ListView<Destination> destinationList;

    @FXML
    private TextField destinationField;

    @FXML
    private Text errorText;

    @FXML
    private TextField name;

    @FXML
    private TextField price;

    @FXML
    private DatePicker from;

    @FXML
    private DatePicker to;

    @FXML
    private TextField extraDetails;

    @FXML
    private TextField availableSpots;

    @FXML
    private TextField destination;

    @FXML
    public void listDestinations() {
        ArrayList<Destination> destinations = destinationController.getAll();
        destinationList.setItems(FXCollections.observableArrayList(
                new ArrayList<>(destinations)
        ));
    }

    @FXML
    public void listPackages() {
        ArrayList<Package> packages = packageController.getAll();
        packageList.setItems(FXCollections.observableArrayList(
                packages.stream()
                        .sorted()
                        .collect(Collectors.toList())));
    }

    @FXML
    public void deleteDestination() {
        Destination destination = getDestinationFromField(destinationField.getText());

        if (destination != null) {
            DELETE_RESPONSE deleteResponse = destinationController.delete(destination);
            if (deleteResponse == DELETE_RESPONSE.INVALID_INPUT) {
                logInfo(errorText, "Destination not selected?");
            } else if (deleteResponse == DELETE_RESPONSE.NOT_FOUND) {
                logInfo(errorText, "Database exception thrown - check the logs");
            } else {
                logInfo(errorText, "");
                listDestinations();
            }
        }
    }

    @FXML
    public void addDestination() {
        String destination = destinationField.getText();
        PERSIST_RESPONSE response = destinationController.addDestination(destination);
        if (response == PERSIST_RESPONSE.DUPLICATE_ENTITY) {
            logInfo(errorText, "The destination already exists in the database");
        } else if (response == PERSIST_RESPONSE.INVALID_INPUT) {
            logInfo(errorText, "Invalid input - please add the name for the destination");
        } else {
            logInfo(errorText, "");
            listDestinations();
        }
    }

    @FXML
    public void addPackage() {
        String nameS = name.getText();
        String priceS = price.getText();
        LocalDate fromDate = from.getValue();
        LocalDate toDate = to.getValue();
        String extraD = extraDetails.getText();
        String available = availableSpots.getText();
        String destinationName = destination.getText();

        Destination destination = getDestinationFromField(destinationName);

        if (destination != null) {

            PERSIST_RESPONSE response = packageController.addPackage(nameS, priceS, fromDate, toDate, extraD, available, destination);
            if (response == PERSIST_RESPONSE.DUPLICATE_ENTITY) {
                logInfo(errorText, "Duplicate package name for: " + nameS);
            } else if (response == PERSIST_RESPONSE.INVALID_INPUT) {
                logInfo(errorText, "Some input is invalid");
            } else if (response == PERSIST_RESPONSE.NULL_DATE) {
                logInfo(errorText, "Date you introduced is null");
            } else if (response == PERSIST_RESPONSE.INVALID_DATE) {
                logInfo(errorText, "The date is invalid");
            } else if (response == PERSIST_RESPONSE.INVALID_NUMBERS) {
                logInfo(errorText, "Please check the fields that are numbers");
            } else {
                listPackages();
                logInfo(errorText, "");
            }
        } else {
            logInfo(errorText, "Destination name doesn't exist!");
        }
    }

    @FXML
    public void deletePackage() {
        Package pack = getPackage();

        if (pack != null) {
            DELETE_RESPONSE deleteResponse = packageController.delete(pack);
            if (deleteResponse == DELETE_RESPONSE.INVALID_INPUT) {
                logInfo(errorText, "Package not selected?");
            } else if (deleteResponse == DELETE_RESPONSE.NOT_FOUND) {
                logInfo(errorText, "Database exception - check the logs");
            } else {
                logInfo(errorText, "");
                listPackages();
                listDestinations();
            }
        } else
            logInfo(errorText, "Please select a package first");
    }

    @FXML
    public void updatePackage() {
        Package pack = getPackage();
        if (pack != null) {
            String nameS = name.getText();
            String priceS = price.getText();
            LocalDate fromDate = from.getValue();
            LocalDate toDate = to.getValue();
            String extraD = extraDetails.getText();
            String available = availableSpots.getText();
            String destinationName = destination.getText();

            Destination d = getDestinationFromField(destinationName);

            if (d != null) {
                pack.setDestination(d);

                PERSIST_RESPONSE response = packageController.update(nameS, priceS, fromDate, toDate, extraD, available, d, pack.getPackage_status(), pack.getId());
                if (response == PERSIST_RESPONSE.DUPLICATE_ENTITY) {
                    logInfo(errorText, "Package doesn't exist: " + pack.getName());
                } else if (response == PERSIST_RESPONSE.INVALID_INPUT) {
                    logInfo(errorText, "Some input is invalid");
                } else if (response == PERSIST_RESPONSE.NULL_DATE) {
                    logInfo(errorText, "Date you introduced is null");
                } else if (response == PERSIST_RESPONSE.INVALID_DATE) {
                    logInfo(errorText, "The date is invalid");
                } else if (response == PERSIST_RESPONSE.INVALID_NUMBERS) {
                    logInfo(errorText, "Please check the fields that are numbers");
                } else
                    logInfo(errorText, "");
            } else {
                logInfo(errorText, "Destination does not exist");
            }
        }
    }

    @FXML
    public void selectPack() {
        Package selectedPack = getPackage();
        if (selectedPack != null) {

            name.setText(selectedPack.getName());
            price.setText(selectedPack.getPrice().toString());

            from.setValue(selectedPack.getFromDate().toLocalDate());
            to.setValue(selectedPack.getToDate().toLocalDate());

            extraDetails.setText(selectedPack.getExtraDetails());
            availableSpots.setText(selectedPack.getAvailableSpots().toString());
            destination.setText(selectedPack.getDestination().getName());
        } else {
            logInfo(errorText, "Please select a package");
        }
    }

    @FXML
    public void selectDestination() {
        Destination selectedDestination = getDestination();
        if (selectedDestination != null) {
            destinationField.setText(selectedDestination.getName());
            destination.setText(selectedDestination.getName());
        } else {
            logInfo(errorText, "Please select a destination");
        }
    }

    public Destination getDestinationFromField(String name) {
        ArrayList<Destination> destinations = destinationController.getAll();
        return destinations
                .stream()
                .filter(d -> name.equals(d.getName()))
                .findFirst().orElse(null);
    }

    public Destination getDestination() {
        return destinationList.getSelectionModel().getSelectedItem();
    }

    public Package getPackage() {
        return packageList.getSelectionModel().getSelectedItem();
    }

    @FXML
    public void goBack(ActionEvent actionEvent) {
        changeScene(actionEvent, "/login.fxml");
    }

}
