package Movie;

import java.util.ArrayList;

public class movieTest {

	public static void main(String[] args) {
		
		movieObject testObject1 = new movieObject("01/26/2019", "Test Movie 1", "Test 1", "01/20/2019");
		movieObject testObject2 = new movieObject("01/24/2019", "Test Movie 2", "Test 2", "01/20/2019");
		movieObject testObject3 = new movieObject("02/01/2019", "Test Movie 3", "Test 3", "01/27/2019");
		
		ArrayList<movieObject> movies = new ArrayList<movieObject>();
		movies.add(testObject1);
		movies.add(testObject2);
		movies.add(testObject3);
		
		for (int i = 0; i < movies.size(); ++i) {
		
			System.out.println(movies.get(i).toString());
		
			System.out.println();
		
		}

	}
	
}
