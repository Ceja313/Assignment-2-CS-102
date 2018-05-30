import java.text.SimpleDateFormat;
import java.util.Scanner;
import static java.lang.Integer.parseInt;

/**
 * The Class Assignment1, used to act as a User Interface and handle the information from the user accordingly.
 * @author Jeffery Ceja
 */
public class Assignment2 {
    /**
     * Main method.
     *
     * Create an instance of Assignment1
     * Create an instance of TennisDatabase
     * call for the database to load information from a given filename from the args
     * call for the assignment to display the user options
     *
     * @param args arguments from the JCL
     */
    public static void main(String[] args) {
        Assignment2 assignment = new Assignment2();
        TennisDatabase database = new TennisDatabase();
        database.loadFromFile(args);
        assignment.displayUserOptions(database);


    }

    /**
     * Method used as the user interface and call the correct methods to do the requests of the user.
     * do a loop that iterates at least once and continues until 8 is inputted
     *      Output interface to show available options of the user
     *      based on user input
     *          print all tennis players
     *          print all tennis matches of a player
     *          print all tennis matches
     *          insert a new player
     *              ask for player information and continue to ask if the information is not valid
     *          insert a new match
     *              ask for match information and continue to ask if the information is not valid
     *          exit the program
     *          if the user selection is invalid say it is invalid and go back to the start of the loop
     *
     * @param database The TennisDatabase holding information of matches and players
     */
    private void displayUserOptions(TennisDatabase database) {
        System.out.println("Welcome to the CS-102 Tennis Manager, my name is Jeff Ceja");
        int selection;
        Scanner userInput = new Scanner(System.in);
        do {
            System.out.println(); System.out.println();
            System.out.println("Available Commands:");
            System.out.println("1 -- Print all tennis players.");
            System.out.println("2 -- Print all tennis matches of a player.");
            System.out.println("3 -- Print all tennis matches.");
            System.out.println("4 -- Insert a new player.");
            System.out.println("5 -- Insert a new match.");
            System.out.println("8 -- Exit.");
            System.out.print("Enter your selection : ");
            selection = userInput.nextInt();

            switch (selection) {
                case 1:
                    printAllTennisPlayers(database);
                    break;
                case 2:
                    printAllTennisMatchesOfAPlayer(userInput, database);
                    break;
                case 3:
                    printAllTennisMatches(database);
                    break;
                case 4:
                    boolean isValidPlayer;
                    String [] playerInfo;
                    do {
                        System.out.println("What is the Id, firstName, lastName, year, and country of the player? ");
                        System.out.println("For example a response could be: JC313,Jeffery,Ceja,2012,America");
                        String playerInformation = userInput.next();
                        playerInfo = playerInformation.split(",");
                        isValidPlayer = isValidPlayer(playerInfo);
                    } while (!isValidPlayer);

                    TennisPlayer player = new TennisPlayer(playerInfo[0], playerInfo[1], playerInfo[2],
                            parseInt(playerInfo[3]), playerInfo[4]);
                    insertNewPlayer(database, player);
                    break;
                case 5:
                    boolean isValidMatch;
                    String [] matchInfo;
                    do {
                        System.out.println("What is the firstPlayerId, secondPlayerId, date, location, and scores " +
                                "of the match?");
                        System.out.println("Response must be in format: FED81/DJO87/20150817/CINCINNATI/7-6,6-4/");
                        String matchInformation = userInput.next();
                        matchInfo = matchInformation.split("/");
                        isValidMatch = isValidMatch(matchInfo);
                    } while (!isValidMatch);
                    try {
                        TennisMatch match = new TennisMatch(matchInfo[0], matchInfo[1], matchInfo[2], matchInfo[3],
                                matchInfo[4]);
                        insertNewMatch(database, match);
                    } catch (TennisDatabaseException ex) {
                        System.out.println("Unable to insert tennisMatch, invalid input.");
                        System.out.println("Information supplied:");
                        for(int index = 0; index < matchInfo.length; index++) {
                            System.out.println(matchInfo[index]);
                        }
                    }
                    break;
                case 8:
                    System.out.print("I hope you had fun!");
                    break;
                default:
                    System.out.println("Input is not a valid option.");
            }

        } while (selection != 8);

    }

    /**
     * Call the database to print all tennisPlayers
     * @param database The TennisDatabase holding information of matches and players
     */
    private void printAllTennisPlayers(TennisDatabase database) {
        database.printAllTennisPlayers();
    }

    /**
     * Ask the user to user to enter the tennis players id and continue asking if the id is not valid
     * Call the database to print the tennis matches of the player with the id.
     * @param userInput The user input of the data
     * @param database The TennisDatabase holding information of matches and players
     */
    private void printAllTennisMatchesOfAPlayer(Scanner userInput, TennisDatabase database) {
        String playerId;
        do {
            System.out.print("Enter Tennis Players Id : ");
            playerId = userInput.next();
        } while (!isValidId(playerId));
        database.printTennisMatchesOfPlayer(playerId);

    }

    /**
     * Call the database to print all the matches.
     * @param database The TennisDatabase holding information of matches and players
     */
    private void printAllTennisMatches(TennisDatabase database) {
        database.printAllMatches();
    }

    /**
     * call the database to create a new player using the user player information
     * catch an exception if the tennis player is already within the container
     * output the information the user inputted for the player
     * @param database The TennisDatabase holding information of matches and players
     * @param player The player information inputted by the user
     */
    private void insertNewPlayer(TennisDatabase database, TennisPlayer player) {

        try {
            database.userCreatePlayer(player);
        } catch (TennisDatabaseRuntimeException ex) {
            System.out.println("Tennis player is already within the container.");
            player.print();
        }
    }

    /**
     * Call the database to create a user using the information inputted for a match.
     * @param database The TennisDatabase holding information of matches and players
     * @param match The match holding the user inputted information of a player
     */
    private void insertNewMatch(TennisDatabase database, TennisMatch match) {
        database.userCreateMatch(match);
    }

    /**
     * Call the methods implemented to check if a player information is valid.
     * @param player The player holding the user inputted information of a match
     * @return Whether or not the player information is valid
     */
    private boolean isValidPlayer(String [] player) {
        //JRC313,Jeffery,Ceja,2012,America
        return isValidId(player[0]) && isValidFirstOrLastName(player[1]) && isValidFirstOrLastName(player[2])
                && isValidYear(player[3]) && isValidCountry(player[4]);
    }

    /**
     * Check to see if the inputted id is valid.
     *
     * if the id is empty or the length is not equal to 5 make it known that the id is not valid
     * otherwise the id is valid
     * @param id The id the user inputted
     * @return Whether or not the id is valid
     */
    private boolean isValidId(String id) {
        if(!id.isEmpty() && id.length() != 5) {
            System.out.println("Id is not valid, " + id
                    + " Id can be any arrangement of 5 characters and or digits but must be 5 in length.");
            return false;
        }
        return true;
    }

    /**
     * Check to see if the expected string is valid.
     *
     * if the inputted information is empty output that the information is not valid
     *
     * if the inputted information's length is less than or equal to 2 output that the input is not valid
     *
     * otherwise it is valid
     *
     * @param firstOrLastName The first or last name of the player
     * @return Whether the first or last name is valid
     */
    private boolean isValidFirstOrLastName(String firstOrLastName) {
        if(firstOrLastName.isEmpty()) {
            System.out.println(firstOrLastName + " is not a valid input.");
            return false;
        } else if (firstOrLastName.length() <= 2) {
            System.out.println(firstOrLastName + " is not a valid input, must contain more than 2 characters");
            return false;
        }
        return true;
    }

    /**
     * Check to see if the inputted year is valid
     *
     * if the year is not numeric or the year length is not for output the year must have 4 digits
     *
     * @param year The year
     * @return Whether or not the year is valid
     */
    private boolean isValidYear(String year) {
        //StringUtils.isNumeric(year);
        if(!isNumeric(year) || year.length() != 4) {
            System.out.println("Year must have 4 digits!");
            return false;
        }
        return true;
    }

    /**
     * Method implemented to check if a string is numeric
     * @param str string to check numerical value
     * @return whether the string is numeric or not
     */
    private boolean isNumeric(String str)
    {
        try
        {
            double valueToCheckNumeric = Double.parseDouble(str);
        }
        catch(NumberFormatException nfe)
        {
            return false;
        }
        return true;
    }

    /**
     * Method to check if the country is valid.
     *
     * if the country is empty or has more than 20 characters it is not valid
     *
     * @param country The country to check
     * @return Whether the country is valid or not
     */
    private boolean isValidCountry(String country) {
        return !country.isEmpty() && country.length() <= 20;
    }

    /**
     * Method to check if a match is valid or not.
     *
     * if the information of the match doesn't have valid id's, a date, a country, or a score it is not valid
     *
     * @param match The match information inputted by the user.
     * @return Whether the match information is valid or not
     */
    private boolean isValidMatch(String [] match) {
        return isValidId(match[0]) && isValidId(match[1]) && isValidDate(match[2]) && isValidCountry(match[3])
                && isValidScore(match[4]);
    }

    /**
     * Method to check if a date is valid or not.
     *
     * Check to see if the date is in simpledate format, if not date is not valid
     * Check to see if length is appropriate, if not date is not valid
     *
     * Otherwise date is valid
     * @param date The date to check validity
     * @return Whether the date is valid or not
     */
    private boolean isValidDate(String date) {
        try {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
            sdf.setLenient(false);
            sdf.parse(date);
        }
        catch (Exception e) {
            System.out.println("Date is not valid, " + date);
            return false;
        }
        if (date.length() != 8) {
            System.out.println("Date must be in YYYYMMDD format, " + date + " is not in this format.");
            return false;
        }
        return true;
    }

    /**
     * Method to check whether the score is valid or not.
     *
     * if the score is empty or the length is less than 3 it is not valid
     *
     * @param score The score to check validity
     * @return Whether the score is valid or not
     */
    private boolean isValidScore(String score) {
        return !score.isEmpty() && score.length() >= 3;
    }


}
