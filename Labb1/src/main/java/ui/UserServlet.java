package ui;

import java.io.*;

import businessObjects.Authority;
import businessObjects.UserHandler;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;

@WebServlet(name = "userServlet", value = "/user-servlet")
public class UserServlet extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
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
                    response.sendRedirect("index.jsp"); // skicka till admin sida eller till home men dem ser allt
                } else if (existingUser.getAuthority() == Authority.WarehouseWorker) {
                    response.sendRedirect("index.jsp"); // skicka till lager sida eller till home men dem ser det dem behöver
                } else {
                    response.sendRedirect("index.jsp"); // gör så man hamnar i checkout endast om man tryck på proceed to pay annars hamna i home
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
                response.sendRedirect("index.jsp"); // ska fortsätta till kassa om man var i varukorgen när man logagr in
            }
        } else if ("logout".equals(action)) {
            session = request.getSession();
            session.invalidate();
            response.sendRedirect("index.jsp");
        } else {
            response.getWriter().write("Invalid action.");
        }
    }
    /*
    @Override
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String action = request.getParameter("action");

        if ("login".equals(action)) {
            UserInfo existingUser = UserHandler.getUserByEmail(request.getParameter("loginEmail"));
            if (existingUser == null) {
                System.out.println("Skriv att user ej finns och öppna create user dialog");
            } else {
                System.out.println("Fortsätt till kassa");
            }
        } else if ("createUser".equals(action)) {
            UserInfo existingUser = UserHandler.getUserByEmail(request.getParameter("email"));
            if (existingUser != null) {
                System.out.println("Skriv att kontot redan finns så får dem försöka igen");
            }
            UserHandler.createUser(new UserInfo(Authority.Customer, request.getParameter("username"), request.getParameter("email"), request.getParameter("password")));
            System.out.println("Fortsätt till kassa");
        } else {
            response.getWriter().write("Invalid action.");
        }
    }

    session :

    If you need to pass any session information (e.g., logged-in user data), you can use the HttpSession object in the servlet:

    HttpSession session = request.getSession();
    session.setAttribute("user", username); // Or other relevant info
    response.sendRedirect("index.jsp");

    In index.jsp, you can then check for session attributes like this:

    <%
        String username = (String) session.getAttribute("user");
        if (username != null) {
    %>
        <p>Welcome, <%= username %>!</p>
    <%
        }
    %>

    */
}