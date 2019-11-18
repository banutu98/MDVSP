package data.dao.models;

import java.util.Set;

public class Driver {

    private int driverId;
    private String name;
    private String carModel;
    private Set<Customer> assignedCustomers;

    public int getDriverId() {
        return driverId;
    }

    public void setDriverId(int driverId) {
        this.driverId = driverId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCarModel() {
        return carModel;
    }

    public void setCarModel(String carModel) {
        this.carModel = carModel;
    }

    public Set<Customer> getAssignedCustomers() {
        return assignedCustomers;
    }

    public void setAssignedCustomers(Set<Customer> assignedCustomers) {
        this.assignedCustomers = assignedCustomers;
    }
}
