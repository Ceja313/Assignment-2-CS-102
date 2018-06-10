import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

/**
 * The Class TennisMatchesContainer, used as a container for all TennisMatches.
 * @author Jeffery Ceja
 */

public class TennisMatchesContainer implements TennisMatchesContainerInterface{

    // Value stored by the Container
    private ArrayList<TennisMatch> matches;

    /**
     * Constructor to create a TennisMatchesContainer with a size of 10 holding 0 matches.
     */
    TennisMatchesContainer() {
        matches = new ArrayList<>();
    }

    /**
     * Method used to add a person to the list of matches in the Array List
     *
     * @param match The TennisMatch to add
     * @throws TennisDatabaseRuntimeException Exception thrown if there is an attempt to access outside of the array
     */
    @Override
    public void insertMatch(TennisMatch match) throws TennisDatabaseRuntimeException {
        matches.add(match);
    }

    /**
     * Method used to make sure the information stored within the tennis matches list is correct.
     * @param players the tennis players stored inside the bst
     */
    public void goOverMatches(TennisPlayersContainer players) {
        for(TennisMatch match : matches) {
            if(match.getPlayer1Name().equals(match.getPlayer1Id())) {
               match.setFirstPlayer(players.getPlayerNode(match.getPlayer1Id()).getData().getPlayer());
            }
            if (match.getPlayer2Name().equals(match.getPlayer2Id())) {
                match.setSecondPlayer(players.getPlayerNode(match.getPlayer2Id()).getData().getPlayer());
            }
        }
    }

    public ArrayList<TennisMatch> getMatches() {
        ArrayList<TennisMatch> placeHolder = new ArrayList<>(this.matches);
        return placeHolder;
    }

    /**
     * call for the sorting of the matches array by date
     * iterate through the array and print the match information
     */
    @Override
    public void printAllMatches() {
        if (matches.size() == 0) {
            System.out.println("No matches to print.");
        } else {
            Collections.sort(matches, new IdSort());
            for(TennisMatch match : matches) {
                match.print();
            }
        }
    }

    /**
     * Class used to go over the matches and sort it by id.
     */
    class IdSort implements Comparator<TennisMatch> {
        @Override
        public int compare(TennisMatch match1, TennisMatch match2) {
            return match1.getTennisMatchDate().compareTo(match2.getTennisMatchDate());
        }
    }
}
