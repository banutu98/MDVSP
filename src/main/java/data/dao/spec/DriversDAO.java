package data.dao.spec;

import data.dao.models.Driver;

public interface DriversDAO {

    void create(Driver driver);

    Driver read(int id);

    void update(Driver driver);

    void delete(Driver driver);

}
