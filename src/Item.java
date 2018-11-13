//项
public class Item {

    private int PointIndex;
    private String predictiveSymbol;
    private Derivation d;


    public Item(Derivation d, int index, String symbol){
        PointIndex = index;
        predictiveSymbol = symbol;
        this.d = d;
    }


    public int getPointIndex() {
        return PointIndex;
    }

    public String getPredictiveSymbol() {
        return predictiveSymbol;
    }

    public Derivation getD() {
        return d;
    }

    public int getDSize(){
        return d.getRightSize();
    }

    public String getStrAfterPoint(){
        return d.getNItem(PointIndex);
    }

    public String getStrAfterB(){
        return d.getNItem(PointIndex+1);
    }




}
