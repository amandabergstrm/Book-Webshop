package businessObjects;

import ui.BookInfo;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;

public class BookHandler {
    public static BookInfo getBookByISBN(String isbn) {
        Book foundBook = Book.searchBookByISBN(isbn);
        return new BookInfo(foundBook.getIsbn(), foundBook.getTitle(), foundBook.getGenre(), foundBook.getAuthor(), foundBook.getNrOfCopies());
    }

    public static Collection<BookInfo> getAllBooks() {
        Collection c = Book.importAllBooks();
        ArrayList<BookInfo> books = new ArrayList<BookInfo>();
        for (Iterator it = c.iterator(); it.hasNext();) {
            Book book = (Book) it.next();
            books.add(new BookInfo(book.getIsbn(), book.getTitle(), book.getGenre(), book.getAuthor(), book.getNrOfCopies()));
        }
        return books;
    }
}