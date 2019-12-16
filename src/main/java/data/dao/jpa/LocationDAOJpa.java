package data.dao.jpa;

import data.dao.models.Location;
import data.dao.spec.BaseDAOJpa;
import data.dao.spec.LocationDAO;
import data.entities.LocationsEntity;
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
public class LocationDAOJpa extends BaseDAOJpa implements LocationDAO {

    @Inject
    private EntityManager em;

    @Override
    public void create(Location location) {
        ModelMapper mapper = new ModelMapper();
        LocationsEntity locationsEntity = new LocationsEntity();
        mapper.map(location, locationsEntity);
        em.persist(locationsEntity);
    }

    @Override
    public Location read(int id) {
        LocationsEntity locationsEntity = em.find(LocationsEntity.class, id);
        ModelMapper mapper = new ModelMapper();
        Location location = new Location();
        mapper.map(location, locationsEntity);
        return location;
    }

    @Override
    public List<Location> readAll() {
        TypedQuery<LocationsEntity> query = em.createQuery("SELECT d from LocationsEntity d", LocationsEntity.class);
        List<LocationsEntity> locationsEntities = query.getResultList();
        List<Location> locations = new ArrayList<>();
        ModelMapper mapper = new ModelMapper();
        for (LocationsEntity e : locationsEntities) {
            Location location = new Location();
            mapper.map(e, location);
            locations.add(location);
        }
        return locations;
    }

    @Override
    public void update(Location location) {
        LocationsEntity old = em.find(LocationsEntity.class, location.getLocationId());
        old.setX(location.getX());
        old.setY(location.getY());
        em.persist(old);
    }

    @Override
    public void delete(int id) {
        LocationsEntity location = em.find(LocationsEntity.class, id);
        em.remove(location);
    }

    @Override
    public void deleteAll() {
        Query query = em.createQuery("DELETE FROM LocationsEntity");
        query.executeUpdate();
    }

    @Override
    public int getId(Location location) {
        LocationsEntity locationsEntity = em.find(LocationsEntity.class, location.getLocationId());
        if (locationsEntity != null) {
            return locationsEntity.getLocationId();
        }
        return 0;
    }

    public LocationDAOJpa() {
        super();
    }
}
