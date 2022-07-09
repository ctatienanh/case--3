package Controller;

import Dao.GheDao;
import Dao.VeDao;
import Modal.Ghe;
import Modal.Ve;
import Service.AccountService;
import Service.FilmService;
import Service.GheService;
import Service.VeService;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;

@WebServlet(urlPatterns = "/BookTicket")
public class BookTicketServlet extends HttpServlet {
    GheDao gheDao = new GheDao();
    VeDao veDao = new VeDao();
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String action = req.getParameter("action");
        if(action==null){
            action="";
        }
        switch (action){
            case "chonghe":
                int idPhim = Integer.parseInt(req.getParameter("idPhim"));
                int idUser = Integer.parseInt(req.getParameter("idUser"));
                int idGhe = Integer.parseInt(req.getParameter("idGhe"));
                String tenGhe = req.getParameter("tenGhe");
                try {
                    gheDao.insertGhe(new Ghe(idGhe,tenGhe));
                } catch (SQLException e){
                    throw new RuntimeException(e);
                }
                try {
                    veDao.insertVe(new Ve(FilmService.findFilm(idPhim), GheService.findGhe(idGhe), AccountService.findAccount(idUser)));
                } catch (SQLException e){
                    throw new RuntimeException(e);
                }
                req.setAttribute("quantity", veDao.quantityTicket(idPhim));
                resp.sendRedirect("/viewUser");
        }
    }
}
