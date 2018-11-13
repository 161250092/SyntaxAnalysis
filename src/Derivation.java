import java.util.ArrayList;
import java.util.Arrays;

/**
 * 产生式
 */
public class Derivation {
    //NT
    private String left;

    //左侧文法文串
    private ArrayList<String> right = new ArrayList<String>();


    public  Derivation(String left, ArrayList<String> right){
        this.left =  left;
        this.right = right;
    }

    public Derivation(String left,String right){

       String[] div = right.split(" ");
       this.right.addAll(Arrays.asList(div));
       this.left = left;
    }

    public String getLeft(){
        return left;
    }

    public String getFirstItem(){
        return getNItem(0);
    }

    public  String getNItem(int n){
        return right.get(n);
    }

    public int getRightSize(){
        return right.size();
    }

}
