import data.dao.jpa.DepotDAOJpa;
import data.dao.models.Depot;
import data.dao.spec.DepotDAO;
import org.junit.Test;

public class DepotDAOJpaTest {

    DepotDAO depotDAO = new DepotDAOJpa();

    @Test
    public void testCreate(){
        Depot depot = new Depot();
        depot.setCapacity(10);
        depot.setName("JPA Depot");
        depotDAO.create(depot);
    }
}
