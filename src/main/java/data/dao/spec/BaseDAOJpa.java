package data.dao.spec;

import javax.persistence.*;
import java.util.HashMap;
import java.util.Map;

public abstract class BaseDAOJpa extends BaseDAO {
    @PersistenceUnit
    protected EntityManagerFactory emf;

    public BaseDAOJpa() {
        EntityManagerFactory managerFactory = null;
        Map<String, String> persistenceMap = new HashMap<String, String>();

        persistenceMap.put("javax.persistence.jdbc.url", "jdbc:mysql://localhost:3306/" + getSessionBean().getUserName());
        persistenceMap.put("javax.persistence.jdbc.user", "banutu98");
        persistenceMap.put("javax.persistence.jdbc.password", "<pass>");
        persistenceMap.put("javax.persistence.jdbc.driver", "com.mysql.cj.jdbc.Driver");

        emf = Persistence.createEntityManagerFactory("MDVSPUnit", persistenceMap);
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
