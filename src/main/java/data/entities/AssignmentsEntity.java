package data.entities;

import data.entityKeys.AssignmentEntityKey;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Table(name = "assignments")
@IdClass(value = AssignmentEntityKey.class)
public class AssignmentsEntity {

    @Id
    @Column(name = "driver_id")
    private int driverId;

    @Id
    @Column(name = "trip_id")
    private int tripId;

    public int getDriverId() {
        return driverId;
    }

    public void setDriverId(int driverId) {
        this.driverId = driverId;
    }

    public int getTripId() {
        return tripId;
    }

    public void setTripId(int tripId) {
        this.tripId = tripId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof AssignmentsEntity)) return false;
        AssignmentsEntity that = (AssignmentsEntity) o;
        return driverId == that.driverId &&
                tripId == that.tripId;
    }

    @Override
    public int hashCode() {
        return Objects.hash(driverId, tripId);
    }
}
