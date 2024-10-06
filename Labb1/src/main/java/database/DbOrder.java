package database;

import businessObjects.*;

import java.sql.*;
import java.util.ArrayList;

public class DbOrder extends Order {

    int nrOfItems;
    public DbOrder(String userEmail, int orderNr, ArrayList<OrderItem> orderItems, OrderStatus status) {
        super(userEmail, orderNr, orderItems, status);
    }

    public static ArrayList<OrderItem> matchAllOrderItems(int orderNr){
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

    public static void executeOrderInsert(Order order) {
        String queryInsertOrder = "INSERT INTO T_Order(userEmail, status) VALUES(?, ?)";
        String queryInsertOrderItem = "INSERT INTO T_OrderItem(orderNr, itemId, nrOfItems) VALUES(?, ?, ?)";

        Connection con = DbManager.getConnection();
        try {
            con.setAutoCommit(false);

            //Lägg in ny order i T_Order
            PreparedStatement preparedStatementOrder = con.prepareStatement(queryInsertOrder, PreparedStatement.RETURN_GENERATED_KEYS);
            preparedStatementOrder.setString(1, order.getUserEmail());  // Use the getUserEmail() method
            preparedStatementOrder.setString(2, order.getOrderStatus().toString());

            preparedStatementOrder.execute();
            // Execute the order insert
            /*int affectedRows = preparedStatement.executeUpdate();

            // Retrieve the generated orderNr (primary key)
            if (affectedRows == 0) {
                throw new SQLException("Creating order failed, no rows affected.");
            }*/

            try (ResultSet generatedOrderNr = preparedStatementOrder.getGeneratedKeys()) {
                if (generatedOrderNr.next()) {
                    int orderNr = generatedOrderNr.getInt(1);

                    // Lägg in i T_OrderItem tabellen
                    PreparedStatement prepareStatementOrderItem = con.prepareStatement(queryInsertOrderItem);
                    for (OrderItem item : order.getOrderItems()) {
                        prepareStatementOrderItem.setInt(1, orderNr);
                        prepareStatementOrderItem.setInt(2, item.getItemId());
                        prepareStatementOrderItem.setInt(3, item.getNrOfItems());
                        prepareStatementOrderItem.addBatch(); // Mer effektivt att lägga till i en batch och sen lägga in allt
                    }
                    prepareStatementOrderItem.executeBatch(); // Gör en insert för allt samtidigt
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

    public static void executeOrderUpdate(int orderNr, String status ) {
        String command = "UPDATE T_Order SET status = ? WHERE orderNr = ?";
        Connection con = DbManager.getConnection();

        try {
            con.setAutoCommit(false);
            PreparedStatement preparedStatement = con.prepareStatement(command);
            //String statusString = orderObj.getOrderStatus().name();
            preparedStatement.setString(1,status);
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
            } e.printStackTrace();
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
