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

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import java.util.ArrayList;
import java.util.List;

@ManagedBean(name = "dataTableBean")
@ViewScoped
public class CustomDataTableBean {

    private List<String[]> rows;
    private List<String[]> editedRows;

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
        return rows;
    }

    public String addRow() {
        int rowLength = rows.get(0)[0].length();
        String[] newRow = new String[rowLength];
        for (int i = 0; i < rowLength; i++) {
            newRow[i] = "";
        }
        rows.add(newRow);
        editedRows.add(newRow);
        return "";
    }

    public String removeRow() {
        String[] lastRow = rows.get(rows.size() - 1);
        rows.remove(lastRow);
        editedRows.remove(lastRow);
        return "";
    }
}
