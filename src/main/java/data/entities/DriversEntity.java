package data.entities;

import javax.persistence.*;
import java.util.Objects;
import java.util.Set;

@Entity
@Table(name = "drivers")
public class DriversEntity {

    private int driverId;
    private String driverName;
    private String carModel;

    @ManyToMany
    @JoinTable(
            name = "drivers_requests",
            joinColumns = @JoinColumn(name = "driver_id"),
            inverseJoinColumns = @JoinColumn(name = "customer_id"))
    private Set<CustomersEntity> assignedCustomers;

    public void setDriver_id(int driverId) {
        this.driverId = driverId;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "driver_id")
    public int getDriver_id() {
        return driverId;
    }

    @Basic
    @Column(name = "driver_name")
    public String getDriverName() {
        return driverName;
    }

    public void setDriverName(String driverName) {
        this.driverName = driverName;
    }

    @Basic
    @Column(name = "car_model")
    public String getCarModel() {
        return carModel;
    }

    public void setCarModel(String carModel) {
        this.carModel = carModel;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        DriversEntity that = (DriversEntity) o;
        return driverId == that.driverId &&
                Objects.equals(driverName, that.driverName) &&
                Objects.equals(carModel, that.carModel) &&
                Objects.equals(assignedCustomers, that.assignedCustomers);
    }

    @Override
    public int hashCode() {
        return Objects.hash(driverId, driverName, carModel, assignedCustomers);
    }

    public Set<CustomersEntity> getAssignedCustomers() {
        return assignedCustomers;
    }

    public void setAssignedCustomers(Set<CustomersEntity> assignedCustomers) {
        this.assignedCustomers = assignedCustomers;
    }
}
