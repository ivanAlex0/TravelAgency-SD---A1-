package service;

import model.Destination;
import model.Package;
import model.enums.PACKAGE_STATUS;
import model.enums.PERSIST_RESPONSE;
import repository.Repository;

import javax.persistence.PersistenceException;
import java.sql.Date;
import java.time.LocalDate;

public class PackageService extends Service<Package> {

    public PackageService(Repository<Package> packageRepository) {
        super(packageRepository);
    }

    //
    //delete and getAll are in Service
    //

    public PERSIST_RESPONSE addPackage(String name, String priceParameter, LocalDate from, LocalDate to, String extraDetails, String availableSpotsParameter, Destination destination) {
        PERSIST_RESPONSE validation_response = validate(name, priceParameter, availableSpotsParameter, from, to);

        if (validation_response != PERSIST_RESPONSE.SUCCESS)
            return validation_response;

        //Duplicate name validation
        if (getAll().stream().anyMatch(p -> p.getName().equals(name)))
            return PERSIST_RESPONSE.DUPLICATE_ENTITY;

        int price = Integer.parseInt(priceParameter);
        int availableSpots = Integer.parseInt(availableSpotsParameter);
        Date fromDate = java.sql.Date.valueOf(from);
        Date toDate = java.sql.Date.valueOf(to);

        if (name != null && price > 0 && availableSpots > 0) {
            Package pack = new Package(name, price, fromDate, toDate, extraDetails, availableSpots, destination);
            try {
                repository.persist(pack);
                return PERSIST_RESPONSE.SUCCESS;
            } catch (PersistenceException e) {
                e.printStackTrace();
                return PERSIST_RESPONSE.DUPLICATE_ENTITY;
            }
        } else {
            return PERSIST_RESPONSE.INVALID_INPUT;
        }
    }

    public PERSIST_RESPONSE updateStatus(Package pack) {
        try {
            repository.update(pack);
            return PERSIST_RESPONSE.SUCCESS;
        } catch (PersistenceException e) {
            e.printStackTrace();
            return PERSIST_RESPONSE.DUPLICATE_ENTITY;
        }
    }

    public PERSIST_RESPONSE update(String name, String priceParameter, LocalDate from, LocalDate to, String extraDetails, String availableSpotsParameter, Destination destination, PACKAGE_STATUS status, Integer id) {
        PERSIST_RESPONSE validation_response = validate(name, priceParameter, availableSpotsParameter, from, to);

        if (validation_response != PERSIST_RESPONSE.SUCCESS)
            return validation_response;

        if (getAll().stream().noneMatch(p -> p.getName().equals(name)))
            return PERSIST_RESPONSE.DUPLICATE_ENTITY;

        int price = Integer.parseInt(priceParameter);
        int availableSpots = Integer.parseInt(availableSpotsParameter);
        Date fromDate = java.sql.Date.valueOf(from);
        Date toDate = java.sql.Date.valueOf(to);

        if (price > 0 && availableSpots > 0) {
            try {
                Package pack = new Package(name, price, fromDate, toDate, extraDetails, availableSpots, destination);
                pack.setId(id);
                pack.setPackage_status(status);
                repository.update(pack);
                return PERSIST_RESPONSE.SUCCESS;
            } catch (PersistenceException e) {
                e.printStackTrace();
                return PERSIST_RESPONSE.DUPLICATE_ENTITY;
            }
        } else {
            return PERSIST_RESPONSE.INVALID_INPUT;
        }
    }

    public PERSIST_RESPONSE validate(String name, String priceParameter, String availableSpotsParameter, LocalDate from, LocalDate to) {
        //Integer parsing validation
        int price, availableSpots;
        try {
            price = Integer.parseInt(priceParameter);
            availableSpots = Integer.parseInt(availableSpotsParameter);
        } catch (NumberFormatException numberFormatException) {
            return PERSIST_RESPONSE.INVALID_NUMBERS;
        }

        //Date validation
        Date fromDate, toDate;
        if (from == null || to == null) {
            return PERSIST_RESPONSE.NULL_DATE;
        } else {
            fromDate = java.sql.Date.valueOf(from);
            toDate = java.sql.Date.valueOf(to);
            if (fromDate.after(toDate))
                return PERSIST_RESPONSE.INVALID_DATE;
        }

        return PERSIST_RESPONSE.SUCCESS;
    }
}
