import java.util.ArrayList;
public class Tracker {
    //variable
    private Book book;
    private ArrayList<Material> inventory;

    //constructor

    public Tracker(){
        this.book = new Book();
        this.inventory = new ArrayList<Material>();
    }

    //method

    public void addGoal(Item item){
        book.addItem(item);
    }

    public void removeGoal(String itemName){
        for(int i = 0; i < book.getItemCount(); i++){
            if(book.getItem(i).getName().equals(itemName)){
                book.deleteItem(i);
                return;
            }
        }
    }

    public void addOwnedMaterial(Material material){
        inventory.add(material);
    }

    public void updateOwnedMaterial(String materialName, int count){
        for(int i = 0; i < inventory.size(); i++){
            if(inventory.get(i).getName().equals(materialName)){
                inventory.get(i).setCount(count);
                return;
            }
        }
    }

    public Material getOwnedMaterial(String materialName){
        for(int i = 0; i < inventory.size(); i++){
            if(inventory.get(i).getName().equals(materialName)){
                return inventory.get(i);
            }
        }
        return null;
    }

    public ArrayList<Material> getOwnedMaterials(){
        return inventory;
    }

    public ArrayList<Material> calculateMissingForItem(String itemName){
        for(int i = 0; i < book.getItemCount(); i++){
            if(book.getItem(i).getName().equals(itemName)){
                Item item = book.getItem(i);
                ArrayList<Material> missingMaterials = new ArrayList<Material>();
                for(int j = 0; j < item.getTotalNeeded().size(); j++){
                    Material neededMaterial = item.getTotalNeeded().get(j);
                    Material ownedMaterial = getOwnedMaterial(neededMaterial.getName());
                    if(ownedMaterial == null || !ownedMaterial.hasEnough(neededMaterial.getCount())){
                        int missingAmount = neededMaterial.getMissingAmount(ownedMaterial == null ? 0 : ownedMaterial.getCount());
                        Material missingMaterial = new Material(neededMaterial.getName());
                        missingMaterial.setCount(missingAmount);
                        missingMaterials.add(missingMaterial);
                    }
                }
                return missingMaterials;
            }
        }
        return null;
    }

    public ArrayList<Material> calculateTotalMissing(){
        ArrayList<Material> totalMissing = new ArrayList<Material>();
        for(int i = 0; i < book.getItemCount(); i++){
            Item item = book.getItem(i);
            for(int j = 0; j < item.getTotalNeeded().size(); j++){
                Material neededMaterial = item.getTotalNeeded().get(j);
                Material ownedMaterial = getOwnedMaterial(neededMaterial.getName());
                if(ownedMaterial == null || !ownedMaterial.hasEnough(neededMaterial.getCount())){
                    int missingAmount = neededMaterial.getMissingAmount(ownedMaterial == null ? 0 : ownedMaterial.getCount());
                    boolean found = false;
                    for(int k = 0; k < totalMissing.size(); k++){
                        if(totalMissing.get(k).getName().equals(neededMaterial.getName())){
                            totalMissing.get(k).addCount(missingAmount);
                            found = true;
                            break;
                        }
                    }
                    if(!found){
                        Material missingMaterial = new Material(neededMaterial.getName());
                        missingMaterial.setCount(missingAmount);
                        totalMissing.add(missingMaterial);
                    }
                }
            }
        }
        return totalMissing;
    }

    public void markItemComplete(String itemName){
        for(int i = 0; i < book.getItemCount(); i++){
            if(book.getItem(i).getName().equals(itemName)){
                book.getItem(i).setComplete(true);
                return;
            }
        }
    }

    public void clearAllProgress(){
        for(int i = 0; i < book.getItemCount(); i++){
            book.getItem(i).setComplete(false);
        }
    }
}
