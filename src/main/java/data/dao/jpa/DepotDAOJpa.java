package data.dao.jpa;

import data.dao.models.Depot;
import data.dao.spec.BaseDAOJpa;
import data.dao.spec.DepotDAO;
import data.entities.DepotsEntity;
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
public class DepotDAOJpa extends BaseDAOJpa implements DepotDAO {

    @Inject
    private EntityManager em;

    @Override
    public void create(Depot depot) {
        ModelMapper mapper = new ModelMapper();
        DepotsEntity depotsEntity = new DepotsEntity();
        mapper.map(depot, depotsEntity);
        em.persist(depotsEntity);
        depot.setId(depotsEntity.getDepotId());
    }

    @Override
    public Depot read(int id) {
        DepotsEntity depotEntity = em.find(DepotsEntity.class, id);
        ModelMapper mapper = new ModelMapper();
        Depot depot = new Depot();
        if (depotEntity != null) {
            mapper.map(depotEntity, depot);
        }
        return depot;
    }

    @Override
    public List<Depot> readAll() {
        TypedQuery<DepotsEntity> query = em.createQuery("SELECT d from DepotsEntity d", DepotsEntity.class);
        List<DepotsEntity> depotsEntity = query.getResultList();
        List<Depot> depots = new ArrayList<>();
        ModelMapper mapper = new ModelMapper();
        for (DepotsEntity e : depotsEntity) {
            Depot depot = new Depot();
            mapper.map(e, depot);
            depots.add(depot);
        }
        return depots;
    }

    @Override
    public void update(Depot depot) {
        DepotsEntity old = em.find(DepotsEntity.class, depot.getId());
        old.setCapacity(depot.getCapacity());
        old.setName(depot.getName());
        em.persist(old);
    }

    @Override
    public void delete(int id) {
        DepotsEntity depot = em.find(DepotsEntity.class, id);
        em.remove(depot);
    }

    @Override
    public void deleteAll() {
        Query query = em.createQuery("DELETE FROM DepotsEntity");
        query.executeUpdate();
    }

    public DepotDAOJpa() {
        super();
    }
}
