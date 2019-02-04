package Application;

import java.io.IOException;

import Files.FileHandler;
import Menu.Menu;
import MovieList.MovieList;

/**
 * This is the main entry point of the program.
 * It's responsible for creating the initial coming and showing MovieLists,
 * an instance of the FileHandler, and Menu.
 * 
 * After loading initial data from an existing text file in to the lists,
 * it runs a simple loop for displaying options and getting user input in order
 * to view/update/save information in this file.
 */
public class Application {
    public static void main(String[] args) throws IOException {
        FileHandler fileHandler = new FileHandler(); // Instance of file handler for file loading/saving
        
        MovieList comingList = new MovieList(); // Stores movies with status of received
        MovieList showingList = new MovieList(); // Stores movies with status of release
        
        Menu menu = new Menu(comingList, showingList, fileHandler);
        
        int userInput =8; // Users input at the menu
        int exitVar; // Value for declaring end of program - returned by menu.executeMenuOption()
        
        // Load initial data
        fileHandler.loadData(showingList, comingList);
        // Main loop
        while(menu.isRunning()) {
            menu.displayMenu();
            userInput = menu.getUserInput();
            exitVar = menu.executeMenuOption(userInput);
            
            if(exitVar != 1) {
            	// If not exit condition
                System.out.println("Press enter to continue.");
                menu.promptNext();
            } else {
                System.out.println("Goodbye!");
            }
        }
    }
}
