<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="java.util.Map" %>
<%@ page import="model.CartItem" %>
<%@ page import="java.util.Iterator" %>
<html>
<head>
    <title>Checkout - Cake Shop</title>
    <link href="https://stackpath.bootstrapcdn.com/bootstrap/4.5.2/css/bootstrap.min.css" rel="stylesheet">
    <link rel="stylesheet" href="styles.css">
</head>
<body>
<header>
        <nav class="navbar navbar-expand-lg navbar-light bg-light">
            <a class="navbar-brand" href="index.jsp">Mamazala's Cake Shop</a>
            <button class="navbar-toggler" type="button" data-toggle="collapse" data-target="#navbarNav" aria-controls="navbarNav" aria-expanded="false" aria-label="Toggle navigation">
                <span class="navbar-toggler-icon"></span>
            </button>
            <div class="collapse navbar-collapse" id="navbarNav">
                <ul class="navbar-nav ml-auto">
                    <li class="nav-item">
                        <a class="nav-link" href="products.html">Shop</a>
                    </li>
                    <li class="nav-item">
                        <a class="nav-link" href="search.jsp">Search</a>
                    </li>
                    <li class="nav-item">
                        <a class="nav-link" href="cart.jsp">Cart</a>
                    </li>
                    <li class="nav-item">
                        <a class="nav-link" href="profile.jsp">Profile</a>
                    </li>
                </ul>
            </div>
        </nav>
    </header>

    <main class="container mt-5">
        <section class="checkout text-center mb-5">
            <h1 class="display-4">Order Summary</h1>
            <p class="lead">Review your order before proceeding to payment.</p>
            <table class="table table-hover">
                <thead>
                    <tr>
                        <th scope="col">Product</th>
                        <th scope="col">Quantity</th>
                        <th scope="col">Price</th>
                        <th scope="col">Total</th>
                    </tr>
                </thead>
                <tbody>
                    <% 
                        Map<String, CartItem> cart = (Map<String, CartItem>) session.getAttribute("cart");
                        double grandTotal = 0.0;
                        if (cart != null) {
                            for (CartItem item : cart.values()) {
                                grandTotal += item.getTotalPrice();
                    %>
                    <tr>
                        <td><%= item.getProductName() %></td>
                        <td><%= item.getQuantity() %></td>
                        <td>R<%= item.getProductPrice() %></td>
                        <td>R<%= item.getTotalPrice() %></td>
                    </tr>
                    <% 
                            }
                        }
                    %>
                    <tr>
                        <td colspan="3" class="text-right"><strong>Grand Total</strong></td>
                        <td>R<%= grandTotal %></td>
                    </tr>
                </tbody>
            </table>
            <a href="payment.jsp" class="btn btn-primary">Proceed To Checkout</a>
        </section>
    </main>

    <script src="https://code.jquery.com/jquery-3.5.1.slim.min.js"></script>
    <script src="https://cdn.jsdelivr.net/npm/@popperjs/core@2.5.2/dist/umd/popper.min.js"></script>
    <script src="https://stackpath.bootstrapcdn.com/bootstrap/4.5.2/js/bootstrap.min.js"></script>
</body>
</html>