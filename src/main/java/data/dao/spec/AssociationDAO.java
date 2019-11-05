package data.dao.spec;

import data.dao.models.Association;

import java.util.List;

public interface AssociationDAO {

    void create(Association association);

    Association read(int tripId);

    List<Association> readAll();

    void update(Association association);

    void delete(Association association);
}
