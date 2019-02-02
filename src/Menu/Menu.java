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

public class Menu {
    boolean running;
    SimpleDateFormat prettyDate = new SimpleDateFormat("MM/dd/yyyy");
    MovieList comingList;
    MovieList showingList;
    FileHandler fileHandler;
    Scanner input;
    
    public boolean isRunning() { return running; }
    
    public Menu() {
        running = true;
        comingList = new MovieList();
        showingList = new MovieList();
        fileHandler = new FileHandler();
        input = new Scanner(System.in);
    }
    
    public Menu(MovieList _comingList, MovieList _showingList, FileHandler _fileHandler) {
        running = true;
        comingList = _comingList;
        showingList = _showingList;
        fileHandler = _fileHandler;
        input = new Scanner(System.in);
    }
    
    public void displayMenu() {
        System.out.println("Choose an option: \n"
                + "1) Display all movies. \n"
                + "2) Add a new movie. \n"
                + "3) Start showing with given release date. \n"
                + "4) Edit a movie. \n"
                + "5) Number of movies released before given date. \n"
                + "6) Save changes. \n"
                + "7) Exit.");
    }
    
    public int getUserInput() {
        if(input.hasNextInt()) {
            int userInput = input.nextInt();
            input.nextLine();
            return userInput;
        } else {
            return -1;
        }
        
    }
    
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
     *  @return int 0 which will tell the program to stop.         *
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
    
    private void displayMovies() {
        System.out.println("Showing Movies:");
        showingList.toPrettyString();
        System.out.println();
        System.out.println("Coming Movies:");
        comingList.toPrettyString();
        System.out.println();
    }
    
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
        
        System.out.println("Enter the title of the movie: ");
        title = input.nextLine();
        
        while( it.hasNext()) {
        	curMovie = it.next();
            if(curMovie.getName().equalsIgnoreCase(title)) {
                nameMatch = true;
                break;
            }
        }
        		
        if(nameMatch == true) {
        			
        	System.out.println("The movie already exists in the coming list.");
        			
        } else {
        	
        	System.out.printf("Enter the description for %s: %n", title);
            descrip = input.nextLine();
        	
        	while(dateComparison == false) {
                    
                System.out.printf("Enter the recieve date for %s: %n", title);
                receive = dateHandling(input);
            
                System.out.printf("Enter the release date for %s: %n", title);
            	release = dateHandling(input);
        		
        		if (release.compareTo(receive) <= 0) {
        		
        			System.out.println("Release date must be greater than the receive date. Try again.");
        			
        		
        		} else {
        			dateComparison = true;
        		}
        	}	
        }
         
        System.out.println("Creating Movie...");
        movie = new Movie(release, title, descrip, receive);
        System.out.println("Adding to list...");
        comingList.addOrderedByReleaseDate(movie);
        System.out.println("Movie added");
    }
    
    private void startShowingByReleaseDate() {
        ListIterator<Movie> it = comingList.listIterator();
        Date currDate = new Date();
        Date date;
        Movie curMovie;
        boolean foundMatch = false;
        
        System.out.println("Enter date in this format: mm/dd/yyyy");
        date = dateHandling(input);
        
        System.out.println("Checking coming movies...");
        while(it.hasNext()) {
            curMovie = it.next();
            if(curMovie.getReleaseDate().equals(date)) {
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
    
    private void editMovie() {
        Iterator<Movie> it = comingList.iterator();
        String name;
        Movie curMovie = null;
        boolean found = false;
        int userInput;
        
        System.out.println("Please enter the name of the movie to edit.");
        name = input.nextLine();
        
        System.out.println("Searching for movie with name " + name);
        while(it.hasNext()) {
            curMovie = it.next();
            if(curMovie.getName().equalsIgnoreCase(name)) {
                found = true;
                break;
            }
        }
        
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
        
        if(!found) {
            System.out.println("Movie with name " + name + " not found.");
        }
    }
    
    private void editName(Movie movie) {
        String name;
        
        System.out.println("Enter new name for the movie " + movie.getName());
        name = input.nextLine();
        movie.setName(name);
        System.out.println("Name changed to " + name);
        
    }
    
    private void editReleaseDate(Movie movie) {
        String prettyDateString;
        Date newDate = null;
        boolean dateComparison = false;
        Date receiveDate = movie.getReceiveDate();
        
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
        
        prettyDateString = prettyDate.format(newDate);
        movie.setReleaseDate(newDate);
        System.out.println("Release date changed to " + prettyDateString);
        System.out.println("Re-sorting list...");
        comingList.sort(null);
    }

    private void editDescription(Movie movie) {
        String description;
        
        System.out.println("Enter a new description for the movie " + movie.getName());
        description = input.nextLine();
        movie.setDescription(description);
        System.out.println("Description changed to " + description);
    }
    
    private void displayNumberOfMoviesBeforeDate() {
        int count = 0;
        Iterator<Movie> it = comingList.iterator();
        Movie curMovie;
        Date parsedDate;
        String prettyDateString;
        
        System.out.println("Enter release date formatted as mm/dd/yyyy.");
        parsedDate = dateHandling(input);
        prettyDateString = prettyDate.format(parsedDate);
        
        System.out.println("Counting movies...");
        while(it.hasNext()) {
            curMovie = it.next();
            if(curMovie.getReleaseDate().compareTo(parsedDate) < 0) {
                count++;
            }
        }
        System.out.println("Found " + count + " coming movies with release dates before " + prettyDateString + ".");
    }
    
    private void saveChanges(FileHandler fileHandler) throws IOException {
        System.out.println("Saving...");
        fileHandler.saveData(showingList, comingList);
        System.out.println("File saved.");
    }
    
    private Date prettyCurrentDate(Date currentDate) {
    	
    	String prettyDateString;
    	
    	try {
        	prettyDateString = prettyDate.format(currentDate);
			currentDate = prettyDate.parse(prettyDateString);
			
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    	
		return currentDate;
    	
    }
    
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
    
    private void exit() {
        running = false;
    }
    
}
