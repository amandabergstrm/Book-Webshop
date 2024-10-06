package businessObjects;

import ui.view.BookInfo;
import ui.view.OrderInfo;
import ui.view.OrderItemInfo;
import java.util.ArrayList;
import java.util.Collection;

/**
 * Handles the business logic related to orders. This class interacts with {@link Order} objects
 * and performs CRUD operations such as creating orders, retrieving orders, updating order status, etc.
 */
public class OrderHandler {

    /**
     * Creates a new order based on the provided {@link OrderInfo} object.
     *
     * @param orderInfo the {@link OrderInfo} object containing order details
     */
    public static void createOrder(OrderInfo orderInfo) {
        ArrayList<OrderItem> orderItems = new ArrayList<>();
        ArrayList<OrderItemInfo> orderItemInfos = orderInfo.getOrderItemInfo();

        for (OrderItemInfo o : orderItemInfos) {
            orderItems.add(new OrderItem(o.getItemId(), o.getNrOfItems()));
        }
        Order orderObj = new Order(orderInfo.getUserEmail(), orderItems);
        Order.createOrder(orderObj);
    }

    /**
     * Retrieves all orders from the database.
     *
     * @return a collection of {@link OrderInfo} objects representing all orders
     */
    public static Collection<OrderInfo> getAllOrders() {
        Collection orders = Order.importAllOrders();
        if (orders == null) {
            System.out.println("No orders found.");
        }
        return convertOrderToOrderInfo(orders);
    }

    /**
     * Retrieves all orders for a specific user by their email.
     *
     * @param email the email of the user whose orders are to be retrieved
     * @return a list of {@link OrderInfo} objects representing the user's orders
     */
    public static ArrayList<OrderInfo> getUserOrders(String email) {
        Collection orders = Order.searchUserOrders(email);
        return convertOrderToOrderInfo(orders);
    }

    /**
     * Updates the status of an order.
     *
     * @param orderNr   the order number to update
     * @param newStatus the new status to set for the order
     */
    public static void updateOrderStatus(int orderNr, String newStatus) {
        Order.updateOrderStatus(orderNr, newStatus);
    }

    /**
     * Converts a collection of {@link Order} objects into a list of {@link OrderInfo} objects.
     *
     * @param orders the collection of {@link Order} objects to convert
     * @return a list of {@link OrderInfo} objects
     */
    private static ArrayList<OrderInfo> convertOrderToOrderInfo(Collection<Order> orders) {
        ArrayList<OrderInfo> orderInfoList = new ArrayList<>();

        for (Order o : orders) {
            ArrayList<OrderItem> orderItems = o.getOrderItems();
            ArrayList<OrderItemInfo> orderItemInfos = new ArrayList<>();

            for (OrderItem item : orderItems) {
                BookInfo bookInfo = BookHandler.getBookByItemId(item.getItemId());
                OrderItemInfo orderItemInfo = new OrderItemInfo(bookInfo, bookInfo.getItemId(), 1);
                //OrderItemInfo orderItemInfo = new OrderItemInfo(item.getItemId(), item.getNrOfItems());
                orderItemInfos.add(orderItemInfo);
            }
            orderInfoList.add(new OrderInfo(o.getUserEmail(), o.getOrderNr(), orderItemInfos, o.getOrderStatus()));
        }
        return orderInfoList;
    }
}