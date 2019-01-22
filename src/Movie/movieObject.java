package Movie;

import java.util.Date;

public class movieObject {

	private Date releaseDate;
	private String name;
	private String description;
	private Date receiveDate;
	
	movieObject(Date movieReleaseDate, String movieName, String movieDescription, Date movieReceiveDate) {
		releaseDate = movieReleaseDate;
		name = movieName;
		description = movieDescription;
		receiveDate = movieReceiveDate;
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

}
