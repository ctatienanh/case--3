package Controller;

import Dao.CommentDao;
import Dao.FilmDao;
import Dao.ReplyDao;
import Modal.Comment;
import Modal.Film;
import Modal.Reply;
import Service.CommentService;
import Service.FilmService;
import Service.ReplyService;
import Service.VeService;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;

@WebServlet(urlPatterns = "/dichvu")
public class ServiceServlet extends HttpServlet {
     FilmDao filmDao = new FilmDao();
     CommentDao commentDao = new CommentDao();
     ReplyDao replyDao = new ReplyDao();


     CommentService commentService = new CommentService();
     ReplyService replyService = new ReplyService();
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
                int idUser = Integer.parseInt(req.getParameter("idUser"));
                req.setAttribute("idUser",idUser);
                requestDispatcher = req.getRequestDispatcher("/Ghe.jsp");
                requestDispatcher.forward(req,resp);
                break;
            case  "cart":
                int idUser1 = Integer.parseInt(req.getParameter("idUser"));
                req.setAttribute("listVe", VeService.listVeByIdUser(idUser1));
                int quantityTicket = VeService.listVeByIdUser(idUser1).size();
                req.setAttribute("quantity",quantityTicket);
                requestDispatcher = req.getRequestDispatcher("/Cart.jsp");
                requestDispatcher.forward(req,resp);
                break;
            case "movie":
                //phim
                String cinema = req.getParameter("cinema");
                req.setAttribute("cinema",cinema);
                int idP = filmDao.finbyid(cinema);
                req.setAttribute("idphim",idP);
                //array  phim
                ArrayList<Film> films = filmDao.selectAllFilms_Star();
                req.setAttribute("phim",films);
                //comment
                ArrayList<Comment>comments=commentDao.full_allComment();
                req.setAttribute("comment",comments);
                //Reply
                ArrayList<Reply>replies =replyDao.allReply();
                req.setAttribute("reply",replies);
                //account
                String username = req.getParameter("username");
                req.setAttribute("username",username);
                //resp
                requestDispatcher = req.getRequestDispatcher("movie-info.jsp");
                requestDispatcher.forward(req,resp);
                break;
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String action = req.getParameter("action");
        RequestDispatcher requestDispatcher;
        if(action==null){
            action="";
        }
        switch (action){
            case "movie":
                //khoi tao comment
                String account = req.getParameter("nameAccount");
                String comment = req.getParameter("comment");
                String cinema = req.getParameter("cinema");
                int star = Integer.parseInt(req.getParameter("sao"));
                int idphim = Integer.parseInt(req.getParameter("idphim"));
                req.setAttribute("idphim",idphim);
                req.setAttribute("cinema",cinema);
                Comment comments = new Comment(account,comment,cinema,star,idphim);
                commentService.Create_Comnent(comments);
                break;


            case "reply":
                int idphim1 = Integer.parseInt(req.getParameter("idphim"));
                String cinema1 = req.getParameter("cinema");
                req.setAttribute("idphim",idphim1);
                req.setAttribute("cinema",cinema1);
                //khoi tao reply
                int id = Integer.parseInt(req.getParameter("id"));
                String account1 = req.getParameter("accountss");
                String comment1 = req.getParameter("commentss");
                Reply reply = new Reply(id,comment1,account1);
                replyService.Create_reply(reply);
                break;
        }
        //array  phim
        ArrayList<Film> films1 = filmDao.selectAllFilms_Star();
        req.setAttribute("phim",films1);
        //comment
        ArrayList<Comment>comments11=commentDao.full_allComment();
        req.setAttribute("comment",comments11);
        //Reply
        ArrayList<Reply>replies1 =replyDao.allReply();
        req.setAttribute("reply",replies1);
        //account
        String username1 = req.getParameter("username");
        req.setAttribute("username",username1);
        //resp
        requestDispatcher = req.getRequestDispatcher("movie-info.jsp");
        requestDispatcher.forward(req,resp);
    }
}
