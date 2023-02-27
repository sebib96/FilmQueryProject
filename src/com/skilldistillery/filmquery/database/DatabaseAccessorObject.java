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

		Film foundFilm = null;
		String query = "SELECT * FROM film WHERE id = ?";

		try {
			Connection conn = DriverManager.getConnection(url, user, pass);
			PreparedStatement stmnt = conn.prepareStatement(query);
			stmnt.setInt(1, filmId);
			ResultSet film = stmnt.executeQuery();

			if (film.next()) {
				foundFilm = new Film(film.getInt("id"), film.getString("title"), film.getString("description"),
						film.getInt("release_year"), film.getInt("language_id"), film.getInt("rental_duration"),
						film.getDouble("rental_rate"), film.getInt("length"), film.getDouble("replacement_cost"),
						film.getString("rating"), film.getString("special_features"));

				foundFilm.setLangName(findFilmLanguage(filmId));
				foundFilm.setFilmActors(findActorsByFilmId(filmId));
				foundFilm.setFilmActors(findActorsByFilmId(foundFilm.getId()));
			}
			conn.close();
			stmnt.close();
			film.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return foundFilm;
	}

	// ______________________________________________________________________________________
	@Override
	public Actor findActorById(int actorId) throws SQLException {
		Actor actor = null;
		String sql = "SELECT id, first_name, last_name FROM actor WHERE id = ?";

		try {
			Connection conn = DriverManager.getConnection(url, user, pass);
			PreparedStatement stmt = conn.prepareStatement(sql);
			stmt.setInt(1, actorId);
			ResultSet actorResult = stmt.executeQuery();

			if (actorResult.next()) {
				actor = new Actor(actorResult.getInt("id"), actorResult.getString("first_name"),
						actorResult.getString("last_name"));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return actor;
	}

	// _____________________________________________________________________________________
	@Override
	public List<Film> findFilmsByActorId(int actorId) {
		List<Film> films = new ArrayList<>();
		String sql = "SELECT id, title, description, release_year, language_id, rental_duration, rental_rate, length, replacement_cost, rating, special_features,   FROM film JOIN film_actor ON film.id = film_actor.film_id WHERE actor_id = ?";

		try {
			Connection conn = DriverManager.getConnection(url, user, pass);
			PreparedStatement stmt = conn.prepareStatement(sql);
			stmt.setInt(1, actorId);
			ResultSet film = stmt.executeQuery();

			while (film.next()) {
				Film foundFilm = new Film(film.getInt("id"), film.getString("title"), film.getString("description"),
						film.getInt("release_year"), film.getInt("language_id"), film.getInt("rental_duration"),
						film.getDouble("rental_rate"), film.getInt("length"), film.getDouble("replacement_cost"),
						film.getString("rating"), film.getString("special_features"));

				foundFilm.setLangName(findFilmLanguage(foundFilm.getId()));
				foundFilm.setFilmActors(findActorsByFilmId(foundFilm.getId()));
				films.add(foundFilm);
			}
			film.close();
			stmt.close();
			conn.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return films;
	}

//	______________________________________________________________________________________
	@Override
	public List<Actor> findActorsByFilmId(int filmId) {
		String sql = "SELECT id, first_name, last_name FROM actor JOIN film_actor ON actor.id = film_actor.actor_id WHERE film_id = ?";
		List<Actor> filmActors = new ArrayList<>();

		try {
			Connection conn = DriverManager.getConnection(url, user, pass);
			PreparedStatement stmnt = conn.prepareStatement(sql);
			stmnt.setInt(1, filmId);
			ResultSet actorResult = stmnt.executeQuery();

			while (actorResult.next()) {
				Actor actor = new Actor(actorResult.getInt("id"), actorResult.getString("first_name"),
						actorResult.getString("last_name"));
				filmActors.add(actor);
			}
			conn.close();
			stmnt.close();
			actorResult.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return filmActors;
	}

//________________________________________________________________________________________
	@Override
	public String findFilmLanguage(int filmId) {
		String langName = null;
		String sql = "SELECT language.name FROM film f JOIN language ON f.language_id=language.id WHERE f.id = ?";

		try {
			Connection conn = DriverManager.getConnection(url, user, pass);
			PreparedStatement stmnt = conn.prepareStatement(sql);
			stmnt.setInt(1, filmId);
			ResultSet lang = stmnt.executeQuery();

			if (lang.next()) {
				langName = lang.getString("language.name");
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return langName;

	}

//_________________________________________________________________________________________
	@Override
	public List<Film> findFilmByKeyWord(String keyWord) {
		List<Film> filmList = new ArrayList<>();
		Film foundFilm = null;
		String sql = "SELECT * FROM film WHERE title LIKE ? OR description LIKE ?;";
		try {
			Connection conn = DriverManager.getConnection(url, user, pass);
			PreparedStatement stmt = conn.prepareStatement(sql);
			stmt.setString(1, "%" + keyWord + "%");
			stmt.setString(2, "%" + keyWord + "%");
			ResultSet film = stmt.executeQuery();

			while (film.next()) {
				foundFilm = new Film(film.getInt("id"), film.getString("title"), film.getString("description"),
						film.getInt("release_year"), film.getInt("language_id"), film.getInt("rental_duration"),
						film.getDouble("rental_rate"), film.getInt("length"), film.getDouble("replacement_cost"),
						film.getString("rating"), film.getString("special_features"));
				foundFilm.setLangName(findFilmLanguage(foundFilm.getId()));
				foundFilm.setFilmActors(findActorsByFilmId(foundFilm.getId()));

				filmList.add(foundFilm);

			}
			conn.close();
			stmt.close();
			film.close();

		} catch (SQLException e) {
			e.printStackTrace();
		}
		return filmList;
	}

}
