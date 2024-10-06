package database;

import businessObjects.*;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

/**
 * The DbBook class is responsible for database interactions related to books,
 * such as inserting, updating, and retrieving books and their details from the database.
 */
public class DbBook extends Book {

    /**
     * Constructor to create a DbBook object with the specified parameters.
     *
     * @param itemId     the unique identifier of the book
     * @param isbn       the ISBN of the book
     * @param title      the title of the book
     * @param genre      the genre of the book
     * @param author     the author of the book
     * @param nrOfCopies the number of copies available
     * @param price      the price of the book
     */
    public DbBook(int itemId, String isbn, String title, String genre, String author, int nrOfCopies, int price) {
        super(itemId, isbn, title, genre, author, nrOfCopies, price);
    }

    /**
     * Inserts a new book into the T_Book table in the database.
     *
     * @param bookObj the Book object containing the book details to insert
     */
    public static void executeBookInsert(Book bookObj) {
        String command = "INSERT INTO T_Book(isbn, title, genre, author, nrOfCopies, price) VALUES(?, ?, ?, ?, ?, ?)";
        Connection con = DbManager.getConnection();

        try {
            con.setAutoCommit(false);
            PreparedStatement preparedStatement = con.prepareStatement(command);
            preparedStatement.setString(1, bookObj.getIsbn());
            preparedStatement.setString(2, bookObj.getTitle());
            preparedStatement.setString(3, bookObj.getGenre());
            preparedStatement.setString(4, bookObj.getAuthor());
            preparedStatement.setInt(5, bookObj.getNrOfCopies());
            preparedStatement.setInt(6, bookObj.getPrice());
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

    /**
     * Searches for a book in the database by its item ID.
     *
     * @param itemId the unique identifier of the book
     * @return a DbBook object if the book is found, otherwise null
     */
    public static DbBook searchBookByItemID(int itemId) {
        DbBook dbBook = null;
        String query = "SELECT T_Book.* FROM T_Book WHERE T_Book.itemId LIKE ?";
        Connection con = DbManager.getConnection();

        try (PreparedStatement preparedStatement = con.prepareStatement(query)) {
            con.setAutoCommit(false);
            preparedStatement.setString(1, itemId + "%");
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                if (resultSet.next()) {
                    itemId = resultSet.getInt("itemId");
                    String isbn = resultSet.getString("isbn");
                    String title = resultSet.getString("title");
                    String genre = resultSet.getString("genre");
                    String author = resultSet.getString("author");
                    int nrOfCopies = resultSet.getInt("nrOfCopies");
                    int price = resultSet.getInt("price");

                    dbBook = new DbBook(itemId, isbn, title, genre, author, nrOfCopies, price);
                } else {
                    System.out.println("Book not found");
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
        return dbBook;
    }

    /**
     * Retrieves all books from the T_Book table in the database.
     *
     * @return an ArrayList of DbBook objects representing all books
     */
    public static ArrayList<DbBook> importAllBooks() {
        ArrayList<DbBook> dbBooks = new ArrayList<>();

        String query = "SELECT T_Book.* FROM T_Book";
        Connection con = DbManager.getConnection();

        try (PreparedStatement preparedStatement = con.prepareStatement(query)) {
            con.setAutoCommit(false);
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                while (resultSet.next()) {
                    int itemId = resultSet.getInt("itemId");
                    String isbn = resultSet.getString("isbn");
                    String title = resultSet.getString("title");
                    String genre = resultSet.getString("genre");
                    String author = resultSet.getString("author");
                    int nrOfCopies = resultSet.getInt("nrOfCopies");
                    int price = resultSet.getInt("price");

                    dbBooks.add(new DbBook(itemId, isbn, title, genre, author, nrOfCopies, price));
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
        return dbBooks;
    }

    /**
     * Updates the number of copies and price of a book in the T_Book table in the database.
     *
     * @param bookObj the Book object containing the updated book details
     */
    public static void executeBookUpdate(Book bookObj) {
        String command = "UPDATE T_Book SET nrOfCopies = ?, price = ? WHERE itemId = ?";
        Connection con = DbManager.getConnection();

        try {
            con.setAutoCommit(false);
            PreparedStatement preparedStatement = con.prepareStatement(command);
            preparedStatement.setInt(1, bookObj.getNrOfCopies());
            preparedStatement.setInt(2, bookObj.getPrice());
            preparedStatement.setInt(3, bookObj.getItemId());

            int rowsUpdated = preparedStatement.executeUpdate();
            if (rowsUpdated > 0) {
                System.out.println("Book details updated successfully.");
            } else {
                System.out.println("No book found with the given itemId.");
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

    /**
     * Removes a book from the T_Book table in the database by its item ID.
     *
     * @param itemId the unique identifier of the book to be removed
     */
    public static void executeBookRemove(int itemId) {
        String command = "DELETE FROM T_Book WHERE itemId = ?";
        Connection con = DbManager.getConnection();

        try {
            con.setAutoCommit(false);
            PreparedStatement preparedStatement = con.prepareStatement(command);
            preparedStatement.setInt(1, itemId);
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

    /**
     * Inserts a new category (genre) into the T_Category table in the database.
     *
     * @param genre the genre to be added
     */
    public static void executeCategoryInsert(String genre) {
        String command = "INSERT INTO T_Category(genre) VALUES(?)";
        Connection con = DbManager.getConnection();

        try {
            con.setAutoCommit(false);
            PreparedStatement preparedStatement = con.prepareStatement(command);
            preparedStatement.setString(1, genre);
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

    /**
     * Retrieves all categories (genres) from the T_Category table in the database.
     *
     * @return an ArrayList of Strings representing all categories
     */
    public static ArrayList<String> importAllCategories() {
        ArrayList<String> categories = new ArrayList<>();

        String query = "SELECT T_Category.* FROM T_Category";
        Connection con = DbManager.getConnection();

        try (PreparedStatement preparedStatement = con.prepareStatement(query)) {
            con.setAutoCommit(false);
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                while (resultSet.next()) {
                    categories.add(resultSet.getString("genre"));
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
        return categories;
    }
}
