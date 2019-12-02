package data.dao.jpa;

import data.dao.models.Trip;
import data.dao.spec.BaseDAOJpa;
import data.dao.spec.TripDAO;
import data.entities.TripsEntity;
import org.modelmapper.ModelMapper;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import javax.persistence.TypedQuery;
import java.util.ArrayList;
import java.util.List;

public class TripDAOJpa extends BaseDAOJpa implements TripDAO {
    @Override
    public void create(Trip trip) {
        EntityManager em = getEntityManager();
        em.getTransaction().begin();
        ModelMapper mapper = new ModelMapper();
        TripsEntity tripsEntity = new TripsEntity();
        mapper.map(trip, tripsEntity);
        em.persist(tripsEntity);
        em.getTransaction().commit();
        em.close();
    }

    @Override
    public Trip read(int id) {
        EntityManager em = getEntityManager();
        em.getTransaction().begin();
        TripsEntity tripsEntity = em.find(TripsEntity.class, id);
        ModelMapper mapper = new ModelMapper();
        Trip trip = new Trip();
        mapper.map(trip, tripsEntity);
        em.getTransaction().commit();
        em.close();
        return trip;
    }

    @Override
    public List<Trip> readAll() {
        EntityManager em = getEntityManager();
        em.getTransaction().begin();
        TypedQuery<TripsEntity> query = em.createQuery("SELECT d from TripsEntity d", TripsEntity.class);
        List<TripsEntity> tripsEntity = query.getResultList();
        List<Trip> trips = new ArrayList<>();
        ModelMapper mapper = new ModelMapper();
        for (TripsEntity e : tripsEntity) {
            Trip trip = new Trip();
            mapper.map(e, trip);
            trips.add(trip);
        }
        em.getTransaction().commit();
        em.close();
        return trips;
    }

    @Override
    public void update(Trip trip) {
        EntityManager em = getEntityManager();
        em.getTransaction().begin();
        TripsEntity old = em.find(TripsEntity.class, trip.getId());
        old.setStartTime(trip.getStartTime().toString());
        old.setDuration(trip.getDuration());
        em.getTransaction().commit();
        em.close();
    }

    @Override
    public void delete(int id) {
        EntityManager em = getEntityManager();
        em.getTransaction().begin();
        TripsEntity tripsEntity = em.find(TripsEntity.class, id);
        em.remove(tripsEntity);
        em.getTransaction().commit();
        em.close();
    }

    @Override
    public void deleteAll() {
        EntityManager em = getEntityManager();
        em.getTransaction().begin();
        Query query = em.createQuery("DELETE FROM TripsEntity");
        query.executeUpdate();
        em.getTransaction().commit();
        em.close();
    }

    @Override
    public int getId(Trip trip) {
        EntityManager em = getEntityManager();
        em.getTransaction().begin();
        TripsEntity tripsEntity = em.find(TripsEntity.class, trip.getId());
        em.getTransaction().commit();
        if (tripsEntity != null) {
            return tripsEntity.getTripId();
        }
        return 0;
    }

    public TripDAOJpa(String persistenceUnit) {
        super(persistenceUnit);
    }

    public TripDAOJpa() {
        super();
    }
}
