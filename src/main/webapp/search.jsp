<!DOCTYPE html>
<%@page import="model.CartItem"%>
<%@page import="java.util.List"%>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Search - Cake Shop</title>
    <link href="https://stackpath.bootstrapcdn.com/bootstrap/4.5.2/css/bootstrap.min.css" rel="stylesheet">
    <link rel="stylesheet" href="styles.css">
    <script src="https://kit.fontawesome.com/a076d05399.js" crossorigin="anonymous"></script>
</head>
<body>
    <!-- Header -->
    <header>
        <nav class="navbar navbar-expand-lg navbar-light bg-light">
            <a class="navbar-brand" href="home.html">Mamazala's Cake Shop</a>
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
                        <a class="nav-link" href="profile.html">Profile</a>
                    </li>
                </ul>
            </div>
        </nav>
    </header>

    <!-- Main Content -->
    <main class="container mt-5">
        <section class="search text-center mb-5">
            <h1 class="display-4">Search Our Cakes</h1>
            <form class="form-inline justify-content-center mt-4" action="SearchServlet" method="get">
                <input class="form-control mr-sm-2" type="search" name="query" placeholder="Search for cakes..." aria-label="Search">
                <button class="btn btn-outline-secondary my-2 my-sm-0" type="submit">Search</button>
            </form>
        </section>

        <!-- Search Results -->
        <section class="search-results">
            <% String query = request.getParameter("query");
               if (query != null && !query.trim().isEmpty()) {
            	   
                   List<CartItem> searchResults = (List<CartItem>) request.getAttribute("searchResults");
                   if (searchResults != null && !searchResults.isEmpty()) {
                       for (CartItem item : searchResults) { %>
                           <div class="product col-md-4 mb-4">
                               <div class="card">
                                   <img src="" class="card-img-top" alt="<%= item.getProductName() %>">
                                   <div class="card-body">
                                       <h5 class="card-title"><%= item.getProductName() %></h5>
                                       <p class="card-text">R<%= item.getProductPrice() %></p>
                                       <form action="AddToCartServlet" method="post">
                                           <input type="hidden" name="productName" value="<%= item.getProductName() %>">
                                           <input type="hidden" name="productPrice" value="<%= item.getProductPrice() %>">
                                           <label for="quantity">Quantity:</label>
                                           <input type="number" id="quantity" name="quantity" value="1" min="1">
                                           <button type="submit" class="btn btn-primary">Add to Cart</button>
                                       </form>
                                   </div>
                               </div>
                           </div>
            <%       }
                   } else { %>
                       <p>No results found for "<%= query %>".</p>
            <%   }
               } %>
        </section>
    </main>

    <script src="https://code.jquery.com/jquery-3.5.1.slim.min.js"></script>
    <script src="https://cdn.jsdelivr.net/npm/@popperjs/core@2.5.2/dist/umd/popper.min.js"></script>
    <script src="https://stackpath.bootstrapcdn.com/bootstrap/4.5.2/js/bootstrap.min.js"></script>
</body>
</html>
