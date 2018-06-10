import java.util.Scanner;
import static java.lang.Integer.parseInt;

/**
 * The Class TennisMatch, used to create and hold information of a tennis match.
 * @author Jeffery Ceja
 */
public class TennisMatch implements TennisMatchInterface {

    // Values stored by TennisMatch
    private TennisPlayer firstPlayer;
    private TennisPlayer secondPlayer;
    private String tennisMatchDate;
    private String locationOfMatch;
    private String winner;
    private String score;
    private TennisMatchSetScore scores;

    /**
     * Constructor to create a Tennis match with the given information.
     *
     * Check if the match information is valid
     * set the first tennis player
     * set the second tennis player
     * set the tennis match date to tennis match date
     * set the location of match to the location of match
     * set the score to the score
     * assign scores to a new tennis match set score object
     *
     * check to see if score is valid by calling to process the match score
     * if the score is not valid
     *      throw a tennis database exception saying the tennis match score is not valid
     * if the score of the first player is greater than the second player
     *      assign the winners id to winner
     * otherwise if the score of the second player is greater than the first player
     *      assign the winners id to winner
     * otherwise throw an exception saying the score is not valid
     * @param firstPlayerId id of the first player
     * @param secondPlayerId id of the second player
     * @param tennisMatchDate date of the tennis match
     * @param locationOfMatch location of the tennis match
     * @param score scores from the tennis match
     * @throws TennisDatabaseException Exception if information is invalid or score is invalid
     */
    public TennisMatch(String firstPlayerId, String secondPlayerId, String tennisMatchDate
            , String locationOfMatch, String score, TennisPlayersContainer bst) throws TennisDatabaseException {
        isMatchInfoValid(firstPlayerId, secondPlayerId, tennisMatchDate, locationOfMatch, score);
        setFirstPlayer(bst.createPlayerIfNeeded(firstPlayerId).getData().getPlayer());
        setSecondPlayer(bst.createPlayerIfNeeded(secondPlayerId).getData().getPlayer());
        setTennisMatchDate(tennisMatchDate);
        setLocationOfMatch(locationOfMatch);
        setScore(score);
        this.scores = new TennisMatchSetScore();

        boolean scoreValid = processMatchScore(score, scores);
        if (!scoreValid) {
            throw new TennisDatabaseException("Tennis match score not valid");
        }
        if (scores.setsPlayer1 > scores.setsPlayer2) {
            winner = firstPlayerId;
        } else if (scores.setsPlayer1 < scores.setsPlayer2) {
            winner = secondPlayerId;
        } else {
            throw new TennisDatabaseException("Tennis match score not valid");
        }
    }

    /**
     * Method used to iterate a set of scores recursively and assign a winner of a tennis match.
     *
     * if the length of the score is 0
     *      return true
     * otherwise
     *      instantiate a scanner to the score using , as the delimeter
     *      assign score 1 to the next value of the scanner
     *      instantiate a scanner to the score value using the delimeter -
     *      assign the next int of the scanner to player 1 games
     *      assign the next int of the scanner to player 2 games
     *
     *      if player 1 games is greater than player 2 games
     *          increment the scores of the setsPlayer1
     *      otherwise if the player 1 games is less than the player 2 games
     *          increment the scores of the setsPlayer2
     *      otherwise return false
     *      if the length of score1 equals the length of score
     *          return true
     *      otherwise
     *          assign the leftover values
     *          recursively call the same method using the leftover values and scores as the parameter
     * @param score The score to recursively shrink
     * @param scores The scores to progressively increment
     * @return Whether or not the recursive call is done
     */
    private boolean processMatchScore( String score, TennisMatchSetScore scores) {
        // score length is 0, base case
        if (score.length() == 0) {
            return true;
        } else {
            // standard call for the recursive shrinking of the problem
            Scanner iteratingScore = new Scanner(score).useDelimiter(",");
            String score1 = iteratingScore.next();
            Scanner scanner1 = new Scanner(score1).useDelimiter("-");
            int player1Games = scanner1.nextInt();
            int player2Games = scanner1.nextInt();

            // increment scores depending on who won
            if (player1Games > player2Games) {
                scores.setsPlayer1++;
            } else if (player1Games < player2Games) {
                scores.setsPlayer2++;
            } else {
                return false;
            }

            if (score1.length() == score.length()) {
                return true;
            } else {
                // recursively find the leftovers and call the method again
                String leftover = score.substring(score1.length() + 1);
                return processMatchScore(leftover, scores);
            }
        }
    }

    /**
     * Method to check the validity of the match information.
     *
     * if the first player id or second player id or tennis match date or location of match or score is null
     *      throw a tennis database exception
     * @param firstPlayerId id of the first player
     * @param secondPlayerId id of the second player
     * @param tennisMatchDate match date of the tennis match
     * @param locationOfMatch location of the tennis match
     * @param score scores from the tennis match
     * @throws TennisDatabaseException Exception thrown if the tennis match info
     */
    private void isMatchInfoValid(String firstPlayerId, String secondPlayerId, String tennisMatchDate
            , String locationOfMatch, String score) throws TennisDatabaseException {
        if(firstPlayerId == null) {
            throw new TennisDatabaseException("firstPlayer Id is not a valid Id");
        } else if (secondPlayerId == null) {
            throw new TennisDatabaseException("Second player Id is not a valid Id");
        } else if (tennisMatchDate == null) {
            throw new TennisDatabaseException("tennisMatchDate is not a valid date");
        } else if (locationOfMatch == null) {
            throw new TennisDatabaseException("locationOfMatch is not valid");
        } else if (score == null) {
            throw new TennisDatabaseException("scores is not valid");
        }
    }

    /**
     * Method to set the first player id.
     * @param tennisPlayer tennis player
     */
    protected void setFirstPlayer(TennisPlayer tennisPlayer) {
        this.firstPlayer = tennisPlayer;
    }

    /**
     * Method to set the second player id.
     * @param tennisPlayer tennis player
     */
    protected void setSecondPlayer(TennisPlayer tennisPlayer) {
        this.secondPlayer = tennisPlayer;
    }

    /**
     * Method to set the tennis match date.
     * @param tennisMatchDate date of the tennis match
     */
    private void setTennisMatchDate(String tennisMatchDate) {
        this.tennisMatchDate = tennisMatchDate;
    }

    /**
     * Method to set the location of the tennis match.
     * @param locationOfMatch location of the tennis match
     */
    private void setLocationOfMatch(String locationOfMatch) {
        this.locationOfMatch = locationOfMatch;
    }

    /**
     * Method to set the score of the tennis match.
     * @param score score of the tennis match
     */
    private void setScore(String score) {
        this.score = score;
    }

    public String getTennisMatchDate() {
        return this.tennisMatchDate;
    }
    public String getPlayer1Id() {
        return this.firstPlayer.getId();
    }

    public String getPlayer1Name() {
        if(this.firstPlayer.getFirstName().isEmpty()) {
            return this.firstPlayer.getId();
        } else {
            return this.firstPlayer.getFirstName() + " " + this.firstPlayer.getLastName();
        }
    }

    public String getPlayer2Name() {
        if(this.secondPlayer.getFirstName().isEmpty()) {
            return this.secondPlayer.getId();
        } else {
            return this.secondPlayer.getFirstName() + " " + this.secondPlayer.getLastName();
        }
    }

    public String getPlayer2Id() {
        return this.secondPlayer.getId();
    }

    public int getDateMonth() {
        return parseInt(getTennisMatchDate().substring(4,6));
    }

    public int getDateYear() {
        return parseInt(getTennisMatchDate().substring(0,4));
    }

    public int getDateDay() {
        return parseInt(getTennisMatchDate().substring(6,8));
    }

    public String getTournament() {
        return this.locationOfMatch;
    }

    public String getScore() { return scores.setsPlayer1 + "-" + scores.setsPlayer2;}

    public String getWinner() {
        return winner;
    }

    /**
     * Method used to print all of the information within the tennis match
     */
    public void print() {
        System.out.print( getPlayer1Id() + ", " );
        System.out.print( getPlayer2Id() + ", ");
        System.out.print(getDateYear() + "-" + getDateMonth() + "-" + getDateDay() + ", ");
        System.out.print( getTournament() + ", ");
        System.out.print(score + ", ");
        System.out.print("Winner: " + winner);
        System.out.println();
    }

    /**
     * Method used to compare a tennis match by date.
     * @param match The tennis match to compare to
     * @return whether the date of the tennis match is before or after the date
     */
    @Override
    public int compareTo(TennisMatch match) {
        if(getDateYear() < match.getDateYear()) {
            return 1;
        } else if(getDateYear() == match.getDateYear()
                && getDateMonth() < match.getDateMonth()) {
            return 1;
        } else if(getDateYear() == match.getDateYear()
                && getDateMonth() == match.getDateMonth()
                && getDateDay() < match.getDateDay()) {
            return 1;
        } else {
            return 0;
        }
    }
}