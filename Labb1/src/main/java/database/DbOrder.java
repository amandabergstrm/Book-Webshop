package database;

import businessObjects.*;
import java.sql.*;
import java.util.ArrayList;

/**
 * The DbOrder class handles database operations related to orders, such as inserting,
 * updating, and retrieving orders from the database. It extends the Order class.
 */
public class DbOrder extends Order {

    int nrOfItems;

    /**
     * Constructs a DbOrder object with the specified user email, order number, list of order items, and status.
     *
     * @param userEmail  the email of the user who placed the order
     * @param orderNr    the unique identifier of the order
     * @param orderItems the list of order items associated with the order
     * @param status     the status of the order
     */
    public DbOrder(String userEmail, int orderNr, ArrayList<OrderItem> orderItems, OrderStatus status) {
        super(userEmail, orderNr, orderItems, status);
    }

    /**
     * Retrieves all order items associated with the specified order number.
     *
     * @param orderNr the unique identifier of the order
     * @return a list of OrderItem objects for the specified order
     */
    public static ArrayList<OrderItem> matchAllOrderItems(int orderNr) {
        ArrayList<OrderItem> orderItems = new ArrayList<>();
        String query = "SELECT itemId, nrOfItems FROM T_OrderItem WHERE orderNr = ?";
        Connection con = DbManager.getConnection();

        try (PreparedStatement preparedStatement = con.prepareStatement(query)) {
            preparedStatement.setInt(1, orderNr);
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                while (resultSet.next()) {
                    int itemId = resultSet.getInt("itemId");
                    int nrOfItems = resultSet.getInt("nrOfItems");
                    orderItems.add(new OrderItem(itemId, nrOfItems));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return orderItems;
    }

    /**
     * Retrieves all orders from the T_Order table in the database.
     *
     * @return a list of DbOrder objects representing all orders
     */
    public static ArrayList<DbOrder> importAllOrders() {
        ArrayList<DbOrder> orders = new ArrayList<>();
        String query = "SELECT T_Order.* FROM T_Order";
        Connection con = DbManager.getConnection();

        try (PreparedStatement preparedStatement = con.prepareStatement(query)) {
            con.setAutoCommit(false);
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                while (resultSet.next()) {
                    int orderNr = resultSet.getInt("orderNr");
                    String userEmail = resultSet.getString("userEmail");
                    String stringStatus = resultSet.getString("status");
                    OrderStatus status = OrderStatus.valueOf(stringStatus);

                    ArrayList<OrderItem> orderItems = matchAllOrderItems(orderNr);
                    orders.add(new DbOrder(userEmail, orderNr, orderItems, status));
                }
            }
            con.commit();
        } catch (SQLException e) {
            if (con != null) {
                try {
                    con.rollback();
                } catch (SQLException ex) {
                    e.printStackTrace();
                }
            }
            e.printStackTrace();
        } finally {
            try {
                if (con != null) {
                    con.setAutoCommit(true);
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return orders;
    }

    /**
     * Inserts a new order and its order items into the T_Order and T_OrderItem tables.
     *
     * @param order the Order object containing the order details
     */
    public static void executeOrderInsert(Order order) {
        String queryInsertOrder = "INSERT INTO T_Order(userEmail, status) VALUES(?, ?)";
        String queryInsertOrderItem = "INSERT INTO T_OrderItem(orderNr, itemId, nrOfItems) VALUES(?, ?, ?)";

        Connection con = DbManager.getConnection();
        try {
            con.setAutoCommit(false);

            // Insert new order into T_Order
            PreparedStatement preparedStatementOrder = con.prepareStatement(queryInsertOrder, PreparedStatement.RETURN_GENERATED_KEYS);
            preparedStatementOrder.setString(1, order.getUserEmail());
            preparedStatementOrder.setString(2, order.getOrderStatus().toString());

            preparedStatementOrder.execute();

            // Retrieve the generated order number (primary key)
            try (ResultSet generatedOrderNr = preparedStatementOrder.getGeneratedKeys()) {
                if (generatedOrderNr.next()) {
                    int orderNr = generatedOrderNr.getInt(1);

                    // Insert into T_OrderItem table
                    PreparedStatement prepareStatementOrderItem = con.prepareStatement(queryInsertOrderItem);
                    for (OrderItem item : order.getOrderItems()) {
                        prepareStatementOrderItem.setInt(1, orderNr);
                        prepareStatementOrderItem.setInt(2, item.getItemId());
                        prepareStatementOrderItem.setInt(3, item.getNrOfItems());
                        prepareStatementOrderItem.addBatch();
                    }
                    prepareStatementOrderItem.executeBatch(); // Batch insert all order items
                } else {
                    throw new SQLException("Creating order failed, no ID obtained.");
                }
            }
            con.commit();

        } catch (SQLException e) {
            if (con != null) {
                try {
                    con.rollback();
                } catch (SQLException ex) {
                    ex.printStackTrace();
                }
            }
            e.printStackTrace();
        } finally {
            try {
                if (con != null) {
                    con.setAutoCommit(true);
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * Searches for all orders placed by a specific user.
     *
     * @param email the email address of the user
     * @return a list of DbOrder objects representing the user's orders
     */
    public static ArrayList<DbOrder> searchUserOrders(String email) {
        ArrayList<DbOrder> orders = new ArrayList<>();
        String query = "SELECT T_Order.* FROM T_Order WHERE T_Order.userEmail LIKE ?";
        Connection con = DbManager.getConnection();

        try (PreparedStatement preparedStatement = con.prepareStatement(query)) {
            con.setAutoCommit(false);
            preparedStatement.setString(1, email + "%");
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                while (resultSet.next()) {
                    int orderNr = resultSet.getInt("orderNr");
                    String userEmail = resultSet.getString("userEmail");
                    String stringStatus = resultSet.getString("status");
                    OrderStatus status = OrderStatus.valueOf(stringStatus);

                    ArrayList<OrderItem> orderItems = matchAllOrderItems(orderNr);
                    orders.add(new DbOrder(userEmail, orderNr, orderItems, status));
                }
            }
            con.commit();
        } catch (SQLException e) {
            if (con != null) {
                try {
                    con.rollback();
                } catch (SQLException ex) {
                    e.printStackTrace();
                }
            }
            e.printStackTrace();
        } finally {
            try {
                if (con != null) {
                    con.setAutoCommit(true);
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return orders;
    }

    /**
     * Updates the status of an order in the T_Order table.
     *
     * @param orderNr the unique identifier of the order
     * @param status  the new status of the order
     */
    public static void executeOrderUpdate(int orderNr, String status) {
        String command = "UPDATE T_Order SET status = ? WHERE orderNr = ?";
        Connection con = DbManager.getConnection();

        try {
            con.setAutoCommit(false);
            PreparedStatement preparedStatement = con.prepareStatement(command);
            preparedStatement.setString(1, status);
            preparedStatement.setInt(2, orderNr);

            int rowsUpdated = preparedStatement.executeUpdate();
            if (rowsUpdated > 0) {
                System.out.println("Order status successfully updated");
            } else {
                System.out.println("No order found with the given order number.");
            }
            con.commit();
        } catch (SQLException e) {
            if (con != null) {
                try {
                    con.rollback();
                } catch (SQLException ex) {
                    e.printStackTrace();
                }
            }
            e.printStackTrace();
        } finally {
            try {
                if (con != null) {
                    con.setAutoCommit(true);
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
}
