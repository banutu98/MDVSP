package data.dao.spec;

import org.hibernate.Session;
import org.hibernate.jdbc.Work;
import presentation.backingBeans.SessionBean;

import javax.persistence.*;
import java.sql.Connection;
import java.sql.SQLException;

public abstract class BaseDAOJpa extends BaseDAO {

    public void setSchemaOnEntityManager(EntityManager em) {
        //em.getTransaction().begin();
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

        //em.getTransaction().commit();
    }
}
