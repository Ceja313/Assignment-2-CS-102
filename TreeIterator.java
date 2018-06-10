
import java.util.LinkedList;

/**
 * Iterator class for the ADT binary tree.
 * @author From Course Materials Given
 */

public class TreeIterator implements java.util.Iterator {
    private TennisPlayersContainer binTree; // The binary tree used.
    private CustomBSTNode currNode; // Current node in the traversal.
    private LinkedList< CustomBSTNode > queue; // Queue for traversal sequence (see JCF).
    // Constructor.
    public TreeIterator( TennisPlayersContainer bt ) {
        binTree = bt;
        currNode = null;
    // Empty queue means no traversal type selected, or end of current traversal.
        queue = new LinkedList<CustomBSTNode>  ();
    }
    // JCF Iterator interface required methods.
    public boolean hasNext() { return !queue.isEmpty(); }
    public TennisPlayerNode next() throws java.util.NoSuchElementException {
        currNode = queue.remove();
        return currNode.getData();
    }
    public void remove() throws UnsupportedOperationException {
        throw new UnsupportedOperationException();
    }

    // Traversal methods (preorder).
    public void setPreorder() {
        queue.clear();
        preorder( binTree.getRoot() );
    }
    private void preorder( CustomBSTNode treeNode ) {
        if( treeNode != null ) {
            queue.add( treeNode );
            preorder( treeNode.getLeftChild() );
            preorder( treeNode.getRightChild() ); }
    }

    // Traversal methods (inorder).
    public void setInorder() {
        queue.clear();
        inorder( binTree.getRoot() );
    }
    private void inorder( CustomBSTNode treeNode ){
        if( treeNode != null ){
            inorder( treeNode.getLeftChild() );
            queue.add( treeNode );
            inorder( treeNode.getRightChild());}
    }

    // Traversal methods (postorder).
    public void setPostorder() {
        queue.clear();
        postorder( binTree.getRoot() );
    }
    private void postorder( CustomBSTNode treeNode ){
        if( treeNode != null ){
            postorder( treeNode.getLeftChild() );
            postorder( treeNode.getRightChild());
            queue.add( treeNode ); }
    }
}