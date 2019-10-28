package data.dao.models;

import java.util.ArrayList;
import java.util.List;

public class Car {
    private List<Trip> assignedTrips = new ArrayList<>();

    public boolean isBusy(Trip trip) {
        for(Trip t:assignedTrips) {
            if(!(t.getStartTime().isAfter(trip.getEndTime()) || t.getEndTime().isBefore(trip.getStartTime())))
                return true;
        }
        return false;
    }

    public void assignTrip(Trip t) {
        assignedTrips.add(t);
    }
}
