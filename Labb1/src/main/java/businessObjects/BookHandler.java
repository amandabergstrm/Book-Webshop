package businessObjects;

import ui.BookInfo;

public class BookHandler {
    public static BookInfo getBookByISBN(String isbn) {
        Book foundBook = Book.searchBookByISBN(isbn);
        return new BookInfo(foundBook.getIsbn(), foundBook.getTitle(), foundBook.getGenre(), foundBook.getAuthor(), foundBook.getNrOfCopies());
    }
}