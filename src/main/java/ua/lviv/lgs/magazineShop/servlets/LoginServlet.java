package ua.lviv.lgs.magazineShop.servlets;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;

import ua.lviv.lgs.magazineShop.daos.DAOException;
import ua.lviv.lgs.magazineShop.domain.User;
import ua.lviv.lgs.magazineShop.service.UserService;
import ua.lviv.lgs.magazineShop.service.impl.UserServiceImpl;

public class LoginServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    private UserService userService = UserServiceImpl.getUserService();

    private Logger log = Logger.getLogger(LoginServlet.class);

    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        log.trace("Opening Login Form...");
        request.getRequestDispatcher("login.jsp").forward(request, response);
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        log.trace("Getting fields values from Login Form...");
        request.setCharacterEncoding("UTF-8");
        String login = request.getParameter("login");
        String password = request.getParameter("password");

        User user = null;

        log.trace("Checking fields values for emptiness...");
        if (!login.isEmpty() && !password.isEmpty()) {
            try {
                log.trace("Getting user from database...");
                user = userService.readByEmail(login);
            } catch (DAOException e) {
                log.error("Getting user by email failed!", e);
            }

            if (user == null) {
                log.warn("There is no user with login \"" + login + "\" in database!");
                log.trace("Reopening Login Form...");
                request.getRequestDispatcher("login.jsp").forward(request, response);
            } else {
                log.trace("Checking user's password for matching database...");
                if (user.getPassword().equals(password)) {
                    log.trace("Preparing fields to return...");
                    request.setAttribute("userFirstName", user.getFirstName());
                    request.setAttribute("userLastName", user.getLastName());
                    request.setAttribute("userAction", "авторизувався");

                    log.trace("Redirecting to User's account page...");
                    request.getRequestDispatcher("cabinet.jsp").forward(request, response);
                    return;
                } else {
                    log.warn("User's password doesn't match database!");
                    log.trace("Reopening Login Form...");
                    request.getRequestDispatcher("login.jsp").forward(request, response);
                }
            }
        } else {
            log.warn("There are still some blank fields yet!");
            log.trace("Reopening Login Form...");
            request.getRequestDispatcher("login.jsp").forward(request, response);
        }
    }

}