package Menu;

import java.io.IOException;
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
        int userInput = input.nextInt();
        input.nextLine();
        return userInput;
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
     *                TEST                                             *
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
        System.out.print(showingList);
        System.out.println("Coming Movies:");
        System.out.print(comingList);
    }
    
    private void addMovieToComingList() {
        Movie movie;
        String line;
        
        System.out.println("Enter movie details in this format: name, releaseDate, description, receiveDate\n"
                + "Dates are formatted mm/dd/yyyy");
        line = input.nextLine();
        movie = new Movie();
        System.out.println("Creating Movie...");
        movie.loadFromString(line);
        System.out.println("Adding to list...");
        comingList.addOrderedByReleaseDate(movie);
        System.out.println("Movie added");
    }
    
    private void startShowingByReleaseDate() {
        ListIterator<Movie> it = comingList.listIterator();
        String line;
        Date date;
        Movie curMovie;
        
        System.out.println("Enter date in this format: mm/dd/yyyy");
        line = input.nextLine();
        date = Movie.parseDate(line);
        
        System.out.println("Checking coming movies...");
        while(it.hasNext()) {
            curMovie = it.next();
            if(curMovie.getReleaseDate().equals(date)) {
                System.out.println(String.format("%s set to release and moved from coming list.", curMovie.getName()));
                it.remove();
                curMovie.setStatus(MovieStatus.release);
                showingList.add(curMovie);
            }
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
        String date;
        Date newDate;
        
        System.out.println("Enter a new release date for the movie " + movie.getName() + "\n"
                            + "Format: mm/dd/yyyy");
        date = input.nextLine();
        newDate = Movie.parseDate(date);
        movie.setReleaseDate(newDate);
        System.out.println("Release date changed to " + date);
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
        String date;
        Date parsedDate;
        
        System.out.println("Enter release date formatted as mm/dd/yyyy.");
        date = input.nextLine();
        parsedDate = Movie.parseDate(date);
        
        System.out.println("Counting movies...");
        while(it.hasNext()) {
            curMovie = it.next();
            if(curMovie.getReleaseDate().compareTo(parsedDate) < 0) {
                count++;
            }
        }
        System.out.println("Found " + count + " coming movies with release dates before " + date + ".");
    }
    
    private void saveChanges(FileHandler fileHandler) throws IOException {
        fileHandler.saveData(showingList, comingList);
    }
    
    private void exit() {
        running = false;
    }

}
