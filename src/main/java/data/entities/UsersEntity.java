package data.entities;

import javax.persistence.*;
import java.util.Objects;

@Cacheable
@Entity
@Table(name = "users")
public class UsersEntity {
    @Id
    @Column(name = "name")
    private String name;

    @Basic
    @Column(name = "pass")
    private String pass;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPass() {
        return pass;
    }

    public void setPass(String pass) {
        this.pass = pass;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        UsersEntity that = (UsersEntity) o;
        return Objects.equals(name, that.name) &&
                Objects.equals(pass, that.pass);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, pass);
    }
}
