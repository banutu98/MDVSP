/*
import data.dao.jpa.DepotDAOJpa;
import data.dao.models.Depot;
import data.dao.spec.DepotDAO;
import data.entities.DepotsEntity;
import org.junit.*;
import org.modelmapper.ModelMapper;

import javax.persistence.*;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.ParameterExpression;
import javax.persistence.criteria.Root;

import data.dao.spec.BaseDAOJpa;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

public class JpaTester {

    private DepotDAO depotDAO;
    private final static String TEST_PERSISTENCE_UNIT = "MDVSPTestUnit";

    @Before
    public void setUp(){
        depotDAO = new DepotDAOJpa(TEST_PERSISTENCE_UNIT);
    }

    @Test
    public void testCrud() throws Exception {

        Depot depot1 = new Depot("TestDepot", 100);
        Depot depot2 = new Depot("TestDepot_2", 200);

        depotDAO.create(depot1);
        depotDAO.create(depot2);

        assertEquals(depot1, depotDAO.read(depot1.getId()));
        assertEquals(depot2, depotDAO.read(depot2.getId()));

        depot1.setName("TestDepot_updated");
        depot2.setName("TestDepot_2_updated");
        depotDAO.update(depot1);
        depotDAO.update(depot2);

        assertEquals(depot1, depotDAO.read(depot1.getId()));
        assertEquals(depot2, depotDAO.read(depot2.getId()));


        depotDAO.delete(depot1.getId());
        depotDAO.delete(depot2.getId());

        assertEquals(new Depot(), depotDAO.read(depot1.getId()));
        assertEquals(new Depot(), depotDAO.read(depot2.getId()));
    }

    @Test
    public void testQueries() throws Exception {
        EntityManager em = new DepotDAOJpa(TEST_PERSISTENCE_UNIT).getEntityManager();
        Depot initialDepot = new Depot("JPQL_Test_Depot", 300);
        depotDAO.create(initialDepot);

        TypedQuery<DepotsEntity> query = em.createQuery("SELECT d from DepotsEntity d WHERE d.id=?1",
                DepotsEntity.class);
        query.setParameter(1, initialDepot.getId());

        ModelMapper mapper = new ModelMapper();
        Depot result = new Depot();
        mapper.map(query.getSingleResult(), result);
        assertEquals(initialDepot, result);

        em.close();
    }

    @Test
    public void testCriteriaQuery() throws Exception {
        BaseDAOJpa baseDAOJpa = new DepotDAOJpa(TEST_PERSISTENCE_UNIT);
        EntityManager em = baseDAOJpa.getEntityManager();
        Depot initialDepot = new Depot("CRITERIA_Test_Depot", 400);
        depotDAO.create(initialDepot);

        CriteriaBuilder cb = em.getCriteriaBuilder();

        CriteriaQuery<DepotsEntity> q = cb.createQuery(DepotsEntity.class);
        Root<DepotsEntity> d = q.from(DepotsEntity.class);
        ParameterExpression<Integer> p = cb.parameter(Integer.class);
        q.select(d).where(cb.equal(d.get("id"), p));

        TypedQuery<DepotsEntity> query = em.createQuery(q);
        query.setParameter(p, initialDepot.getId());

        ModelMapper mapper = new ModelMapper();
        Depot result = new Depot();
        mapper.map(query.getSingleResult(), result);
        assertEquals(initialDepot, result);
        em.close();
    }

    @After
    public void cleanUp(){
        depotDAO.deleteAll();
    }

}
*/
