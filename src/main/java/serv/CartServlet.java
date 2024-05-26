package serv;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import model.CartItem;




import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Map;

public class CartServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

   
        
    

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String action = request.getParameter("action");
        HttpSession session = request.getSession();
        Map<String, CartItem> cart = (Map<String, CartItem>) session.getAttribute("cart");

        
        
        if (cart == null) {
            cart = new HashMap<>();
            session.setAttribute("cart", cart);
        }

        switch (action) {
        
        case "remove":
            removeFromCart(request, response, cart);
            break;
        case "checkout":
            proceedToCheckout(request, response);
            return;
    }

    request.getRequestDispatcher("cart.jsp").forward(request, response);
}



private void removeFromCart(HttpServletRequest request,HttpServletResponse response, Map<String, CartItem> cart) throws ServletException, IOException {
    String productName = request.getParameter("productName");
    cart.remove(productName);
    
    request.getRequestDispatcher("cart.jsp").forward(request, response);
}

private void proceedToCheckout(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    response.sendRedirect("checkout.jsp");
}
}