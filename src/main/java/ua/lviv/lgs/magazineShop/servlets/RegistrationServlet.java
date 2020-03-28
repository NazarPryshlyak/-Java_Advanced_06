package ua.lviv.lgs.magazineShop.servlets;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;

import ua.lviv.lgs.magazineShop.daos.DAOException;
import ua.lviv.lgs.magazineShop.domain.AccessLevel;
import ua.lviv.lgs.magazineShop.domain.User;
import ua.lviv.lgs.magazineShop.service.UserService;
import ua.lviv.lgs.magazineShop.service.impl.UserServiceImpl;

public class RegistrationServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    private UserService userService = UserServiceImpl.getUserService();

    private Logger log = Logger.getLogger(RegistrationServlet.class);

    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        log.trace("Opening Registration Form...");
        request.getRequestDispatcher("register.jsp").forward(request, response);
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        log.trace("Getting fields values from Registration Form...");
        request.setCharacterEncoding("UTF-8");
        String firstName = request.getParameter("firstName");
        String lastName = request.getParameter("lastName");
        String email = request.getParameter("email");
        String password = request.getParameter("password");
        String accessLevel = null;

        if ("user".equals(request.getParameter("accessLevel"))) {
            accessLevel = AccessLevel.USER.toString();
        } else if ("admin".equals(request.getParameter("accessLevel"))) {
            accessLevel = AccessLevel.ADMIN.toString();
        }

        log.trace("Checking fields values for emptiness...");
        if (!firstName.isEmpty() && !lastName.isEmpty() && !email.isEmpty() && !password.isEmpty()) {
            try {
                log.trace("Saving user in database...");
                userService.insert(new User(firstName, lastName, email, password, accessLevel));
            } catch (DAOException e) {
                log.error("Creating user failed!", e);
            }

            log.trace("Preparing fields to return...");
            request.setAttribute("userFirstName", firstName);
            request.setAttribute("userLastName", lastName);
            request.setAttribute("userAction", "зареєструвався");

            log.trace("Redirecting to User's account page...");
            request.getRequestDispatcher("cabinet.jsp").forward(request, response);
            return;
        }

        log.warn("There are still some blank fields yet!");
        log.trace("Reopening Registration Form...");
        request.getRequestDispatcher("register.jsp").forward(request, response);
    }

}
