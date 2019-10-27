package data.dao.spec;

import data.dao.Models.Trip;

public interface TripDAO {

    void create(Trip trip);

    Trip read(int id);

    void update(Trip trip);

    void delete(int id);
}
