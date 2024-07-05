package database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import model.Film;

public class FilmDAO {
    
    private Connection conn = null;
    private Statement stmt = null;
    private String user = "ghaedira";
    private String password = "prIstwan3";
    private String url = "jdbc:mysql://mudfoot.doc.stu.mmu.ac.uk:6306/"+user;

    public FilmDAO() {
    	
    }

    // Open connection to the database
    private void openConnection(){
        try {
        	System.out.println("calling");
            Class.forName("com.mysql.jdbc.Driver");
            conn = DriverManager.getConnection(url, user, password);
            stmt = conn.createStatement();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    // Close connection to the database
    private void closeConnection(){
        try {
            conn.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // Get the next film from the ResultSet
    private Film getNextFilm(ResultSet rs){
        Film thisFilm = null;
        try {
            thisFilm = new Film(
                rs.getInt("id"),
                rs.getString("title"),
                rs.getInt("year"),
                rs.getString("director"),
                rs.getString("stars"),
                rs.getString("review")
            );
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return thisFilm;        
    }

    // Get all films from the database
    public ArrayList<Film> getAllFilms(){
        ArrayList<Film> allFilms = new ArrayList<Film>();
        openConnection();
        try {
            String selectSQL = "SELECT * FROM films";
            ResultSet rs = stmt.executeQuery(selectSQL);
            while(rs.next()){
                Film oneFilm = getNextFilm(rs);
                allFilms.add(oneFilm);
            }
            rs.close();
            stmt.close();
            closeConnection();
        } catch(SQLException se) {
            se.printStackTrace();
        }
        return allFilms;
    }

    // Get film by ID from the database
    public Film getFilmByID(int id) {
    	Film film = null;
        openConnection();
        
        try {
        	String query = "SELECT * FROM films WHERE id = ?";
            PreparedStatement pstmt = conn.prepareStatement(query);
            pstmt.setInt(1, id);
            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    film = getNextFilm(rs);
                }
            }
            pstmt.close();
            closeConnection();
        } catch(SQLException se) {
            se.printStackTrace();
        }
      
        return film;
    }
    
 // Get films by title from the database
    public ArrayList<Film> getFilmsByTitle(String title){
        ArrayList<Film> films = new ArrayList<>();
        openConnection();
        try {
            String query = "SELECT * FROM films WHERE title LIKE ?";
            PreparedStatement pstmt = conn.prepareStatement(query);
            pstmt.setString(1, "%" + title + "%");
            ResultSet rs = pstmt.executeQuery();
            while(rs.next()){
                Film film = getNextFilm(rs);
                films.add(film);
            }
        } catch(SQLException se) {
            se.printStackTrace();
        } finally {
            closeConnection();
        }
        return films;
    }

    // Insert a new film into the database
    public void insertFilm(Film film){
        openConnection();
        try {
        	int newId = 0;
        	String query = "SELECT ROW_NUMBER() OVER (ORDER BY some_column) AS newId, other_columns FROM films";
        	Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(query);
            while (rs.next()) {
            	newId = rs.getInt("newId");
            }
            String insertSQL = "INSERT INTO films (id, title, year, director, stars, review) VALUES (?, ?, ?, ?, ?, ?)";
            PreparedStatement pstmt = conn.prepareStatement(insertSQL);
            pstmt.setInt(1, newId);
            pstmt.setString(2, film.getTitle());
            pstmt.setInt(3, film.getYear());
            pstmt.setString(4, film.getDirector());
            pstmt.setString(5, film.getStars());
            pstmt.setString(6, film.getReview());
            pstmt.executeUpdate();
            pstmt.close();
            closeConnection();
        } catch(SQLException se) {
            se.printStackTrace();
        }
    }

    // Update an existing film in the database
    public void updateFilm(Film film){
        openConnection();
        try {
            String updateSQL = "UPDATE films SET title=?, year=?, director=?, stars=?, review=? WHERE id=?";
            PreparedStatement pstmt = conn.prepareStatement(updateSQL);
            pstmt.setString(1, film.getTitle());
            pstmt.setInt(2, film.getYear());
            pstmt.setString(3, film.getDirector());
            pstmt.setString(4, film.getStars());
            pstmt.setString(5, film.getReview());
            pstmt.setInt(6, film.getId());
            pstmt.executeUpdate();
            pstmt.close();
            closeConnection();
        } catch(SQLException se) {
            se.printStackTrace();
        }
    }

    // Delete a film from the database by ID
    public void deleteFilm(int id){
        openConnection();
        try {
            String deleteSQL = "DELETE FROM films WHERE id=?";
            PreparedStatement pstmt = conn.prepareStatement(deleteSQL);
            pstmt.setInt(1, id);
            pstmt.executeUpdate();
            pstmt.close();
            closeConnection();
        } catch(SQLException se) {
            se.printStackTrace();
        }
    }

    // Search films by title in the database
    public ArrayList<Film> searchFilm(String title){
        ArrayList<Film> searchResults = new ArrayList<Film>();
        openConnection();
        try {
            String searchSQL = "SELECT * FROM films WHERE title LIKE ?";
            PreparedStatement pstmt = conn.prepareStatement(searchSQL);
            pstmt.setString(1, "%" + title + "%");
            ResultSet rs = pstmt.executeQuery();
            while(rs.next()){
                Film oneFilm = getNextFilm(rs);
                searchResults.add(oneFilm);
            }
            rs.close();
            pstmt.close();
            closeConnection();
        } catch(SQLException se) {
            se.printStackTrace();
        }
        return searchResults;
    }



}
