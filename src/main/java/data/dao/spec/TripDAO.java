package data.dao.spec;

import data.dao.models.Trip;

import java.util.List;

public interface TripDAO {

    void create(Trip trip);

    Trip read(int id);

    List<Trip> readAll();

    void update(Trip trip);

    void delete(int id);
}
