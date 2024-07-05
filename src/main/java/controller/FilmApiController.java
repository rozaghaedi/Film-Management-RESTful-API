package controller;

import FilmService.FilmService;
import jakarta.xml.bind.JAXBException;
import model.Film;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.util.ArrayList;

@WebServlet("/api/films")
public class FilmApiController extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private final FilmService filmService;

    public FilmApiController() {
        this.filmService = new FilmService();
    }
    
    //Handles HTTP GET requests to retrieve film information.

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String idStr = request.getParameter("id");
        String title = request.getParameter("title");

        try {
            if (idStr != null && !idStr.isEmpty()) {
                int id = Integer.parseInt(idStr);//  // Retrieve film by ID
                Film film = filmService.getFilmById(id);
                if (film != null) {
                    sendResponse(film, request.getHeader("Accept"), response);
                } else {
                    sendError(response, "Film not found with ID: " + id, HttpServletResponse.SC_NOT_FOUND);
                }
            } else if (title != null && !title.isEmpty()) {
                ArrayList<Film> films = filmService.getFilmsByTitle(title);
                if (!films.isEmpty()) {
                    sendResponse(films, request.getHeader("Accept"), response);
                } else {
                    sendError(response, "No films found with title: " + title, HttpServletResponse.SC_NOT_FOUND);
                }
            } else {
                ArrayList<Film> allFilms = filmService.getAllFilms();
                sendResponse(allFilms, request.getHeader("Accept"), response);
            }
        } catch (NumberFormatException e) {
            sendError(response, "Invalid ID format", HttpServletResponse.SC_BAD_REQUEST);
        } catch (JAXBException e) {
            sendError(response, "Serialization error", HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
        }
    }

    //Sends a response containing a list of films.

    private void sendResponse(ArrayList<Film> films, String format, HttpServletResponse response) throws IOException, JAXBException {
        PrintWriter out = response.getWriter();
        String result = filmService.serializeFilms(films, format);
        response.setContentType(format);
        out.write(result);
        out.close();
    }

    //Sends a response containing a single film.
    private void sendResponse(Film film, String format, HttpServletResponse response) throws IOException, JAXBException {
        PrintWriter out = response.getWriter();
        String result = filmService.serializeFilm(film, format);
        response.setContentType(format);
        out.write(result);
        out.close();
    }
    
// Sends an error response with a specified message and status code.
    
    private void sendError(HttpServletResponse response, String message, int statusCode) throws IOException {
        response.setStatus(statusCode);
        response.getWriter().write(message);
        response.getWriter().close();
    }
//Handles HTTP POST requests to create a new film.
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            String contentType = request.getHeader("Content-Type");
            Film film = parseFilmFromRequest(request, contentType);
            if (film != null) {
                filmService.insertFilm(film);
                response.setStatus(HttpServletResponse.SC_CREATED);
                response.getWriter().write("Film inserted successfully");
            } else {
                sendError(response, "Invalid input parameters", HttpServletResponse.SC_BAD_REQUEST);
            }
        } catch (JAXBException e) {
            sendError(response, "Deserialization error", HttpServletResponse.SC_BAD_REQUEST);
        }
    }

    //Handles HTTP PUT requests to update an existing film.
    @Override
    protected void doPut(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            String contentType = request.getHeader("Content-Type");
            Film film = parseFilmFromRequest(request, contentType);
            if (film != null) {
                filmService.updateFilm(film);
                response.getWriter().write("Film updated successfully");
            } else {
                sendError(response, "Invalid input parameters", HttpServletResponse.SC_BAD_REQUEST);
            }
        } catch (JAXBException e) {
            sendError(response, "Deserialization error", HttpServletResponse.SC_BAD_REQUEST);
        }
    }
    
   // Handles HTTP DELETE requests to delete a film by its ID.
    @Override
    protected void doDelete(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String idStr = request.getParameter("id");
        try {
            int id = Integer.parseInt(idStr);
            filmService.deleteFilm(id);
            response.setStatus(HttpServletResponse.SC_NO_CONTENT);
        } catch (NumberFormatException e) {
            sendError(response, "Invalid ID format", HttpServletResponse.SC_BAD_REQUEST);
        }
    }
    
    //Parses the film object from the HTTP request body based on the content type.
    
    private Film parseFilmFromRequest(HttpServletRequest request, String contentType) throws IOException, JAXBException {
        StringBuilder jsonBuffer = new StringBuilder();
        String line;
        BufferedReader reader = new BufferedReader(new InputStreamReader(request.getInputStream()));
        while ((line = reader.readLine()) != null) {
            jsonBuffer.append(line);
        }
        return filmService.deserializeFilm(jsonBuffer.toString(), contentType);
    }
}
