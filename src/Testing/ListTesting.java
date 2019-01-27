package Testing;

import java.io.IOException;
import java.util.Calendar;
import java.util.GregorianCalendar;

import Files.FileHandler;
import Movie.Movie;
import Movie.MovieStatus;
import MovieList.MovieList;

public class ListTesting {
    public static void main(String[] args) throws IOException {
        MovieList comingMovies = new MovieList();
        MovieList showingMovies = new MovieList();
        
        FileHandler fileHandler = new FileHandler();
        
        fileHandler.loadData(showingMovies, comingMovies);
        
        System.out.println(comingMovies);
        comingMovies.sort(null);
        
        Movie testMovie = new Movie(new GregorianCalendar(1700, Calendar.FEBRUARY, 13).getTime(), "Test", "Test", new GregorianCalendar(1999, Calendar.FEBRUARY, 12).getTime(), MovieStatus.received);
        comingMovies.addOrderedByReleaseDate(testMovie);
        testMovie = new Movie(new GregorianCalendar(1995, Calendar.FEBRUARY, 13).getTime(), "Test", "Test", new GregorianCalendar(1999, Calendar.FEBRUARY, 12).getTime(), MovieStatus.received);
        comingMovies.addOrderedByReleaseDate(testMovie);
        testMovie = new Movie(new GregorianCalendar(2016, Calendar.FEBRUARY, 13).getTime(), "Test", "Test", new GregorianCalendar(1999, Calendar.FEBRUARY, 12).getTime(), MovieStatus.received);
        comingMovies.addOrderedByReleaseDate(testMovie);
        testMovie = new Movie(new GregorianCalendar(2025, Calendar.FEBRUARY, 13).getTime(), "Test", "Test", new GregorianCalendar(1999, Calendar.FEBRUARY, 12).getTime(), MovieStatus.received);
        comingMovies.addOrderedByReleaseDate(testMovie);
        System.out.println(comingMovies);
        
    }
}
