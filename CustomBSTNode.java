public class CustomBSTNode {

    // Needed for program
    private String key;
    private TennisPlayerNode data;
    private TennisMatchesList list;

    private CustomBSTNode leftChild;
    private CustomBSTNode rightChild;

    public CustomBSTNode(String k, TennisPlayerNode d) {
        this.key = k;
        this.data = d;
        this.leftChild = null;
        this.rightChild = null;
    }

    public String getKey() {return this.key;}
    public TennisPlayerNode getData() {return this.data;}
    public CustomBSTNode getLeftChild() { return this.leftChild;}
    public CustomBSTNode getRightChild() { return this.rightChild;}

    public void setData(TennisPlayerNode d) { this.data = d;}
    public void setLeftChild(CustomBSTNode lc) {this.leftChild = lc;}
    public void setRightChild(CustomBSTNode rc) {this.rightChild = rc;}

    public boolean hasLeftChild() { return this.leftChild != null;}
    public boolean hasRightChild() { return this.rightChild != null;}
    public boolean isLeaf() {
        return (!hasLeftChild()) && (!hasRightChild());
    }
}
