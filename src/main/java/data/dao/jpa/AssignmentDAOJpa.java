package data.dao.jpa;

import business.Pair;
import data.dao.spec.AssignmentDAO;
import data.dao.spec.BaseDAOJpa;
import data.entities.AssignmentsEntity;

import javax.ejb.Stateless;
import javax.enterprise.inject.Default;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import java.util.List;

@Stateless
@Default
public class AssignmentDAOJpa extends BaseDAOJpa implements AssignmentDAO {

    @Inject
    private EntityManager em;

    @Override
    public void create(int driverId, int tripId) {
        AssignmentsEntity entity = new AssignmentsEntity();
        entity.setDriverId(driverId);
        entity.setTripId(tripId);
        em.persist(entity);
    }

    @Override
    public List<AssignmentsEntity> findAll() {
        TypedQuery<AssignmentsEntity> query = em.createQuery("select a from AssignmentsEntity a", AssignmentsEntity.class);
        return query.getResultList();
    }
}
