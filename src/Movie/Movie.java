package Movie;

import java.util.Date;
import java.util.GregorianCalendar;
import java.util.regex.Pattern;

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
    
    public Movie() {
        releaseDate = new GregorianCalendar().getTime();
        name = "Undefined";
        description = "Undefined";
        receiveDate = new GregorianCalendar().getTime();
        status = MovieStatus.received;
    }
    
    public Movie(Date releaseDate, String name, String description, Date receiveDate, Enum<MovieStatus> status) {
        this.releaseDate = releaseDate;
        this.name = name;
        this.description = description;
        this.receiveDate = receiveDate;
        this.status = status;
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
    @Override
    public int compareTo(Movie o) {
        return releaseDate.compareTo(o.releaseDate);
    }
}
