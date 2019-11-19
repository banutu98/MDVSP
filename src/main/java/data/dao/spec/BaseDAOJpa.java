package data.dao.spec;

import javax.persistence.*;
import java.util.HashMap;
import java.util.Map;

public abstract class BaseDAOJpa extends BaseDAO {
    @PersistenceUnit
    protected EntityManagerFactory emf;

    public BaseDAOJpa() {
        EntityManagerFactory managerFactory = null;
        emf = Persistence.createEntityManagerFactory("MDVSPUnit");
    }

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    @Override
    protected void finalize() throws Throwable {
        super.finalize();
        emf.close();
    }
}
