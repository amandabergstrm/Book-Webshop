package businessObjects;

import database.DbOrder;
import java.util.ArrayList;

/**
 * Represents an Order in the system. This class contains the order details such as user email,
 * order number, status, and items in the order.
 */
public class Order {
    private final String userEmail;
    private int orderNr;
    private OrderStatus status;
    private ArrayList<OrderItem> orderItems;

    /**
     * Inserts a new order into the database.
     *
     * @param order the {@link Order} object containing order details to insert
     */
    public static void createOrder(Order order) {
        DbOrder.executeOrderInsert(order);
    }

    /**
     * Imports all orders from the database.
     *
     * @return a list of all orders from the database
     */
    public static ArrayList<DbOrder> importAllOrders() {
        return DbOrder.importAllOrders();
    }

    /**
     * Updates the status of an existing order.
     *
     * @param orderNr the order number to update
     * @param status  the new status of the order
     */
    public static void updateOrderStatus(int orderNr, String status) {
        DbOrder.executeOrderUpdate(orderNr, status);
    }

    /**
     * Retrieves all orders for a specific user by their email.
     *
     * @param email the email of the user whose orders are to be retrieved
     * @return a list of orders associated with the user
     */
    public static ArrayList<DbOrder> searchUserOrders(String email) {
        return DbOrder.searchUserOrders(email);
    }

    /**
     * Constructs an {@link Order} object with the given user email and order items.
     * The status is set to Pending by default.
     *
     * @param userEmail  the email of the user who made the order
     * @param orderItems a list of items in the order
     */
    protected Order(String userEmail, ArrayList<OrderItem> orderItems) {
        this.userEmail = userEmail;
        this.orderItems = orderItems;
        this.status = OrderStatus.Pending;
    }

    /**
     * Constructs an {@link Order} object with the given user email, order number, order items, and status.
     *
     * @param userEmail  the email of the user who made the order
     * @param orderNr    the unique order number
     * @param orderItems a list of items in the order
     * @param status     the current status of the order
     */
    protected Order(String userEmail, int orderNr, ArrayList<OrderItem> orderItems, OrderStatus status) {
        this.userEmail = userEmail;
        this.orderNr = orderNr;
        this.orderItems = orderItems;
        this.status = status;
    }

    public String getUserEmail() { return userEmail; }
    public int getOrderNr() { return orderNr; }
    public OrderStatus getOrderStatus() { return status; }
    public ArrayList<OrderItem> getOrderItems() { return orderItems; }
}