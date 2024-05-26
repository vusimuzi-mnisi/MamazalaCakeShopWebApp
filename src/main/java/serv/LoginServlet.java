package serv;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import model.*;
/**
 * Servlet implementation class LoginServlet
 */
public class LoginServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
	    private static final String JDBC_URL = "jdbc:postgresql://localhost:5432/cakeshopdb";
	    private static final String JDBC_USER = "postgres";
	    private static final String JDBC_PASSWORD = "123";

	    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	        String username = request.getParameter("username");
	        String password = request.getParameter("password");

	        try {
	            Class.forName("org.postgresql.Driver");
	            Connection conn = DriverManager.getConnection(JDBC_URL, JDBC_USER, JDBC_PASSWORD);
	            String sql = "SELECT * FROM customers_tbl WHERE username = ? AND password = ?";
	            PreparedStatement ps = conn.prepareStatement(sql);
	            ps.setString(1, username);
	            ps.setString(2, password);

	            ResultSet rs = ps.executeQuery();

	            if (rs.next()) {
	                Customer customer = new Customer(
	                        rs.getString("name"),
	                        rs.getString("surname"),
	                        rs.getString("email"),
	                        rs.getString("username"),
	                        rs.getString("password")
	                );

	                HttpSession session = request.getSession();
	                session.setAttribute("customer", customer);
	                response.sendRedirect("login-success.jsp");
	            } else {
	                response.sendRedirect("login.jsp?error=true");
	            }
	        } catch (SQLException e) {
	            e.printStackTrace();
	            throw new ServletException("Database operation failed!", e);
	        } catch (ClassNotFoundException e) {
	            e.printStackTrace();
	        }
	    }
	
	


}
