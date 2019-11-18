package data.dao.models;

import java.util.Set;

public class Customer {

    private int customerId;
    private String name;
    private Trip trip;
    private Set<Driver> requestedDrivers;

    public int getCustomerId() {
        return customerId;
    }

    public void setCustomerId(int customerId) {
        this.customerId = customerId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Trip getTrip() {
        return trip;
    }

    public void setTrip(Trip trip) {
        this.trip = trip;
    }

    public Set<Driver> getRequestedDrivers() {
        return requestedDrivers;
    }

    public void setRequestedDrivers(Set<Driver> requestedDrivers) {
        this.requestedDrivers = requestedDrivers;
    }
}
