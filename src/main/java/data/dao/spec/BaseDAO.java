package data.dao.spec;

import presentation.backingBeans.SessionBean;

import javax.faces.context.FacesContext;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

public abstract class BaseDAO {

    protected Connection connection;

    protected SessionBean getSessionBean() {
        FacesContext facesContext = FacesContext.getCurrentInstance();

        return (SessionBean) facesContext.getApplication()
                .createValueBinding("#{sessionBean}").getValue(facesContext);
    }

    protected void setSchema(Connection connection, String name) {
        if (name == null || name.isEmpty()) {
            name = "default_schema";
        }

        if (!name.equals(getSessionBean().getCurrentSchema())) {
            try (Statement stmt = connection.createStatement()) {
                stmt.executeUpdate("USE " + name);
                getSessionBean().setCurrentSchema(name);
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
}
