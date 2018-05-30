/**
 * The Class TennisPlayersMatchesContainer, used as a container for all TennisPlayers.
 * @author Jeffery Ceja
 */
public class TennisPlayersContainer implements TennisPlayersContainerInterface {

    private TennisPlayerNode head;
    private int numPlayers;

    /**
     * Constructor to create a Tennis player container
     */
    TennisPlayersContainer() {
        head = null;
        numPlayers = 0;
    }

    /**
     * Method to get the player node of a given id
     * <p>
     * iterate through the list and return the node with the id or return null if it does not exist
     *
     * @param id the id of the player to grab
     * @return the tennis player node or null if not found
     */
    private TennisPlayerNode getPlayerNode(String id) {
        TennisPlayerNode currNode = head;
        int indexCurrNode = 0;
        while ((indexCurrNode < numPlayers) &&
                (currNode.getPlayer().getId().compareTo(id) < 0)) {
            currNode = currNode.getNext();
            indexCurrNode++;
        }
        if (currNode.getPlayer().getId().equals(id)) {
            return currNode;
        } else {
            return null;
        }
    }

    /**
     * Method to get the tennis player with given id.
     * <p>
     * iterate through the nodes until the player is found
     *
     * @param id the id of the tennis player
     * @return the tennis player with the id or null if not found
     */
    private TennisPlayer getPlayer(String id) {
        TennisPlayerNode currNode = head;
        for (int i = 0; i < numPlayers; i++) {
            if (currNode.getPlayer().getId().equals(id)) {
                return currNode.getPlayer();
            } else {
                currNode = currNode.getNext();
            }
        }
        return null;
    }


    /**
     * Method to insert a player into the container/list accordingly.
     *
     * @param player Tennis player to insert into the container
     * @throws TennisDatabaseRuntimeException Exception thrown if the insertion goes wrong
     */
    @Override
    public void insertPlayer(TennisPlayer player) throws TennisDatabaseRuntimeException {
        TennisPlayerNode newNode = new TennisPlayerNode(player);
        TennisPlayer sameId = getPlayer(player.getId());
        if (sameId == null) {
            // List is empty, Special Case
            if (head == null) {
                head = newNode;
                newNode.setNext(newNode);
                newNode.setPrev(newNode);
                numPlayers++;
            } else {
                // List is not empty, regular case
                TennisPlayerNode nodeAfterIns = head;
                TennisPlayerNode nodeBeforeIns = head.getPrev();
                int nodeAfterInsIdx = 1;
                while (nodeAfterInsIdx <= numPlayers
                        && nodeAfterIns.getPlayer().compareTo(player) <= 0) {
                    nodeBeforeIns = nodeAfterIns;
                    nodeAfterIns = nodeAfterIns.getNext();
                    nodeAfterInsIdx++;
                }
                if (nodeAfterInsIdx == 1) {
                    head = newNode;
                    newNode.setNext(nodeAfterIns);
                    newNode.setPrev(nodeBeforeIns);
                    nodeBeforeIns.setNext(newNode);
                    nodeAfterIns.setPrev(newNode);
                    numPlayers++;
                } else {
                    newNode.setNext(nodeAfterIns);
                    newNode.setPrev(nodeBeforeIns);
                    nodeBeforeIns.setNext(newNode);
                    nodeAfterIns.setPrev(newNode);
                    numPlayers++;
                }
            }
        } else {
            try {
                throw new TennisDatabaseRuntimeException("Duplicate player");
            } catch (TennisDatabaseRuntimeException ex) {
                System.out.println("Duplicate player found, continuing");
            }

            sameId.setFirstName(player.getFirstName());
            sameId.setLastName(player.getLastName());
            sameId.setBirthYear(player.getBirthYear());
            sameId.setCountry(player.getCountry());
            sameId.setDummy(false);
        }
    }

    /**
     * Method to insert a match into the list.
     *
     * @param match tennis match to insert
     * @throws TennisDatabaseRuntimeException Exception thrown if insertion goes wrong
     */
    @Override
    public void insertMatch(TennisMatch match) throws TennisDatabaseRuntimeException {
        TennisPlayerNode nodePlayer1 = getPlayerNode(match.getPlayer1Id());
        if (nodePlayer1 == null) {
            TennisPlayer player = new TennisPlayer(match.getPlayer1Id(),
                    "", "", 0, "");
            player.setDummy(true);
            try {
                insertPlayer(player);
            } catch (TennisDatabaseRuntimeException ex) {
                System.out.println("Problem inserting player One.");
                player.print();
            }
            nodePlayer1 = getPlayerNode(match.getPlayer1Id());
        }
        if (nodePlayer1 != null) {
            nodePlayer1.insertMatch(match);
        }

        TennisPlayerNode nodePlayer2 = getPlayerNode(match.getPlayer2Id());
        if (nodePlayer2 == null) {
            TennisPlayer player2 = new TennisPlayer(match.getPlayer2Id(),
                    "", "", 0, "");
            player2.setDummy(true);
            try {
                insertPlayer(player2);
            } catch (TennisDatabaseRuntimeException ex) {
                System.out.println("Problem inserting player Two.");
                player2.print();
            }
            nodePlayer2 = getPlayerNode(match.getPlayer2Id());
        }
        if (nodePlayer2 != null) {
            nodePlayer2.insertMatch(match);
        }
    }

    /**
     * Method used to iterate through the container and output the information in every node.
     *
     * @throws TennisDatabaseRuntimeException Exception thrown if there is an error outputting node information
     */
    @Override
    public void printAllPlayers() throws TennisDatabaseRuntimeException {
        if (numPlayers == 0) {
            System.out.println("No players to print");
        } else {
            TennisPlayerNode currNode = head;
            for (int i = 0; i < numPlayers; i++) {
                currNode.getPlayer().print();

                currNode = currNode.next;
            }
        }
    }

    /**
     * Method used to print the matches of a player with a given id.
     *
     * attempt to get the player node
     * if the player node is not null, print the matches of that player
     * otherwise throw a tennis database runtime exception saying there does not exist a player with the selected id
     *
     * @param playerId id of the player to print matches for
     * @throws TennisDatabaseRuntimeException Exception thrown if there is an error in printing matches
     */
    public void printMatchesOfPlayer(String playerId) throws TennisDatabaseRuntimeException {
        TennisPlayerNode player = getPlayerNode(playerId);

        if (player != null) {
            player.printMatches();
        } else {
            try {
                throw new TennisDatabaseRuntimeException("There does not exist a player with the selected Id " + playerId);
            } catch (TennisDatabaseRuntimeException ex) {
                System.out.println("No player with selected id, continuing.");
            }
        }
    }
}
