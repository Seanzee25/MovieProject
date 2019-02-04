package Menu;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Iterator;
import java.util.ListIterator;
import java.util.Scanner;

import Files.FileHandler;
import Movie.Movie;
import Movie.MovieStatus;
import MovieList.MovieList;

/**
 * Menu interface to allow user to interact with
 * the program and view/update/save data.
 */
public class Menu {
    private boolean running;
    private SimpleDateFormat prettyDate = new SimpleDateFormat("MM/dd/yyyy");
    private MovieList comingList;
    private MovieList showingList;
    private FileHandler fileHandler;
    private Scanner input;
    
    public boolean isRunning() { return running; }
    
    /**
     * Default constructor
     */
    public Menu() {
        running = true;
        comingList = new MovieList();
        showingList = new MovieList();
        fileHandler = new FileHandler();
        input = new Scanner(System.in);
    }
    
    /**
     * Constructor
     * @param _comingList MovieList reference to the list to store coming movies.
     * @param _showingList MovieList reference to the list to store released movies
     * @param _fileHandler FileHandler reference to the fileHandler created in Application for saving/loading data.
     */
    public Menu(MovieList _comingList, MovieList _showingList, FileHandler _fileHandler) {
        running = true;
        comingList = _comingList;
        showingList = _showingList;
        fileHandler = _fileHandler;
        input = new Scanner(System.in);
    }
    
    /**
     * Text representation of the menu options for display to user.
     */
    public void displayMenu() {
        System.out.println("Choose an option: \n"
                + "1) Display all movies. \n"
                + "2) Add a new movie. \n"
                + "3) Start showing with given release date. \n"
                + "4) Edit a coming movie. \n"
                + "5) Number of movies released before given date. \n"
                + "6) Save changes. \n"
                + "7) Exit.");
    }
    
    /**
     * Gets user input for execution of menu options
     * @return Returns -1 if no int exists in user input
     */
    public int getUserInput() {
        if(input.hasNextInt()) {
            int userInput = input.nextInt();
            input.nextLine();
            return userInput;
        } else {
            return -1;
        }
        
    }
    
    /**
     * Simply gets the next line from user, in order to stall
     * the application until the user hits enter.
     */
    public void promptNext() {
        input.nextLine();
    }
    
    /***************************************************************
     *                                                             *
     * 	exectuteMenuOption                                         *
     *                                                             *  
     *  Tells the application which script to run based on the     *
     *  users numeric input.                                       *                                                                        
     *                                                             *
     *  @param int option which is the users desired menu choice.  *
     *  @return int returns -1 to represent application end        *
     *                                                             *
     *                                                             *
     ***************************************************************/
    
    public int executeMenuOption(int option) throws IOException {
        switch(option) {
        case 1:
            displayMovies();
            break;
        case 2:
            addMovieToComingList();
            break;
        case 3:
            startShowingByReleaseDate();
            break;
        case 4:
            editMovie();
            break;
        case 5:
            displayNumberOfMoviesBeforeDate();
            break;
        case 6:
            saveChanges(fileHandler);
            break;
        case 7:
            exit();
            return 1; // return value of 1 tells the application not to prompt to continue
        default:
            System.out.println("Invalid option.");
        }
        return 0;
    }
    
    /**
     * Displays the coming and showing lists of movies to the user.
     */
    private void displayMovies() {
        System.out.println("Showing Movies:");
        showingList.toPrettyString();
        System.out.println();
        System.out.println("Coming Movies:");
        comingList.toPrettyString();
        System.out.println();
    }
    
    /**
     * Prompts user for appropriate data for adding a new movie to the comingList
     */
    private void addMovieToComingList() {
    	Iterator<Movie> it = comingList.iterator();
    	Movie curMovie;
        Movie movie;
        String title;
        String descrip = "";
        Date release = new Date();
        Date receive = new Date();
        boolean nameMatch = false;
        boolean dateComparison = false;
        
        // Get Name of movie from user
        System.out.println("Enter the title of the movie: ");
        title = input.nextLine();
        
        // Looking for if movie name already exists.
        while( it.hasNext()) {
        	curMovie = it.next();
            if(curMovie.getName().equalsIgnoreCase(title)) {
                nameMatch = true;
                break;
            }
        }
        
        // If movie name exists return 
        if(nameMatch == true) {
        			
        	System.out.println("The movie already exists in the coming list.");
        	return;
        
        // Movie name is unique 
        } else {
        	
        	// Enter a description
        	System.out.printf("Enter the description for %s: %n", title);
            descrip = input.nextLine();
        	
            // Enter a loop for entering receive/release dates
        	while(dateComparison == false) {
                    
                System.out.printf("Enter the recieve date for %s: %n", title);
                receive = dateHandling(input);
            
                System.out.printf("Enter the release date for %s: %n", title);
            	release = dateHandling(input);
        		
            	// Make sure release is greater than receive 
        		if (release.compareTo(receive) <= 0) {
        		
        			System.out.println("Release date must be greater than the receive date. Try again.");
        			
        		// All good; condition for exiting loop
        		} else {
        			dateComparison = true;
        		}
        	}	
        }
        
        // Create the movie
        System.out.println("Creating Movie...");
        movie = new Movie(release, title, descrip, receive);
        System.out.println("Adding to list...");
        comingList.addOrderedByReleaseDate(movie);
        System.out.println("Movie added");
    }
    
    /**
     * Prompts user for a date, and sets all movies with a releaseDate matching that date to a status of release.
     */
    private void startShowingByReleaseDate() {
    	// Gets all movies that are releasing on a date
        ListIterator<Movie> it = comingList.listIterator();
        Date currDate = new Date();
        Date date;
        Movie curMovie;
        boolean foundMatch = false;
        
        // Get date from user
        System.out.println("Enter date in this format: mm/dd/yyyy");
        date = dateHandling(input);
        
        // Iterate through list, comparing releaseDates to user entered date.
        System.out.println("Checking coming movies...");
        while(it.hasNext()) {
            curMovie = it.next();
            if(curMovie.getReleaseDate().equals(date)) {
                // Changes status and move to showingList.
                System.out.println(String.format("%s set to release and moved from coming list.", curMovie.getName()));
                currDate = prettyCurrentDate(currDate);
                curMovie.setReleaseDate(currDate);
                curMovie.setStatus(MovieStatus.release);
                showingList.add(curMovie);
                it.remove();
                foundMatch = true;
            }
        }
        
        if(foundMatch == false ) {
        	System.out.println("No match found or the film is already showing.");
        }
    }
    
    /**
     * Prompts user for a name of an existing movie in the comingList,
     * and allows user to change name, release date, or description
     */
    private void editMovie() {
        Iterator<Movie> it = comingList.iterator();
        String name;
        Movie curMovie = null;
        boolean found = false;
        int userInput;
        
        // Get name from user
        System.out.println("Please enter the name of the movie to edit.");
        name = input.nextLine();
        
        // Search for movie with matching name
        System.out.println("Searching for movie with name " + name);
        while(it.hasNext()) {
            curMovie = it.next();
            if(curMovie.getName().equalsIgnoreCase(name)) {
                found = true;
                break;
            }
        }
        
        // If found, prompt for what data to update
        if(found && curMovie != null) {
            System.out.println("What would you like to edit? \n"
                    + "1) Name\n"
                    + "2) Release Date\n"
                    + "3) Description\n");
            userInput = getUserInput();
            
            switch(userInput) {
                case 1: {
                    editName(curMovie);
                    break;
                }
                case 2: {
                    editReleaseDate(curMovie);
                    break;
                }
                case 3: {
                    editDescription(curMovie);
                    break;
                }
                default: {
                    System.out.println("Not a valid option.");
                }
            }
        }
        
        // If not found, tell user.
        if(!found) {
            System.out.println("Movie with name " + name + " not found.");
        }
    }
    
    /**
     * Prompts user for a new name, and updates the movie
     * @param movie The Movie to edit.
     */
    private void editName(Movie movie) {
        String name;
        
        System.out.println("Enter new name for the movie " + movie.getName());
        name = input.nextLine();
        movie.setName(name);
        System.out.println("Name changed to " + name);
        
    }
    
    /**
     * Prompts user for a new release date and updates the movie
     * @param movie The Movie to edit.
     */
    private void editReleaseDate(Movie movie) {
        String prettyDateString;
        Date newDate = null;
        boolean dateComparison = false;
        Date receiveDate = movie.getReceiveDate();
        
        // Prompts user for a date until the entered date is correctly formatted
        while(dateComparison == false) {
            System.out.println("Enter a new release date for the movie " + movie.getName() + "\n"
                    + "Format: mm/dd/yyyy");
            newDate = dateHandling(input);
            
            if (newDate.compareTo(receiveDate) <= 0) {
                
                System.out.println("Release date must be greater than the receive date. Try again.");
                
            } else {
                dateComparison = true;
            }
        }
        
        // Update the movies data
        prettyDateString = prettyDate.format(newDate);
        movie.setReleaseDate(newDate);
        System.out.println("Release date changed to " + prettyDateString);
        System.out.println("Re-sorting list...");
        comingList.sort(null);
    }

    /**
     * Prompts user for new description and updates the movie
     * @param movie The Movie to edit
     */
    private void editDescription(Movie movie) {
    	// Edits description of movie
        String description;
        
        System.out.println("Enter a new description for the movie " + movie.getName());
        description = input.nextLine();
        movie.setDescription(description);
        System.out.println("Description changed to " + description);
    }
    
    /**
     * Display the number of movies with a release date before a user entered date.
     */
    private void displayNumberOfMoviesBeforeDate() {
        int count = 0;
        Iterator<Movie> it = comingList.iterator();
        Movie curMovie;
        Date parsedDate;
        String prettyDateString;
        
        // Get date from user.
        System.out.println("Enter release date formatted as mm/dd/yyyy.");
        parsedDate = dateHandling(input);
        prettyDateString = prettyDate.format(parsedDate);
        
        // Iterate through list, generating a count
        System.out.println("Counting movies...");
        while(it.hasNext()) {
            curMovie = it.next();
            if(curMovie.getReleaseDate().compareTo(parsedDate) < 0) {
                count++;
            }
        }
        // Output result
        System.out.println("Found " + count + " coming movies with release dates before " + prettyDateString + ".");
    }
    
    /**
     * Saves the data stored in the coming and showing lists.
     * Overwrites previously held data in the text file.
     * @param fileHandler Reference to the FileHandler created in Application
     * @throws IOException
     */
    private void saveChanges(FileHandler fileHandler) throws IOException {
        System.out.println("Saving...");
        fileHandler.saveData(showingList, comingList);
        System.out.println("File saved.");
    }
    
    /**
     * Converts a Date object to a String easily viewable by the user.
     * @param currentDate Date
     * @return String
     */
    private Date prettyCurrentDate(Date currentDate) {
    	
    	String prettyDateString;
    	
    	try {
        	prettyDateString = prettyDate.format(currentDate);
			currentDate = prettyDate.parse(prettyDateString);
			
		} catch (ParseException e) {
			e.printStackTrace();
		}
    	
		return currentDate;
    	
    }
    
    /**
     * Validates the users input to ensure formatting of dates.
     * @param userInput
     * @return Date
     */
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
    
    /**
     * Sets running to false, ending the main loop in Application, ending the program.
     */
    private void exit() {
        running = false;
    }
    
}
