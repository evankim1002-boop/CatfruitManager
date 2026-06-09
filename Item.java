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

    //getter for name
    public String getName(){
        return name;
    }

//getter for material
    public Material getMaterial(int i){
        return list.get(i);
    }

    //getter for currentCount
    public int getCurrentCount(){
        return currentCount;
    }


    //adding to current count
    public void addCurrentCount(int i){
        currentCount+= i;
    }

    //getter for list
    public ArrayList<Material> getTotalNeeded(){
        return list;
    }

    //adding material to list
    public void addMaterial(Material material){
        list.add(material);
    }

    //removing material from list
    public void removeMaterial(int i){
        if (i < 0 || i >= list.size()) {
            return;
        }
        list.remove(i);
    }

    //getting material by name
    public Material getMaterialByName(String name){
        for(int i = 0; i < list.size(); i++){
            if(list.get(i).getName().equals(name)){
                return list.get(i);
            }
        }
        return null;
    }
}
