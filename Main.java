
import java.util.ArrayList;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        Tracker tracker = new Tracker();

        boolean running = true;

        System.out.println("Welcome to the Battle Cats Catfruit Tracker!");

        while (running) {
            System.out.println("\n===== MENU =====");
            System.out.println("1. Add cat goal");
            System.out.println("2. Add required material to cat");
            System.out.println("3. Add/update owned material");
            System.out.println("4. Show missing materials for one cat");
            System.out.println("5. Show total missing materials");
            System.out.println("6. View inventory");
            System.out.println("7. Exit");
            System.out.print("Choose an option: ");

            int choice = scanner.nextInt();
            scanner.nextLine();

            if (choice == 1) {
                System.out.print("Enter cat name: ");
                String catName = scanner.nextLine();

                Item item = new Item(catName);
                tracker.addGoal(item);

                System.out.println(catName + " added as a goal.");
            } else if (choice == 2) {
                System.out.print("Enter cat name: ");
                String catName = scanner.nextLine();

                Item item = tracker.getBook().getItemByName(catName);

                if (item == null) {
                    System.out.println("That cat does not exist yet.");
                } else {
                    System.out.print("Enter material name: ");
                    String materialName = scanner.nextLine();

                    System.out.print("Enter required amount: ");
                    int amount = scanner.nextInt();
                    scanner.nextLine();

                    Material material = new Material(materialName);
                    material.setCount(amount);

                    item.addMaterial(material);

                    System.out.println("Added requirement: " + amount + " " + materialName);
                }
            } else if (choice == 3) {
                System.out.print("Enter material name: ");
                String materialName = scanner.nextLine();

                System.out.print("Enter owned amount: ");
                int amount = scanner.nextInt();
                scanner.nextLine();

                Material owned = tracker.getOwnedMaterial(materialName);

                if (owned == null) {
                    Material material = new Material(materialName);
                    material.setCount(amount);
                    tracker.addOwnedMaterial(material);
                } else {
                    owned.setCount(amount);
                }

                System.out.println("Inventory updated.");
            } else if (choice == 4) {
                System.out.print("Enter cat name: ");
                String catName = scanner.nextLine();

                ArrayList<Material> missing = tracker.calculateMissingForItem(catName);

                if (missing == null) {
                    System.out.println("That cat does not exist.");
                } else if (missing.size() == 0) {
                    System.out.println("You have enough materials for " + catName + "!");
                } else {
                    System.out.println("Missing materials for " + catName + ":");

                    for (int i = 0; i < missing.size(); i++) {
                        System.out.println(missing.get(i).getName() + ": " + missing.get(i).getCount());
                    }
                }
            } else if (choice == 5) {
                ArrayList<Material> totalMissing = tracker.calculateTotalMissing();

                if (totalMissing.size() == 0) {
                    System.out.println("You have enough materials for all goals!");
                } else {
                    System.out.println("Total missing materials:");

                    for (int i = 0; i < totalMissing.size(); i++) {
                        System.out.println(totalMissing.get(i).getName() + ": " + totalMissing.get(i).getCount());
                    }
                }
            } else if (choice == 6) {
                ArrayList<Material> inventory = tracker.getOwnedMaterials();

                if (inventory.size() == 0) {
                    System.out.println("Your inventory is empty.");
                } else {
                    System.out.println("Your inventory:");

                    for (int i = 0; i < inventory.size(); i++) {
                        System.out.println(inventory.get(i).getName() + ": " + inventory.get(i).getCount());
                    }
                }
            } else if (choice == 7) {
                System.out.println("Exiting the tracker. Goodbye!");
                running = false;
            } else {
                System.out.println("Invalid option.");
            }
        }

        scanner.close();
    }
}
