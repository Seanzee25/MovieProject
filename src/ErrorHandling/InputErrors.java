package ErrorHandling;

import java.util.Date;
import java.util.Scanner;

import Movie.Movie;

public class InputErrors {

    // Time to handle those errors.
	
	// Make sure the user's date can be parsed *RECURSIVE*
    public static Date dateHandling(Scanner userInput) {
    	String date;
        Date newDate;
        
        try {
        
        	date = userInput.nextLine();
        	newDate = Movie.parseDate(date);
    	
        	return newDate;
        	
        } catch (Exception e) {
        	
        	System.out.print("Your input does not follow mm/dd/yyyy \nFor example 12/13/2018 \nPlease try again...\n\n"
        			+ "Please enter the date in mm/dd/yyyy format (ex: 12/13/1993)\n");
        	
        	return dateHandling(userInput);
        	
        }

    }
    
}