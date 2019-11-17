package data.dao.jpa;

import data.dao.models.Association;
import data.dao.models.Depot;
import data.dao.models.Location;
import data.dao.models.Trip;
import data.dao.spec.AssociationDAO;
import data.dao.spec.BaseDAOJpa;
import data.entities.AssociationsEntity;
import data.entities.DepotsEntity;
import data.entities.LocationsEntity;
import data.entities.TripsEntity;
import org.modelmapper.ModelMapper;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import javax.persistence.TypedQuery;
import java.util.ArrayList;
import java.util.List;

public class AssociationDAOJpa extends BaseDAOJpa implements AssociationDAO {
    @Override
    public void create(Association association) {
        EntityManager em = getEntityManager();
        em.getTransaction().begin();
        ModelMapper mapper = new ModelMapper();
        AssociationsEntity associationsEntity = new AssociationsEntity();
        mapper.map(association, associationsEntity);
        em.persist(associationsEntity);
        em.getTransaction().commit();
        em.close();
    }

    @Override
    public Association read(int tripId) {
        EntityManager em = getEntityManager();
        em.getTransaction().begin();
        AssociationsEntity associationsEntity = em.find(AssociationsEntity.class, tripId);
        ModelMapper mapper = new ModelMapper();
        Association association = new Association();
        mapper.map(association, associationsEntity);
        em.close();
        return association;
    }

    @Override
    public List<Association> readAll() {
        EntityManager em = getEntityManager();
        em.getTransaction().begin();
        TypedQuery<AssociationsEntity> query = em.createQuery("SELECT d from AssociationsEntity d", AssociationsEntity.class);
        List<AssociationsEntity> associationsEntity = query.getResultList();
        List<Association> associations = new ArrayList<>();
        ModelMapper mapper = new ModelMapper();
        for (AssociationsEntity e : associationsEntity) {
            Association association = new Association();
            mapper.map(e, association);
            associations.add(association);
        }
        em.close();
        return associations;
    }

    @Override
    public void update(Association association) {
        EntityManager em = getEntityManager();
        em.getTransaction().begin();
        AssociationsEntity old = em.find(AssociationsEntity.class, association.getTrip().getId());
        ModelMapper mapper = new ModelMapper();
        Trip trip = association.getTrip();
        TripsEntity tripsEntity = new TripsEntity();
        mapper.map(trip, tripsEntity);
        old.setTrip(tripsEntity);

        Location startLocation = association.getStartLocation();
        LocationsEntity startLocationEntity = new LocationsEntity();
        mapper.map(startLocation, startLocationEntity);
        old.setStartLocation(startLocationEntity);

        Location endLocation = association.getStartLocation();
        LocationsEntity endLocationEntity = new LocationsEntity();
        mapper.map(endLocation, endLocationEntity);
        old.setEndLocation(endLocationEntity);

        em.getTransaction().commit();
        em.close();
    }

    @Override
    public void delete(Association association) {
        EntityManager em = getEntityManager();
        em.getTransaction().begin();
        AssociationsEntity associationsEntity = em.find(AssociationsEntity.class, association.getTrip().getId());
        em.remove(associationsEntity);
        em.getTransaction().commit();
        em.close();
    }

    @Override
    public void deleteAll() {
        EntityManager em = getEntityManager();
        em.getTransaction().begin();
        Query query = em.createQuery("DELETE FROM AssociationsEntity");
        query.executeUpdate();
        em.getTransaction().commit();
        em.close();
    }
}
