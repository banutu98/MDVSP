package data.dao.jpa;

import data.dao.models.Driver;
import data.dao.spec.BaseDAOJpa;
import data.dao.spec.DriversDAO;
import data.entities.CustomersEntity;
import data.entities.DriversEntity;
import data.entities.TripsEntity;
import data.mappers.DriverMapper;
import org.modelmapper.ModelMapper;

import javax.annotation.PostConstruct;
import javax.ejb.Stateless;
import javax.enterprise.inject.Default;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import java.util.ArrayList;
import java.util.List;

@Stateless
@Default
public class DriversDAOJpa extends BaseDAOJpa implements DriversDAO {

    @Inject
    private EntityManager em;

    @Override
    public void create(Driver driver) {
        em.persist(DriverMapper.fromModelToEntity(driver));
    }

    @Override
    public Driver read(int id) {
        DriversEntity entity = em.find(DriversEntity.class, id);
        return DriverMapper.fromEntityToModel(entity);
    }

    @Override
    public void update(Driver driver) {
        em.persist(DriverMapper.fromModelToEntity(driver));
    }

    @Override
    public void delete(Driver driver) {
        em.remove(em.find(DriversEntity.class, driver.getDriverId()));
    }

    @Override
    public List<Driver> readAll() {
        TypedQuery<DriversEntity> query = em.createQuery("SELECT d from DriversEntity d", DriversEntity.class);
        List<DriversEntity> driversEntity = query.getResultList();
        List<Driver> drivers = new ArrayList<>();
        ModelMapper mapper = new ModelMapper();
        for (DriversEntity e : driversEntity) {
            Driver driver = new Driver();
            mapper.map(e, driver);
            drivers.add(driver);
        }
        return drivers;
    }

    public DriversDAOJpa() {
        super();
    }
}
