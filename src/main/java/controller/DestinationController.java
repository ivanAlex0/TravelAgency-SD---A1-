package controller;

import model.Destination;
import model.enums.PERSIST_RESPONSE;
import service.DestinationService;

import java.util.ArrayList;

public class DestinationController extends Controller<Destination> {

    public DestinationController(DestinationService destinationService) {
        super(destinationService);
    }

    public ArrayList<Destination> getAll() {
        ArrayList<Destination> destinations = service.getAll();
        for (Destination destination : destinations) {
            destination.setPackages(((DestinationService) service).getRelatedPackages(destination));
        }
        return destinations;
    }

    public PERSIST_RESPONSE addDestination(String destination) {
        return ((DestinationService) service).addDestination(destination);
    }
}
