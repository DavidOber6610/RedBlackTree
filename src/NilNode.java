public class NilNode extends Node{
    public NilNode(Node parent){
        super(parent,true);
        super.setRed(false);
    }
    @Override
    public boolean isRed(){
        return false;
    }
}
