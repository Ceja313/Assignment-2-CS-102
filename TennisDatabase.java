import java.io.*;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;

import static java.lang.Integer.parseInt;

/**
 * The Class TennisDatabase, used to take a given backslash document with expected information and
 * store the information accordingly
 * @author Jeffery Ceja
 */
class TennisDatabase {

    // Tennis containers to hold information
    private TennisPlayersContainer tpc = new TennisPlayersContainer();
    private TennisMatchesContainer tmc = new TennisMatchesContainer();

    /**
     * Method used to look at the args and use the wanted argument to pull information from.
     *
     * if args length is greater than 0
     *      inputFileName is the first argument listed
     *      if the args length is greater than 1 output that only the first argument will be used and output the args
     *
     *      try to create a scanner to read information from the inputFile
     *          call to read the file and store data with the scanner as the argument
     *      catch an exception if the file path is incorrect and output that the file may not exist
     * otherwise output that the tennis database has no arguments to read a file from
     * @param args The command line arguments
     */
    void loadFromFile(String [] args) {
        if (args.length > 0) {
            String inputFileName= args[0];

            if (args.length >= 2) {
                System.out.println("There is more than 1 parameter, only using the first."
                        + "The list of args is : " + args.toString());
            }


            try (Scanner scanner = new Scanner(new File(inputFileName))) {
                readFileStoreData(scanner);
            } catch (IOException ex) {
                Logger.getLogger( Assignment2.class.getName() ).log( Level.SEVERE,
                        "Error during reading, command line argument file may not exist." + args[0], ex );
            }


        } else {
            System.out.println("Sorry, TennisDatabase has no argument file to read from ");
        }
    }

    /**
     * Method used to export the information stored inside the database to an external file.
     *
     * Uses an iterator to output the players.
     * Uses a loop to output the matches.
     * @param args
     */
    void exportToFile(String [] args) {
        TreeIterator saveIter1 =
                new TreeIterator( tpc ); // Init the iterator.
        saveIter1.setInorder();
        try { // Open file for writing.
            PrintWriter writer = new PrintWriter( args[0], "UTF-8" );
            TennisPlayer player;
            TennisPlayerNode currItem;
            while( saveIter1.hasNext() ) { // Binary search tree traversal using iterator.
                currItem = saveIter1.next();
                player = currItem.getPlayer();
                //PLAYER/DJA80/Chris/Paul/1987/Ireland
                writer.println(
                        "PLAYER/" + player.getId() + "/" + player.getFirstName()
                                + "/" + player.getLastName() + "/" + player.getBirthYear()
                                + "/" + (player.getCountry().isEmpty() ? " " : player.getCountry()) + "/");
            }
            for(TennisMatch match : getTmc().getMatches()) {
                writer.println("MATCH/" + match.getPlayer1Id() + "/" + match.getPlayer2Id()
                        + "/" + match.getTennisMatchDate() + "/" + match.getTournament() + "/" + match.getScore());
            }
            writer.close(); } // Close file.
        catch( FileNotFoundException e ) {
            System.out.println("File not found " + e);
        }
        catch( UnsupportedEncodingException e ) {
            System.out.println("Unsupported Encoding " + e);
        }
    }

    /**
     * Method to read the file using a scanner and call for the creation of objects from given input.
     *
     * while the scanner has a next line
     *      set the record information equal to the line split using a backslash (/) as the parameter
     *      if the record information length is greater than 0
     *          call for the creation of an object and send the record information as the parameter
     * @param scanner Scanner used to read the file
     */
    private void readFileStoreData(Scanner scanner) {
        String[] recordInformation;
        String importedFileInformation = "";
        String line;
        while (scanner.hasNextLine()) {
            line = scanner.nextLine();
            importedFileInformation = importedFileInformation + " " + line;
            recordInformation = line.split("/");

            if (recordInformation.length > 0) {
                createObjectsFromGivenInput(recordInformation);
            }
        }
    }

    /**
     * Method used to create objects from given input.
     *
     * depending on the first value of the recordInformation create a player, create a match, or if the value isn't
     *      expected then let it be known that the value is unexpected and output the value
     * @param recordInformation record information from the file
     */
    private void createObjectsFromGivenInput(String[] recordInformation) {
        switch (recordInformation[0]) {
            case "PLAYER":
                createPlayer(recordInformation);
                break;
            case "MATCH":
                createMatch(recordInformation);
                break;
            default:
                System.out.println("Unexpected data type in document" + recordInformation[0]);
        }
    }

    /**
     * Method to create a player using the player information from the file.
     *
     * if the playerInformation length is the expected length, try to create a new tennis player using the given info
     *      insert the player into the tennis player container
     * otherwise throw an exception if the length is not the expected length
     *
     * catch a tennis database exception if there is an issue with the player and output the given information
     * @param playerInformation player information from the file
     */
    private void createPlayer(String[] playerInformation) {
        try {
            if (playerInformation.length == 6) {
                final TennisPlayer player = new TennisPlayer(playerInformation[1], playerInformation[2]
                        , playerInformation[3], parseInt(playerInformation[4]), playerInformation[5]);
                final TennisPlayerNode playerNode = new TennisPlayerNode(player);
                tpc.insertPlayer(playerNode);
                //tpc.insertPlayer(player);
            } else {
                throw new TennisDatabaseException("Do not have sufficient information for a TennisPlayer");
            }
        } catch (TennisDatabaseException ex) {
            System.out.println("Issue creating the player, invalid input for a field");
            System.out.println("Given Information");
            for (int index = 0; index < playerInformation.length; index++) {
                System.out.println("Data " + index + ": " + playerInformation[index]);
            }
        }
    }

    /**
     * Method to call for the creation of a player using the created tennis player with the information from the user.
     * @param player The tennis player created from the information of the user
     */
    void userCreatePlayer(TennisPlayer player) {
        final TennisPlayerNode playerNode = new TennisPlayerNode(player);
        tpc.insertPlayer(playerNode);
        //tpc.insertPlayer(player);
    }

    /**
     * Method to create a match using the match information from the file.
     *
     * if the matchInformation length is the expected length, try to create a new tennis match using the given info
     *      insert the match into the tennis match container and tennis player container
     * otherwise throw an exception if the length is not the expected length
     *
     * catch a tennis database exception if there is an issue with the match and output the given information
     * @param matchInformation match information from the file
     */
    private void createMatch(String[] matchInformation) {
        try {
            if (matchInformation.length == 6) {
                final TennisMatch match = new TennisMatch(matchInformation[1], matchInformation[2]
                        , matchInformation[3], matchInformation[4], matchInformation[5], tpc);
                tpc.insertMatch(match);
                tmc.insertMatch(match);
            } else {
                throw new TennisDatabaseException("Do not have sufficient information for a TennisMatch");
            }
        } catch (TennisDatabaseException ex) {
            System.out.println("Issue creating the match, invalid input for a field");
            System.out.println("Given Information");
            for(int index = 0; index < matchInformation.length; index++) {
                System.out.println("Data " + index + ": " + matchInformation[index]);
            }
        }
    }

    /**
     * Method to call for the creation of a match using the created tennis match with the information from the user.
     * @param match The tennis match created from the information of the user
     */
    void userCreateMatch(TennisMatch match) {
        tpc.insertMatch(match);
        tmc.insertMatch(match);
    }

    /**
     * Method used to call for the binary search tree
     * @return the Binary Search Tree
     */
    TennisPlayersContainer getBST() {
        return this.tpc;
    }

    /**
     * Method used to call for the tennis matches container of the database.
     * @return the tennisMatchesContainer
     */
    TennisMatchesContainer getTmc() {return this.tmc;}
    /**
     * Method used to call for the tennis player container to print all the players.
     */
    void printAllTennisPlayers() {
        tpc.printInorder();
    }

    /**
     * Method used to call for the tennis player container to print all the matches of a selected player.
     * @param playerId The id of the player selected
     */
    void printTennisMatchesOfPlayer(String playerId) {
        tpc.printMatchesOfPlayer(playerId);
    }

    /**
     * Method used to call fo the tennis match container to print all of the matches.
     */
    void printAllMatches() {
        tmc.printAllMatches();
    }


    /**
     * Method used to remove a player with a given key
     * @param key id of player
     * @return true/false depending on if the player was deleted
     */
    boolean removePlayer(String key) { return tpc.deleteKey(key); }

    /**
     * Method used to delete all tennis players and tennis matches
     */
    void reset() {
        tpc = new TennisPlayersContainer();
        tmc = new TennisMatchesContainer();
    }

}
