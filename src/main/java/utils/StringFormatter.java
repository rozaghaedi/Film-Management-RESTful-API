package utils;

import java.util.List;

import model.Film;

/**
 * Utility class providing static methods to convert lists of Film objects into a single string,
 * and to parse a string back into a Film object. This is useful for serialization and deserialization
 * of film data for storage or transmission in a non-JSON format.
 */

public class StringFormatter {
	
    /**
     * Converts a list of Film objects into a single string representation.
     * Each film's string representation is appended with a delimiter "|".
     * 
     * @param allFilms The list of Film objects to be converted.
     * @return A string representation of the list of films, with each film separated by a "|" delimiter.
     */

    public static String formatFilmsToString(List<Film> allFilms) {
        StringBuilder filmsString = new StringBuilder();
        for (Film film : allFilms) {
            filmsString.append(film.toString()).append("|");
        }
        return filmsString.toString();
    }
    
    /**
     * Parses a string representation of a film into a Film object.
     * The string should contain key-value pairs separated by ":", and each pair separated by "#".
     * For example, a valid film string might look like "id:1#title:Inception#year:2010#director:Nolan#stars:DiCaprio#review:Great".
     * 
     * @param filmString The string containing the serialized film data.
     * @return A Film object parsed from the string. Returns a Film object without an ID if the ID is not specified in the string.
     */
    
    public static Film formatStringToFilm(String filmString) {
    	String[] parts = filmString.split("#");
        int id = 0;
        String title = "";
        int year = 0;
        String director = "";
        String stars = "";
        String review = "";

        for (String part : parts) {
            String[] keyValue = part.split(":");
            if (keyValue.length == 2) {
                String key = keyValue[0];
                String value = keyValue[1];
                if (key.equals("id")) {
                    id = Integer.parseInt(value);
                } else if (key.equals("title")) {
                    title = value;
                } else if (key.equals("year")) {
                    year = Integer.parseInt(value);
                } else if (key.equals("director")) {
                    director = value;
                } else if (key.equals("stars")) {
                    stars = value;
                } else if (key.equals("review")) {
                    review = value;
                }
            }
        }

        if (id == 0) {
            return new Film(id,title, year, director, stars, review);
        } else {
            return new Film(id, title, year, director, stars, review);
        }
    }
}
