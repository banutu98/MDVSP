package data.dao.spec;

import data.dao.Models.Depot;

public interface DepotDAO {

    void create(Depot depot);

    Depot read(int id);

    void update(Depot depot);

    void delete(int id);

}
