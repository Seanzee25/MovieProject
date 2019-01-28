
public class InputErrors {

    // Time to handle those errors.
    private Date dateHadling(Scanner userInput) {
    	String date;
        Date newDate;
        
        try {
        
        	date = userInput.nextLine();
        	newDate = Movie.parseDate(date);
    	
        	return newDate;
        	
        } catch (DateTimeParseException e) {
        	
        	
        	
        }
    
    }
    
}
