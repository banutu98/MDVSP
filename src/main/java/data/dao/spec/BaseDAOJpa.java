package data.dao.spec;

import org.hibernate.Session;
import org.hibernate.jdbc.Work;
import presentation.backingBeans.SessionBean;

import javax.persistence.*;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

public abstract class BaseDAOJpa extends BaseDAO {
    @PersistenceUnit
    protected EntityManagerFactory emf;

    public BaseDAOJpa() {
        emf = Persistence.createEntityManagerFactory("MDVSPUnit");
    }

    public BaseDAOJpa(String persistenceUnit){
        emf = Persistence.createEntityManagerFactory(persistenceUnit);
    }

    public EntityManager getEntityManager() {
        EntityManager em = emf.createEntityManager();
        em.getTransaction().begin();
        Session session = em.unwrap(Session.class);
        session.doWork(new Work() {
            @Override
            public void execute(Connection connection) throws SQLException {
                SessionBean sessionBean = getSessionBean();
                if (sessionBean != null) {
                    setSchema(connection, sessionBean.getUserName());
                }
            }
        });

        em.getTransaction().commit();
        return em;
    }

    @Override
    protected void finalize() throws Throwable {
        super.finalize();
        emf.close();
    }
}
