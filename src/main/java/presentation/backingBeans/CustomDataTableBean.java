package presentation.backingBeans;

import data.dao.jdbc.AssociationDAOJdbc;
import data.dao.jdbc.DepotDAOJdbc;
import data.dao.jdbc.LocationDAOJdbc;
import data.dao.jdbc.TripDAOJdbc;
import data.dao.models.Association;
import data.dao.models.Depot;
import data.dao.models.Location;
import data.dao.models.Trip;
import data.dao.spec.AssociationDAO;
import data.dao.spec.DepotDAO;
import data.dao.spec.LocationDAO;
import data.dao.spec.TripDAO;
import org.primefaces.event.CellEditEvent;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.component.UIComponent;
import javax.faces.component.UIInput;
import javax.faces.context.FacesContext;
import java.sql.SQLException;
import java.text.ParseException;
import java.time.DateTimeException;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

@ManagedBean(name = "dataTableBean")
@ViewScoped
public class CustomDataTableBean {

    private List<String[]> rows;
    private String table;

    public String[] getColumns(String table) {
        if (table.equalsIgnoreCase("depots")) {
            return new String[]{"Id", "Name", "Capacity"};
        }
        if (table.equalsIgnoreCase("locations")) {
            return new String[]{"Id", "X", "Y"};
        }
        if (table.equalsIgnoreCase("trips")) {
            return new String[]{"Id", "Start time", "Duration"};
        }
        if (table.equalsIgnoreCase("associations")) {
            return new String[]{"Trip id", "Start location id", "End location id"};
        }

        return new String[]{};
    }

    public List<String[]> getRows(String table) {
        this.table = table;
        if (rows == null) {
            rows = new ArrayList<>();
            if (table.equalsIgnoreCase("depots")) {
                DepotDAO depotDAO = new DepotDAOJdbc();
                for (Depot depot : depotDAO.readAll()) {
                    String[] row = new String[3];
                    row[0] = Integer.toString(depot.getId());
                    row[1] = depot.getName();
                    row[2] = Integer.toString(depot.getCapacity());
                    rows.add(row);
                }
            }
            if (table.equalsIgnoreCase("locations")) {
                LocationDAO locationDAO = new LocationDAOJdbc();
                for (Location location : locationDAO.readAll()) {
                    String[] row = new String[3];
                    row[0] = Integer.toString(location.getId());
                    row[1] = Double.toString(location.getX());
                    row[2] = Double.toString(location.getY());
                    rows.add(row);
                }
            }

            if (table.equalsIgnoreCase("trips")) {
                TripDAO tripDAO = new TripDAOJdbc();
                for (Trip trip : tripDAO.readAll()) {
                    String[] row = new String[3];
                    row[0] = Integer.toString(trip.getId());
                    row[1] = trip.getStartTime().toString();
                    row[2] = Integer.toString(trip.getDuration());
                    rows.add(row);
                }
            }

            if (table.equalsIgnoreCase("associations")) {
                AssociationDAO associationDAO = new AssociationDAOJdbc();
                for (Association association : associationDAO.readAll()) {
                    String[] row = new String[3];
                    row[0] = Integer.toString(association.getTrip().getId());
                    row[1] = Integer.toString(association.getStartLocation().getId());
                    row[2] = Integer.toString(association.getEndLocation().getId());
                    rows.add(row);
                }
            }
        }
        return rows;
    }

    public void addRow() {
        String[] newRow = new String[3];
        for (int i = 0; i < 3; i++) {
            newRow[i] = "";
        }
        rows.add(newRow);
    }

    public void removeRow() {
        String[] lastRow = rows.get(rows.size() - 1);
        rows.remove(lastRow);
    }

    public String submit(String table) {
        try {
            if (table.equalsIgnoreCase("depots")) {
                DepotDAO depotDAO = new DepotDAOJdbc();
                depotDAO.deleteAll();
                for (String[] row : rows) {
                    depotDAO.create(createDepot(row));
                }
            }

            if (table.equalsIgnoreCase("locations")) {
                LocationDAO locationDAO = new LocationDAOJdbc();
                locationDAO.deleteAll();
                for (String[] row : rows) {
                    locationDAO.create(createLocation(row));
                }
            }

            if (table.equalsIgnoreCase("trips")) {
                TripDAO tripDAO = new TripDAOJdbc();
                tripDAO.deleteAll();
                for (String[] row : rows) {
                    tripDAO.create(createTrip(row));
                }
            }

            if (table.equalsIgnoreCase("associations")) {
                AssociationDAO associationDAO = new AssociationDAOJdbc();
                associationDAO.deleteAll();
                for (String[] row : rows) {
                    associationDAO.create(createAssociation(row));
                }
            }
        }
        catch (NumberFormatException | DateTimeException e){
            return "failure";
        }

        return "success";
    }

    private Depot createDepot(String[] row) {
        Depot depot = new Depot();
        depot.setId(Integer.valueOf(row[0]));
        depot.setName(row[1]);
        depot.setCapacity(Integer.valueOf(row[2]));
        return depot;
    }

    private Location createLocation(String[] row) {
        Location location = new Location();
        location.setId(Integer.valueOf(row[0]));
        location.setX(Double.valueOf(row[1]));
        location.setY(Double.valueOf(row[2]));
        return location;
    }

    private Trip createTrip(String[] row) {
        Trip trip = new Trip();
        trip.setId(Integer.valueOf(row[0]));
        int hour = Integer.valueOf(row[1].split(":")[0]);
        int minutes = Integer.valueOf(row[1].split(":")[1]);
        LocalTime startTime = LocalTime.of(hour, minutes);
        trip.setStartTime(startTime);
        trip.setDuration(Integer.valueOf(row[2]));
        return trip;
    }

    private Association createAssociation(String[] row) {
        Association association = new Association();
        association.setTrip(new Trip());
        association.setStartLocation(new Location());
        association.setEndLocation(new Location());
        association.getTrip().setId(Integer.valueOf(row[0]));
        association.getStartLocation().setId(Integer.valueOf(row[1]));
        association.getEndLocation().setId(Integer.valueOf(row[2]));
        return association;
    }

    public void onCellEdit(CellEditEvent event) {
        if (event.getColumn().getColumnKey().equalsIgnoreCase("Id")
                || event.getColumn().getColumnKey().equalsIgnoreCase("Trip id")) {
            FacesMessage msg = new FacesMessage("#{label['idWarn']}");
            FacesContext.getCurrentInstance().addMessage(null, msg);
        }
    }

    public void validateLocation(FacesContext context, UIComponent component, Object value) {
        if (table.equalsIgnoreCase("locations")) {
            Double number = Double.valueOf((String) value);
            ((UIInput) component).setValid(number >= 0);
            return;
        }

        ((UIInput) component).setValid(true);
    }
}
