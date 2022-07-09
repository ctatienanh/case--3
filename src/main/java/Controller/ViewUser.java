package Controller;

import Controller.Filter.Filter;
import Dao.VeDao;
import Modal.Film;
import Service.AccountService;
import Service.FilmService;
import Service.VeService;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet(urlPatterns = "/viewUser")
public class ViewUser extends HttpServlet {
    VeDao veDao = new VeDao();
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setAttribute("films", FilmService.films);
        req.setAttribute("idUser",  AccountService.findIdAccountByName(Filter.account.getUserName()));
        req.setAttribute("username",Filter.account.getUserName());
//        req.setAttribute("Quantiy",veDao.);
//        req.setAttribute("", veDao.quantityTicket(FilmService.films.));
        req.setAttribute("quantity", VeService.ves.size());
        RequestDispatcher requestDispatcher = req.getRequestDispatcher("/ViewUser.jsp");
        requestDispatcher.forward(req,resp);
    }
}
