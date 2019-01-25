import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class movieObject {
	
	private SimpleDateFormat goodDate = new SimpleDateFormat("MM/dd/yyyy");
	private Date releaseDate;
	private String name;
	private String description;
	private Date receiveDate;
	private movieStatus status;
	
	movieObject(String movieReleaseDate, String movieName, String movieDescription, String movieReceiveDate) {
		// Constructor to define everything.
		try {
			releaseDate = goodDate.parse(movieReleaseDate);
			receiveDate = goodDate.parse(movieReceiveDate);
		} catch (ParseException e) {
			
			e.printStackTrace();
			
		}
		name = movieName;
		description = movieDescription;
		
		Date currDate = new Date();
		
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
		String movie;
		movie = "Release Date: " + goodDate.format(releaseDate);
		movie += "\nName: " + name;
		movie += "\nDescription: " + description;
		movie += "\nRecieve Date: " + goodDate.format(receiveDate);
		movie += "\nStatus: " + status;
		return movie;
	}
}

