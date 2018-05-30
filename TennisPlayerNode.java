/**
 * The Class TennisPlayerNode, used as a Node for a tennis player.
 * @author Jeffery Ceja
 */
public class TennisPlayerNode implements  TennisPlayerNodeInterface {

    private TennisPlayer player;
    private TennisMatchesList list;
    private TennisPlayerNode prev;
    TennisPlayerNode next;

    /**
     * Constructor to create a tennis player node
     * @param player tennis player to store in the node
     */
    TennisPlayerNode( TennisPlayer player) {
        this.player = player;
        this.list = new TennisMatchesList();
        this.prev = null;
        this.next = null;
    }

    @Override
    public TennisPlayer getPlayer() {
        return player;
    }

    @Override
    public TennisPlayerNode getPrev() {
        return prev;
    }

    @Override
    public TennisPlayerNode getNext() {
        return next;
    }

    @Override
    public void setPrev(TennisPlayerNode player) {
        prev = player;
    }

    @Override
    public void setNext(TennisPlayerNode node) {
        next = node;
    }

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
