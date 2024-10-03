package database;

import businessObjects.*;

import java.sql.*;
import java.util.ArrayList;

public class DbOrder extends Order {

    int nrOfItems;
    public DbOrder(User user, int orderNr, OrderStatus status, int itemId, int nrOfItems) {
        super(user, orderNr, new ArrayList<OrderItem>(), status);
        this.nrOfItems = nrOfItems;
    }
    //hämta en bok baserat på isbn - klar
    //hämta alla böcker - klar
    //hämta filtrerade böcker - amanda
    public static ArrayList<DbOrder> importAllOrders() {
        ArrayList<DbOrder> orders = new ArrayList<>();
        String query = "SELECT T_Order.* FROM T_Order";
        Connection con = DbManager.getConnection();

        try (PreparedStatement preparedStatement = con.prepareStatement(query)) {
            con.setAutoCommit(false);
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                while (resultSet.next()) {
                    String userEmail = resultSet.getString("user");
                    int orderNr = resultSet.getInt("orderNr");
                    String stringStatus = resultSet.getString("status");
                    OrderStatus status = OrderStatus.valueOf(stringStatus);
                    int itemId = resultSet.getInt("itemId");
                    int nrOfItems = resultSet.getInt("nrOfItems");

                    //gör en lista med orderItems istället, gör en check för de som har samma ordernr -> lägg till i samma order
                    //alla med samma ordernummer ska bli samma order
                    //orders.add(new DbOrder(userEmail, orderNr, status, itemId, nrOfItems));
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
}