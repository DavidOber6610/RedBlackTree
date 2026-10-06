public class RedBlackTree {
    private Node root;
    public RedBlackTree(){

        root = null;
    }




    public void remove(int _value){
        if (root == null) {
            System.out.println("No values there");
            return;
        }
        Node node =get(_value,root);
        remove(node);
    }
    private void remove(Node node) {
        if (node == null || node instanceof NilNode) {
            return;
        }

        Node replacing;
        Node child;
        boolean wasRed;

        // Two children
        if (!(node.getLeft() instanceof NilNode) &&
                !(node.getRight() instanceof NilNode)) {
            replacing = getBiggestChild(node.getLeft());
            wasRed = replacing.isRed();
            child = replacing.getLeft();
            Node parent = replacing.getParent();

            // Predecessor is not the direct left child
            if (parent != node) {
                parent.setRight(child);
                child.setParent(parent);

                replacing.setLeft(node.getLeft());
                node.getLeft().setParent(replacing);
            } else {
                child.setParent(replacing);

            }

            replacing.setRight(node.getRight());
            node.getRight().setParent(replacing);

            replacing.setParent(node.getParent());
            replacing.setRed(node.isRed());

            if (node.getParent() == null) {
                root = replacing;
            } else if (node == node.getParent().getLeft()) {
                node.getParent().setLeft(replacing);
            } else {
                node.getParent().setRight(replacing);
            }

        } else {
            // Zero or one child
            replacing = node.getLeft() instanceof NilNode
                    ? node.getRight()
                    : node.getLeft();

            wasRed = node.isRed();
            child = replacing;

            if (node.getParent() == null) {
                root = replacing;
            } else if (node == node.getParent().getLeft()) {
                node.getParent().setLeft(replacing);
            } else {
                node.getParent().setRight(replacing);
            }

            replacing.setParent(node.getParent());
        }
        System.out.println("Removing " + node.getValue() +
                ", replacement " + child.getValue());
        if (!wasRed) {
            if (child.isRed()) {
                child.setRed(false);
            } else {
                fixRemove(child);
            }
        }
    }
    private void fixRemove(Node node){

        if (node == root) {
            root.setRed(false);
            return;
        }
        Node sibling = getSibling(node);
        /*if (sibling instanceof NilNode) {
            fixRemove(node.getParent());
            return;
        }*/
        if (sibling.isRed()) {
            fixRedSibling(node, sibling);
            sibling = getSibling(node);
        }
        if (!sibling.getLeft().isRed() && !sibling.getRight().isRed()) {
            if (!(sibling instanceof NilNode)) {
                sibling.setRed(true);
            }
            if (node.getParent().isRed()) {
                node.getParent().setRed(false);
            }

            else {
    fixRemove(node.getParent());}
        }

        else {
            fixOneRedChild(node, sibling);
        }
    }


   


    // helper funktions
    
    private void fixRedSibling(Node node, Node sibling) {
            sibling.setRed(false);
            node.getParent().setRed(true);
            if (node == node.getParent().getLeft()) {
                rodateLeft(node.getParent());
            } else {
                rodateRight(node.getParent());
            }

    }

    private void fixOneRedChild(Node node, Node sibling) {
        if (sibling == null) {
            return;
        }
        boolean nodeIsLeftChild = node == node.getParent().getLeft();
        if (nodeIsLeftChild && !sibling.getLeft().isRed()) {
            sibling.getLeft().setRed(false);
            sibling.setRed(true);
            rodateRight(sibling);
            sibling = node.getParent().getRight();
        } else if (!nodeIsLeftChild && !sibling.getRight().isRed()) {
            sibling.getRight().setRed(false);
            sibling.setRed(true);
            rodateLeft(sibling);
            sibling = node.getParent().getLeft();
        }
        sibling.setRed(node.getParent().isRed());
        node.getParent().setRed(false);
        if (nodeIsLeftChild) {
            sibling.getRight().setRed(false);
            rodateLeft(node.getParent());
        } else {
            sibling.getLeft().setRed(false);
            rodateRight(node.getParent());
        }
    }
    private Node getUncle(Node node){
        Node parent = node.getParent();
        if(parent == parent.getParent().getLeft()){
            return parent.getParent().getRight();
        }else{
            return parent.getParent().getLeft();
        }
    }

    private Node getBiggestChild(Node node){
        if (node == null) {
            return null;
        }
        if (node.getRight() instanceof NilNode) {
            return node;
        }
        return getBiggestChild(node.getRight());
    }
    private Node getSibling(Node node){
        if (node == node.getParent().getLeft()) {
            return node.getParent().getRight();
        }else{
            return node.getParent().getLeft();
        }
    }
    // add funktions
    public void add(int _value){
        if (root == null){
            root = new Node(_value);
            root.setRed(false);
            return;
        }
        add(_value, root);

    }
    private void add(int _value,Node node){
        Node newNode = new Node(_value);
        if (node.getValue() > _value) {
            if(node.getLeft() instanceof NilNode){
                newNode.setParent(node);
                node.setLeft(newNode);
            }else{
                add(_value,node.getLeft());
                return;
            }
        }else if (node.getValue() < _value) {
            if (node.getRight() instanceof NilNode) {
                newNode.setParent(node);
                node.setRight(newNode);
            } else {
                add(_value, node.getRight());
                return;
            }
        }
        fixAdd(newNode);
    }
    private void fixAdd(Node node){
        Node parent = node.getParent();
        if (parent == null) {
            node.setRed(false);
            return;
        }
        if (!parent.isRed()) {
            return;
        }
        Node grandparent = parent.getParent();
        if (grandparent == null) {
            parent.setRed(false);
            return;
        }
        Node uncle = getUncle(node);

        if (uncle != null && uncle.isRed()) {
            parent.setRed(false);
            grandparent.setRed(true);
            uncle.setRed(false);
            fixAdd(grandparent);
        }
        else if (parent == grandparent.getLeft()) {
            if (node == parent.getRight()) {
                rodateLeft(parent);
                parent = node;
            }
            rodateRight(grandparent);
            parent.setRed(false);
            grandparent.setRed(true);
        }
        else {
            if (node == parent.getLeft()) {
                rodateRight(parent);
                parent = node;
            }
            rodateLeft(grandparent);
            parent.setRed(false);
            grandparent.setRed(true);
        }

    }

    // Rotations partially what makes a tree that eficient!
    private void rodateLeft(Node _root){
        Node futurRoot = _root.getRight();
        if (futurRoot instanceof NilNode) {
            return;
        }
        _root.setRight(futurRoot.getLeft());
        if(futurRoot.getLeft() != null){
            futurRoot.getLeft().setParent(_root);
        }
        futurRoot.setParent(_root.getParent());
        if (_root.getParent() == null){
            this.root = futurRoot;
        } else if (_root == _root.getParent().getLeft()) {
            _root.getParent().setLeft(futurRoot);
        }else{
            _root.getParent().setRight(futurRoot);
        }
        futurRoot.setLeft(_root);
        _root.setParent(futurRoot);
    }

    private void rodateRight(Node _root){
        Node futurRoot = _root.getLeft();
        if (futurRoot instanceof NilNode) {
            return;
        }
        _root.setLeft(futurRoot.getRight());
        if(futurRoot.getRight() != null){
            futurRoot.getRight().setParent(_root);
        }
        futurRoot.setParent(_root.getParent());
        if (_root.getParent() == null){
            this.root = futurRoot;
        } else if (_root == _root.getParent().getLeft()) {
            _root.getParent().setLeft(futurRoot);
        }else{
            _root.getParent().setRight(futurRoot);
        }
        futurRoot.setRight(_root);
        _root.setParent(futurRoot);
    }
    
    // searching the thing that SHULD be optimised in a tree
    public boolean contains(int _value){
        return !(get(_value,root) == null);
    }
    private Node get(int _value, Node node){
        if (node instanceof NilNode){
            return null;
        }
        if (node.getValue() == _value){return node;}
        else if (node.getValue() > _value){
            return get(_value,node.getLeft());
        }else{
            return get(_value,node.getRight());
        }

    }

    // print funktions (basicly useless for me 2 of them at least ) also not interesting the same for all trees
    public String preOrder(){
        return preOrder(root).trim();
    }
    private String preOrder(Node node){
        if (node instanceof NilNode){
            return "";
        }
        String temp = preOrder(node.getLeft()) +" ";
        if (temp.equals(" ") ){
            temp = "";
        }
        String temp1 = preOrder(node.getRight());
        String extra = " ";
        return (node.getValue()+extra+temp+ temp1).trim();
    }
    public String inOrder(){
        return inOrder(root).trim();
    }
    private String inOrder(Node node){
        if (node instanceof NilNode){
            return "";
        }
        String temp = inOrder(node.getLeft()) +" ";
        if (temp.equals(" ")){
            temp = "";
        }

        String temp1 = inOrder(node.getRight());
        String extra = " ";
        return (temp+ node.getValue()+ extra+temp1).trim();
    }
    public String postOrder(){
        return postOrder(root).trim();
    }
    private String postOrder(Node node){
        if (node instanceof NilNode){
            return "";
        }
        String temp = postOrder(node.getLeft()) +" ";
        if (temp.equals(" ")){
            temp = "";
        }
        String temp1 = postOrder(node.getRight()) +" ";
        if (temp1.equals(" ")){
            temp1 = "";
        }
        return (temp+ temp1+node.getValue()).trim();
    }



    // unit test
    public boolean unitTest() {
        if (root == null) {
            return true;
        }

        // Root must be black
        if (root.isRed()) {
            return false;
        }

        return unitTest(root) != -1;
    }

    private int unitTest(Node node) {
        if (node instanceof NilNode) {
            return 1;
        }

        if (node.isRed()) {
            if (node.getLeft().isRed() || node.getRight().isRed()) {
                System.out.println("RED-RED violation at " + node.getValue());
                return -1;
            }
        }

        int left = unitTest(node.getLeft());
        int right = unitTest(node.getRight());

        if (left == -1 || right == -1) {
            return -1;
        }

        if (left != right) {
            System.out.println(
                    "BLACK HEIGHT violation at " + node.getValue() +
                            " left=" + left +
                            " right=" + right
            );
            return -1;
        }

        if (node.isRed()) {
            return left;
        }

        return left + 1;
    }
}
