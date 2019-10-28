package data.dao.jdbc;

import data.dao.models.Depot;
import data.dao.spec.DepotDAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class DepotDAOJdbc implements DepotDAO {

    private Connection connection;

    @Override
    public void create(Depot depot) {
        try (PreparedStatement pstmt = connection.prepareStatement("INSERT INTO depots(name, capacity) VALUES (?, ?)")) {
            pstmt.setString(1, depot.getName());
            pstmt.setInt(2, depot.getCapacity());
            pstmt.execute();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public Depot read(int id) {
        Depot result = new Depot();

        try (PreparedStatement pstmt = connection.prepareStatement("SELECT * FROM depots where depot_id = ?")) {
            pstmt.setInt(1, id);
            pstmt.execute();
            ResultSet rs = pstmt.getResultSet();
            if (rs != null) {
                rs.next();
                result.setId(id);
                result.setName(rs.getString(2));
                result.setCapacity(rs.getInt(3));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return result;
    }

    @Override
    public List<Depot> readAll() {
        List<Depot> resultList = new ArrayList<>();
        try (PreparedStatement pstmt = connection.prepareStatement("SELECT * FROM depots")) {
            pstmt.execute();
            ResultSet rs = pstmt.getResultSet();
            while (rs.next()) {
                Depot result = new Depot();
                result.setId(rs.getInt(1));
                result.setName(rs.getString(2));
                result.setCapacity(rs.getInt(3));
                resultList.add(result);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return resultList;
    }

    @Override
    public void update(Depot depot) {
        try (PreparedStatement pstmt = connection.prepareStatement("UPDATE depots SET name = ?, capacity = ? WHERE depot_id = ?")) {
            pstmt.setString(1, depot.getName());
            pstmt.setInt(2, depot.getCapacity());
            pstmt.setInt(3, depot.getId());
            pstmt.execute();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void delete(int id) {
        try (PreparedStatement pstmt = connection.prepareStatement("DELETE FROM depots WHERE depot_id = ?")) {
            pstmt.setInt(1, id);
            pstmt.execute();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public DepotDAOJdbc() {
        try {
            connection = DatabaseConnection.getInstance().getConn();
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }

    }
}
