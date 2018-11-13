import java.util.ArrayList;

/**
 * 状态机
 */
public class DFA {
    private ArrayList<DFAState> states;

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
//System.out.println(I0.getItemSetSize());
         for(int i=0;i<I0.getItemSetSize();i++){
System.out.println(I0.getItemSetSize());
             Item item = I0.getItem(i);
             //.不在推导式的最后
             if(item.getPointIndex()<item.getDSize()){
                 String strAfterPoint = item.getD().getNItem(item.getPointIndex());//B
                 String beta = null;
                 if(item.getPointIndex() == item.getDSize()-1)//when beta = null
                     beta = item.getPredictiveSymbol(); //beta a = a ,first(beta a) = first(a)
                 else
                     //when bata != null;
                     //beta = item.getD().getNItem(item.getPointIndex()+1);//beta = item after B,first(beta a)= first(beta)
                     beta = item.getStrAfterB();
                 System.out.println(strAfterPoint+" "+beta);

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


//        for(String jump:I0.getJumpPath()){
//                addState(0,jump,I0.getJumpableItems(jump));
//        }

    }

    private void addState(int previousState,String jump,ArrayList<Item> core){

    }


}
