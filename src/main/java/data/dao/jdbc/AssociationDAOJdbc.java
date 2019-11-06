package data.dao.jdbc;

import data.dao.models.Association;
import data.dao.spec.AssociationDAO;
import data.dao.spec.LocationDAO;
import data.dao.spec.TripDAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class AssociationDAOJdbc implements AssociationDAO {

    private Connection connection;

    @Override
    public void create(Association association) {
        try (PreparedStatement pstmt = connection.prepareStatement(
                "INSERT INTO associations(trip_id, start_location_id, end_location_id) VALUES (?, ?, ?)")) {
            pstmt.setInt(1, association.getTrip().getId());
            pstmt.setInt(2, association.getStartLocation().getId());
            pstmt.setInt(3, association.getEndLocation().getId());
            pstmt.execute();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public Association read(int tripId) {
        Association result = new Association();
        TripDAO tripDAO = new TripDAOJdbc();
        LocationDAO locationDAO = new LocationDAOJdbc();
        try (PreparedStatement pstmt = connection.prepareStatement("SELECT * FROM associations WHERE trip_id = ?")) {
            pstmt.setInt(1, tripId);
            pstmt.execute();
            ResultSet rs = pstmt.getResultSet();
            if (rs != null) {
                rs.next();
                result.setTrip(tripDAO.read(tripId));
                result.setStartLocation(locationDAO.read(rs.getInt(2)));
                result.setEndLocation(locationDAO.read(rs.getInt(3)));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return result;
    }

    @Override
    public List<Association> readAll() {
        List<Association> result = new ArrayList<>();
        TripDAO tripDAO = new TripDAOJdbc();
        LocationDAO locationDAO = new LocationDAOJdbc();

        try (PreparedStatement pstmt = connection.prepareStatement("SELECT * FROM associations")) {
            pstmt.execute();
            ResultSet rs = pstmt.getResultSet();
            if (rs != null) {
                while (rs.next()) {
                    Association association = new Association();
                    association.setTrip(tripDAO.read(rs.getInt(1)));
                    association.setStartLocation(locationDAO.read(2));
                    association.setEndLocation(locationDAO.read(3));
                    result.add(association);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return result;
    }

    @Override
    public void update(Association association) {
        try (PreparedStatement pstmt = connection.prepareStatement(
                "UPDATE associations SET start_location_id = ? , end_location_id = ? WHERE trip_id = ?")) {
            pstmt.setInt(1, association.getStartLocation().getId());
            pstmt.setInt(2, association.getEndLocation().getId());
            pstmt.setInt(3, association.getTrip().getId());
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void delete(Association association) {
        try (PreparedStatement pstmt = connection.prepareStatement("DELETE FROM associations WHERE trip_id = ?")) {
            pstmt.setInt(1, association.getTrip().getId());
            pstmt.execute();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void deleteAll() {
        try(PreparedStatement pstmt = connection.prepareStatement("DELETE FROM associations")){
            pstmt.execute();
        }catch (SQLException e){
            e.printStackTrace();
        }
    }

    public AssociationDAOJdbc() {
        try {
            connection = DatabaseConnection.getInstance().getConn();
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }
}
