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

    protected <T> T find(EntityManager em, Class<T> objClass, Object id) {
        try {
            T result = em.find(objClass, id);
            if (result == null) {
                return objClass.newInstance();
            }

            return result;
        } catch (IllegalAccessException | InstantiationException e) {
            e.printStackTrace();
            return null;
        }
    }
}
