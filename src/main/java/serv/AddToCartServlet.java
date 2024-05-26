package serv;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import model.CartItem;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;


public class AddToCartServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession();
        String productName = request.getParameter("productName");
        double productPrice = Double.parseDouble(request.getParameter("productPrice"));
        int quantity = Integer.parseInt(request.getParameter("quantity"));
        String productImage = request.getParameter("productImage"); // Get product image URL from the request

        // Calculate total price
        double totalPrice = productPrice * quantity;

        // Create or retrieve cart from session
        Map<String, CartItem> cart;
        if (session.getAttribute("cart") == null) {
            cart = new HashMap<>();
            session.setAttribute("cart", cart);
        } else {
            cart = (Map<String, CartItem>) session.getAttribute("cart");
        }

        // Add product to cart or update quantity if already exists
        if (cart.containsKey(productName)) {
            CartItem item = cart.get(productName);
            item.setQuantity(item.getQuantity() + quantity);
            item.setTotalPrice(item.getProductPrice() * item.getQuantity());
        } else {
            CartItem item = new CartItem(productName, quantity, productPrice, productImage);
            cart.put(productName, item);
        }

        // Redirect back to the cart page
        response.sendRedirect("cart.jsp");
    }
}
