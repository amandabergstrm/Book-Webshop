package database;

import businessObjects.*;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class DbBook extends Book {
    public DbBook(String isbn, String title, Genre genre, String author, int nrOfCopies) {
        super(isbn, title, genre, author, nrOfCopies);
    }

    //hämta en bok baserat på isbn - klar
    //hämta alla böcker
    //hämta filtrerade böcker - sen

    public static DbBook searchBookByISBN(String isbn) {
        DbBook foundBook = null;
        String query = "SELECT T_Book.* FROM T_Book WHERE T_Book.isbn LIKE ?";
        Connection con = DbManager.getConnection();

        try (PreparedStatement preparedStatement = con.prepareStatement(query)) {
            // con.setAutoCommit(false);
            preparedStatement.setString(1, isbn + "%");
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                if (resultSet.next()) {
                    isbn = resultSet.getString("isbn");
                    String title = resultSet.getString("title");
                    String stringGenre = resultSet.getString("genre");
                    Genre genre = Genre.valueOf(stringGenre);
                    String author = resultSet.getString("author");
                    int nrOfCopies = resultSet.getInt("nrOfCopies");

                    foundBook = new DbBook(isbn, title, genre, author, nrOfCopies);
                }
                else System.out.println("User not found");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return foundBook;
    }
}
