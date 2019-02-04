package Movie;

import java.util.Date;
import java.util.GregorianCalendar;
import java.util.regex.Pattern;

/** 
 * Movie object represents a movie with all of its data: Name, Description, 
 * Release and Receive Dates, as well as its status.
 * 
 * Implements comparable for easy sorting in the MovieList class.
 */

public class Movie implements Comparable<Movie>{
    private Date releaseDate;
    private String name;
    private String description;
    private Date receiveDate;
    private Enum<MovieStatus> status;
    
    public Date getReleaseDate() {
        return releaseDate;
    }
    public void setReleaseDate(Date releaseDate) {
        this.releaseDate = releaseDate;
    }
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public String getDescription() {
        return description;
    }
    public void setDescription(String description) {
        this.description = description;
    }
    public Date getReceiveDate() {
        return receiveDate;
    }
    public void setReceiveDate(Date receiveDate) {
        this.receiveDate = receiveDate;
    }
    public Enum<MovieStatus> getStatus() {
        return status;
    }
    public void setStatus(Enum<MovieStatus> status) {
        this.status = status;
    }
    
    /**
     * Default constructor
     */
    public Movie() {
        releaseDate = new GregorianCalendar().getTime();
        name = "Undefined";
        description = "Undefined";
        receiveDate = new GregorianCalendar().getTime();
        status = MovieStatus.received;
    }
    
    /**
     * Constructor for initializing all attributes
     * @param releaseDate Date
     * @param name String
     * @param description String
     * @param receiveDate Date
     * @param status Enum<MovieStatus> release, or received.
     */
    public Movie(Date releaseDate, String name, String description, Date receiveDate, Enum<MovieStatus> status) {
        this.releaseDate = releaseDate;
        this.name = name;
        this.description = description;
        this.receiveDate = receiveDate;
        this.status = status;
    }
    
    /**
     * Constructor for initializing without a status.
     * MovieStatus is set based on a current local date compared to the given release date.
     * @param release Date
     * @param title String
     * @param descrip String
     * @param receive Date
     */
    public Movie(Date release, String title, String descrip, Date receive) {
    	releaseDate = release;
    	name = title;
    	description = descrip;
    	receiveDate = receive;
    	
    	Date currDate = new Date();
    	
    	if (currDate.compareTo(release) >= 0) {
    		status = MovieStatus.release;
    	} else {
    		status = MovieStatus.received;
    	}
    	
    }
    
    @Override
    /**
     * Outputs String formatted as "name, releaseDate, description, receiveDate, status"
     */
    public String toString() {
        return String.format("%s, %tm/%td/%tY, %s, %tm/%td/%tY, %s",
                   name, releaseDate, releaseDate, releaseDate, description,
                   receiveDate, receiveDate, receiveDate, status);
    }
    
    /**
     * Sets the data of the movie as dictated by the String parameter passed to it. If status is omitted, it defaults to received.
     * @param data A String dictated by the Movie Data format "name, releaseDate, description, receiveDate, status". status is optional.
     */
    public void loadFromString(String data) {
        String fieldDelimiter = ", ";
        Pattern fieldPattern = Pattern.compile(fieldDelimiter);
        
        String[] fields;
        fields = fieldPattern.split(data); // an array of strings separated by ", "
        name = fields[0];
        releaseDate = parseDate(fields[1]);
        description = fields[2];
        receiveDate = parseDate(fields[3]);
        if(fields.length == 5) { // If movie status is given or not.
            if(fields[4].equals("received")) {
                status = MovieStatus.received;
            } else { 
                status = MovieStatus.release; 
            }
        } else { // Movie status wasn't given. Status defaults to received.
            status = MovieStatus.received;
        }
    }
    
    /**
     * Parses the String date format and returns a Date object.
     * @param date String date format: mm/dd/yyyy
     * @return Date object as dictated by the String date parameter.
     */
    public static Date parseDate(String date) {
        String dateDelimiter = "/";
        Pattern datePattern = Pattern.compile(dateDelimiter);
        
        String[] tokens;
        int year;
        int month;
        int day;
        
        // Split the string in to tokens separated by "/"
        tokens = datePattern.split(date);
        // Convert String tokens to int.
        month = Integer.parseInt(tokens[0]) - 1; // Months start at 0
        day = Integer.parseInt(tokens[1]);
        year = Integer.parseInt(tokens[2]);
        
        // Date methods are declared as deprecated. GregorianCalendars getTime function returns
        // a Date. 
        return new GregorianCalendar(year, month, day).getTime();
    }
    
    /**
     * Makes Movie objects comparable by releaseDate
     */
    @Override
    public int compareTo(Movie o) {
        return releaseDate.compareTo(o.releaseDate);
    }
    
}
