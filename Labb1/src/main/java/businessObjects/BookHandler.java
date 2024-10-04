package businessObjects;

import ui.BookInfo;

import java.util.ArrayList;
import java.util.Collection;

public class BookHandler {
    public static void createBook(BookInfo book) {
        Book bookObj = new Book(book.getIsbn(), book.getTitle(), book.getGenre(), book.getAuthor(), book.getNrOfCopies(), book.getPrice());
        Book.createBook(bookObj);
    }

    public static BookInfo getBookByItemId(int itemId) {
        Book foundBook = Book.searchBookByItemId(itemId);
        return new BookInfo(foundBook.getItemId(), foundBook.getIsbn(), foundBook.getTitle(), foundBook.getGenre(), foundBook.getAuthor(), foundBook.getNrOfCopies(), foundBook.getPrice());
    }

    public static Collection<BookInfo> getAllBooks() {
        Collection c = Book.importAllBooks();
        ArrayList<BookInfo> books = new ArrayList<BookInfo>();
        for (Object b : c) {
            Book book = (Book) b;
            books.add(new BookInfo(book.getItemId(),book.getIsbn(), book.getTitle(), book.getGenre(), book.getAuthor(), book.getNrOfCopies(), book.getPrice()));
        }
        return books;
    }

    public static void updateBook(BookInfo book, int newNrOfCopies, int newPrice) {
        Book bookObj = new Book(book.getItemId(), book.getIsbn(), book.getTitle(), book.getGenre(), book.getAuthor(), newNrOfCopies, newPrice);
        Book.updateBook(bookObj);
    }

    public static void deleteBookById(int itemId) {
        Book.deleteBookById(itemId);
    }
}