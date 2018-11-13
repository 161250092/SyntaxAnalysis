import java.util.ArrayList;

/**
 * 状态机
 */
public class DFA {
    private ArrayList<DFAState> states;

    //用以记录
    public ArrayList<Integer> begin = new ArrayList<Integer>();
    public ArrayList<Integer> end = new ArrayList<Integer>();
    public ArrayList<String>  shift = new ArrayList<String>();


    public DFA(){
        states = new ArrayList<DFAState>();
    }

    public ArrayList<DFAState> getDFA(){
        return states;
    }

    //构造DFA
    public void constructDFA(){
        DFAState I0 = new DFAState(0);
        //add S'-> .S ,$ into I0
        I0.addNewItem(new Item(CFG.getDerivation("S'").get(0),0,"$"));
        //I0 external

         for(int i=0;i<I0.getItemSetSize();i++){
             Item item = I0.getItem(i);
             //.不在推导式的最后
             if(item.getPointIndex()<item.getDSize()){
                 String strAfterPoint = item.getD().getNItem(item.getPointIndex());//B
                 String beta = null;
                 if(item.getPointIndex() == item.getDSize()-1)//when beta = null
                     beta = item.getPredictiveSymbol(); //beta a = a ,first(beta a) = first(a)
                 else
                     //when bata != null;
                     //beta = item.getD().getNItem(item.getPointIndex()+1);
                     // beta = item after B,first(beta a)= first(beta)
                     beta = item.getStrAfterB();
// System.out.println(strAfterPoint+" "+beta);

                 //if B is vn,I0 add B -> gamma,first(beta a)
                 if(CFG.vn.contains(strAfterPoint)){
                     ArrayList<String> PredictiveSymbolSet = CFG.getFirstSet(beta);
                     ArrayList<Derivation>  derivationFromB = CFG.getDerivation(strAfterPoint);


                     for(int j=0;j<derivationFromB.size();j++){
                         for(int m=0;m<PredictiveSymbolSet.size();m++){
                            Item it = new Item(derivationFromB.get(j),0,PredictiveSymbolSet.get(m));
                            I0.addNewItem(it);
                         }
                     }

                 }

             }
         }

        //I0 completed and added into DFA,and create other DFAStates by recursion
        states.add(I0);


        for(String jump:I0.getJumpPath()){
                addState(0,jump,I0.getJumpableItems(jump));
        }

        System.out.println("共有"+states.size()+"个项集");

    }

    private void addState(int previousState,String jump,ArrayList<Item> core){
        //构建项集的核
        DFAState temp = new DFAState(-1);
        for(Item it:core){
            it.addIndex();//点全部向右移动1
            temp.addNewItem(it);
        }

        //core extend
        for(int i=0;i<temp.getItemSetSize();i++){
            Item item = temp.getItem(i);
        //    System.out.println(item.getPointIndex()+" "+item.getDSize());
            if(item.getPointIndex()<item.getDSize()){
                String strAfterPoint = item.getD().getNItem(item.getPointIndex());//B
                String beta = null;
                if(item.getPointIndex() == item.getDSize()-1)//when beta = null
                    beta = item.getPredictiveSymbol(); //beta a = a ,first(beta a) = first(a)
                else
                /**
                 * when bata != null;
                 * beta = item.getD().getNItem(item.getPointIndex()+1);//beta = item after B,first(beta a)= first(beta)
                 */
                    beta = item.getStrAfterB();

                if(CFG.vn.contains(strAfterPoint)) {
                    //if B is vn,temp add B -> gamma,first(beta a)
                    ArrayList<String> PredictiveSymbolSet = CFG.getFirstSet(beta);
                    ArrayList<Derivation> derivationFromB = CFG.getDerivation(strAfterPoint);
                    for(int j=0;j<derivationFromB.size();j++) {
                        for (int m = 0; m < PredictiveSymbolSet.size(); m++) {
                            Item it = new Item(derivationFromB.get(j),0,PredictiveSymbolSet.get(m));
                            temp.addNewItem(it);
                        }
                    }
                }
            }
        }
        /**
         * 由核扩展的项集中的项推导结束，需要验证该项集是否已经存在
         * 如果该项集已经存在则终止递归
         */

        for(DFAState dfsState:states){
            if(temp.isSame(dfsState)) {
                this.recordShift(previousState,dfsState.getStateId(),jump);
                return;
            }
        }

        /**
         * 如果不存在该项集，则ID赋值为当前DFAstate.size()，并添加进DFA中
         */
        temp.changeStateId(states.size());
        states.add(temp);
        this.recordShift(previousState,temp.getStateId(),jump);

        for(String nextJump:temp.getJumpPath()){
            addState(temp.getStateId(),nextJump,temp.getJumpableItems(nextJump));
        }

    }

    //记录状态跳转
    public void recordShift(int begin,int end,String jump){
        this.begin.add(begin);
        this.end.add(end);
        this.shift.add(jump);
    }


    public  ArrayList<DFAState> getAllDFAStates(){
        return this.states;
    }

    public int getStateNum(){
        return states.size();
    }

}
