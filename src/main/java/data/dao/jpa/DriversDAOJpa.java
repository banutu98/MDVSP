package data.dao.jpa;

import data.dao.models.Driver;
import data.dao.spec.BaseDAOJpa;
import data.dao.spec.DriversDAO;
import data.entities.CustomersEntity;
import data.entities.DriversEntity;
import data.entities.TripsEntity;
import data.mappers.DriverMapper;
import org.modelmapper.ModelMapper;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import java.util.ArrayList;
import java.util.List;

public class DriversDAOJpa extends BaseDAOJpa implements DriversDAO {

    @Override
    public void create(Driver driver) {
        persistToDatabase(driver);
    }

    private void persistToDatabase(Driver driver) {
        EntityManager em = getEntityManager();
        em.getTransaction().begin();
        em.persist(DriverMapper.fromModelToEntity(driver));
        em.getTransaction().commit();
        em.close();
    }

    @Override
    public Driver read(int id) {
        EntityManager em = getEntityManager();
        em.getTransaction().begin();
        DriversEntity entity = em.find(DriversEntity.class, id);
        em.getTransaction().commit();
        em.close();
        return DriverMapper.fromEntityToModel(entity);
    }

    @Override
    public void update(Driver driver) {
        persistToDatabase(driver);
    }

    @Override
    public void delete(Driver driver) {
        EntityManager em = getEntityManager();
        em.getTransaction().begin();
        em.remove(em.find(DriversEntity.class, driver.getDriverId()));
        em.getTransaction().commit();
        em.close();
    }

    @Override
    public List<Driver> readAll() {
        EntityManager em = getEntityManager();
        em.getTransaction().begin();
        TypedQuery<DriversEntity> query = em.createQuery("SELECT d from DriversEntity d", DriversEntity.class);
        List<DriversEntity> driversEntity = query.getResultList();
        List<Driver> drivers = new ArrayList<>();
        ModelMapper mapper = new ModelMapper();
        for (DriversEntity e : driversEntity) {
            Driver driver = new Driver();
            mapper.map(e, driver);
            drivers.add(driver);
        }
        em.getTransaction().commit();
        em.close();
        return drivers;
    }

    public DriversDAOJpa(String persistenceUnit) {
        super(persistenceUnit);
    }

    public DriversDAOJpa() {
        super();
    }
}
