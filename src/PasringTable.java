//预测分析表
public class PasringTable {

    DFA dfa;

    //状态数
    int stateNum;
    //非终结符数
    int gotoCol;
    //终结符数
    int actionCol;

    //用来存方 action goto 项，类似表头
    String[] actionItems;
    String[] gotoItems;

    // table body
    String[][] actionTable;
    int[][] gotoTable;


    public PasringTable(){
        dfa = new DFA();
        dfa.constructDFA();
// System.out.println(dfa.begin.size()+" "+dfa.shift.size());

        stateNum = dfa.getStateNum();

        gotoCol = CFG.vn.size();
        actionCol = CFG.vt.size()+1;

        this.initHeader();
        this.initTable();

        System.out.println("分析表：");
        System.out.print("state  ");
        for(int i=0;i<actionCol;i++)
            System.out.print(this.actionItems[i]+" ");
        for(int i=0;i<gotoCol-1;i++)
            System.out.print(this.gotoItems[i]+" ");
        System.out.println();



        for(int i=0;i<stateNum;i++){
            System.out.print(i+" ");
            for(int j=0;j<actionCol;j++)
                System.out.print(actionTable[i][j]+" ");
            for(int j=0;j<gotoCol-1;j++)
                System.out.print(gotoTable[i][j]+" ");

            System.out.println();
        }

    }

    public void initHeader(){
        actionItems = new String[actionCol];
        gotoItems = new String[gotoCol];
        int index = 0;
        for(String s:CFG.vt) {
            actionItems[index] =s;
            index++;
        }
        actionItems[index] = "$";
        index = 0;
        for(String s:CFG.vn){
            gotoItems[index] = s;
            index++;
        }
    }

    public void initTable(){


        actionTable = new String[stateNum][actionCol];
        gotoTable = new int[stateNum][gotoCol];


        for(int i=0;i<stateNum;i++){
            for(int j=0;j<actionCol;j++)
                actionTable[i][j]="x";
            for(int j=0;j<gotoCol;j++)
                gotoTable[i][j] =-1;
        }

        //reduce
        for(int i=0;i<stateNum;i++) {
            DFAState state = dfa.getAllDFAStates().get(i);
            for(int j=0;j<state.getItemSetSize();j++){
                //. 在推倒时的最后 进行规约
                if(state.getItem(j).getPointIndex() == state.getItem(j).getDSize()){
                    if(state.getItem(j).getD().getLeft().equals("S'")) {
                        actionTable[i][actionCol-1]="acc";
                    }
                    else {
                        int jumpIndex = turnActionJumpToIndex(state.getItem(j).getPredictiveSymbol());
                        if(jumpIndex ==-1)
                            System.out.print(state.getItem(j).getPredictiveSymbol());

                        actionTable[i][jumpIndex] ="R"+ CFG.getGammaIndex(state.getItem(j).getD());
                    }
                }
            }
        }

        //action goto  shift part in table
        for(int i=0;i<dfa.begin.size();i++){ //i: all jump record 90
            for(int j=0;j<stateNum;j++){//j point stateId
                if(dfa.begin.get(i) == j){
                    if(CFG.vt.contains(dfa.shift.get(i))) {
                        int jumpIndex = turnActionJumpToIndex(dfa.shift.get(i));
                        actionTable[j][jumpIndex] = "S"+dfa.end.get(i);
                    }

                    else if(CFG.vn.contains((dfa.shift.get(i)))){
                        int jumpIndex = turnGotoJumpToIndex(dfa.shift.get(i));
                        gotoTable[j][jumpIndex] = dfa.end.get(i);
                    }

                }
            }
        }



    }


    public int turnActionJumpToIndex(String jump){
        if(jump.equals("$"))
            return actionCol-1;

        for(int i=0;i<actionCol;i++){
            if(jump.equals(actionItems[i]))
                return i;
        }
        return -1;
    }

    public int turnGotoJumpToIndex(String jump){

        for(int i=0;i<gotoCol;i++){
            if(jump.equals(gotoItems[i]))
                return i;
        }
        return -1;
    }


}
