package data.dao.jpa;

import data.dao.models.Depot;
import data.dao.spec.BaseDAOJpa;
import data.dao.spec.DepotDAO;
import data.entities.DepotsEntity;
import org.modelmapper.ModelMapper;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import javax.persistence.TypedQuery;
import java.util.ArrayList;
import java.util.List;

public class DepotDAOJpa extends BaseDAOJpa implements DepotDAO {
    @Override
    public void create(Depot depot) {
        EntityManager em = getEntityManager();
        em.getTransaction().begin();
        ModelMapper mapper = new ModelMapper();
        DepotsEntity depotsEntity = new DepotsEntity();
        mapper.map(depot, depotsEntity);
        em.persist(depotsEntity);
        em.getTransaction().commit();
        em.close();
    }

    @Override
    public Depot read(int id) {
        EntityManager em = getEntityManager();
        em.getTransaction().begin();
        DepotsEntity depotEntity = em.find(DepotsEntity.class, id);
        ModelMapper mapper = new ModelMapper();
        Depot depot = new Depot();
        mapper.map(depot, depotEntity);
        em.getTransaction().commit();
        em.close();
        return depot;
    }

    @Override
    public List<Depot> readAll() {
        EntityManager em = getEntityManager();
        em.getTransaction().begin();
        TypedQuery<DepotsEntity> query = em.createQuery("SELECT d from DepotsEntity d", DepotsEntity.class);
        List<DepotsEntity> depotsEntity = query.getResultList();
        List<Depot> depots = new ArrayList<>();
        ModelMapper mapper = new ModelMapper();
        for (DepotsEntity e : depotsEntity) {
            Depot depot = new Depot();
            mapper.map(e, depot);
            depots.add(depot);
        }
        em.getTransaction().commit();
        em.close();
        return depots;
    }

    @Override
    public void update(Depot depot) {
        EntityManager em = getEntityManager();
        em.getTransaction().begin();
        DepotsEntity old = em.find(DepotsEntity.class, depot.getId());
        old.setCapacity(depot.getCapacity());
        old.setName(depot.getName());
        em.persist(old);
        em.getTransaction().commit();
        em.close();
    }

    @Override
    public void delete(int id) {
        EntityManager em = getEntityManager();
        em.getTransaction().begin();
        DepotsEntity depot = em.find(DepotsEntity.class, id);
        em.remove(depot);
        em.getTransaction().commit();
        em.close();
    }

    @Override
    public void deleteAll() {
        EntityManager em = getEntityManager();
        em.getTransaction().begin();
        Query query = em.createQuery("DELETE FROM DepotsEntity");
        query.executeUpdate();
        em.getTransaction().commit();
        em.close();
    }
}
