package model;

public class Order {
    private int orderNumber;
    private String customerName;
    private double totalPrice;

    // Constructors
    public Order() {
    }

    public Order(int orderNumber, String customerName, double totalPrice) {
        this.orderNumber = orderNumber;
        this.customerName = customerName;
        this.totalPrice = totalPrice;
    }

    // Getters and Setters
    public int getOrderNumber() {
        return orderNumber;
    }

    public void setOrderNumber(int orderNumber) {
        this.orderNumber = orderNumber;
    }

    public String getCustomerName() {
        return customerName;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    public double getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(double totalPrice) {
        this.totalPrice = totalPrice;
    }
}
