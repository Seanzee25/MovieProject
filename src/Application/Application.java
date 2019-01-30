package Application;

import java.io.IOException;

import Files.FileHandler;
import Menu.Menu;
import MovieList.MovieList;

public class Application {
    public static void main(String[] args) throws IOException {
        FileHandler fileHandler = new FileHandler();
        
        MovieList comingList = new MovieList();
        MovieList showingList = new MovieList();
        
        Menu menu = new Menu(comingList, showingList, fileHandler);
        
        int userInput =8; // Users input at the menu
        int exitVar; // Value for declaring end of program - returned by menu.executeMenuOption()
        
        // Load initial data
        fileHandler.loadData(showingList, comingList);
        
        // Main loop
        while(menu.isRunning()) {
            menu.displayMenu();
            while (userInput > 7 && userInput < 1) {
            	try {
            		userInput = menu.getUserInput();
            	} catch (Exception e){
            		
            	}
            }
            exitVar = menu.executeMenuOption(userInput);
            
            if(exitVar != 1) {
                System.out.println("Press enter to continue.");
                menu.promptNext();
            } else {
                System.out.println("Goodbye!");
            }
        }
    }
}
