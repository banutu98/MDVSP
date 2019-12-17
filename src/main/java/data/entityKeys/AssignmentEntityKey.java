package data.entityKeys;

import java.io.Serializable;

public class AssignmentEntityKey implements Serializable {

    private static final long serialVersionUID = 642028003925055852L;

    private int driverId;
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
}
