import java.util.ArrayList;

//项集
public class DFAState {
    private int id;
    private ArrayList<Item>  ItemSet;

    public DFAState(int id){
        this.id = id;
        ItemSet = new ArrayList<Item>();
    }



}
