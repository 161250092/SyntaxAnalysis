import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;

/**
 * 上下文无关文法
 */
public class CFG {

    //非终结符
    public static TreeSet<String>  vn = new TreeSet<String>();

    //终结符
    public static TreeSet<String>  vt = new TreeSet<String>();

    //production 产生式
    public static ArrayList<Derivation> p = new ArrayList<Derivation>();


    //first集
    public static HashMap<String,TreeSet<String>>  firstSet = new HashMap<String,TreeSet<String>>();

    static {
        initializeDerivations("src/IOFile/cfg.txt");
//      /Users/apple/IdeaProjects/SyntaxAnalysis/src/IOFIle/cfg.txt
//        S'->S
//        S->if ( B ) S ;|if ( B ) S ; else S ;|id = E|S ; S
//        B->B >= B|num|id
//        E->E + E|E - E|num|id

        vn.add("S");
        vn.add("S'");
        vn.add("B");
        vn.add("E");

        vt.add("if");vt.add("else");vt.add(";");vt.add("=");vt.add(">=");
        vt.add("num");vt.add("id");vt.add("-");vt.add("+");
        vt.add("(");vt.add(")");

        getFirstSet();
    }





    private static void initializeDerivations(String fileName){
        File file = new File(fileName);
        try {
            Scanner  scanner = new Scanner(file);
            while(scanner.hasNext()){
                String line = scanner.nextLine();
                String[] twoSide = line.split("->");

                // | 作为分隔符时需要使用转义符
                String[] right = twoSide[1].split("\\|");
                for(String r:right){
                    Derivation d = new Derivation(twoSide[0],r);
                    p.add(d);
                }
            }

            scanner.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }


    private  static void getFirstSet(){
        /**
         * 终结符的first集就是自身
         * 非终结符的first集是其推导得到的串的首符号的集合；
         */

        for (String t : vt) {
            firstSet.put(t, new TreeSet<String>());
            firstSet.get(t).add(t);
        }


        for (String nt : vn) {
            firstSet.put(nt, new TreeSet<String>());

            //int productionKinds = p.size();
            for (Derivation d : p) {
                if (d.getLeft().equals(nt)) {
                    //右式第一个是终结符
                    if (vt.contains(d.getFirstItem()))
                        firstSet.get(nt).add(d.getFirstItem());
                    else
                    //右时第一个是非终结符
                        firstSet.get(nt).addAll(findFirst(d.getFirstItem()));
                }
            }//遍历所有产生式

        }//遍历所有非终结符

    }


    private  static TreeSet<String>  findFirst(String vn){
        TreeSet<String> set = new TreeSet<String>();

        for(Derivation d:p){
            if(d.getLeft().equals(vn)){
                //右式第一个是终结符
                if(vt.contains(d.getFirstItem()))
                    set.add(d.getFirstItem());
                else
                {
                    if(!vn.equals(d.getFirstItem()))//避免左递归
                        set.addAll(findFirst(d.getFirstItem()));
                }

            }

        }
        return set;
    }


    //获取非终结符vn的推导
    public static ArrayList<Derivation>  getDerivation(String vn){
        ArrayList<Derivation>  res = new ArrayList<Derivation>();
        for(Derivation d:p){
            if(d.getLeft().equals((vn))){
                res.add(d);
            }
        }
        return res;
    }

    //获取一个文法串的FIRST SET，即求FIRST(beta a)

    /**
     * v为终结符或者非终结符，而非一个文法串
     * @param v
     * @return
     */
    public static ArrayList<String> getFirstSet(String v){
        ArrayList<String> res = new ArrayList<String>();

        if(v.equals("$"))
            res.add("$");
        else
            res.addAll(firstSet.get(v));

        return res;
    }

//根据产生式求产生式的编号
    public  static int  getGammaIndex(Derivation reduceD){
        int res = -1;
        for(int i=0;i<p.size();i++){
            if(reduceD.isSame(p.get(i)))
                return i;
        }
        return -1;
    }

}





