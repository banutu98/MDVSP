package data.entityKeys;

import data.entities.LocationsEntity;
import data.entities.TripsEntity;

import java.io.Serializable;

public class AssociationsEntityKey implements Serializable {

    private static final long serialVersionUID = -2536823832026977259L;
    private TripsEntity trip;
    private LocationsEntity startLocation;
    private LocationsEntity endLocation;

    public TripsEntity getTrip() {
        return trip;
    }

    public void setTrip(TripsEntity trip) {
        this.trip = trip;
    }

    public LocationsEntity getStartLocation() {
        return startLocation;
    }

    public void setStartLocation(LocationsEntity startLocation) {
        this.startLocation = startLocation;
    }

    public LocationsEntity getEndLocation() {
        return endLocation;
    }

    public void setEndLocation(LocationsEntity endLocation) {
        this.endLocation = endLocation;
    }
}
