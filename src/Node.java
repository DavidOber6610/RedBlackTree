public class Node {
    private int value;
    private Node parent;
    private Node left;
    private Node right;
    private boolean red;
    public Node(int _value){
        this.value = _value;
        this.parent = null;
        this.left = new NilNode(this);
        this.right = new NilNode(this);
        this.red = true;
    }
    public Node(Node parent,boolean temp){
        this.parent = parent;
        this.left = null;
        this.right = null;
        this.red = true;
    }

    public Node getParent() {
        return parent;
    }


    public int getValue() {
        return value;
    }


    public Node getLeft() {
        return left;
    }

    public Node getRight() {
        return right;
    }

    public boolean isRed() {
        return red;
    }

    public void setParent(Node parent) {
        this.parent = parent;
    }

    public void setLeft(Node left) {
        this.left = left;
    }

    public void setRight(Node right) {
        this.right = right;
    }

    public void setRed(boolean red) {
        this.red = red;
    }
}
