package data.entities;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Table(name = "locations")
public class LocationsEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "location_id")
    private int locationId;

    @Basic
    @Column(name = "x")
    private double x;

    @Basic
    @Column(name = "y")
    private double y;


    public int getLocationId() {
        return locationId;
    }

    public void setLocationId(int locationId) {
        this.locationId = locationId;
    }

    public double getX() {
        return x;
    }

    public void setX(double x) {
        this.x = x;
    }

    public double getY() {
        return y;
    }

    public void setY(double y) {
        this.y = y;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        LocationsEntity that = (LocationsEntity) o;
        return locationId == that.locationId &&
                Double.compare(that.x, x) == 0 &&
                Double.compare(that.y, y) == 0;
    }

    @Override
    public int hashCode() {
        return Objects.hash(locationId, x, y);
    }
}
