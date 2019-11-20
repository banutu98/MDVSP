package data.entities;

import data.entityKeys.AssociationsEntityKey;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "associations")
@IdClass(value = AssociationsEntityKey.class)
public class AssociationsEntity{

    @Id
    @OneToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinColumn(name = "trip_id", referencedColumnName = "trip_id")
    private TripsEntity trip;

    @Id
    @OneToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinColumn(name = "start_location_id", referencedColumnName = "location_id")
    private LocationsEntity startLocation;

    @Id
    @OneToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinColumn(name = "end_location_id", referencedColumnName = "location_id")
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
