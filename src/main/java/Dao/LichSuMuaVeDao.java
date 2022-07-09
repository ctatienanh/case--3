package Dao;

import Connect.ConnectionMySQL;
import Dao.Iterface.ILichSuMuaVeDao;
import Modal.*;
import Service.AccountService;
import Service.FilmService;
import Service.GheService;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class LichSuMuaVeDao implements ILichSuMuaVeDao {
    ConnectionMySQL connectionMySQL = new ConnectionMySQL();
    private  static final  String
            INSERT_VE_ENTER_LSMV_SQL = "INSERT INTO velichsumua(idVe,idPhim,idGhe,idAccount,ngayMua) VALUES (?,?, ?, ?,?);";
    private static final String SELECT_ALL_VE_LSMV ="SELECT * FROM velichsumua";

    private static final String SELECT_QUANTITY_VE_BY_ID_FILM = "select count(idVe) as quantity from velichsumua where idPhim=?";
    @Override
    public void insertLichSuMuaVe(LichSuMuaVe ve) throws SQLException {
        try( Connection connection =connectionMySQL.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(INSERT_VE_ENTER_LSMV_SQL)){
            preparedStatement.setInt(1,ve.getIdVe());
            preparedStatement.setInt(2,ve.getFilm().getIdPhim());
            preparedStatement.setInt(3,ve.getGhe().getIdGhe());
            preparedStatement.setInt(4,ve.getAccount().getId());
            preparedStatement.setDate(5,ve.getNgayMua());
            preparedStatement.executeUpdate();
        }
        catch (SQLException e){
            printSQLException(e);
        }
    }
    public int quantityTicket(int idPhim){
        int quantities =0;
        try(Connection connection = connectionMySQL.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(SELECT_QUANTITY_VE_BY_ID_FILM)){
            preparedStatement.setInt(1,idPhim);
            ResultSet resultSet =preparedStatement.executeQuery();
            resultSet.next();
            quantities =resultSet.getInt("quantity");

        } catch (SQLException e){
        }
        return quantities;
    }
    @Override
    public Ve selectLichSuMuaVe(int id) {
        return null;
    }

    @Override
    public List<LichSuMuaVe> selectAllVe() {
        ArrayList<LichSuMuaVe> ves = new ArrayList<>();
        try(Connection connection = connectionMySQL.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(SELECT_ALL_VE_LSMV)){
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()){
                int idVe = resultSet.getInt("idVe");
                Film film = FilmService.findFilm(resultSet.getInt("idPhim"));
                Ghe ghe = GheService.findGhe(resultSet.getInt("idGhe"));
                Account account = AccountService.findAccount(resultSet.getInt("idAccount"));
                Date date = resultSet.getDate("ngayMua");
                ves.add(new LichSuMuaVe(idVe,film,ghe,account,date));
            }

        } catch (SQLException e) {
            printSQLException(e);
        }
        return ves;
    }
    private void printSQLException(SQLException ex) {
        for (Throwable e : ex) {
            if (e instanceof SQLException) {
                e.printStackTrace(System.err);
                System.err.println("SQLState: " + ((SQLException) e).getSQLState());
                System.err.println("Error Code: " + ((SQLException) e).getErrorCode());
                System.err.println("Message: " + e.getMessage());
                Throwable t = ex.getCause();
                while (t != null) {
                    System.out.println("Cause: " + t);
                    t = t.getCause();
                }
            }
        }
    }

}
