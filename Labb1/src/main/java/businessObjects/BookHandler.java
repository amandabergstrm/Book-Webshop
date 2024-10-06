package businessObjects;

import ui.BookInfo;
import java.util.ArrayList;
import java.util.Collection;

public class BookHandler {
    public static void createBook(BookInfo bookInfo) {
        Book bookObj = new Book(bookInfo.getIsbn(), bookInfo.getTitle(), bookInfo.getGenre(), bookInfo.getAuthor(), bookInfo.getNrOfCopies(), bookInfo.getPrice());
        Book.createBook(bookObj);
    }

    public static BookInfo getBookByItemId(int itemId) {
        Book bookObj = Book.searchBookByItemId(itemId);
        return new BookInfo(bookObj.getItemId(), bookObj.getIsbn(), bookObj.getTitle(), bookObj.getGenre(), bookObj.getAuthor(), bookObj.getNrOfCopies(), bookObj.getPrice());
    }

    public static Collection<BookInfo> getAllBooks() {
        Collection c = Book.importAllBooks();
        ArrayList<BookInfo> booksInfo = new ArrayList<BookInfo>();
        for (Object b : c) {
            Book bookObj = (Book) b;
            booksInfo.add(new BookInfo(bookObj.getItemId(), bookObj.getIsbn(), bookObj.getTitle(), bookObj.getGenre(), bookObj.getAuthor(), bookObj.getNrOfCopies(), bookObj.getPrice()));
        }
        return booksInfo;
    }

    public static void updateBook(BookInfo bookInfo, int newNrOfCopies, int newPrice) {
        Book bookObj = new Book(bookInfo.getItemId(), bookInfo.getIsbn(), bookInfo.getTitle(), bookInfo.getGenre(), bookInfo.getAuthor(), newNrOfCopies, newPrice);
        Book.updateBook(bookObj);
    }

    public static void deleteBookById(int itemId) {
        Book.deleteBookById(itemId);
    }

    public static void addCategory(String category) {
        Book.addCategory(category);
    }

    public static ArrayList<String> getAllCategories() {
        ArrayList<String> categories = Book.importAllCategories();
        return new ArrayList<>(categories);
    }
}