package ui;

import java.io.*;

import businessObjects.Authority;
import businessObjects.UserHandler;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;

@WebServlet(name = "loginServlet", value = "/login-servlet")
public class LoginServlet extends HttpServlet {
    @Override
    public void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String action = request.getParameter("action");
        HttpSession session = request.getSession();

        if ("login".equals(action)) {
            String email = request.getParameter("email");
            String password = request.getParameter("password");

            UserInfo existingUser = UserHandler.getUserByEmail(email);
            if (existingUser == null) {
                System.out.println("User hittades inte, prova igen eller create");
                response.sendRedirect("login.jsp");
            } else if (existingUser.getPassword().equals(password)) {
                System.out.println("logged in as" + existingUser.getName());
                session.setAttribute("currentUser", existingUser);

                if (existingUser.getAuthority() == Authority.Admin) {
                    response.sendRedirect("shop.jsp"); // skicka till admin sida eller till home men dem ser allt
                } else if (existingUser.getAuthority() == Authority.WarehouseWorker) {
                    response.sendRedirect("shop.jsp"); // skicka till lager sida eller till home men dem ser det dem behöver
                } else {
                    response.sendRedirect("shop.jsp"); // gör så man hamnar i checkout endast om man tryck på proceed to pay annars hamna i home
                }
            } else {
                System.out.println("Fel lösenord (rätt email)");
                response.sendRedirect("login.jsp"); // prova igen
            }
        } else if ("createUser".equals(action)) {
            String username = request.getParameter("username");
            String email = request.getParameter("email");
            String password = request.getParameter("password");

            UserInfo existingUser = UserHandler.getUserByEmail(email);
            if (existingUser != null) {
                System.out.println("Skriv att kontot redan finns så får dem försöka igen");
                response.sendRedirect("login.jsp");
            } else {
                UserInfo newUserInfo = new UserInfo(Authority.Customer, username, email, password);
                UserHandler.createUser(newUserInfo);
                System.out.println("Fortsätt till kassa");
                session.setAttribute("currentUser", newUserInfo);
                response.sendRedirect("shop.jsp"); // ska fortsätta till kassa om man var i varukorgen när man logagr in
            }
        } else if ("logout".equals(action)) {
            session = request.getSession();
            session.invalidate();
            response.sendRedirect("index.jsp");
        } else {
            response.getWriter().write("Invalid action.");
        }
    }
}