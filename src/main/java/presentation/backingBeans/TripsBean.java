package presentation.backingBeans;



import data.dao.jdbc.TripDAOJdbc;
import data.dao.models.Trip;
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

    public TripsBean() {
        trips = new ArrayList<>();
        trips.add(new Trip());
        startTimes = new ArrayList<>();
        startTimes.add(new Date());
        endTimes = new ArrayList<>();
        endTimes.add(new Date());
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
    }

    public void removeListener() {
        trips.remove(trips.size() - 1);
        startTimes.remove(startTimes.size() - 1);
        endTimes.remove(endTimes.size() - 1);
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

    public String submitListener() {
        TripDAO tripDAO = new TripDAOJdbc();
        Calendar calendar = GregorianCalendar.getInstance();
        for (int i = 0; i < trips.size(); i++) {
            calendar.setTime(startTimes.get(i));
            trips.get(i).setStartTime(LocalTime.of(calendar.get(Calendar.HOUR_OF_DAY), calendar.get(Calendar.MINUTE)));
            long duration = endTimes.get(i).getTime() - startTimes.get(i).getTime();
            if(duration < 0){
                return "";
            }
            duration = duration / (60 * 1000) % 60;
            trips.get(i).setDuration((int) duration);
            tripDAO.create(trips.get(i));
        }

        return "schedule";
    }
}
