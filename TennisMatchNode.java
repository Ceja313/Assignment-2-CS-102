/**
 * The Class TennisMatchesNode, used as a Node for a tennis match.
 * @author Jeffery Ceja
 */
public class TennisMatchNode {

    private TennisMatch match;
    private TennisMatchNode prev;
    private TennisMatchNode next;

    /**
     * Constructor to create a tennis match node
     * @param match tennis match to store in the node
     */
    TennisMatchNode( TennisMatch match) {
        this.match = match;
        this.prev = null;
        this.next = null;
    }

    /**
     * Method used to get the match of the node.
     * @return the tennis match
     */
    public TennisMatch getMatch() {
        return match;
    }

    /**
     * Method used to get the prev node.
     * @return the previous tennis match node
     */
    public TennisMatchNode getPrev() {
        return prev;
    }

    /**
     * Method to get the next node.
     * @return the next tennis match node
     */
    TennisMatchNode getNext() {
        return next;
    }

    /**
     * Method to set the previous node.
     * @param node the previous node to set
     */
    void setPrev(TennisMatchNode node) {
        prev = node;
    }

    /**
     * Method to set the next node.
     * @param node the next node to set
     */
    void setNext(TennisMatchNode node) {
        next = node;
    }

}
