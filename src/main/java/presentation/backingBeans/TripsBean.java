package presentation.backingBeans;


import data.dao.jpa.AssociationDAOJpa;
import data.dao.jpa.LocationDAOJpa;
import data.dao.jpa.TripDAOJpa;
import data.dao.models.Association;
import data.dao.models.Location;
import data.dao.models.Trip;
import data.dao.spec.AssociationDAO;
import data.dao.spec.LocationDAO;
import data.dao.spec.TripDAO;


import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import java.time.LocalTime;
import java.util.*;

@ManagedBean(name = "tripsBean")
@ViewScoped
public class TripsBean {

    private List<Trip> trips;
    private List<Date> startTimes;
    private List<Date> endTimes;
    private List<Location> startLocations;
    private List<Location> endLocations;

    public TripsBean() {
        trips = new ArrayList<>();
        trips.add(new Trip());
        startTimes = new ArrayList<>();
        startTimes.add(new Date());
        endTimes = new ArrayList<>();
        endTimes.add(new Date());
        startLocations = new ArrayList<>();
        startLocations.add(new Location());
        endLocations = new ArrayList<>();
        endLocations.add(new Location());
    }

    public List<Trip> getTrips() {
        return trips;
    }

    public void setTrips(List<Trip> trips) {
        this.trips = trips;
    }

    public void addListener() {
        trips.add(new Trip());
        startTimes.add(new Date());
        endTimes.add(new Date());
        startLocations.add(new Location());
        endLocations.add(new Location());
    }

    public void removeListener() {
        trips.remove(trips.size() - 1);
        startTimes.remove(startTimes.size() - 1);
        endTimes.remove(endTimes.size() - 1);
        startLocations.remove(startLocations.size() - 1);
        endLocations.remove(endLocations.size() - 1);
    }

    public List<Date> getStartTimes() {
        return startTimes;
    }

    public void setStartTimes(List<Date> startTimes) {
        this.startTimes = startTimes;
    }

    public List<Date> getEndTimes() {
        return endTimes;
    }

    public void setEndTimes(List<Date> endTimes) {
        this.endTimes = endTimes;
    }

    public List<Location> getStartLocations() {
        return startLocations;
    }

    public void setStartLocations(List<Location> startLocations) {
        this.startLocations = startLocations;
    }

    public List<Location> getEndLocations() {
        return endLocations;
    }

    public void setEndLocations(List<Location> endLocations) {
        this.endLocations = endLocations;
    }

    public String submitListener() {
        TripDAO tripDAO = new TripDAOJpa();
        Calendar calendar = GregorianCalendar.getInstance();
        for (int i = 0; i < trips.size(); i++) {
            calendar.setTime(startTimes.get(i));
            trips.get(i).setStartTime(LocalTime.of(calendar.get(Calendar.HOUR_OF_DAY), calendar.get(Calendar.MINUTE)));
            long duration = endTimes.get(i).getTime() - startTimes.get(i).getTime();
            if (duration < 0) {
                return "";
            }
            duration = duration / (60 * 1000) % 60;
            trips.get(i).setDuration((int) duration);
            tripDAO.create(trips.get(i));
        }
        LocationDAO locationDAO = new LocationDAOJpa();

        for (Location startLocation : startLocations) {
            locationDAO.create(startLocation);
        }

        for (Location endLocation : endLocations) {
            locationDAO.create(endLocation);
        }

        AssociationDAO associationDAO = new AssociationDAOJpa();
        for (int i = 0; i < trips.size(); i++) {
            Association association = new Association();
            trips.get(i).setId(tripDAO.getId(trips.get(i)));
            startLocations.get(i).setLocationId(locationDAO.getId(startLocations.get(i)));
            endLocations.get(i).setLocationId(locationDAO.getId(endLocations.get(i)));
            association.setTrip(trips.get(i));
            association.setStartLocation(startLocations.get(i));
            association.setEndLocation(endLocations.get(i));
            associationDAO.create(association);
        }
        return "schedule";
    }
}
