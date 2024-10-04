package database;

import businessObjects.*;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class DbBook extends Book {
    public DbBook(int itemId, String isbn, String title, Genre genre, String author, int nrOfCopies, int price) {
        super(isbn, title, genre, author, nrOfCopies, price);
    }

    public static void executeBookInsert(Book book) {
        String command = "INSERT INTO " + "T_User(isbn, title, genre, author, nrOfCopies, price) VALUES(?, ?, ?, ?, ?, ?)";
        Connection con = DbManager.getConnection();

        try {
            con.setAutoCommit(false);
            PreparedStatement preparedStatement = con.prepareStatement(command);
            preparedStatement.setString(1, book.getIsbn());
            preparedStatement.setString(2, book.getTitle());
            preparedStatement.setString(3, book.getGenre().toString());
            preparedStatement.setString(4, book.getAuthor());
            preparedStatement.setInt(5, book.getNrOfCopies());
            preparedStatement.setInt(6, book.getPrice());
            preparedStatement.execute();
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

    public static DbBook searchBookByISBN(String isbn) {
        DbBook foundBook = null;
        String query = "SELECT T_Book.* FROM T_Book WHERE T_Book.isbn LIKE ?";
        Connection con = DbManager.getConnection();

        try (PreparedStatement preparedStatement = con.prepareStatement(query)) {
            con.setAutoCommit(false);
            preparedStatement.setString(1, isbn + "%");
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                if (resultSet.next()) {
                    int itemId = resultSet.getInt("itemId");
                    isbn = resultSet.getString("isbn");
                    String title = resultSet.getString("title");
                    String stringGenre = resultSet.getString("genre");
                    Genre genre = Genre.valueOf(stringGenre);
                    String author = resultSet.getString("author");
                    int nrOfCopies = resultSet.getInt("nrOfCopies");
                    int price = resultSet.getInt("price");

                    foundBook = new DbBook(itemId, isbn, title, genre, author, nrOfCopies, price);
                }
                else System.out.println("User not found");
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
        return foundBook;
    }

    public static ArrayList<DbBook> importAllBooks() {
        ArrayList<DbBook> books = new ArrayList<>();
        String query = "SELECT T_Book.* FROM T_Book";
        Connection con = DbManager.getConnection();

        try (PreparedStatement preparedStatement = con.prepareStatement(query)) {
            con.setAutoCommit(false);
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                while (resultSet.next()) {
                    int itemId = resultSet.getInt("itemId");
                    String isbn = resultSet.getString("isbn");
                    String title = resultSet.getString("title");
                    String stringGenre = resultSet.getString("genre");
                    Genre genre = Genre.valueOf(stringGenre);
                    String author = resultSet.getString("author");
                    int nrOfCopies = resultSet.getInt("nrOfCopies");
                    int price = resultSet.getInt("price");

                    books.add(new DbBook(itemId, isbn, title, genre, author, nrOfCopies, price));
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
        return books;
    }
}