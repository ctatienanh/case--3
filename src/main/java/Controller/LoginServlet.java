package Controller;

import Controller.Filter.Filter;
import Dao.UserDao;
import Modal.Account;
import Service.FilmService;
import Service.AccountService;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.Date;
import java.sql.SQLException;

@WebServlet(urlPatterns = "/login")
public class LoginServlet extends HttpServlet {
    UserDao userDao = new UserDao();
    AccountService userService = new AccountService();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String action = req.getParameter("action");
        if (action == null) {
            action = "";
        }
        RequestDispatcher requestDispatcher;
        switch (action) {
            case"logout":
                Filter.account=null;
                requestDispatcher = req.getRequestDispatcher("/index.jsp");
                requestDispatcher.forward(req, resp);
                break;
        }
    }


    @Override
    public void  doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String action = req.getParameter("action");
        if(action==null){
            action="";
        }
        RequestDispatcher requestDispatcher;
        switch (action){
            case "signup":
                signup(req, resp);
                break;
            default:
               if(checkAccountExit(req,resp)){
                   req.setAttribute("films", FilmService.films);
                   String userName = req.getParameter("username");
                   String passWord = req.getParameter("password");
                   Filter.account= new Account(userName,passWord);
                    resp.sendRedirect("/viewUser");
               }
               else {
                   String message = "Check password again !";
                   req.setAttribute("message", message);
                   requestDispatcher = req.getRequestDispatcher("/index.jsp");
                   requestDispatcher.forward(req, resp);
               }
        }
    }
    public boolean checkAccountExit(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException{
        String userName = req.getParameter("username");
        String passwordUser = req.getParameter("password");
        for(int i = 0; i< AccountService.accounts.size(); i++){
            if(AccountService.accounts.get(i).getUserName().equals(userName)
            && AccountService.accounts.get(i).getPasswordUser().equals(passwordUser)){
                return true;
            }
        }
        return false;
    }
    public void signup(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException{
        String userName = req.getParameter("userName");
        Date birthdayUser= Date.valueOf(req.getParameter("userBirthday"));
        int numberUser = Integer.parseInt(req.getParameter("userNumber"));
        String mailUser = req.getParameter("userEmail");
        String passwordUser = req.getParameter("userPassword");
        String addressUser = req.getParameter("userAddress");
        try {
            userDao.insertAccount(new Account(userName,birthdayUser,numberUser,mailUser,passwordUser,addressUser));
        } catch (SQLException e){
            throw new RuntimeException(e);
        }
        RequestDispatcher requestDispatcher = req.getRequestDispatcher("/index.jsp");
        requestDispatcher.forward(req, resp);
    }
}
