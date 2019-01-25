package Movie;

import java.util.ArrayList;

public class movieTest {

	public static void main(String[] args) {
		
		ArrayList<movieObject> movieImport = textHandler.movieImporter();
		ArrayList<movieObject> movies = movieImport;
		
		for (int i = 0; i < movies.size(); ++i) {
		
			System.out.println(movies.get(i).toString());
		
		}

	}
	
}
