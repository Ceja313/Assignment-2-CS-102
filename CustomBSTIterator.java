import java.util.LinkedList;

public class CustomBSTIterator {

    // Not needed
    CustomBST bst;
    CustomBSTNode currNode;
    LinkedList<CustomBSTNode> queue;

    public CustomBSTIterator(CustomBST bst) {
        this.bst = bst;
        currNode = null;
        queue = new LinkedList<CustomBSTNode>();
    }

    private void inorder(CustomBSTNode currRootNode) {
        if( currRootNode != null) {
            inorder(currRootNode.getLeftChild());
            queue.add(currRootNode);
            inorder(currRootNode.getRightChild());
        }
    }

    public boolean hasNext() { return !queue.isEmpty();}

    public TennisPlayer next() {
        currNode = queue.remove();
        return currNode.getData();
    }
}
