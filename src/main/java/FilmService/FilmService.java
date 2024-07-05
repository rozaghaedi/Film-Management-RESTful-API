package FilmService;

import java.util.ArrayList;
import database.FilmDAO;
import jakarta.xml.bind.JAXBContext;
import jakarta.xml.bind.JAXBException;
import jakarta.xml.bind.Unmarshaller;
import model.Film;
import com.google.gson.Gson;
import utils.StringFormatter;
import utils.XmlFormatter;
import java.io.StringReader;

/**
 * The FilmService class provides business logic for managing films,
 * including CRUD operations and serialization/deserialization of film data.
 */

public class FilmService {

    private final FilmDAO filmDao;
    private final Gson gson;

    public FilmService() {
        this.filmDao = new FilmDAO();
        this.gson = new Gson();
    }
    //Retrieves a film by its ID.

    public Film getFilmById(int id) {
        return filmDao.getFilmByID(id);
    }
    //Retrieves a list of films that match the specified title.
    public ArrayList<Film> getFilmsByTitle(String title) {
        return filmDao.getFilmsByTitle(title);
    }

    //Retrieves a list of all films.
    public ArrayList<Film> getAllFilms() {
        return filmDao.getAllFilms();
    }

    //Inserts a new film into the database.
    public void insertFilm(Film film) {
        filmDao.insertFilm(film);
    }
     //Updates an existing film in the database.
    public void updateFilm(Film film) {
        filmDao.updateFilm(film);
    }
    //FilmService.FilmService.deleteFilm(int)
    public void deleteFilm(int id) {
        filmDao.deleteFilm(id);
    }
     //Serializes a single Film object to the specified format.
    public String serializeFilm(Film film, String format) throws JAXBException {
        switch (format) {
            case "text/xml":
                return XmlFormatter.generateAndParseXml(film);
            case "text/string":
                return film.toString();
            default:
                return gson.toJson(film);
        }
    }
      // Serializes a list of Film objects to the specified format.
    public String serializeFilms(ArrayList<Film> films, String format) throws JAXBException {
        if ("text/xml".equals(format)) {
            return XmlFormatter.generateAndParseXml(films);
        } else if ("text/string".equals(format)) {
            return StringFormatter.formatFilmsToString(films);
        } else {
            return gson.toJson(films);
        }
    }
     //Deserializes a Film object from the specified format.
    public Film deserializeFilm(String data, String format) throws JAXBException {
        if ("text/xml".equals(format)) {
            JAXBContext jaxbContext = JAXBContext.newInstance(Film.class);
            Unmarshaller unmarshaller = jaxbContext.createUnmarshaller();
            StringReader xmlReader = new StringReader(data);
            return (Film) unmarshaller.unmarshal(xmlReader);
        } else if ("text/string".equals(format)) {
            return StringFormatter.formatStringToFilm(data);
        } else {
            return gson.fromJson(data, Film.class);
        }
    }
}
