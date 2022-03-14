package controller;

import model.Destination;
import model.enums.PACKAGE_STATUS;
import model.enums.PERSIST_RESPONSE;
import model.Package;
import service.PackageService;

import java.time.LocalDate;


public class PackageController extends Controller<Package> {

    public PackageController(PackageService packageService) {
        super(packageService);
    }

    public PERSIST_RESPONSE addPackage(String name, String price, LocalDate from, LocalDate to, String extraDetails, String availableSpots, Destination destination) {
        return ((PackageService) service).addPackage(name, price, from, to, extraDetails, availableSpots, destination);
    }

    public PERSIST_RESPONSE update(String name, String price, LocalDate from, LocalDate to, String extraDetails, String availableSpots, Destination destination, PACKAGE_STATUS status, Integer id) {
        return ((PackageService) service).update(name, price, from, to, extraDetails, availableSpots, destination, status, id);
    }

    public PERSIST_RESPONSE updateStatus(Package pack) {
        return ((PackageService) service).updateStatus(pack);
    }
}
