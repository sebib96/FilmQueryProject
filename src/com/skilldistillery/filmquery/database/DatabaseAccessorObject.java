package com.skilldistillery.filmquery.database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.skilldistillery.filmquery.entities.Actor;
import com.skilldistillery.filmquery.entities.Film;

public class DatabaseAccessorObject implements DatabaseAccessor {
	private static String url = "jdbc:mysql://localhost:3306/sdvid?useSSL=false&useLegacyDatetimeCode=false&serverTimezone=US/Mountain";
	private static final String user = "student";
	private static final String pass = "student";
	
	
//	______________________________________________________________________________________
	public DatabaseAccessorObject() {
		try {
			Class.forName("com.mysql.jdbc.Driver");
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
	}
//	______________________________________________________________________________________
	@Override
	public Film findFilmById(int filmId) {
		Film foundFilm =null;
		String query = "SELECT * FROM film WHERE id = ?";
		
		
		try {
			Connection conn = DriverManager.getConnection(url, user, pass);
			PreparedStatement stmnt = conn.prepareStatement(query);
			stmnt.setInt(1, filmId);
			ResultSet film = stmnt.executeQuery();
			
//			int id, String title, String desc, int releaseYear, int langId, int rentDur, double rentRate,
//			Integer length, double repCost, String rating, String feature
			
			if(film.next()) {
				foundFilm = new Film(film.getInt("id"), film.getString("title"), film.getString("description"),film.getInt("release_year"),
						film.getInt("language_id"), film.getInt("rental_duration"), film.getDouble("rental_rate"),film.getInt("length"),
						film.getDouble("replacement_cost"), film.getString("rating"), film.getString("special_features"));
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return foundFilm;
	}
	//IT WORKS
	
	//______________________________________________________________________________________
//	@Override
	public Actor findActorById(int actorId) throws SQLException {
		return null;}
//		Actor actor = null;
//		// ...
//		String sql = "SELECT id, first_name, last_name FROM actor WHERE id = ?";
//
//		Connection conn = DriverManager.getConnection(url, user, pass);
//		PreparedStatement stmt = conn.prepareStatement(sql);
//
//		stmt.setInt(1, actorId);
//		ResultSet actorResult = stmt.executeQuery();
//
//		if (actorResult.next()) {
//			actor = new Actor(actorResult.getInt("id"), actorResult.getString("first_name"),
//					actorResult.getString("last_name"), actorResult.getString("film list")); 
//			actor.setId(actorResult.getInt("id"));
//			actor.setFirstName(actorResult.getString("first_name"));
//			actor.setLastName(actorResult.getString("last_name"));
//		}
//		return actor;
//	}
	
	//_____________________________________________________________________________________
	List<Film> findFilmsByActorId(int actorId) {
		List<Film> films = new ArrayList<>();
		
		try {
			Connection conn = DriverManager.getConnection(url, user, pass);
			
			String sql = "SELECT id, title, description, release_year, language_id, rental_duration, ";
			sql += " rental_rate, length, replacement_cost, rating, special_features "
					+ " FROM film JOIN film_actor ON film.id = film_actor.film_id " + " WHERE actor_id = 13";
			PreparedStatement stmt = conn.prepareStatement(sql);
			
			
			stmt.setInt(1, actorId);

			
			
			ResultSet rs = stmt.executeQuery();

			while (rs.next()) {
				int filmId = rs.getInt(1);
				String title = rs.getString(2);
				String desc = rs.getString(3);
				short releaseYear = rs.getShort(4);
				int langId = rs.getInt(5);
				int rentDur = rs.getInt(6);
				double rate = rs.getDouble(7);
				int length = rs.getInt(8);
				double repCost = rs.getDouble(9);
				String rating = rs.getString(10);
				String features = rs.getString(11);
				Film film = new Film(filmId, title, desc, releaseYear, langId, rentDur, rate, length, repCost, rating,
						features);
				films.add(film);
			}
			rs.close();
			stmt.close();
			conn.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return films;
	}
//	______________________________________________________________________________________
	@Override
	public List<Film> findFilmByActorId(int actorId) {
		// TODO Auto-generated method stub
		return null;
	}
	@Override
	public List<Actor> findActorsByFilmId(int filmId) {
		// TODO Auto-generated method stub
		return null;
	}
}
