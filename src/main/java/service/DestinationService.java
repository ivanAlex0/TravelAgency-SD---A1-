package service;

import model.Destination;
import model.enums.PERSIST_RESPONSE;
import repository.Repository;

import javax.persistence.PersistenceException;
import java.util.List;

public class DestinationService extends Service<Destination> {

    public DestinationService(Repository<Destination> destinationRepository) {
        super(destinationRepository);
    }

    //
    //delete and getAll are in Service
    //

    public List getRelatedPackages(Destination id) {
        return repository.findByFieldId(id);
    }

    public PERSIST_RESPONSE addDestination(String name) {
        if (name != null && !name.equals("")) {
            try {
                repository.persist(new Destination(name));
                return PERSIST_RESPONSE.SUCCESS;
            } catch (PersistenceException e) {
                return PERSIST_RESPONSE.DUPLICATE_ENTITY;
            }
        } else {
            return PERSIST_RESPONSE.INVALID_INPUT;
        }
    }
}
