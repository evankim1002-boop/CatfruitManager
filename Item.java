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
        list = new ArrayList<Material>();
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
        neededCount += i;
    }

    public ArrayList<Material> getTotalNeeded(){
        return list;
    }

    public void addMaterial(Material material){
        list.add(material);
    }

    public void removeMaterial(int i){
        list.remove(i);
    }

    public Material getMaterialByName(String name){
        for(int i = 0; i < list.size(); i++){
            if(list.get(i).getName().equals(name)){
                return list.get(i);
            }
        }
        return null;
    }

    public int getTotalMissingMaterials(){
        int total = 0;
        for(int i = 0; i < list.size(); i++){
            total += list.get(i).getMissingAmount(list.get(i).getCount());
        }
        return total;
    }
}
