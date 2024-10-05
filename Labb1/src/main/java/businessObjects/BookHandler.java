package businessObjects;

import database.DbBook;
import ui.BookInfo;
import ui.UserInfo;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;

public class BookHandler {
    public static void createBook(BookInfo book) {
        Book bookObj = new Book(book.getIsbn(), book.getTitle(), book.getGenre(), book.getAuthor(), book.getNrOfCopies(), book.getPrice());
        Book.createBook(bookObj);
    }

    public static BookInfo getBookByISBN(String isbn) {
        Book foundBook = Book.searchBookByISBN(isbn);
        return new BookInfo(foundBook.getIsbn(), foundBook.getTitle(), foundBook.getGenre(), foundBook.getAuthor(), foundBook.getNrOfCopies(), foundBook.getPrice());
    }

    public static Collection<BookInfo> getAllBooks() {
        ArrayList<DbBook> books = Book.importAllBooks();
        ArrayList<BookInfo> bookInfos = new ArrayList<BookInfo>();
        for(Book book: books){
            bookInfos.add(new BookInfo(book.getItemId(),book.getIsbn(), book.getTitle(), book.getGenre(), book.getAuthor( ), book.getNrOfCopies(), book.getPrice()));
        }
        return bookInfos;
    }

    public static void updateBook(BookInfo book, int newNrOfCopies, int newPrice) {
        Book bookObj = new Book(book.getItemId(), book.getIsbn(), book.getTitle(), book.getGenre(), book.getAuthor(), newNrOfCopies, newPrice);
        Book.updateBook(bookObj);
    }

    public static void deleteBook(BookInfo book) {
        Book bookObj = new Book(book.getItemId(), book.getIsbn(), book.getTitle(), book.getGenre(), book.getAuthor(), book.getNrOfCopies(), book.getPrice());
        bookObj.deleteBook(bookObj);
    }
}