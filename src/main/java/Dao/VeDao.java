package Dao;

import Connect.ConnectionMySQL;
import Dao.Iterface.IVeDao;
import Modal.Account;
import Modal.Film;
import Modal.Ghe;
import Modal.Ve;
import Service.AccountService;
import Service.FilmService;
import Service.GheService;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class VeDao implements IVeDao {
    ConnectionMySQL connectionMySQL = new ConnectionMySQL();
    private  static final  String
            INSERT_VE_SQL = "INSERT INTO ve (idPhim,idGhe,idAccount) VALUES (?, ?, ?);";
    private static final String SELECT_ALL_VE ="SELECT * FROM ve";

    private static final String SELECT_QUANTITY_VE_BY_ID_FILM = "select count(idVe) as quantity from ve where idPhim = ?";
    @Override
    public void insertVe(Ve ve) throws SQLException {
        try(Connection connection = connectionMySQL.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(INSERT_VE_SQL)){
            preparedStatement.setInt(1,ve.getFilm().getIdPhim());
            preparedStatement.setInt(2,ve.getGhe().getIdGhe());
            preparedStatement.setInt(3,ve.getAccount().getId());
            preparedStatement.executeUpdate();
        }
        catch (SQLException e){
            printSQLException(e);
        }
    }
    public int quantityTicket(int idPhim){
        int  quantity=0;
        try(Connection connection = connectionMySQL.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(SELECT_QUANTITY_VE_BY_ID_FILM)){
            preparedStatement.setInt(1,idPhim);
            ResultSet resultSet =preparedStatement.executeQuery();
            resultSet.next();
           quantity= resultSet.getInt(1);
        } catch (SQLException e){
        }
        return quantity;
    }
    @Override
    public Ve selectVe(int id) {
        return null;
    }

    @Override
    public List<Ve> selectAllVe() {
        ArrayList<Ve> ves = new ArrayList<>();
        try(Connection connection = connectionMySQL.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(SELECT_ALL_VE)) {
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()){
                int idVe = resultSet.getInt("idVe");
                Film film = FilmService.findFilm(resultSet.getInt("idPhim"));
                Ghe ghe = GheService.findGhe(resultSet.getInt("idGhe"));
                Account account = AccountService.findAccount(resultSet.getInt("idAccount"));
                ves.add(new Ve(idVe, film, ghe, account));
            }

        } catch (SQLException e) {
            printSQLException(e);
        }
        return ves;
    }

    @Override
    public boolean deleteVe(int id) throws SQLException {
        return false;
    }

    @Override
    public boolean updateVe(Ve ve) throws SQLException {
        return false;
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
