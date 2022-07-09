package Controller;

import Dao.LichSuMuaVeDao;
import Dao.VeDao;
import Modal.Film;
import Service.FilmService;
import Service.LichSuMuaVeSerVice;
import Service.VeService;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
@WebServlet(urlPatterns = "/dichvu")
public class ServiceServlet extends HttpServlet {
   VeDao veDao = new VeDao();

   LichSuMuaVeDao lichSuMuaVeDao = new LichSuMuaVeDao();
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String action = req.getParameter("action");
        if(action==null){
            action="";
        }
        RequestDispatcher requestDispatcher;
        switch (action){
            case "chonghe":
                int idPhim = Integer.parseInt(req.getParameter("idPhim"));
                req.setAttribute("idPhim",idPhim);
                Film film= FilmService.films.get(FilmService.vtPhim(idPhim));
                req.setAttribute("img", film.getImg());
                req.setAttribute("tenPhim", film.getTenPhim());
                req.setAttribute("loaiPhim",film.getLoaiPhim());
                req.setAttribute("quocGia",film.getQuocGia());
                req.setAttribute("thoiGian",film.getThoiGian());
                req.setAttribute("ngayKhoiChieu",film.getNgayKhoiChieu());
                req.setAttribute("giaPhim",film.getGiaPhim());
                int idUser = Integer.parseInt(req.getParameter("idUser"));
                req.setAttribute("idUser",idUser);
                req.setAttribute("soLuongDaMua",lichSuMuaVeDao.quantityTicket(idPhim));
                requestDispatcher = req.getRequestDispatcher("/Ghe.jsp");
                requestDispatcher.forward(req,resp);
                break;
            case  "cart":
                int idUser1 = Integer.parseInt(req.getParameter("idUser"));
                req.setAttribute("idUser",idUser1);
                req.setAttribute("listVe", VeService.listVeByIdUser(idUser1));
                int quantityTicket = VeService.listVeByIdUser(idUser1).size();
                req.setAttribute("quantity",quantityTicket);
                requestDispatcher = req.getRequestDispatcher("/Cart.jsp");
                requestDispatcher.forward(req,resp);
                break;
            case "lichsumuave":
                int idUser2 = Integer.parseInt(req.getParameter("idUser"));
                req.setAttribute("idUser",idUser2);
                req.setAttribute("listVeLSMV", LichSuMuaVeSerVice.listVeFromLSMVByIdUser(idUser2));
                req.setAttribute("totalMoney",LichSuMuaVeSerVice.totalMoneyPaymentBuyId(idUser2));
                int quantityTicketHistory = LichSuMuaVeSerVice.listVeFromLSMVByIdUser(idUser2).size();
                req.setAttribute("quantityLSMV",quantityTicketHistory);
                requestDispatcher = req.getRequestDispatcher("/History.jsp");
                requestDispatcher.forward(req,resp);
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String action = req.getParameter("action");
        if(action==null){
            action="";
        }
        switch(action){
            case "thanhtoan":
                int idUser =  Integer.parseInt(req.getParameter("idUser"));
                veDao.deleteVeByIdAccount(idUser);
                VeService.ves= veDao.selectAllVe();
                resp.sendRedirect("/viewUser");
                break;
        }
    }
}
