package serv;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import model.Order;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import model.*;

public class ProfileServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    private static final String JDBC_URL = "jdbc:postgresql://localhost:5432/cakeshopdb";
    private static final String JDBC_USER = "postgres";
    private static final String JDBC_PASSWORD = "123";

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession();
        String action = request.getParameter("action");
        
        Customer customer = (Customer) session.getAttribute("customer");
        String name = customer.getName();
        
        if (action.equals("accountDetails")) {
			retrieveAccountDetails(request, response);
        	
		}
        else {
        	retrieveOrderHistory(request, response);
        }
            
        
        
    }
    private void retrieveAccountDetails(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession();
        Customer customer = (Customer) session.getAttribute("customer");
        String name = customer.getName();
        
        if (name != null) {
            // Retrieve details from customers_tbl
            String surname = "";
            String email = "";
            String cardNumber = "";
            String expiryDate = "";
            try {
                Connection conn = DriverManager.getConnection(JDBC_URL, JDBC_USER, JDBC_PASSWORD);
                String sql = "SELECT surname, email FROM customers_tbl WHERE name = ?";
                PreparedStatement pstmt = conn.prepareStatement(sql);
                pstmt.setString(1, name);
                ResultSet rs = pstmt.executeQuery();
                if (rs.next()) {
                    surname = rs.getString("surname");
                    email = rs.getString("email");
                }
                rs.close();
                pstmt.close();

                // Retrieve details from payment_tbl
                sql = "SELECT card_number, expiry_date FROM payment_tbl WHERE name = ?";
                pstmt = conn.prepareStatement(sql);
                pstmt.setString(1, name);
                rs = pstmt.executeQuery();
                if (rs.next()) {
                    cardNumber = rs.getString("card_number");
                    expiryDate = rs.getString("expiry_date");
                }
                rs.close();
                pstmt.close();
                
                conn.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }

            // Set attributes to forward to account_details.jsp
            request.setAttribute("name", name);
            request.setAttribute("surname", surname);
            request.setAttribute("email", email);
            request.setAttribute("cardNumber", cardNumber);
            request.setAttribute("expiryDate", expiryDate);

            // Forward to account_details.jsp
            request.getRequestDispatcher("account_details.jsp").forward(request, response);
        } else {
            // If name is not found in session, redirect to login or error page
            response.sendRedirect("login.jsp"); // Replace with your login page
        }
    }
    private void retrieveOrderHistory(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession();
        Customer customer = (Customer) session.getAttribute("customer");
        String name = customer.getName();
        
        if (name != null) {
            // Retrieve order history from orders_tbl
        	
        	List<Order> orderHistory = new ArrayList();
            try {
                Connection conn = DriverManager.getConnection(JDBC_URL, JDBC_USER, JDBC_PASSWORD);
                String sql = "SELECT order_number, total_price FROM orders_tbl WHERE name = ?";
                PreparedStatement pstmt = conn.prepareStatement(sql);
                pstmt.setString(1, name);
                ResultSet rs = pstmt.executeQuery();
                while (rs.next()) {
                    int orderNumber = rs.getInt("order_number");
                    double totalPrice = rs.getDouble("total_price");
                    
                    orderHistory.add(new Order(orderNumber, name, totalPrice));
                }
                rs.close();
                pstmt.close();
                conn.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
            if(orderHistory.isEmpty()) {
            	request.getRequestDispatcher("noOrderHistory.jsp").forward(request, response);
            }else {
            	request.setAttribute("orderHistory", orderHistory);

            // Forward to order_history.jsp
            request.getRequestDispatcher("order_history.jsp").forward(request, response);
            }
            // Set attribute to forward to order_history.jsp
            
        } else {
            // If name is not found in session, redirect to login or error page
            response.sendRedirect("login.jsp"); // Replace with your login page
        }
    }
}
