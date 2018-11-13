import java.util.ArrayList;

//项集
public class DFAState {
    private int id;
    private ArrayList<Item>  ItemSet;

    public DFAState(int id){
        this.id = id;
        ItemSet = new ArrayList<Item>();
    }

    public void addNewItem(Item it){
        if(!ItemSet.contains(it))
             ItemSet.add(it);
    }

    public int getItemSetSize(){
        return ItemSet.size();
    }

    public Item getItem(int n){
        return ItemSet.get(n);
    }

    //获取项集中 . 后面的终结符和非终结符
    public ArrayList<String> getJumpPath(){
        ArrayList<String> res = new ArrayList<String>();
        for(Item it:ItemSet){
            if(it.getPointIndex()==it.getDSize())
                continue;
            String strAfterPoint = it.getStrAfterPoint();
            if(!res.contains(strAfterPoint))
                res.add(strAfterPoint);
        }

        return res;
    }


    //获取项集中 .后是jump的项
    public ArrayList<Item>  getJumpableItems(String jump){
        ArrayList<Item> res = new ArrayList<Item>();
        for(Item it:ItemSet){
            if(it.getPointIndex()!=it.getDSize() ){
                 String strAfterPoint = it.getStrAfterPoint();
                 if(strAfterPoint.equals(jump))
                     res.add(it);
            }
        }

        return res;
    }
}
