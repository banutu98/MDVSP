package data.dao.jpa;

import org.hibernate.Session;
import org.hibernate.jdbc.Work;
import presentation.backingBeans.SessionBean;

import javax.enterprise.inject.Default;
import javax.enterprise.inject.Disposes;
import javax.enterprise.inject.Produces;
import javax.faces.bean.ApplicationScoped;
import javax.faces.context.FacesContext;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.PersistenceUnit;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

@ApplicationScoped
public class ResourceProducer {

   @PersistenceUnit(unitName = "MDVSPUnit")
   private EntityManagerFactory emf;

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

    private SessionBean getSessionBean() {
        FacesContext facesContext = FacesContext.getCurrentInstance();

        if (facesContext != null) {
            return (SessionBean) facesContext.getApplication()
                    .createValueBinding("#{sessionBean}").getValue(facesContext);
        }
        return null;
    }

    private void setSchema(Connection connection, String name) {
        if (name == null || name.isEmpty()) {
            name = "default_schema";
        }

        try (Statement stmt = connection.createStatement()) {
            stmt.executeUpdate("USE " + name);
            getSessionBean().setCurrentSchema(name);
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    @Produces
    @Default
    public EntityManager create(){
        EntityManager em = emf.createEntityManager();
        setSchemaOnEntityManager(em);
        return em;
    }

    public void dispose(@Disposes @Default EntityManager em){
        em.close();
    }
}
