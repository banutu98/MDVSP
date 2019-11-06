package data.dao.spec;

import data.dao.models.Location;

import java.util.List;

public interface LocationDAO {

    void create(Location location);

    Location read(int id);

    List<Location> readAll();

    void update(Location location);

    void delete(int id);

    void deleteAll();

}
