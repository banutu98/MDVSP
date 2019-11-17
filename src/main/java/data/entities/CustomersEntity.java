package data.entities;

import javax.persistence.*;
import java.util.Objects;
import java.util.Set;

@Entity
@Table(name = "customers")
public class CustomersEntity {

    private int customerId;
    private String customerName;

    @OneToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinColumn(name = "trip_id", referencedColumnName = "trip_id")
    private TripsEntity trip;

    @ManyToMany(mappedBy = "assignedCustomers")
    private Set<DriversEntity> requestedDrivers;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "customer_id")
    public int getCustomerId() {
        return customerId;
    }

    public void setCustomerId(int customerId) {
        this.customerId = customerId;
    }

    @Basic
    @Column(name = "customer_name")
    public String getCustomerName() {
        return customerName;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    public Set<DriversEntity> getRequestedDrivers() {
        return requestedDrivers;
    }

    public void setRequestedDrivers(Set<DriversEntity> requestedDrivers) {
        this.requestedDrivers = requestedDrivers;
    }

    public TripsEntity getTrip() {
        return trip;
    }

    public void setTrip(TripsEntity trip) {
        this.trip = trip;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        CustomersEntity that = (CustomersEntity) o;
        return customerId == that.customerId &&
                Objects.equals(customerName, that.customerName) &&
                Objects.equals(trip, that.trip) &&
                Objects.equals(requestedDrivers, that.requestedDrivers);
    }

    @Override
    public int hashCode() {
        return Objects.hash(customerId, customerName, trip, requestedDrivers);
    }
}
