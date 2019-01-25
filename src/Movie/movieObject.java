package Movie;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/*******************************************
** Based on code from Thomas Hopkins fork **
*******************************************/

public class movieObject {
	
	/*************************************************************************************************************
	** Thanks to https://examples.javacodegeeks.com/core-java/util/date/java-util-date-example-how-to-use-date/ **
	** for the Date() examples!										    **
	*************************************************************************************************************/
	
	// Setup new Date() format (SimpleDateFormat) for String calls.
	private SimpleDateFormat goodDate = new SimpleDateFormat("MM/dd/yyyy");
	
	private Date releaseDate;
	private String name;
	private String description;
	private Date receiveDate;
	private movieStatus status;
	
	// Made the constructor text file ready by requiring a String rather than a Date() which also expediting formatting.
	movieObject(String movieReleaseDate, String movieName, String movieDescription, String movieReceiveDate) {
		// Constructor to define everything.
		
		// Attempts to convert date() into MM/dd/yyyy format. Input format is important.
		try {
			releaseDate = goodDate.parse(movieReleaseDate);
			receiveDate = goodDate.parse(movieReceiveDate);
		} catch (ParseException e) {
			
			e.printStackTrace();
			
		}
		
		name = movieName;
		description = movieDescription;
		
		// Create current date to compare
		Date currDate = new Date();
		
		// Date() comparisons allowing the movies status to be automatically set.
		if (currDate.compareTo(receiveDate) < 0) {
			
			status = movieStatus.UNRECEIVED;
			
		}
		
		if (currDate.compareTo(receiveDate) >= 0 && currDate.compareTo(releaseDate) < 0) {
			
			status = movieStatus.RECEIVED;
			
		}
		
		if (currDate.compareTo(releaseDate) >= 0) {
			
			status = movieStatus.RELEASED;
			
		}
		
	}
	
	movieObject() {
		// Constructor with no args.
		releaseDate = new Date();
		name = "Name";
		description = "Description";
		receiveDate = new Date();
		this.status = movieStatus.UNRECEIVED;
	}
	
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
	
	public movieStatus getStatus() {
		return status;
	}
	
	public String toString() {
		// Printing of the object in clean format.
		String movie = "";
		movie += "Name: " + name;
		movie += "\nDescription: " + description;
		// After being parsed during our objects instantiation our date is ready to be presented thanks SimpleDateFormat.
		movie += "\nRecieve Date: " + goodDate.format(receiveDate);
		movie += "Release Date: " + goodDate.format(releaseDate);
		movie += "\nStatus: " + status;
		movie += "\n";
		return movie;
	}
}
