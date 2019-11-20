package data.dao.jpa;

import data.dao.models.Driver;
import data.dao.spec.BaseDAOJpa;
import data.dao.spec.DriversDAO;
import data.entities.CustomersEntity;
import data.entities.DriversEntity;
import data.mappers.DriverMapper;

import javax.persistence.EntityManager;

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
}
