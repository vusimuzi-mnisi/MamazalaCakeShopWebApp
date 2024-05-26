package serv;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import model.CartItem;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Map;


public class ConfirmPaymentServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private static final String JDBC_URL = "jdbc:postgresql://localhost:5432/cakeshopdb";
    private static final String JDBC_USER = "postgres";
    private static final String JDBC_PASSWORD = "123";

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String name = request.getParameter("name");
        String cardNumber = request.getParameter("cardNumber");
        String expiryDate = request.getParameter("expiryDate");
        String cvv = request.getParameter("cvv");

        // Check if the name exists in the customers_tbl
        boolean nameExists = checkNameExists(name);

        if (nameExists) {
            persistPaymentDetails(name, cardNumber, expiryDate);

            // Assuming you have a method to calculate the total price for the order
            double totalPrice = calculateTotalPrice(request.getSession());
            persistOrderDetails(name, totalPrice);

            // Remove all items from the cart
            clearCart(request.getSession());

            response.sendRedirect("paymentSuccess.jsp");
        } else {
            response.sendRedirect("paymentError.jsp");
        }
    }

    private boolean checkNameExists(String name) {
        boolean nameExists = false;

        try {
            Connection conn = DriverManager.getConnection(JDBC_URL, JDBC_USER, JDBC_PASSWORD);
            String sql = "SELECT COUNT(*) FROM customers_tbl WHERE name = ?";
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setString(1, name);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                int count = rs.getInt(1);
                if (count > 0) {
                    nameExists = true;
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return nameExists;
    }

    private void persistPaymentDetails(String name, String cardNumber, String expiryDate) {
        try {
            Connection conn = DriverManager.getConnection(JDBC_URL, JDBC_USER, JDBC_PASSWORD);
            String sql = "INSERT INTO payment_tbl (name, card_number, expiry_date) VALUES (?, ?, ?)";
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setString(1, name);
            stmt.setString(2, cardNumber);
            stmt.setString(3, expiryDate);
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void persistOrderDetails(String name, double totalPrice) {
        try {
            Connection conn = DriverManager.getConnection(JDBC_URL, JDBC_USER, JDBC_PASSWORD);
            String sql = "INSERT INTO orders_tbl (name, total_price) VALUES (?, ?)";
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setString(1, name);
            stmt.setDouble(2, totalPrice);
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void clearCart(HttpSession session) {
        session.removeAttribute("cart");
    }

    private double calculateTotalPrice(HttpSession session) {
        double totalPrice = 0.0;
        Map<String, CartItem> cart = (Map<String, CartItem>) session.getAttribute("cart");
        if (cart != null) {
            for (CartItem item : cart.values()) {
                totalPrice += item.getTotalPrice();
            }
        }
        return totalPrice;
    }
}
