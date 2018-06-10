import static java.lang.Integer.parseInt;

/**
 * The Class TennisPlayer, used as a holder for the winner of a tennis match.
 * @author Jeffery Ceja
 */
 public class TennisPlayer implements TennisPlayerInterface{

     // Values stored for a tennis player
    private String Id;
    private String firstName;
    private String lastName;
    private String country;
    private boolean dummy;
    private int birthYear;

    /**
     * Constructor to create a tennis player with the information in the arguments.
     *
     * @param playerId player id of the player
     * @param firstName first name of the player
     * @param lastName last name of the player
     * @param year year of the player
     * @param country country of the player
     */
    TennisPlayer(String playerId, String firstName, String lastName, int year, String country) {
        setId(playerId);
        setFirstName(firstName);
        setLastName(lastName);
        setBirthYear(year);
        setCountry(country);
    }

    /**
     * Method to set the id of the tennis player.
     * @param Id the id of the tennis player
     */
    private void setId(String Id) {
        this.Id = Id;
    }

    /**
     * Method to set the first name of the tennis player.
     * @param firstName the first name of the tennis player
     */
    void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    /**
     * Method to set the birth year of the tennis player.
     * @param year the year of the tennis player
     */
    void setBirthYear(int year) {
        this.birthYear = year;
    }

    /**
     * Method to set last name of the tennis player
     * @param lastName the last name of the tennis player
     */
    void setLastName(String lastName) {
        this.lastName = lastName;
    }

    /**
     * Method to set the country of the tennis player
     * @param country the country of the tennis player
     */
    void setCountry(String country) {
        this.country = country;
    }

    public String getId() {
        return this.Id;
    }

    public String getFirstName() {
        return this.firstName;
    }

    public String getLastName() {
        return this.lastName;
    }

    public int getBirthYear() {
        return this.birthYear;
    }

    public String getStringBirthYear() { return Integer.toString(this.birthYear);}

    public String getCountry() {
        return this.country;
    }

    /**
     * Method to print the information of the tennis player
     */
    public void print() {
        System.out.println("Id: " + getId());
        System.out.println("FirstName: " + getFirstName());
        System.out.println("LastName: " + getLastName());
        System.out.println("Country: " + getCountry());
    }

    /**
     * Method to return if the tennis player is a dummy.
     * @return The dummy value stored of the tennis player
     */
    boolean isDummy() {
        return dummy;
    }

    /**
     * Method used to set the dummy value of the tennis player.
     * @param dummy true/false value to save to the tennis player
     */
    void setDummy(boolean dummy) {
        this.dummy = dummy;
    }

    /**
     * Method to compare the id's of the player
     * @param player the tennis player
     * @return an integer depending on which id is greater
     */
    @Override
    public int compareTo(TennisPlayer player) {
        return getId().compareTo(player.getId());
    }
}