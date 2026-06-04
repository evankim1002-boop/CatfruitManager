import java.util.ArrayList;

public class Item {
    //variable
    private String name;
    private ArrayList<Material> list;
    private int currentCount;
    private int neededCount;
    private int neededMaterial;

    //consturcotr
    public Item(String name){
        this.name = name;
        this.currentCount = 0;
        this.neededCount = 0;
        this.neededMaterial = 0;
    }

    //method

    public String getName(){
        return name;
    }

    public Material getMaterial(int i){
        return list.get(i);
    }

    public int getCurrentCount(){
        return currentCount;
    }

    public void addCurrentCount(int i){
        currentCount+= i;
    }

    public int getNeededCount(){
        return neededCount;
    }

    public void addNeededCount(int i){
        neededCount -s= i;
    }

    public ArrayList<Material> getTotalNeeded(){
        return list;
    }
}
