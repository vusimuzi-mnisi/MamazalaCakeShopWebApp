package serv;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import model.CartItem;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;


public class SearchServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    // This should be replaced with actual product fetching logic
    private List<CartItem> getAllProducts() {
        List<CartItem> products = new ArrayList<>();
        products.add(new CartItem("Chocolate Cake", 1, 180, "chocolate-cake.jpg"));
        products.add(new CartItem("Vanilla Cake", 1, 120, "vanilla-cake.jpg"));
        products.add(new CartItem("Strawberry Cake", 1, 120, "strawberry-cake.jpg"));
        products.add(new CartItem("Lemon Cake", 1, 100, "lemon-cake.jpg"));
        products.add(new CartItem("Red Velvet Cake", 1, 140, "red-velvet-cake.jpg"));
        products.add(new CartItem("Cheesecake", 1, 100, "cheesecake.jpg"));
        products.add(new CartItem("Carrot Cake", 1, 80, "carrot-cake.jpg"));
        products.add(new CartItem("Banana Cake", 1, 80, "banana-cake.jpg"));
        products.add(new CartItem("Coffee Cake", 1, 100, "coffee-cake.jpg"));
        return products;
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String query = request.getParameter("query");
        List<CartItem> products = getAllProducts();
        List<CartItem> searchResults = new ArrayList<>();

        if (query != null && !query.trim().isEmpty()) {
            query = query.toLowerCase();
            for (CartItem product : products) {
                if (product.getProductName().toLowerCase().contains(query)) {
                    searchResults.add(product);
                }
            }
        }

        request.setAttribute("searchResults", searchResults);
        request.getRequestDispatcher("search.jsp").forward(request, response);
    }
}
