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
import java.sql.SQLException;
import model.*;

public class RegisterServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
	private static final String JDBC_URL = "jdbc:postgresql://localhost:5432/cakeshopdb";
    private static final String JDBC_USER = "postgres";
    private static final String JDBC_PASSWORD = "123";
   
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		HttpSession session = request.getSession(true);
		
		 String name = request.getParameter("name");
		 String surname = request.getParameter("surname");
	     String email = request.getParameter("email");
	     String username = request.getParameter("username");
	     String password = request.getParameter("password");
	     
	     Customer c = new Customer(name, surname, email, username, password);
	     
	     try 
	     {
	    	 Class.forName("org.postgresql.Driver");
			Connection conn = DriverManager.getConnection(JDBC_URL, JDBC_USER, JDBC_PASSWORD);
			String sql = "INSERT INTO customers_tbl(name,surname,email,username,password) VALUES(?,?,?,?,?)";
			PreparedStatement ps = conn.prepareStatement(sql);
			
			ps.setString(1, name);
			ps.setString(2, surname);
			ps.setString(3, email);
			ps.setString(4, username);
			ps.setString(5, password);
			
			int rowsAffected = ps.executeUpdate();
			
			if (rowsAffected>0) {
				System.out.println("Customer added!");
			}
		 } 
	     catch (SQLException e) 
	     {
			// TODO Auto-generated catch block
			e.printStackTrace();
			throw new ServletException("Database Operation Failed!!!", e);
		 } catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	     
	     
	     session.setAttribute("customer", c);
	     response.sendRedirect("registration-success.jsp");
	}

}
