package business;

import data.dao.models.Car;
import data.dao.models.Depot;
import data.dao.models.Trip;
import data.dao.jdbc.DepotDAOJdbc;
import data.dao.jdbc.TripDAOJdbc;
import javafx.util.Pair;
import data.dao.spec.TripDAO;
import data.dao.spec.DepotDAO;

import java.util.*;

import static java.time.temporal.ChronoUnit.SECONDS;

public class GreedySolver implements SolverStrategy {
    @Override
    public List<Pair<Trip, Depot>> solve() {
        List<Pair<Trip, Depot>> result = new ArrayList<>();
        TripDAO tripDao = new TripDAOJdbc();
        DepotDAO depotDao = new DepotDAOJdbc();
        Queue<Trip> tripsPriority = new PriorityQueue<Trip>(100, new Comparator<Trip>() {
            @Override
            public int compare(Trip trip, Trip t1) {
                return trip.getDuration() - t1.getDuration();
            }
        });
        tripsPriority.addAll(tripDao.readAll());
        List<Depot> depots = depotDao.readAll();
        List<Car> assignedCars = new ArrayList<>();
        while (!tripsPriority.isEmpty()) {
            Trip currentTrip = tripsPriority.remove();
            boolean tripAssigned = false;
            for (Car c : assignedCars) {
                if (!c.isBusy(currentTrip)) {
                    c.assignTrip(currentTrip);
                    tripAssigned = true;
                    break;
                }
            }
            if (!tripAssigned) {
                boolean depotFound = false;
                for (Depot d : depots) {
                    if (d.getCapacity() > 0) {
                        result.add(new Pair<>(currentTrip, d));
                        d.setCapacity(d.getCapacity() - 1);
                        Car currentCar = new Car();
                        currentCar.assignTrip(currentTrip);
                        assignedCars.add(currentCar);
                        depotFound = true;
                        break;
                    }
                }
                if (!depotFound) {
                    break;
                }
            }
        }
        return result;
    }
}
