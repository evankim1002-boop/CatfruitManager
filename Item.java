import java.util.ArrayList;

public class Item {
    //variable
    private String name;
    private ArrayList<Material> list;
    private int currentCount;

    //consturcotr
    public Item(String name){
        list = new ArrayList<Material>();
        this.name = name;
        this.currentCount = 0;
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

    public ArrayList<Material> getTotalNeeded(){
        return list;
    }

    public void addMaterial(Material material){
        list.add(material);
    }

    public void removeMaterial(int i){
        if (i < 0 || i >= list.size()) {
            return;
        }
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
}
