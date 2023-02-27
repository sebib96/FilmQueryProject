package com.skilldistillery.filmquery.app;

import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;

import com.skilldistillery.filmquery.database.DatabaseAccessor;
import com.skilldistillery.filmquery.database.DatabaseAccessorObject;
import com.skilldistillery.filmquery.entities.Film;

public class FilmQueryApp {

	DatabaseAccessor db = new DatabaseAccessorObject();

	public static void main(String[] args) {
		FilmQueryApp app = new FilmQueryApp();
		app.launch();
	}

	private void launch() {
		Scanner input = new Scanner(System.in);

		startUserInterface(input);

		input.close();
	}

	private void startUserInterface(Scanner input) {
		boolean running = true;
		int userChoice = 0;
		String userWord = null;
		int menuChoice = 0;
		do {
			try {
				System.out.println("\nWelcome To The Film Query System (FQS)\n\n");
				System.out.println("How Can the FQS help?\n");
				System.out.println("1. Look Up a Film by it's ID");
				System.out.println("2. Look up a Film by a keyword");
				System.out.println("3. Exit Application.");
				menuChoice = input.nextInt();
			} catch (InputMismatchException e) {
				System.out.println("Enter a valid choice");
				FilmQueryApp app = new FilmQueryApp();
				app.launch();
			}

			switch (menuChoice) {
			case 1:
				System.out.println("Enter Film ID to retrieve details.");
				try {
					userChoice = input.nextInt();
				} catch (InputMismatchException e) {
					System.out.println("Enter a valid choice");
					return;
				}
				input.nextLine();
				Film film = db.findFilmById(userChoice);
				if (film == null) {
					System.out.println("Film ID does not exist in database");
				} else {
					System.out.println(film);
				}
				break;

			case 2:
				List<Film> userFilmList = new ArrayList<>();
				System.out.println("Enter a title or description keyword");
				userWord = input.next();
				userFilmList = db.findFilmByKeyWord(userWord);
				if (userFilmList.isEmpty()) {
					System.out.println("No Films match that keyword");
				} else {
					System.out.println(userFilmList);
				}
				break;
			case 3:
				System.out.println("Shutting Down FQS");
				running = false;
			}
		} while (running);
	}
}
