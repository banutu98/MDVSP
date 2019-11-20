package data.entities;

import javax.persistence.*;
import java.util.Objects;
import java.util.Set;

@Entity
@Table(name = "drivers")
public class DriversEntity extends Person {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "driver_id")
    private int driverId;

    @Basic
    @Column(name = "car_model")
    private String carModel;

    @ManyToMany(cascade = {CascadeType.ALL})
    @JoinTable(
            name = "drivers_requests",
            joinColumns = @JoinColumn(name = "driver_id"),
            inverseJoinColumns = @JoinColumn(name = "customer_id"))
    private Set<CustomersEntity> assignedCustomers;

    public void setDriverId(int driverId) {
        this.driverId = driverId;
    }

    public int getDriverId() {
        return driverId;
    }

    public String getCarModel() {
        return carModel;
    }

    public void setCarModel(String carModel) {
        this.carModel = carModel;
    }

    public Set<CustomersEntity> getAssignedCustomers() {
        return assignedCustomers;
    }

    public void setAssignedCustomers(Set<CustomersEntity> assignedCustomers) {
        this.assignedCustomers = assignedCustomers;
    }
}
