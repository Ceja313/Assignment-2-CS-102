/**
 * The Class TennisMatchesList, used as a list for all TennisMatches of a player.
 * @author Jeffery Ceja
 */
public class TennisMatchesList implements TennisMatchesListInterface {
    private TennisMatchNode head;
    private TennisMatchNode tail ;
    private int size;

    /**
     * Constructor to create a Tennis Matches List
     */
    TennisMatchesList()
    {
        head = null;
        tail = null;
        size = 0;
    }

    /**
     * Method to check if the list is empty
     * @return Whether or not the list is empty
     */
    public boolean isEmpty()
    {
        return head == null;
    }

    /**
     * Method to check the size of the lise.
     * @return the size of the list
     */
    public int getSize()
    {
        return size;
    }

    /**
     * Method to add a tennis match node to the front of the list
     *
     * if the head is null
     *      set the next node of the match to match and the prev node of the match to match
     *      set the head to match and tail to head
     * otherwise
     *      set the prev tail of the match to tail and the next node of the tail to match
     *      set the next node of the match to head and the head to match
     * increment the size
     * @param match the Tennis match node to add to the beginning of the list
     */
    private void prepend(TennisMatchNode match)
    {
        if (head == null)
        {
            match.setNext(match);
            match.setPrev(match);
            head = match;
            tail = head;
        } else {
            match.setPrev(tail);
            tail.setNext(match);
            head.setPrev(match);
            match.setNext(head);
            head = match;
        }

        // Update size to reflect new addition
        size++ ;
    }

    /**
     * Method used to enter a new node at the end of the list.
     *
     * set the prev node of the match to the tail and the next node of the tail to match
     * set the prev node of the head to match and the next node of the match to the head
     * set the tail equal to the match
     * increment size to reflect the addition
     * @param match Tennis match node to add to the end of the list
     */
    private void append(TennisMatchNode match) {
        match.setPrev(tail);
        tail.setNext(match);
        head.setPrev(match);
        match.setNext(head);
        tail = match;
        size++;
    }

    /**
     * Method to insert a match to a given position.
     *
     * if the start of the list is empty insert accordingly
     * if the new match equals the start of the list prepend the node
     * otherwise if it is equal to the tail of the list append the node
     *
     * iterate to position where insertion must take place
     * if head node insert accordingly, otherwise insert the node between
     *
     * increment size
     *
     * @param match tennis match to insert
     */
    @Override
    public void insertMatch(TennisMatch match) {
        TennisMatchNode newMatch = new TennisMatchNode(match);
        if (head == null) {
            newMatch.setNext(newMatch);
            newMatch.setPrev(newMatch);
            head = newMatch;
            tail = head;
        }
        if (head.equals(newMatch)) {
            prepend(newMatch);
            return;
        } else if (tail.equals(newMatch)) {
            append(newMatch);
            return;
        }

        // Iterate to position starting at head
        TennisMatchNode node = head;

        while (head != node.getNext() || ((node.getMatch().compareTo(match) != 1) && head != tail)) {
            node = node.getNext();
        }

        if (head == node.getNext()) {
            prepend(newMatch);
        } else {
            TennisMatchNode tmp = node.getNext();
            node.setNext(newMatch);
            newMatch.setPrev(node);
            newMatch.setNext(tmp);
            tmp.setPrev(newMatch);
        }

        // Increment size to show addition
        size++;
    }

    /**
     * Method used to empty the list.
     */
    public void removeAll() {
        head = null;
        tail = null;
        size = 0;
    }

    /**
     * Method used to print the matches inside the list.
     *
     * if list is empty output that the list is empty
     * otherwise iterate through the list outputting the information of the list
     * output the information of the last node in the list
     */
    public void printMatches() {
        TennisMatchNode node;

        // Check if empty first
        if (size == 0) {
            System.out.println("Empty list, no values to print");
        } else {
            node = head;

            // Iterate over all matches
            while (node.getNext() != head) {
                node.getMatch().print();
                node = node.getNext();
            }
            node.getMatch().print();
        }
    }
}