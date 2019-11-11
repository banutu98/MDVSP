package data.dao.jdbc;

import data.dao.models.User;
import data.dao.spec.BaseDAOJdbc;
import data.dao.spec.UserDAO;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class UserDAOJdbc extends BaseDAOJdbc implements UserDAO {

    @Override
    public void create(User user) {
        try (PreparedStatement pstmt = connection.prepareStatement("INSERT INTO default_schema.users(name, pass) VALUES (?, ?)")) {
            pstmt.setString(1, user.getName());
            pstmt.setString(2, user.getPassword());
            pstmt.execute();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public User findByName(String name) {
        User user = new User();
        try (PreparedStatement pstmt = connection.prepareStatement("SELECT * FROM default_schema.users WHERE name = ?")) {
            pstmt.setString(1, name);
            pstmt.execute();
            ResultSet rs = pstmt.getResultSet();
            if (rs != null && rs.next()) {
                user.setName(name);
                user.setPassword(rs.getString(2));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return user;
    }
}
