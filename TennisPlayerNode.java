/**
 * The Class TennisPlayerNode, used as a Node for a tennis player.
 * @author Jeffery Ceja
 */
public class TennisPlayerNode implements  TennisPlayerNodeInterface {

    // Information stored for the node
    private TennisPlayer player;
    private TennisMatchesList list;
    private TennisPlayerNode prev;
    TennisPlayerNode next;

    /**
     * Constructor to create a tennis player node.
     * @param player tennis player to store in the node
     */
    TennisPlayerNode( TennisPlayer player) {
        this.player = player;
        this.list = new TennisMatchesList();
        this.prev = null;
        this.next = null;
    }

    /**
     * Method used get the player stored.
     * @return the player
     */
    @Override
    public TennisPlayer getPlayer() {
        return player;
    }

    /**
     *  Method used to get the previous player node stored.
     * @return previous player node
     */
    @Override
    public TennisPlayerNode getPrev() {
        return prev;
    }

    /**
     * Method used to get the next player node stored.
     * @return the next player node
     */
    @Override
    public TennisPlayerNode getNext() {
        return next;
    }

    /**
     * Method used to set the previous player node.
     * @param player The player node
     */
    @Override
    public void setPrev(TennisPlayerNode player) {
        prev = player;
    }

    /**
     * Method used to set the next player node.
     * @param node The player node
     */
    @Override
    public void setNext(TennisPlayerNode node) {
        next = node;
    }

    /**
     * Method used to insert a match into the list stored by the tennis player node.
     * @param match the match to insert into the list
     * @throws TennisDatabaseRuntimeException Exception thrown if match info is invalid
     */
    @Override
    public void insertMatch(TennisMatch match) throws TennisDatabaseRuntimeException {
        list.insertMatch(match);
    }

    /**
     * Method used to print the matches of a tennis player from the list.
     * @throws TennisDatabaseRuntimeException Exception thrown if there is an issue printing the matches
     */
    @Override
    public void printMatches() throws TennisDatabaseRuntimeException {
        list.printMatches();
    }
}
