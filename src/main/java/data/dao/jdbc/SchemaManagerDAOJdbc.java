package data.dao.jdbc;

import data.dao.spec.BaseDAOJdbc;
import data.dao.spec.SchemaManagerDAO;
import org.apache.ibatis.jdbc.ScriptRunner;
import presentation.backingBeans.SessionBean;

import javax.annotation.PostConstruct;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import java.io.*;
import java.sql.SQLException;
import java.sql.Statement;

@Stateless
public class SchemaManagerDAOJdbc extends BaseDAOJdbc implements SchemaManagerDAO {

    private final String scriptPath = "C:\\Users\\tatu georgian\\Desktop\\MDVSP\\src\\main\\resources\\sql\\createTables.sql";

    @Override
    @TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
    public void createSchema(String name) {
        try (Statement stmt = connection.createStatement()) {
            stmt.executeUpdate("DROP SCHEMA IF EXISTS " + name);
            stmt.executeUpdate("CREATE SCHEMA " + name);
            setSchema(connection, name);
            runScript();
            connection.commit();
        } catch (SQLException | FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    private void runScript() throws FileNotFoundException {
        Reader reader = new BufferedReader(new FileReader(scriptPath));
        //Running the script
        new ScriptRunner(connection).runScript(reader);
    }

    @PostConstruct
    public void init(){
        try {
            connection = dataSource.getConnection();
            setSchema(connection, getSessionBean().getUserName());
        } catch (SQLException  e) {
            e.printStackTrace();
        }
    }
}
