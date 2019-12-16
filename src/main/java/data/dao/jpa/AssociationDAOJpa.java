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
import data.mappers.TripMapper;
import org.modelmapper.ModelMapper;

import javax.annotation.PostConstruct;
import javax.ejb.Stateless;
import javax.enterprise.inject.Default;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.TypedQuery;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

@Stateless
@Default
public class AssociationDAOJpa extends BaseDAOJpa implements AssociationDAO {

    @Inject
    private EntityManager em;

    @Override
    public void create(Association association) {
        ModelMapper mapper = new ModelMapper();
        AssociationsEntity associationsEntity = new AssociationsEntity();
        mapper.map(association, associationsEntity);
        em.persist(associationsEntity);
    }

    @Override
    public Association read(int tripId) {
        AssociationsEntity associationsEntity = em.find(AssociationsEntity.class, tripId);
        ModelMapper mapper = new ModelMapper();
        Association association = new Association();
        mapper.map(association, associationsEntity);
        return association;
    }

    @Override
    public List<Association> readAll() {
        TypedQuery<AssociationsEntity> query = em.createQuery("SELECT d from AssociationsEntity d", AssociationsEntity.class);
        List<AssociationsEntity> associationsEntity = query.getResultList();
        List<Association> associations = new ArrayList<>();
        ModelMapper mapper = new ModelMapper();
        for (AssociationsEntity e : associationsEntity) {
            Association association = new Association();
            mapper.map(e, association);
            associations.add(association);
        }
        return associations;
    }

    @Override
    public void update(Association association) {
        AssociationsEntity old = em.find(AssociationsEntity.class, association.getTrip().getId());
        Trip trip = association.getTrip();
        TripsEntity tripsEntity = TripMapper.fromModelToEntity(trip);
        old.setTrip(tripsEntity);

        ModelMapper mapper = new ModelMapper();
        Location startLocation = association.getStartLocation();
        LocationsEntity startLocationEntity = new LocationsEntity();
        mapper.map(startLocation, startLocationEntity);
        old.setStartLocation(startLocationEntity);

        Location endLocation = association.getStartLocation();
        LocationsEntity endLocationEntity = new LocationsEntity();
        mapper.map(endLocation, endLocationEntity);
        old.setEndLocation(endLocationEntity);

        em.persist(old);
    }

    @Override
    public void delete(Association association) {
        AssociationsEntity associationsEntity = em.find(AssociationsEntity.class, association.getTrip().getId());
        em.remove(associationsEntity);
    }

    @Override
    public void deleteAll() {
        Query query = em.createQuery("DELETE FROM AssociationsEntity");
        query.executeUpdate();
    }

    public AssociationDAOJpa() {
        super();
    }
}
