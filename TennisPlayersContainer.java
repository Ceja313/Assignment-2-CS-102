/**
 * The Class TennisPlayersMatchesContainer, used as a container for all TennisPlayers.
 * @author Jeffery Ceja
 */

import java.util.ArrayList;
import java.util.LinkedList;

public class TennisPlayersContainer implements TennisPlayersContainerInterface {

    // values stored by the tennis players container (Binary search tree)
    private CustomBSTNode root;
    private ArrayList<TennisPlayer> playersArray = new ArrayList<TennisPlayer>();
    private LinkedList< CustomBSTNode > queue;

    /**
     * Constructor to create a Tennis player container
     */
    public TennisPlayersContainer() {
        this.root = null;
    }

    protected CustomBSTNode getRoot() {
        return this.root;
    }

    public ArrayList<TennisPlayer> getPlayersArray() {
        return this.playersArray;
    }

    /**
     * Method used to insert a node into the container.
     * @param id id of the player
     * @param playerNode player node holding the player and matches list
     */
    public void insert(String id, TennisPlayerNode playerNode) {
        this.root = insertRec(id, playerNode, this.root);
    }

    /**
     * Method used to insert the player node into the bst recursively.
     * @param id the id of the player
     * @param playerNode the player node holding the player and matches list
     * @param currRoot the root of the search tree
     * @return
     */
    public CustomBSTNode insertRec(String id, TennisPlayerNode playerNode, CustomBSTNode currRoot) {
        if (currRoot == null) {
            currRoot = new CustomBSTNode(id, playerNode);
            return currRoot;
        } else {
            if (currRoot.getID().equals(id)) {
                return currRoot;
            } else if (currRoot.getID().compareTo(id) < 0) {
                CustomBSTNode newRoot = insertRec( id, playerNode, currRoot.getRightChild());
                currRoot.setRightChild(newRoot);
                return currRoot;
            } else {
                CustomBSTNode newRoot = insertRec( id, playerNode, currRoot.getLeftChild());
                currRoot.setLeftChild(newRoot);
                return currRoot;
            }
        }
    }

    /**
     * Method to insert a player into the container/list accordingly.
     *
     * @param player Tennis player to insert into the container
     * @throws TennisDatabaseRuntimeException Exception thrown if the insertion goes wrong
     */
    public void insertPlayer(TennisPlayerNode player) throws TennisDatabaseRuntimeException {
        if(search(player.getPlayer().getId()) && !player.getPlayer().isDummy()) {
            getPlayerNode(player.getPlayer().getId()).setData(player);
        }

        if(!search(player.getPlayer().getId())) {
            insert(player.getPlayer().getId(), player);
        }
    }

    /**
     * Method used to create a tennis player if is not already inside the container, otherwise it returns the
     * tennis player already in the container.
     * @param id id of the tennis player
     * @return the node holding the tennis player
     */
    public CustomBSTNode createPlayerIfNeeded(String id) {
        CustomBSTNode nodePlayer1;
        if (!search(id)) {
            TennisPlayer player = new TennisPlayer(id,
                    "", "", 0, "");
            player.setDummy(true);
            try {
                TennisPlayerNode playerNode = new TennisPlayerNode(player);
                insertPlayer(playerNode);
            } catch (TennisDatabaseRuntimeException ex) {
                System.out.println("Problem inserting player One.");
                player.print();
            }
            nodePlayer1 = getPlayerNode(id);
        } else {
            nodePlayer1 = getPlayerNode(id);
        }
        return nodePlayer1;
    }

    /**
     * Method to insert a player into the container/list accordingly.
     *
     * @param player Tennis player to insert into the container
     * @throws TennisDatabaseRuntimeException Exception thrown if the insertion goes wrong
     */
    @Override
    public void insertPlayer(TennisPlayer player) throws TennisDatabaseRuntimeException {
        // method now used is insert(key, playerNode)
    }

    /**
     * Method to insert a match into the list.
     *
     * @param match tennis match to insert
     * @throws TennisDatabaseRuntimeException Exception thrown if insertion goes wrong
     */
    public void insertMatch(TennisMatch match) throws TennisDatabaseRuntimeException {
        CustomBSTNode nodePlayer1 = createPlayerIfNeeded(match.getPlayer1Id());

        if (nodePlayer1 != null) {
            nodePlayer1.getData().insertMatch(match);
        }

        CustomBSTNode nodePlayer2 = createPlayerIfNeeded(match.getPlayer2Id());

        if (nodePlayer2 != null) {
            nodePlayer2.getData().insertMatch(match);
        }
    }

    // This method mainly calls deleteRec()
    public boolean deleteKey(String key)
    {
        if(search(key)) {
            this.root = deleteRec(this.root, key);
            return true;
        } else {
            return false;
        }
    }

    /* A recursive function to insert a new key in BST */
    private CustomBSTNode deleteRec(CustomBSTNode currRoot, String key)
    {
        /* Base Case: If the tree is empty */
        if (currRoot == null){
            return null;
        }

        /* Otherwise, recur down the tree */
        if (key.compareTo(currRoot.getID()) < 0) {
            currRoot.setLeftChild(deleteRec(currRoot.getLeftChild(), key));
        }
        else if (key.compareTo(currRoot.getID()) > 0) {
            currRoot.setRightChild(deleteRec(currRoot.getRightChild(), key));
        }
        // if key is same as root's key, then This is the node
        // to be deleted
        else
        {
            // node with only one child or no child
            if(!currRoot.hasLeftChild()) {
                return currRoot.getRightChild();
            } else if (!currRoot.hasRightChild()) {
                return currRoot.getLeftChild();
            }
            // node with two children: Get the inorder successor (smallest
            // in the right subtree)
            currRoot.setKey(minValue(currRoot.getRightChild()));

            // Delete the inorder successor
            currRoot.setRightChild(deleteRec(currRoot.getRightChild(), currRoot.getID()));
        }

        return currRoot;
    }

    /**
     * Method used to return the inorder successor of the currRoot in the tree
     * @param currRoot the current node in the tree
     * @return the id of the player
     */
    private String minValue(CustomBSTNode currRoot)
    {
        String minValue = currRoot.getID();
        while (currRoot.hasLeftChild())
        {
            minValue = currRoot.getLeftChild().getID();
            currRoot = currRoot.getLeftChild();
        }
        return minValue;
    }

    /**
     * Method used to find the right most node of the tree.
     * @param currRoot the node searching from
     * @return the right most node
     */
    private CustomBSTNode findRightMost( CustomBSTNode currRoot) {
        if( currRoot == null) {
            return null;
        } else {
            if(currRoot.hasRightChild()) {
                return findRightMost(currRoot.getRightChild());
            } else {
                return currRoot;
            }
        }
    }

    /**
     * Method used to find the left most node of the tree.
     * @param currRoot the node searching from
     * @return the left most node
     */
    private CustomBSTNode findLeftMost( CustomBSTNode currRoot) {
        if( currRoot == null) {
            return null;
        } else {
            if(currRoot.hasLeftChild()) {
                return findLeftMost(currRoot.getLeftChild());
            } else {
                return currRoot;
            }
        }
    }

    /**
     * Method to get the player node of a given id
     * <p>
     * calls the recursive method to find the player
     *
     * @param key the id of the player to grab
     * @return the node or null if not found
     */
    public CustomBSTNode getPlayerNode(String key) {
        return getPlayerRec(key, this.root);
    }

    /**
     * Method used to recursively find the node holding the tennis player.
     * @param id the id of the player
     * @param currRoot the root to search from to find the node
     * @return the node containing the player id
     */
    private CustomBSTNode getPlayerRec(String id, CustomBSTNode currRoot) {
        if(currRoot == null) {
            return null;
        } else {
            if(currRoot.getID().equals(id)) {
                return currRoot;
            } else if (currRoot.getID().compareTo(id) < 0) {
                return getPlayerRec(id, currRoot.getRightChild());
            } else {
                return getPlayerRec(id, currRoot.getLeftChild());
            }
        }
    }

    /**
     * Calls the recursive method.
     * @param id the id to search for
     * @return true or false if the id is in the search tree
     */
    public boolean search(String id) {
        return searchRec( id, this.root);
    }

    /**
     * Method used to recursively look for the player in the tree.
     * @param id the id of the tennis player
     * @param currRoot the node to search from
     * @return true or false if the is is in the search tree
     */
    private boolean searchRec(String id, CustomBSTNode currRoot) {
        if (currRoot == null) {
            return false;
        } else {
            if(currRoot.getID().equals(id)) {
                return true;
            } else if (currRoot.getID().compareTo(id) < 0) {
                return searchRec(id, currRoot.getRightChild());
            } else {
                return searchRec(id, currRoot.getLeftChild());
            }
        }
    }

    // Wrappers over above recursive functions
    public void printPostorder()  {     printRecPostorder(this.root);  }

    /* Given a binary tree, print its nodes according to the
      "bottom-up" postorder traversal. */
    private void printRecPostorder(CustomBSTNode node)
    {
        if (node == null) {
            return;
        }
        // first recur on left subtree
        printRecPostorder(node.getLeftChild());

        // then recur on right subtree
        printRecPostorder(node.getRightChild());

        // now deal with the node
        node.getData().getPlayer().print();
    }

    /**
     * Method used to call for the insertion fo the search tree info into the array list.
     */
    public void putInArrayList()    {
        if(this.root != null) {
            putInArrayListRec(this.root);
        }
    }

    /* Given a binary tree, print its nodes in inorder*/
    private void putInArrayListRec(CustomBSTNode node)
    {
        if (node == null) {
            return;
        }

        /* first recur on left child */
        putInArrayListRec(node.getLeftChild());

        /* then print the data of node */
        playersArray.add(node.getData().getPlayer());

        /* now recur on right child */
        putInArrayListRec((node.getRightChild()));
    }

    /**
     * Calls for the inorder output of the container
     */
    void printInorder()    {
        if(this.root == null) {
            System.out.println("No Tennis Players to Print.");
        } else {
            printRecInorder(this.root);
        }
    }

    /* Given a binary tree, print its nodes in inorder*/
    private void printRecInorder(CustomBSTNode node)
    {
        if (node == null) {
            return;
        }

        /* first recur on left child */
        printRecInorder(node.getLeftChild());

        /* then print the data of node */
        node.getData().getPlayer().print();

        /* now recur on right child */
        printRecInorder(node.getRightChild());
    }

    /**
     * Call for the recursive printing of the container.
     */
    void printPreorder()   {     printRecPreorder(this.root);  }

    /* Given a binary tree, print its nodes in preorder*/
    private void printRecPreorder(CustomBSTNode node)
    {
        if (node == null) {
            return;
        }

        /* first print data of node */
        node.getData().getPlayer().print();

        /* then recur on left subtree */
        printRecPreorder(node.getLeftChild());

        /* now recur on right subtree */
        printRecPreorder(node.getRightChild());
    }

    /**
     * Method used to iterate through the container and output the information in every node.
     *
     * @throws TennisDatabaseRuntimeException Exception thrown if there is an error outputting node information
     */
    @Override
    public void printAllPlayers() throws TennisDatabaseRuntimeException {
        if (playersArray.size() == 0) {
            System.out.println("No players to print");
        } else {
            for (TennisPlayer player : playersArray) {
                player.print();
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
        CustomBSTNode player = getPlayerNode(playerId);

        if (player != null) {
            player.getData().printMatches();
        } else {
            try {
                throw new TennisDatabaseRuntimeException("There does not exist a player with the selected Id " + playerId);
            } catch (TennisDatabaseRuntimeException ex) {
                System.out.println("No player with selected id, continuing.");
            }
        }
    }
}
