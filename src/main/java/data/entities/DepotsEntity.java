package data.entities;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Table(name = "depots")
public class DepotsEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "depot_id")
    private int id;

    @Basic
    @Column(name = "name")
    private String name;

    @Basic
    @Column(name = "capacity")
    private int capacity;


    public int getDepotId() {
        return id;
    }

    public void setDepotId(int depotId) {
        this.id = depotId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getCapacity() {
        return capacity;
    }

    public void setCapacity(int capacity) {
        this.capacity = capacity;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        DepotsEntity that = (DepotsEntity) o;
        return id == that.id &&
                capacity == that.capacity &&
                Objects.equals(name, that.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, capacity);
    }
}
