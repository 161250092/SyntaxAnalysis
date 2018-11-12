import java.util.ArrayList;

public class Derivation {
    //NT
    private String left;

    //左侧文法文串
    private ArrayList<String> right = new ArrayList<String>();


    public  Derivation(String left, ArrayList<String> right){
        this.left =  left;
        this.right = right;
    }


}
