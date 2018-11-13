import java.util.ArrayList;

//项集
public class DFAState {
    private int id;
    private ArrayList<Item>  ItemSet;

    public DFAState(int id){
        this.id = id;
        ItemSet = new ArrayList<Item>();
    }


    //该项集是否与另一个相同
    public boolean isSame(DFAState state){

        if(ItemSet.size()!=state.getItemSetSize()) {
            return false;
        }
        for(int i=0;i<ItemSet.size();i++){
            if(!this.getItem(i).isSame(state.getItem(i)))
                return false;
        }

        return true;


    }

    public String toString(){
        String result = "";
        for(int i = 0;i < ItemSet.size();i++){
            result =result+ItemSet.get(i).getPointIndex()+ItemSet.get(i).getDSize();
        }
        return result;
    }

    //增加一个不重复的项
    public void addNewItem(Item it){

        if(!isContained(it))
            ItemSet.add(it);

    }

    //项集是否包含该项
     public boolean isContained(Item it){
        for(Item item:ItemSet){
            if(item.isSame(it)) {
                return true;

            }
        }
        return false;
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
                     res.add(it.clone());//不能把it直接加入结果集，因为IT是引用类型，其值可能发生改变。
            }
        }

        return res;
    }

    public void changeStateId(int id){
        this.id =id;
    }

    public int getStateId(){
        return this.id;
    }



}
