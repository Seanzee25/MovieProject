package MovieList;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.ListIterator;

import Movie.Movie;

/**
 * An ArrayList of movieObjects with specified methods of addOrderedByReleaseDate
 * and specified toString method to reflect our need for data output.
 * Also, as movieObjects implement comparable by release date, the entire list can be
 * sorted by calling .sort(null) on the MovieList.
 * @author Seanzee
 *
 */
@SuppressWarnings("serial")
public class MovieList extends ArrayList<Movie> {
    
    /**
     * Adds a movie to the list in an ordered(non-decreasing) position based on release date.
     * @param movie Movie to add
     */
    public void addOrderedByReleaseDate(Movie movie) {
        ListIterator<Movie> it = listIterator();
        Movie curMovie;
        
        while(it.hasNext()) {
            curMovie = it.next();
            if(movie.compareTo(curMovie) <= 0) {
                it.previous();
                it.add(movie);
                return;
            }
        }
        add(movie); // Method only reaches here if item was greater than all items in list.
    }
    
    /**
     * Adds a movie to the list in an ordered(non-decreasing) position based on release date.
     * @param movie Movie to add
     */
    @Override
    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        Iterator<Movie> it = iterator();
        while(it.hasNext()) {
            stringBuilder.append(it.next() + "\n");
        }
        return stringBuilder.toString();
    }
    
    public void toPrettyString() {
        Iterator<Movie> it = iterator();
        Movie currMovie;
        while(it.hasNext()) {
            currMovie = it.next();
            
            System.out.printf("\nTitle: %s%nDescription: %s%nReceive Date: %s%nRelease Date: %s\n",
            		currMovie.getName(), 
            		currMovie.getDescription(), 
            		toPrettyDateString(currMovie.getReceiveDate()), 
            		toPrettyDateString(currMovie.getReleaseDate()));
            
            
        }
    }
    
    public String toPrettyDateString(Date date) {
    	SimpleDateFormat prettyDateFormat = new SimpleDateFormat("MM/dd/yyyy");
    	String prettyDateString = prettyDateFormat.format(date);
    	
    	return prettyDateString;
    }
}
