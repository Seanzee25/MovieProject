package Movie;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

/*****************************************************************************************
 *****************************************************************************************
 **																						**
 ** Many thanks to the guidance of													    **
 ** https://caveofprogramming.com/java/java-file-reading-and-writing-files-in-java.html **
 **																						**
 *****************************************************************************************
 *****************************************************************************************/

public class textHandler {
	
	// Transforms characters in the text file into a complete ArrayList of movies
	public static ArrayList<movieObject> movieImporter() {
		
		// Return value
		ArrayList<movieObject> importedMovies = new ArrayList<movieObject>();
		
		// File path
		String textFile = "src/text/MovieList.txt";
		
		// Reads text files line by line
		String lineReader = "";
		
		// Movie Info
		String title = "";
		String description = "";
		String releaseDate = "";
		String receiveDate = "";
		
		// "Iterator"
		int indexTracker;
	
		try {
			
			// FileReader reads text files in the default encoding.
			FileReader readFile = new FileReader(textFile);

			// Always wrap FileReader in BufferedReader.
			BufferedReader bufferedReader = new BufferedReader(readFile);
			
			// While line is not empty
			while((lineReader = bufferedReader.readLine()) != null) {
				
				// For the length of the line of text
				for (indexTracker = 0; indexTracker < lineReader.length(); indexTracker++) {
					
					// While we have not hit the custom escape key set for each variable in text file
					while (lineReader.charAt(indexTracker) != '|') {
						
						title += lineReader.charAt(indexTracker);
						
						indexTracker++;
						
					}
					
					// Force the iterator past the escape character
					indexTracker++;
					
					// While we have not hit the custom escape key set for each variable in text file
					while (lineReader.charAt(indexTracker) != '|') {
						
						description += lineReader.charAt(indexTracker);
						
						indexTracker++;
						
					}
					
					// Force the iterator past the escape character
					indexTracker++;
					
					// While we have not hit the custom escape key set for each variable in text file
					while (lineReader.charAt(indexTracker) != '|') {
						
						releaseDate += lineReader.charAt(indexTracker);
						
						indexTracker++;
						
					}
					
					// Force the iterator past the escape character
					indexTracker++;
					
					// While we have not hit the custom escape key set for each variable in text file
					while (lineReader.charAt(indexTracker) != '|') {
						
						receiveDate += lineReader.charAt(indexTracker);
						
						indexTracker++;
						
					}
					
				}
				
				// Create new movie object
				movieObject movieToken = new movieObject(releaseDate, title, description, receiveDate);
				
				// Add movieObject to the returned ArrayList
				// Note: All array objects will have the same name in the ArrayList but can be identified by their individual characteristics
				importedMovies.add(movieToken);
				
				// Reset variables for our next movieObject
				title = "";
				description = "";
				releaseDate = "";
				receiveDate = "";
				indexTracker = 0;
				
			}

			// Always close files.
			bufferedReader.close(); 
			
			return importedMovies;
			
		} catch(FileNotFoundException e) {
			e.printStackTrace();             
		} catch(IOException ex) {
			ex.printStackTrace();
		}
		
		return importedMovies;
		
	}
	
	public static void textWriter(ArrayList<movieObject> moviesToSave) {
		
		// The name of the file to open.
        String writeFile = "src/Text/Movielist.txt";
        
        // Movie information
        String title = "";
		String description = "";
		String releaseDate = "";
		String receiveDate = "";
		
		// "Iterator"
		int indexTracker;

        try {
            // Assume default encoding.
            FileWriter fileWriter = new FileWriter(writeFile);

            // Always wrap FileWriter in BufferedWriter.
            BufferedWriter bufferedWriter = new BufferedWriter(fileWriter);

            for (indexTracker = 0; indexTracker < moviesToSave.size(); indexTracker++) {
            	
            	title = moviesToSave.get(indexTracker).getName();
            	description = moviesToSave.get(indexTracker).getDescription();
            	releaseDate = moviesToSave.get(indexTracker).getGoodReleaseDate();
            	receiveDate = moviesToSave.get(indexTracker).getGoodReceiveDate();
            	
            	bufferedWriter.write(title + "|" + description + "|" + releaseDate + "|" + receiveDate + "|");
            	bufferedWriter.newLine();
            
            }

            // Always close files.
            bufferedWriter.close();
        }
        catch(IOException ex) {
            ex.printStackTrace();
        }
		
	}

}
