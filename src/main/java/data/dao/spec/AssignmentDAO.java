package data.dao.spec;


import business.Pair;
import data.entities.AssignmentsEntity;

import java.util.List;

public interface AssignmentDAO {

    void create(int driverId, int tripId);

    List<AssignmentsEntity> findAll();
}
