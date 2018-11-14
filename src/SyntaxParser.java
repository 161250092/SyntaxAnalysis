import sun.tools.jstat.Token;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;

/**
 * 语法分析
 */
public class SyntaxParser {

    //分析表
    PasringTable parsingTable;

    //状态栈
    Stack<Integer> stack;

    //输入的token流
    Queue<TokenItem> tokens;

    //记录RS操作
    ArrayList<String> operations;


    public SyntaxParser(){

        parsingTable = new PasringTable();
        stack = new Stack<Integer>();
        operations = new ArrayList<String>();
        initTokens("src/IOFile/tokenStream.txt");
System.out.println("栈的变化过程: ");
//        System.out.println(tokens.element().type);//队首
//        System.out.println(tokens.remove());//移除队首
        //规约，移入序列
        stack.push(0);
        //operations.add("移入 "+tokens.element().content);

        while( !(stack.peek()==1&&tokens.element().content.equals("$") ) ){
                String nextState = getJumpState(stack.peek(),tokens.element().type);
System.out.println("使用"+nextState);
                if(nextState.equals("x")){
                    System.out.println("语法错误");
                    break;
                }
                else{
                    String[] SnOrRn =new String[2];
                    SnOrRn[0] = nextState.substring(0,1);
                    SnOrRn[1] = nextState.substring(1);
                    if(SnOrRn[0].equals("S")) {//移入
                            int next = Integer.parseInt(SnOrRn[1]);//将入栈的状态
                        System.out.println(next + " pushed into stack");
                            stack.push(next);
                            operations.add("移入"+tokens.element().type);
                            tokens.remove();
                    }
                    else if(SnOrRn[0].equals("R")){//规约
                            int reduceP = Integer.parseInt(SnOrRn[1]);//将规约的式子编号
                            Derivation d = CFG.p.get(reduceP);

                            String A = d.getLeft();
                            int r = d.getRightSize();

                            for(int i=0;i<r;i++)//Sm-r
                                stack.pop();

                            int gotoCol = parsingTable.turnGotoJumpToIndex(A);
                            int next = parsingTable.gotoTable[stack.peek()][gotoCol];
                            stack.push(next);

                            operations.add("规约，使用 "+reduceP+" 产生式");
                    }
                }
                showStack();
        }
        //last deal once again
        if(stack.peek()==1&&tokens.element().content.equals("$"))
            operations.add("接受");
        else
            operations.add("语法错误");
        System.out.println();
        System.out.println("RS动作描述:");
        for(String s:operations)
            System.out.println(s);
    }


    public void initTokens(String fileName){
        tokens =new ArrayDeque<TokenItem>();
        File file = new File(fileName);
        try {
            Scanner scanner = new Scanner(file);
            while(scanner.hasNext()){
                String line = scanner.nextLine();
                String[] threePart = line.split(",");
                TokenItem t = new TokenItem(threePart[0], Integer.parseInt(threePart[1]), threePart[2]);
                tokens.add(t);
            }
            tokens.add(new TokenItem("$",-10,"$"));
            scanner.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    public String getJumpState(int previousState,String jump){
        int actionCol = parsingTable.turnActionJumpToIndex(jump);
        return parsingTable.actionTable[previousState][actionCol];
    }


    public void showStack(){
        System.out.println("now stack is: "+stack);
    }
}
