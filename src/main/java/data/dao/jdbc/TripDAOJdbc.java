package data.dao.jdbc;

import data.dao.Models.Trip;
import data.dao.spec.TripDAO;

import java.sql.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.Calendar;

public class TripDAOJdbc implements TripDAO {

    private Connection connection;
    private static DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm");

    @Override
    public void create(Trip trip) {
        try (PreparedStatement pstmt = connection.prepareStatement("INSERT INTO trips(start_time, duration) VALUES(?,?)")) {
            pstmt.setString(1, trip.getStartTime().format(formatter));
            pstmt.setInt(2, trip.getDuration());
            pstmt.execute();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public Trip read(int id) {
        Trip result = new Trip();
        try (PreparedStatement pstmt = connection.prepareStatement("SELECT * FROM trips WHERE trip_id = ?")) {
            pstmt.setInt(1, id);
            pstmt.execute();
            ResultSet rs = pstmt.getResultSet();
            if (rs != null) {
                rs.next();
                result.setId(id);
                String[] time = rs.getString(2).split(":");
                result.setStartTime(LocalTime.of(Integer.parseInt(time[0]), Integer.parseInt(time[1])));
                result.setDuration(rs.getInt(3));
                result.setEndTime(result.getStartTime().plusMinutes(result.getDuration()));
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return result;
    }

    @Override
    public void update(Trip trip) {
        try (PreparedStatement pstmt = connection.prepareStatement("UPDATE trips SET start_time = ?, duration = ? WHERE trip_id = ?")) {
            pstmt.setString(1, formatter.format(trip.getStartTime()));
            pstmt.setInt(2, trip.getDuration());
            pstmt.setInt(3, trip.getId());
            pstmt.execute();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void delete(int id) {
        try (PreparedStatement pstmt = connection.prepareStatement("DELETE FROM trips WHERE trip_id = ?")) {
            pstmt.setInt(1, id);
            pstmt.execute();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public TripDAOJdbc() {
        try {
            connection = DatabaseConnection.getInstance().getConn();
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

}
