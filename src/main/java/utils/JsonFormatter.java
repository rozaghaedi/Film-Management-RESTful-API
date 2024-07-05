package utils;

import java.io.IOException;
import java.io.PrintWriter; 
import java.util.ArrayList;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import database.FilmDAO;
import model.Film;

import javax.servlet.http.HttpServletResponse; // Import HttpServletResponse

public class JsonFormatter {

    public static void formatToJson(ArrayList<Film> allFilms, HttpServletResponse response) throws IOException {
        // Convert to JSON format
        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        String jsonOutput = gson.toJson(allFilms); // Rename the variable to avoid conflicts
        System.out.println("All Films (JSON):\n" + jsonOutput);
        
        // Write JSON to response
        PrintWriter out = response.getWriter(); // Assuming 'response' is available
        FilmDAO filmDao = new FilmDAO();
        ArrayList<Film> allFilm = filmDao.getAllFilms();
        String jsonFilm = gson.toJson(allFilm); // Rename the variable to avoid conflicts
        out.write(jsonFilm);
        out.close();
    }
}
