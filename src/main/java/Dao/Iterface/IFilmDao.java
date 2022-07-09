package Dao.Iterface;

import Modal.Account;
import Modal.Film;

import java.sql.SQLException;
import java.util.List;

public interface IFilmDao {
    void insertFilm(Film film) throws SQLException;

    Film selectFilm (int id);

    List<Film> selectAllFilms ();

    boolean deleteFilm (int id) throws SQLException;

    boolean updateFilm(Film film) throws SQLException;
}
