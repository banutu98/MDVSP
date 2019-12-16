package data.dao.spec;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import javax.sql.DataSource;
import java.sql.SQLException;

public abstract class BaseDAOJdbc extends BaseDAO {

    @Resource(mappedName = "java:jboss/datasources/mdvsp")
    protected DataSource dataSource;

    public BaseDAOJdbc() {
    }
}
