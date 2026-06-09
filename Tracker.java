
import java.io.File;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Scanner;


public class Tracker {

    //variable
    private Book book;
    private ArrayList<Material> inventory;

    //constructor
    public Tracker() {
        this.book = new Book();
        this.inventory = new ArrayList<Material>();
    }

    //method
    public Book getBook() {
        return book;
    }

    public void addGoal(Item item) {
        book.addItem(item);
    }

    public void removeGoal(String itemName) {
        for (int i = 0; i < book.getItemCount(); i++) {
            if (book.getItem(i).getName().equals(itemName)) {
                book.deleteItem(i);
                return;
            }
        }
    }

    public void addOwnedMaterial(Material material) {
        inventory.add(material);
    }

    public void updateOwnedMaterial(String materialName, int count) {
        for (int i = 0; i < inventory.size(); i++) {
            if (inventory.get(i).getName().equals(materialName)) {
                inventory.get(i).setCount(count);
                return;
            }
        }
    }

    public Material getOwnedMaterial(String materialName) {
        for (int i = 0; i < inventory.size(); i++) {
            if (inventory.get(i).getName().equals(materialName)) {
                return inventory.get(i);
            }
        }
        return null;
    }

    public ArrayList<Material> getOwnedMaterials() {
        return inventory;
    }

    public ArrayList<Material> calculateMissingForItem(String itemName) {
        for (int i = 0; i < book.getItemCount(); i++) {
            if (book.getItem(i).getName().equals(itemName)) {
                Item item = book.getItem(i);
                ArrayList<Material> missingMaterials = new ArrayList<Material>();
                for (int j = 0; j < item.getTotalNeeded().size(); j++) {
                    Material neededMaterial = item.getTotalNeeded().get(j);
                    Material ownedMaterial = getOwnedMaterial(neededMaterial.getName());
                    if (ownedMaterial == null || !ownedMaterial.hasEnough(neededMaterial.getCount())) {
                        int ownedCount = 0;

                        if (ownedMaterial != null) {
                            ownedCount = ownedMaterial.getCount();
                        }

                        int missingAmount = neededMaterial.getCount() - ownedCount;
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

    public ArrayList<Material> calculateTotalMissing() {
        ArrayList<Material> totalMissing = new ArrayList<Material>();
        ArrayList<Material> totalRequired = new ArrayList<Material>();

        for (int i = 0; i < book.getItemCount(); i++) {
            Item item = book.getItem(i);

            for (int j = 0; j < item.getTotalNeeded().size(); j++) {
                Material neededMaterial = item.getTotalNeeded().get(j);

                boolean found = false;
                for (int k = 0; k < totalRequired.size(); k++) {
                    if (totalRequired.get(k).getName().equals(neededMaterial.getName())) {
                        totalRequired.get(k).addCount(neededMaterial.getCount());
                        found = true;
                        break;
                    }
                }
                if (!found) {
                    Material missingMaterial = new Material(neededMaterial.getName());
                    missingMaterial.setCount(neededMaterial.getCount());
                    totalRequired.add(missingMaterial);
                }

            }
        }

        for (int i = 0; i < totalRequired.size(); i++) {
            Material requiredMaterial = totalRequired.get(i);
            Material ownedMaterial = getOwnedMaterial(requiredMaterial.getName());

            int ownedCount = 0;

            if (ownedMaterial != null) {
                ownedCount = ownedMaterial.getCount();
            }

            int missingAmount = requiredMaterial.getCount() - ownedCount;

            if (missingAmount > 0) {
                Material missingMaterial = new Material(requiredMaterial.getName());
                missingMaterial.setCount(missingAmount);
                totalMissing.add(missingMaterial);
            }
        }

        return totalMissing;
    }

    public void saveData(String fileName) throws Exception {
    PrintWriter out = new PrintWriter(fileName);

    out.println(inventory.size());

    for (int i = 0; i < inventory.size(); i++) {
        out.println(inventory.get(i).getName());
        out.println(inventory.get(i).getCount());
    }

    out.println(book.getItemCount());

    for (int i = 0; i < book.getItemCount(); i++) {
        Item item = book.getItem(i);

        out.println(item.getName());
        out.println(item.getTotalNeeded().size());

        for (int j = 0; j < item.getTotalNeeded().size(); j++) {
            Material material = item.getTotalNeeded().get(j);

            out.println(material.getName());
            out.println(material.getCount());
        }
    }

    out.close();
}

public void loadData(String fileName) throws Exception {
    Scanner in = new Scanner(new File(fileName));

    inventory.clear();
    book.clearBook();

    int inventorySize = Integer.parseInt(in.nextLine());

    for (int i = 0; i < inventorySize; i++) {
        String name = in.nextLine();
        int count = Integer.parseInt(in.nextLine());

        Material material = new Material(name);
        material.setCount(count);
        inventory.add(material);
    }

    int itemCount = Integer.parseInt(in.nextLine());

    for (int i = 0; i < itemCount; i++) {
        String itemName = in.nextLine();
        Item item = new Item(itemName);

        int materialCount = Integer.parseInt(in.nextLine());

        for (int j = 0; j < materialCount; j++) {
            String materialName = in.nextLine();
            int materialAmount = Integer.parseInt(in.nextLine());

            Material material = new Material(materialName);
            material.setCount(materialAmount);

            item.addMaterial(material);
        }

        book.addItem(item);
    }

    in.close();
}

    // public void markItemComplete(String itemName) {
    //     for (int i = 0; i < book.getItemCount(); i++) {
    //         if (book.getItem(i).getName().equals(itemName)) {
    //             book.getItem(i).setComplete(true);
    //             return;
    //         }
    //     }
    // }
    // public void clearAllProgress() {
    //     for (int i = 0; i < book.getItemCount(); i++) {
    //         book.getItem(i).setComplete(false);
    //     }
    // }
    // public ArrayList<Material> getMissingMaterials(){
    //     ArrayList<Material> missing = new ArrayList<Material>();
    //     for(int i = 0; i < list.size(); i++){
    //         if(!list.get(i).hasEnough(list.get(i).getCount())){
    //             missing.add(list.get(i));
    //         }
    //     }
    //     return missing;
    // }
    // public boolean isComplete(){
    //     for(int i = 0; i < list.size(); i++){
    //         if(!list.get(i).hasEnough(list.get(i).getCount())){
    //             return false;
    //         }
    //     }
    //     return true;
    // }
    // public void setComplete(boolean complete){
    //     if(complete){
    //         for(int i = 0; i < list.size(); i++){
    //             list.get(i).setCount(list.get(i).getCount() + list.get(i).getMissingAmount(list.get(i).getCount()));
    //         }
    //     } else {
    //         for(int i = 0; i < list.size(); i++){
    //             list.get(i).setCount(0);
    //         }
    //     }
    // }
}
