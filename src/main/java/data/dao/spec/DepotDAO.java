package data.dao.spec;

import data.dao.models.Depot;

import java.util.List;

public interface DepotDAO {

    void create(Depot depot);

    Depot read(int id);
    List<Depot> readAll();

    void update(Depot depot);

    void delete(int id);

}
