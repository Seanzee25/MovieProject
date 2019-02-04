package Files;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Date;
import java.util.Scanner;

import Movie.Movie;
import Movie.MovieStatus;
import MovieList.MovieList;

/**
 * Handles loading and saving to files.
 */
public class FileHandler {
    
    public FileHandler() {

    }
    
    /**
     * Loads data from input file, creates Movie objects based on input, and adds them to the correct list based on movies status.
     * @param showingList
     * @param comingList
     * @throws IOException 
     */
    public void loadData(MovieList showingList, MovieList comingList) throws IOException {
        FileInputStream fileInput = new FileInputStream("saveFile.txt");
        Scanner input = new Scanner(fileInput);
        
        // Break down the file by line, then load the data for the Movie object through the loadFromString method.
        String line;
        Movie movie;
        Date currDate = new Date();

        while(input.hasNext()) {
            line = input.nextLine(); // gets next line of file.
            movie = new Movie();
            movie.loadFromString(line);
             
            if(movie.getReleaseDate().compareTo(currDate) < 0 && movie.getStatus() == MovieStatus.received) {
            	movie.setStatus(MovieStatus.release);
        	}
            
            if(movie.getReceiveDate().compareTo(currDate) > 0 && movie.getReleaseDate().compareTo(currDate) > 0 && movie.getStatus() == MovieStatus.release) {
            	movie.setStatus(MovieStatus.received);
        	}
        	
            // Add to correct list
            if(movie.getStatus() == MovieStatus.release) {
                showingList.add(movie);
            } else { 
                comingList.add(movie); 
            }
        }
        
        comingList.sort(null); // Sort coming list.
        
        fileInput.close();
        input.close();
    }

    /**
     * Writes the movies contained in both lists to an output file.
     * @param showingList
     * @param comingList
     * @throws IOException
     */
    public void saveData(MovieList showingList, MovieList comingList) throws IOException {
        FileOutputStream fileOutput = new FileOutputStream("saveFile.txt");
        PrintWriter printWriter = new PrintWriter(fileOutput);
        
        printWriter.print(showingList);
        printWriter.print(comingList);
        printWriter.flush();
        
        fileOutput.close();
        printWriter.close();
    }
}
