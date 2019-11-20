package data.entities;

import javax.persistence.*;
import java.util.Objects;
import java.util.Set;

@Entity
@Table(name = "customers")
public class CustomersEntity extends Person{

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "customer_id")
    private int customerId;

    @OneToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinColumn(name = "trip_id", referencedColumnName = "trip_id")
    private TripsEntity trip;

    @ManyToMany(mappedBy = "assignedCustomers", cascade = {CascadeType.ALL})
    private Set<DriversEntity> requestedDrivers;

    public int getCustomerId() {
        return customerId;
    }

    public void setCustomerId(int customerId) {
        this.customerId = customerId;
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
}
