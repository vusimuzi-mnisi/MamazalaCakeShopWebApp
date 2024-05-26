package model;

public class CartItem {
    private String productName;
    private int quantity;
    private double productPrice;
    private double totalPrice;
    private String productImage; // Add product image field

    public CartItem(String productName, int quantity, double productPrice, String productImage) {
        this.productName = productName;
        this.quantity = quantity;
        this.productPrice = productPrice;
        this.totalPrice = productPrice * quantity;
        this.productImage = productImage; // Initialize product image
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
        this.totalPrice = this.productPrice * this.quantity; // Update totalPrice when quantity changes
    }

    public double getProductPrice() {
        return productPrice;
    }

    public void setProductPrice(double productPrice) {
        this.productPrice = productPrice;
        this.totalPrice = this.productPrice * this.quantity; // Update totalPrice when productPrice changes
    }

    public double getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(double totalPrice) {
        this.totalPrice = totalPrice;
    }

    public String getProductImage() {
        return productImage;
    }

    public void setProductImage(String productImage) {
        this.productImage = productImage;
    }
}
