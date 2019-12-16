package business;

import data.dao.models.Depot;
import data.dao.models.Trip;

import java.util.List;

public interface SolverStrategy {
    List<Pair<Trip, Depot>> solve();
}
