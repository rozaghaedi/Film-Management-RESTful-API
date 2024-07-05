package utils;

import java.io.StringReader;
import java.io.StringWriter;
import java.util.ArrayList;

import jakarta.xml.bind.JAXBContext;
import jakarta.xml.bind.JAXBException;
import jakarta.xml.bind.Marshaller;
import jakarta.xml.bind.Unmarshaller;
import model.Film;
import model.FilmsList;

public class XmlFormatter {

    // Method to generate XML from a list of films and parse XML back to a list of films

    /**
     * Generates XML representation of a list of films and parses XML back to a list of films.
     */
    public static String generateAndParseXml(ArrayList<Film> data) {
        try {
            FilmsList filmList = new FilmsList();
            filmList.setFilmList(data);
            StringWriter sw = new StringWriter();
            JAXBContext context = JAXBContext.newInstance(FilmsList.class);
            Marshaller m = context.createMarshaller();
            m.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE);
            m.marshal(filmList, sw);
            // Converting StringWriter to String and returning the XML
            return sw.toString();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
    
    public static String generateAndParseXml(Film film) {
        try {
          
//            FilmsList filmList = new FilmsList(film);
            StringWriter sw = new StringWriter();
            JAXBContext context = JAXBContext.newInstance(Film.class);
            Marshaller m = context.createMarshaller();
            m.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE);
            m.marshal(film, sw);
            // Converting StringWriter to String and returning the XML
            return sw.toString();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }


    // Method to parse XML string into a Film object

   
    public static Film parseFilmFromXML(String xmlString) {
        try {
            // Creating a JAXBContext for the Film class
            JAXBContext jaxbContext = JAXBContext.newInstance(Film.class);
            Unmarshaller unmarshaller = jaxbContext.createUnmarshaller();
            return (Film) unmarshaller.unmarshal(new StringReader(xmlString));
        } catch (JAXBException e) {
            e.printStackTrace();
            return null;
        }
    }
}


