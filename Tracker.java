
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

    //getter for book
    public Book getBook() {
        return book;
    }

    //adding goal to the book by using arraylist default function
    public void addGoal(Item item) {
        book.addItem(item);
    }


    //remove goal by using a for loop to find item and then using arraylist function to remove it
    //need to see if it can remove for multiple items with the same name
    public void removeGoal(String itemName) {
        for (int i = 0; i < book.getItemCount(); i++) {
            if (book.getItem(i).getName().equals(itemName)) {
                book.deleteItem(i);
                return;
            }
        }
    }


    //addowned material to inventory. simple
    public void addOwnedMaterial(Material material) {
        inventory.add(material);
    }

    //update owned inventory by using a for loop to loop through inventory and find material and then set to count

    public void updateOwnedMaterial(String materialName, int count) {
        for (int i = 0; i < inventory.size(); i++) {
            if (inventory.get(i).getName().equals(materialName)) {
                inventory.get(i).setCount(count);
                return;
            }
        }
    }


    //gtter for owned material by name uses for loop and then if it is equal to the name then reutrn materila if else return null
    public Material getOwnedMaterial(String materialName) {
        for (int i = 0; i < inventory.size(); i++) {
            if (inventory.get(i).getName().equals(materialName)) {
                return inventory.get(i);
            }
        }
        return null;
    }


    // getter for owned materials and basically just returns inventory
    public ArrayList<Material> getOwnedMaterials() {
        return inventory;
    }


    //this was a pain. so we loop through the book to find the item we want and then thats the first for loop
    //second for the second for loop we use another for loop to loop through the material needed for the item and then 
    //we check if the material and if we do have the material we check if we have enough of it and if we dont we just subtract
    //the amount we have from the amount we need andn voila we have the amount missing for one item
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

    //this is basically same as last one but we just loop through this for the entire book and end up all the things we need
    // and then we subtract the total of all the things we have and this was also a pain becausen i kept getting double count
    //error but it works now.
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


    //i used the "PrintWriter" api to save the data. really cool function.
    public void saveData(String fileName) throws Exception {
    // creates object like how we use scanner
    PrintWriter out = new PrintWriter(fileName);
    // we save the inventory size so we know how much we need to read
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

public void addToInventory(String materialName, int amount) {
    Material owned = getOwnedMaterial(materialName);

    if (owned == null) {
        Material material = new Material(materialName);
        material.setCount(amount);
        inventory.add(material);
    } else {
        owned.addCount(amount);
    }
}



    
}
