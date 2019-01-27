# Movie Project
Data Structures class
James Bialon, Sean Stewart, Michael Steffens, Thomas Hopkins

## Contributing
Please write comments with any code that is not blatantly obvious. If you're not sure, write comments.

Keep code clean and readable. Don't sacrifice readablity for complexity or efficency.

## TODO

Menu currently handles inputting an int that does not represent a menu option.
However, it doesn't handle the user entering something other than a number, so we should handle this.
Also, test various amounts of input to ensure that the program handles input correctly and doesn't crash.

Various error handling needs to be implemented as outlined in the instructors specifications for the project.

Adding a new movie(to the coming list):
Invalid dates.
Release date < receive date.
Movie already in list.

Start showing movies with given release date:
The movie doesn't exist. (This should be handled already, I believe.)
Movie already exists in showing list.
Invalid release date.

Edit a movie:
Movie does not exists(I believe this should be handled already)

Count the number of coming movies with release date earlier than input:
Invalid release date.
