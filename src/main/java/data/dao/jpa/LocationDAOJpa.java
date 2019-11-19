package data.dao.jpa;

import data.dao.models.Location;
import data.dao.spec.BaseDAOJpa;
import data.dao.spec.LocationDAO;
import data.entities.LocationsEntity;
import org.modelmapper.ModelMapper;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import javax.persistence.TypedQuery;
import java.util.ArrayList;
import java.util.List;

public class LocationDAOJpa extends BaseDAOJpa implements LocationDAO {
    @Override
    public void create(Location location) {
        EntityManager em = getEntityManager();
        em.getTransaction().begin();
        ModelMapper mapper = new ModelMapper();
        LocationsEntity locationsEntity = new LocationsEntity();
        mapper.map(location, locationsEntity);
        em.persist(locationsEntity);
        em.getTransaction().commit();
        em.close();
    }

    @Override
    public Location read(int id) {
        EntityManager em = getEntityManager();
        em.getTransaction().begin();
        LocationsEntity locationsEntity = em.find(LocationsEntity.class, id);
        ModelMapper mapper = new ModelMapper();
        Location location = new Location();
        mapper.map(location, locationsEntity);
        em.getTransaction().commit();
        em.close();
        return location;
    }

    @Override
    public List<Location> readAll() {
        EntityManager em = getEntityManager();
        em.getTransaction().begin();
        TypedQuery<LocationsEntity> query = em.createQuery("SELECT d from LocationsEntity d", LocationsEntity.class);
        List<LocationsEntity> locationsEntities = query.getResultList();
        List<Location> locations = new ArrayList<>();
        ModelMapper mapper = new ModelMapper();
        for (LocationsEntity e : locationsEntities) {
            Location location = new Location();
            mapper.map(e, location);
            locations.add(location);
        }
        em.getTransaction().commit();
        em.close();
        return locations;
    }

    @Override
    public void update(Location location) {
        EntityManager em = getEntityManager();
        em.getTransaction().begin();
        LocationsEntity old = em.find(LocationsEntity.class, location.getLocationId());
        old.setX(location.getX());
        old.setY(location.getY());
        em.persist(old);
        em.getTransaction().commit();
        em.close();
    }

    @Override
    public void delete(int id) {
        EntityManager em = getEntityManager();
        em.getTransaction().begin();
        LocationsEntity location = em.find(LocationsEntity.class, id);
        em.remove(location);
        em.getTransaction().commit();
        em.close();
    }

    @Override
    public void deleteAll() {
        EntityManager em = getEntityManager();
        em.getTransaction().begin();
        Query query = em.createQuery("DELETE FROM LocationsEntity");
        query.executeUpdate();
        em.getTransaction().commit();
        em.close();
    }

    @Override
    public int getId(Location location) {
        EntityManager em = getEntityManager();
        em.getTransaction().begin();
        LocationsEntity locationsEntity = em.find(LocationsEntity.class, location.getLocationId());
        em.getTransaction().commit();
        if (locationsEntity != null) {
            return locationsEntity.getLocationId();
        }
        return 0;
    }
}
