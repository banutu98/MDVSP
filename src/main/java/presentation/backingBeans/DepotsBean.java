package presentation.backingBeans;

import data.dao.jdbc.DepotDAOJdbc;
import data.dao.jpa.*;
import data.dao.models.*;
import data.dao.spec.*;


import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

@ManagedBean(name = "depotsBean")
@ViewScoped
public class DepotsBean {

    private List<Depot> depots;

    public void addListener() {
        depots.add(new Depot());
    }

    public void removeListener() {
        if (!depots.isEmpty()) {
            depots.remove(depots.size() - 1);
        }
    }

    public DepotsBean() {
        depots = new ArrayList<>();
        depots.add(new Depot());
    }

    public List<Depot> getDepots() {
        return depots;
    }

    public void setDepots(List<Depot> depots) {
        this.depots = depots;
    }

    public String submitListener() {
        DepotDAO depotDAO = new DepotDAOJpa();
        for (Depot depot : depots) {
            depotDAO.create(depot);
        }

        return "trips";
    }
/*
    private void insertCustomersAndDrivers() {

        Customer customer = new Customer();
        Driver driver = new Driver();
        Trip trip = new Trip();
        customer.setName("customer 1");
        customer.setTrip(trip);
        driver.setName("driver 1");
        driver.setCarModel("car model 1");
        driver.setAssignedCustomers(new HashSet<>());
        driver.getAssignedCustomers().add(customer);
        trip.setDuration(10);
        trip.setStartTime(LocalTime.of(14, 30));

        TripDAO tripDAO = new TripDAOJpa();
        DriversDAO driversDAO = new DriversDAOJpa();

        tripDAO.create(trip);
        //customersDAO.create(customer);
        driversDAO.create(driver);
    }

    private void insertAssociation() {
        AssociationDAO associationDAO = new AssociationDAOJpa();
        Association association = new Association();
        Trip trip = new Trip();
        trip.setDuration(25);
        trip.setStartTime(LocalTime.of(10, 30));
        association.setTrip(trip);
        association.setStartLocation(new Location(10d, 10d));
        association.setEndLocation(new Location(11d, 11d));
        associationDAO.create(association);
    }*/
}
