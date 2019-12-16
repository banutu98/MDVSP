package business;

import data.dao.jpa.DepotDAOJpa;
import data.dao.jpa.TripDAOJpa;
import data.dao.models.Car;
import data.dao.models.Depot;
import data.dao.models.Trip;
import data.dao.jdbc.DepotDAOJdbc;
import data.dao.jdbc.TripDAOJdbc;
import data.dao.spec.TripDAO;
import data.dao.spec.DepotDAO;

import javax.ejb.EJB;
import java.util.*;

public class GreedySolver implements SolverStrategy {

    @EJB
    private DepotDAO depotDao;

    @EJB
    private TripDAO tripDao;

    @Override
    public List<Pair<Trip, Depot>> solve() {
        List<Pair<Trip, Depot>> result = new ArrayList<>();
        Queue<Trip> tripsPriority = new PriorityQueue<Trip>(100, new Comparator<Trip>() {
            @Override
            public int compare(Trip trip, Trip t1) {
                return t1.getDuration() - trip.getDuration();
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
                    result.add(new Pair<>(currentTrip, c.getAssignedDepot()));
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
                        currentCar.setAssignedDepot(d);
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
