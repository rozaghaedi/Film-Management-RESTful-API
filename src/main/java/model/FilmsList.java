package model;

// Generating XML in JAVA ---this is parent class 

import java.util.List;
import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlRootElement;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlRootElement(name = "FilmsList")
public class FilmsList {

    @XmlElement(name = "Film")
    private List<Film> FilmList;

    public FilmsList() {}

    public FilmsList(List<Film> FilmList) {
        this.FilmList = FilmList;
    }

    public List<Film> getFilmList() {
        return FilmList;
    }

    public void setFilmList(List<Film> FilmList) {
        this.FilmList = FilmList;
    }
}
