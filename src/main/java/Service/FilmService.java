package Service;

import Dao.FilmDao;
import Modal.Film;

import java.util.List;

public class FilmService {
    static FilmDao filmDao = new FilmDao();

    static public List<Film> films = filmDao.selectAllFilms();

    static public int vtPhim(int id){
        for(int i=0;i<films.size();i++){
            if(films.get(i).getIdPhim()==id){
                return i;
            }
        }
        return -1;
    }
    static public Film findFilm(int id){
        for(int i=0;i<films.size();i++){
            if(films.get(i).getIdPhim()==id){
                return films.get(i);
            }
        }
        return null;
    }
}
