package repository;

import model.Destination;
import model.Package;
import model.User;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.Restrictions;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class Repository<T> {

    public static final EntityManagerFactory entityManagerFactory =
            Persistence.createEntityManagerFactory("ro.tutorial.lab.SD");

    private final Class<T> type;

    public Repository(Class<T> type) {
        this.type = type;
    }

    public T findById(Integer id) {
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        entityManager.getTransaction().begin();
        T t = entityManager.find(type, id);
        entityManager.getTransaction().commit();
        entityManager.close();
        return t;
    }

    public List findByFieldId(Destination id) {
        Session session = (Session) entityManagerFactory.createEntityManager().getDelegate();
        Criteria criteria = session.createCriteria(Package.class);
        return criteria.add(Restrictions.eq("destination", id))
                .list();
    }

    public ArrayList<T> getAll() {
        String query = "FROM " + type.getSimpleName();
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        return new ArrayList<T>(entityManager.createQuery(query).getResultList());
    }

    public void persist(T t) throws PersistenceException {
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        entityManager.getTransaction().begin();
        entityManager.persist(t);
        entityManager.getTransaction().commit();
        entityManager.close();
    }

    public void delete(T t) {
        try {
            EntityManager entityManager = entityManagerFactory.createEntityManager();
            entityManager.getTransaction().begin();
            entityManager.remove(entityManager.contains(t) ? t : entityManager.merge(t));
            entityManager.getTransaction().commit();
            entityManager.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public T findByField(String field, String value) {
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        entityManager.getTransaction().begin();
        String query = "FROM " + type.getSimpleName() + " WHERE " + field + " = '" + value + "'";
        try {
            T t = (T) entityManager.createQuery(query).getSingleResult();
            entityManager.getTransaction().commit();
            entityManager.close();
            return t;
        } catch (NoResultException noResultException) {
            return null;
        }
    }

    public void update(T t) {
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        entityManager.getTransaction().begin();
        if (t instanceof Package) {
            Package pack = (Package) t;
            Package existing = entityManager.find(Package.class, pack.getId());
            existing.update(pack);
        } else if (t instanceof Destination) {
            Destination destination = (Destination) t;
            Destination existing = entityManager.find(Destination.class, destination.getId());
            existing.update(destination);
        } else if (t instanceof User) {
            User user = (User) t;
            User existing = entityManager.find(User.class, user.getId());
            existing.setBookedPackages(
                    user.getBookedPackages().stream()
                            .map(pack -> entityManager.contains(pack) ? pack : entityManager.merge(pack))
                            .collect(Collectors.toList())
            );
        }
        entityManager.getTransaction().commit();
        entityManager.close();
    }

}
