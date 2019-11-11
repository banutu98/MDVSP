package data.dao.jdbc;

import data.dao.spec.BaseDAOJdbc;
import data.dao.spec.SchemaManagerDAO;
import org.apache.ibatis.jdbc.ScriptRunner;
import presentation.backingBeans.SessionBean;

import java.io.*;
import java.sql.SQLException;
import java.sql.Statement;

public class SchemaMaangerDAOJdbc extends BaseDAOJdbc implements SchemaManagerDAO {

    private final String scriptPath = "C:\\Users\\tatu georgian\\Desktop\\MDVSP\\src\\main\\resources\\sql\\createTables.sql";

    @Override
    public void createSchema(String name) {
        try (Statement stmt = connection.createStatement()) {
            stmt.executeUpdate("CREATE SCHEMA " + name);
            setSchema(name);
            runScript();
        } catch (SQLException | FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    private void runScript() throws FileNotFoundException {
        Reader reader = new BufferedReader(new FileReader(scriptPath));
        //Running the script
        new ScriptRunner(connection).runScript(reader);
    }
}
