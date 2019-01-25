package Movie;

import java.util.ArrayList;

public class movieTest {

	public static void main(String[] args) {
		
		ArrayList<movieObject> movieImport = textHandler.movieImporter();
		ArrayList<movieObject> movies = movieImport;
		
		// Add your custom movie here and watch it output to the text file.
		// movieObject writeTest = new movieObject(ReleaseDate, Title, Description, ReceiveDate);
		// movies.add(writeTest);
		
		for (int i = 0; i < movies.size(); ++i) {
		
			System.out.println(movies.get(i).toString());
		
		}
		
		textHandler.textWriter(movies);

	}
	
}
