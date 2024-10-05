package ui;

import businessObjects.BookHandler;
import businessObjects.UserHandler;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

@WebServlet(name = "shopServlet", value = "/shop-servlet")
public class ShopServlet extends HttpServlet {
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        HttpSession session = request.getSession();

        Collection<BookInfo> booksInfo = BookHandler.getAllBooks();
        session.setAttribute("booksInfo", booksInfo);

        Set<String> uniqueGenres = new HashSet<>();
        uniqueGenres.add(" ");
        for (BookInfo bookInfo : booksInfo) {
            uniqueGenres.add(bookInfo.getGenre());
        }
        session.setAttribute("genres", new ArrayList<>(uniqueGenres));

        Collection<UserInfo> usersInfo = UserHandler.getAllUsers();
        session.setAttribute("usersInfo", usersInfo);

        request.getRequestDispatcher("shop.jsp").forward(request, response);
    }
}
