package flood;

import data.dao.jdbc.DatabaseConnection;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.sql.DataSource;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Date;
import java.util.Random;

@WebServlet(name = "FloodServlet", urlPatterns = {"/Flood"})
public class FloodServlet extends HttpServlet {

    private Connection getConnection() throws NamingException, SQLException, ClassNotFoundException {
        ServletContext servletContext = this.getServletContext();
        String method = servletContext.getInitParameter("connMethod");
        if(method.equals("ConnectionPool")) {
            Context context = new InitialContext();
            DataSource ds = (DataSource) context.lookup("java:jboss/datasources/mdvsp");
            return ds.getConnection();
        }
        return DatabaseConnection.getInstance().getConn();
    }

    private void insert(int id, Date request_time, String remote_addr, String data, int i, Connection conn) throws SQLException {
        PreparedStatement pstmt = conn.prepareStatement("INSERT INTO flood.table_" + i + " VALUES(?, ?, ?, ?)");
        pstmt.setInt(1, id);
        java.sql.Date sDate = new java.sql.Date(request_time.getTime());
        pstmt.setDate(2, sDate);
        pstmt.setString(3, remote_addr);
        pstmt.setString(4, data);
        pstmt.execute();
        pstmt.close();
        conn.close();
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        Random r = new Random();
        PrintWriter writer = response.getWriter();
        int i = r.nextInt(100) + 1;
        int k = r.nextInt(10) + 1;
        for(int index = 0;index < k;index++) {
            try {
                insert(r.nextInt(100), new Date(), request.getRemoteAddr(), "data", i, getConnection());
            } catch (SQLException | ClassNotFoundException | NamingException e) {
                writer.println("Error 404!");
            }
        }
        writer.println("Success 200!");
    }
}
