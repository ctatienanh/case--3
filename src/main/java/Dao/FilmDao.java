package Dao;

import Connect.ConnectionMySQL;
import Dao.Iterface.IFilmDao;
import Modal.Film;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class FilmDao implements IFilmDao {
    ConnectionMySQL connectionMySQL = new ConnectionMySQL();
    private  static final  String
            INSERT_FILM_SQL =
            "INSERT INTO Phim (idPhim ,tenPhim,thoiGian,loaiPhim,daoDien,quocGia,dienVien,nhaSX,ngayKhoiChieu,moTa,trailer) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?);";
    private static final String SELECT_ALL_PHIM ="SELECT * FROM Phim";

    @Override
    public void insertFilm(Film film) throws SQLException {

    }

    @Override
    public Film selectFilm(int id) {
        return null;
    }

    @Override
    public ArrayList<Film> selectAllFilms() {
        ArrayList<Film> films = new ArrayList<>();
        try(Connection connection = connectionMySQL.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(SELECT_ALL_PHIM)) {
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()){
                int idPhim = resultSet.getInt("idPhim");
                String tenPhim = resultSet.getString("tenPhim");
                String thoiGian = resultSet.getString("thoiGian");
                String loaiPhim = resultSet.getString("loaiPhim");
                String daoDien = resultSet.getString("daoDien");
                String quocGia = resultSet.getString("quocGia");
                String dienVien = resultSet.getString("dienVien");
                String nhaSX = resultSet.getString("nhaSX");
                Date ngayKhoiChieu = resultSet.getDate("ngayKhoiChieu");
                String moTa = resultSet.getString("moTa");
                String trailer = resultSet.getString("trailer");
                String img = resultSet.getString("img");
                double star = resultSet.getDouble("star");
                films.add(new Film(idPhim,tenPhim,thoiGian,loaiPhim,daoDien,quocGia,dienVien,nhaSX,ngayKhoiChieu,moTa,trailer,img,star));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return films;
    }

    public ArrayList<Film> selectAllFilms_Star() {
        ArrayList<Film> films = new ArrayList<>();
        try(Connection connection = connectionMySQL.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(SELECT_ALL_PHIM)) {
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()){
                int idPhim = resultSet.getInt("idPhim");
                String tenPhim = resultSet.getString("tenPhim");
                String thoiGian = resultSet.getString("thoiGian");
                String loaiPhim = resultSet.getString("loaiPhim");
                String daoDien = resultSet.getString("daoDien");
                String quocGia = resultSet.getString("quocGia");
                String dienVien = resultSet.getString("dienVien");
                String nhaSX = resultSet.getString("nhaSX");
                Date ngayKhoiChieu = resultSet.getDate("ngayKhoiChieu");
                String moTa = resultSet.getString("moTa");
                String trailer = resultSet.getString("trailer");
                String img = resultSet.getString("img");
                double star = resultSet.getDouble("star");
                films.add(new Film(idPhim,tenPhim,thoiGian,loaiPhim,daoDien,quocGia,dienVien,nhaSX,ngayKhoiChieu,moTa,trailer,img,star));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return films;
    }





    public int finbyid(String name) {
        String checkid = "SELECT idPhim from phim where tenPhim = ?";
        int idphim = -1;
        try (Connection connection = connectionMySQL.getConnection()) {
            PreparedStatement preparedStatement = connection.prepareStatement(checkid);
            preparedStatement.setString(1, name);
            ResultSet resultSet = preparedStatement.executeQuery();
            resultSet.next();
            idphim = resultSet.getInt("idPhim");
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return idphim;
    }




    @Override
    public boolean deleteFilm(int id) throws SQLException {
        return false;
    }

    @Override
    public boolean updateFilm(Film film) throws SQLException {
        return false;
    }
}
