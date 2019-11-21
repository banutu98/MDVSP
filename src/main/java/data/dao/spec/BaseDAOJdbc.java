package data.dao.spec;

import presentation.backingBeans.SessionBean;

import javax.faces.context.FacesContext;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

public abstract class BaseDAOJdbc extends BaseDAO {

    public BaseDAOJdbc() {
        try {
            Context context = new InitialContext();
            DataSource ds = (DataSource) context.lookup("java:/comp/env/jdbc/mdvsp");
            connection = ds.getConnection();
            setSchema(connection, getSessionBean().getUserName());
        } catch (SQLException | NamingException e) {
            e.printStackTrace();
        }
    }
}
