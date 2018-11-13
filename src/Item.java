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

    public boolean isSame(Item it){

        if(PointIndex!=it.getPointIndex()) {
           //System.out.println("点不同");
            return false;
        }

        if(!predictiveSymbol.equals(it.predictiveSymbol)) {
           //System.out.println("预测符号不同");
            return false;
        }

        if(!d.isSame(it.getD()) ) {
           //System.out.println("表达式不同");
            return false;
        }

        return true;
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

    public void addIndex(){
        PointIndex++;
    }


    /**
     * 此处额外说明，
     * @return
     */
    public Item clone(){

        return new Item(d,PointIndex,predictiveSymbol);
    }





}
