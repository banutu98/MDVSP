package data.dao.jpa;

import data.dao.models.Trip;
import data.dao.spec.BaseDAOJpa;
import data.dao.spec.TripDAO;
import data.entities.TripsEntity;
import org.modelmapper.ModelMapper;

import javax.annotation.PostConstruct;
import javax.ejb.Stateless;
import javax.enterprise.inject.Default;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.Query;
import javax.persistence.TypedQuery;
import java.util.ArrayList;
import java.util.List;

@Stateless
@Default
public class TripDAOJpa extends BaseDAOJpa implements TripDAO {

    @Inject
    private EntityManager em;

    @Override
    public void create(Trip trip) {
        ModelMapper mapper = new ModelMapper();
        TripsEntity tripsEntity = new TripsEntity();
        mapper.map(trip, tripsEntity);
        em.persist(tripsEntity);
    }

    @Override
    public Trip read(int id) {
        TripsEntity tripsEntity = em.find(TripsEntity.class, id);
        ModelMapper mapper = new ModelMapper();
        Trip trip = new Trip();
        mapper.map(trip, tripsEntity);
        return trip;
    }

    @Override
    public List<Trip> readAll() {
        TypedQuery<TripsEntity> query = em.createQuery("SELECT d from TripsEntity d", TripsEntity.class);
        List<TripsEntity> tripsEntity = query.getResultList();
        List<Trip> trips = new ArrayList<>();
        ModelMapper mapper = new ModelMapper();
        for (TripsEntity e : tripsEntity) {
            Trip trip = new Trip();
            mapper.map(e, trip);
            trips.add(trip);
        }
        return trips;
    }

    @Override
    public void update(Trip trip) {
        TripsEntity old = em.find(TripsEntity.class, trip.getId());
        old.setStartTime(trip.getStartTime().toString());
        old.setDuration(trip.getDuration());
    }

    @Override
    public void delete(int id) {
        TripsEntity tripsEntity = em.find(TripsEntity.class, id);
        em.remove(tripsEntity);
    }

    @Override
    public void deleteAll() {
        Query query = em.createQuery("DELETE FROM TripsEntity");
        query.executeUpdate();
    }

    @Override
    public int getId(Trip trip) {
        TripsEntity tripsEntity = em.find(TripsEntity.class, trip.getId());
        if (tripsEntity != null) {
            return tripsEntity.getTripId();
        }
        return 0;
    }

    public TripDAOJpa() {
        super();
    }
}
