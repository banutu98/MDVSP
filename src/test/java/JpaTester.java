import data.dao.jpa.DepotDAOJpa;
import data.dao.models.Depot;
import data.dao.spec.DepotDAO;
import data.entities.DepotsEntity;
import org.modelmapper.ModelMapper;
import org.testng.annotations.Test;

import javax.persistence.*;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.ParameterExpression;
import javax.persistence.criteria.Root;

import data.dao.spec.BaseDAOJpa;

import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

public class JpaTester {
    @PersistenceUnit
    protected EntityManagerFactory emf;

    @Test
    public void TestCrud() throws Exception {
        emf = Persistence.createEntityManagerFactory("MDVSPTestUnit");
        EntityManager em = emf.createEntityManager();

        DepotDAO depotDAO = new DepotDAOJpa();
        try {
            Depot depot1 = new Depot(1000, "TestDepot", 100);
            Depot depot2 = new Depot(2000, "TestDepot_2", 200);
            depotDAO.create(depot1);
            depotDAO.create(depot2);

            assertEquals(depot1, depotDAO.read(1000));
            assertEquals(depot2, depotDAO.read(2000));

            depot1.setName("TestDepot_updated");
            depot2.setName("TestDepot_2_updated");
            depotDAO.update(depot1);
            depotDAO.update(depot2);

            assertEquals(depot1, depotDAO.read(1000));
            assertEquals(depot2, depotDAO.read(2000));


            depotDAO.delete(1000);
            depotDAO.delete(2000);

            assertNull(depotDAO.read(1000));
            assertNull(depotDAO.read(2000));
        } finally {
            em.getTransaction().rollback();
            em.close();
        }
    }

    @Test
    public void TestQueries() throws Exception {
        BaseDAOJpa baseDAOJpa = new DepotDAOJpa();
        EntityManager em = baseDAOJpa.getEntityManager();

        DepotDAO depotDAO = new DepotDAOJpa();
        try {
            Depot initialDepot = new Depot(3000, "JPQL_Test_Depot", 300);
            depotDAO.create(initialDepot);

            TypedQuery<DepotsEntity> query = em.createQuery("SELECT d from DepotsEntity d WHERE d.id=?1",
                    DepotsEntity.class);
            query.setParameter(1, 3000);

            ModelMapper mapper = new ModelMapper();
            Depot result = new Depot();
            mapper.map(query.getSingleResult(), result);
            assertEquals(initialDepot, result);

            TypedQuery<DepotsEntity> deleteQuery = em.createQuery("DELETE from DepotsEntity d WHERE d.id=?1",
                    DepotsEntity.class);
            int deletedCount = deleteQuery.setParameter(1, 3000).executeUpdate();
            assertEquals(deletedCount, 1);
        } finally {
            em.getTransaction().rollback();
            em.close();
        }
    }

    @Test
    public void TestCriteriaQuery() throws Exception {
        BaseDAOJpa baseDAOJpa = new DepotDAOJpa();
        EntityManager em = baseDAOJpa.getEntityManager();

        DepotDAO depotDAO = new DepotDAOJpa();
        try {
            Depot initialDepot = new Depot(4000, "CRITERIA_Test_Depot", 400);
            depotDAO.create(initialDepot);

            CriteriaBuilder cb = em.getCriteriaBuilder();

            CriteriaQuery<DepotsEntity> q = cb.createQuery(DepotsEntity.class);
            Root<DepotsEntity> d = q.from(DepotsEntity.class);
            ParameterExpression<Integer> p = cb.parameter(Integer.class);
            q.select(d).where(cb.equal(d.get("id"), p));

            TypedQuery<DepotsEntity> query = em.createQuery(q);
            query.setParameter(p, 4000);

            ModelMapper mapper = new ModelMapper();
            Depot result = new Depot();
            mapper.map(query.getSingleResult(), result);
            assertEquals(initialDepot, result);
        } finally {
            em.getTransaction().rollback();
            em.close();
        }
    }
}
