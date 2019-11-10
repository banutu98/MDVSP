package data.dao.jdbc;

import data.dao.models.Location;
import data.dao.spec.BaseDAOJdbc;
import data.dao.spec.LocationDAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class LocationDAOJdbc extends BaseDAOJdbc implements LocationDAO {

    @Override
    public void create(Location location) {
        try (PreparedStatement pstmt = connection.prepareStatement("INSERT INTO locations(x, y) VALUES (?, ?)")) {
            pstmt.setDouble(1, location.getX());
            pstmt.setDouble(2, location.getY());
            pstmt.execute();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public Location read(int id) {
        Location result = new Location();

        try (PreparedStatement pstmt = connection.prepareStatement("SELECT * FROM locations WHERE location_id = ?")) {
            pstmt.setInt(1, id);
            pstmt.execute();
            ResultSet rs = pstmt.getResultSet();
            if (rs != null && rs.next()) {
                result.setId(id);
                result.setX(rs.getDouble(2));
                result.setY(rs.getDouble(3));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return result;
    }

    @Override
    public List<Location> readAll() {
        List<Location> result = new ArrayList<>();

        try (PreparedStatement pstmt = connection.prepareStatement("SELECT * FROM locations")) {
            pstmt.execute();
            ResultSet rs = pstmt.getResultSet();
            if (rs != null) {
                while (rs.next()) {
                    Location location = new Location();
                    location.setId(rs.getInt(1));
                    location.setX(rs.getDouble(2));
                    location.setY(rs.getDouble(3));
                    result.add(location);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return result;
    }

    @Override
    public void update(Location location) {
        try (PreparedStatement pstmt = connection.prepareStatement("UPDATE locations SET x = ? , y = ? WHERE location_id = ?")) {
            pstmt.setDouble(1, location.getX());
            pstmt.setDouble(2, location.getY());
            pstmt.setInt(3, location.getId());
            pstmt.execute();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void delete(int id) {
        try (PreparedStatement pstmt = connection.prepareStatement("DELETE FROM locations WHERE location_id = ?")) {
            pstmt.setInt(1, id);
            pstmt.execute();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void deleteAll() {
        try (PreparedStatement pstmt = connection.prepareStatement("DELETE FROM locations")) {
            pstmt.execute();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public int getId(Location location) {
        try(PreparedStatement pstmt = connection.prepareStatement("SELECT location_id FROM locations WHERE x = ? AND y = ?")){
            pstmt.setDouble(1, location.getX());
            pstmt.setDouble(2, location.getY());
            pstmt.execute();
            ResultSet rs = pstmt.getResultSet();
            if(rs != null && rs.next()){
                return rs.getInt(1);
            }
        }catch (SQLException e){
            e.printStackTrace();
        }

        return 0;
    }

    public LocationDAOJdbc() {
       super();
    }
}
