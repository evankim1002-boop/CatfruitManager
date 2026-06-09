
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import javax.swing.*;

public class MainGUI {

    private Tracker tracker;

    private JFrame frame;
    private JTextField catNameField;
    private JTextField materialNameField;
    private JTextField requiredAmountField;
    private JTextField ownedMaterialField;
    private JTextField ownedAmountField;
    private JTextArea outputArea;
    private JTextArea catsArea;
    private JTextArea inventoryArea;
    private JTextArea missingArea;

    public MainGUI() {
        tracker = new Tracker();

        frame = new JFrame("Battle Cats Catfruit Tracker");
        frame.setSize(700, 500);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLayout(new BorderLayout());

        JPanel topPanel = new JPanel(new GridLayout(2, 3));

        catNameField = new JTextField();
        JButton addCatButton = new JButton("Add Cat");
        JButton deleteCatButton = new JButton("Delete Cat");

        materialNameField = new JTextField();
        requiredAmountField = new JTextField();
        JButton addRequirementButton = new JButton("Add Requirement");
        JButton editRequirementButton = new JButton("Edit Requirement");

        topPanel.add(new JLabel("Cat Name:"));
        topPanel.add(catNameField);
        topPanel.add(addCatButton);
        topPanel.add(deleteCatButton);
        topPanel.add(new JLabel("Material Required:"));
        topPanel.add(materialNameField);
        topPanel.add(requiredAmountField);

        frame.add(topPanel, BorderLayout.NORTH);

        JPanel middlePanel = new JPanel(new GridLayout(1, 2));

        JPanel inventoryPanel = new JPanel(new GridLayout(3, 2));

        ownedMaterialField = new JTextField();
        ownedAmountField = new JTextField();
        JButton updateInventoryButton = new JButton("Update Inventory");
        JButton addInventoryButton = new JButton("Add Inventory");
        inventoryPanel.add(new JLabel("Owned Material:"));
        inventoryPanel.add(ownedMaterialField);
        inventoryPanel.add(new JLabel("Owned Amount:"));
        inventoryPanel.add(ownedAmountField);
        inventoryPanel.add(updateInventoryButton);
        inventoryPanel.add(addInventoryButton);

        JPanel buttonPanel = new JPanel(new GridLayout(7, 1));

        JButton viewCatsButton = new JButton("View Cats");
        JButton viewMissingButton = new JButton("View Missing For Cat");

        JButton viewTotalMissingButton = new JButton("View Total Missing");
        JButton viewInventoryButton = new JButton("View Inventory");
        JButton saveButton = new JButton("Save");
        JButton loadButton = new JButton("Load");
        JButton clearButton = new JButton("Clear Output");

        buttonPanel.add(viewCatsButton);
        buttonPanel.add(addRequirementButton);
        buttonPanel.add(editRequirementButton);

        buttonPanel.add(viewMissingButton);
        buttonPanel.add(viewTotalMissingButton);
        buttonPanel.add(viewInventoryButton);
        buttonPanel.add(saveButton);
        buttonPanel.add(loadButton);
        buttonPanel.add(clearButton);

        middlePanel.add(inventoryPanel);
        middlePanel.add(buttonPanel);

        frame.add(middlePanel, BorderLayout.CENTER);

        JPanel displayPanel = new JPanel(new GridLayout(1, 3));

        catsArea = new JTextArea();
        inventoryArea = new JTextArea();
        missingArea = new JTextArea();

        catsArea.setEditable(false);
        inventoryArea.setEditable(false);
        missingArea.setEditable(false);

        displayPanel.add(new JScrollPane(catsArea));
        displayPanel.add(new JScrollPane(inventoryArea));
        displayPanel.add(new JScrollPane(missingArea));

        frame.add(displayPanel, BorderLayout.SOUTH);

        addCatButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                addCat();
            }
        });

        viewCatsButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                refreshDisplay();
                outputArea.append("Cats refreshed.\n");
            }
        });

        editRequirementButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                editRequirement();
            }
        });

        addRequirementButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                addRequirement();
            }
        });

        updateInventoryButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                updateInventory();
            }
        });

        addInventoryButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                addInventory();
            }
        });

        deleteCatButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                deleteCat();
            }
        });

        viewMissingButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                viewMissingForCat();
            }
        });

        viewTotalMissingButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                viewTotalMissing();
            }
        });

        viewInventoryButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                viewInventory();
            }
        });

        saveButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                saveData();
            }
        });

        loadButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                loadData();
            }
        });

        clearButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                outputArea.setText("");
            }
        });

        frame.setVisible(true);
    }

    private void editRequirement() {
        String catName = catNameField.getText();
        String materialName = materialNameField.getText();

        if (catName.equals("") || materialName.equals("")) {
            outputArea.append("Enter cat name and material name.\n");
            return;
        }

        int amount;

        try {
            amount = Integer.parseInt(requiredAmountField.getText());
        } catch (NumberFormatException e) {
            outputArea.append("Enter a valid required amount.\n");
            return;
        }

        Item item = tracker.getBook().getItemByName(catName);

        if (item == null) {
            outputArea.append("Cat not found: " + catName + "\n");
            return;
        }

        Material existingMaterial = item.getMaterialByName(materialName);

        if (existingMaterial == null) {
            Material material = new Material(materialName);
            material.setCount(amount);
            item.addMaterial(material);

            outputArea.append("Added new requirement for " + catName + ": " + amount + " " + materialName + "\n");
        } else {
            existingMaterial.setCount(amount);

            outputArea.append("Edited requirement for " + catName + ": " + materialName + " = " + amount + "\n");
        }

        materialNameField.setText("");
        requiredAmountField.setText("");

        refreshDisplay();
    }

    private void addCat() {
        String catName = catNameField.getText();

        if (catName.equals("")) {
            outputArea.append("Enter a cat name.\n");
            return;
        }

        Item item = new Item(catName);
        tracker.addGoal(item);

        outputArea.append("Added cat goal: " + catName + "\n");
        // catNameField.setText("");
    }

    private void addRequirement() {
        String catName = catNameField.getText();
        String materialName = materialNameField.getText();

        if (catName.equals("") || materialName.equals("")) {
            outputArea.append("Enter cat name and material name.\n");
            return;
        }

        int amount;

        try {
            amount = Integer.parseInt(requiredAmountField.getText());
        } catch (NumberFormatException e) {
            outputArea.append("Enter a valid required amount.\n");
            return;
        }

        Item item = tracker.getBook().getItemByName(catName);

        if (item == null) {
            outputArea.append("Cat not found: " + catName + "\n");
            return;
        }

        Material material = new Material(materialName);
        material.setCount(amount);
        item.addMaterial(material);

        outputArea.append("Added requirement for " + catName + ": " + amount + " " + materialName + "\n");

        materialNameField.setText("");
        requiredAmountField.setText("");
    }

    private void updateInventory() {
        String materialName = ownedMaterialField.getText();

        if (materialName.equals("")) {
            outputArea.append("Enter a material name.\n");
            return;
        }

        int amount;

        try {
            amount = Integer.parseInt(ownedAmountField.getText());
        } catch (NumberFormatException e) {
            outputArea.append("Enter a valid owned amount.\n");
            return;
        }

        Material owned = tracker.getOwnedMaterial(materialName);

        if (owned == null) {
            Material material = new Material(materialName);
            material.setCount(amount);
            tracker.addOwnedMaterial(material);
        } else {
            owned.setCount(amount);
        }

        outputArea.append("Updated inventory: " + materialName + " = " + amount + "\n");

        ownedMaterialField.setText("");
        ownedAmountField.setText("");
    }

    private void deleteCat() {
        String catName = catNameField.getText();

        if (catName.equals("")) {
            outputArea.append("Enter a cat name.\n");
            return;
        }

        Item item = tracker.getBook().getItemByName(catName);

        if (item == null) {
            outputArea.append("Cat not found.\n");
            return;
        }

        tracker.removeGoal(catName);

        outputArea.append("Deleted cat: " + catName + "\n");

        catNameField.setText("");
    }

    private void viewMissingForCat() {
        String catName = catNameField.getText();

        if (catName.equals("")) {
            outputArea.append("Enter a cat name.\n");
            return;
        }

        ArrayList<Material> missing = tracker.calculateMissingForItem(catName);

        if (missing == null) {
            outputArea.append("Cat not found: " + catName + "\n");
            return;
        }

        outputArea.append("Missing materials for " + catName + ":\n");

        if (missing.size() == 0) {
            outputArea.append("None. You have enough.\n");
        } else {
            for (int i = 0; i < missing.size(); i++) {
                outputArea.append(missing.get(i).getName() + ": " + missing.get(i).getCount() + "\n");
            }
        }
    }

    private void viewTotalMissing() {
        ArrayList<Material> totalMissing = tracker.calculateTotalMissing();

        outputArea.append("Total missing materials:\n");

        if (totalMissing.size() == 0) {
            outputArea.append("None. You have enough for all goals.\n");
        } else {
            for (int i = 0; i < totalMissing.size(); i++) {
                outputArea.append(totalMissing.get(i).getName() + ": " + totalMissing.get(i).getCount() + "\n");
            }
        }
    }

    private void viewInventory() {
        ArrayList<Material> inventory = tracker.getOwnedMaterials();

        outputArea.append("Inventory:\n");

        if (inventory.size() == 0) {
            outputArea.append("Empty.\n");
        } else {
            for (int i = 0; i < inventory.size(); i++) {
                outputArea.append(inventory.get(i).getName() + ": " + inventory.get(i).getCount() + "\n");
            }
        }
    }

    private void addInventory() {
        String materialName = ownedMaterialField.getText();

        if (materialName.equals("")) {
            outputArea.append("Enter a material name.\n");
            return;
        }

        int amount;

        try {
            amount = Integer.parseInt(ownedAmountField.getText());
        } catch (NumberFormatException e) {
            outputArea.append("Enter a valid owned amount.\n");
            return;
        }

        Material owned = tracker.getOwnedMaterial(materialName);

        if (owned == null) {
            Material material = new Material(materialName);
            material.setCount(amount);
            tracker.addOwnedMaterial(material);
        } else {
            owned.addCount(amount);
        }

        outputArea.append("Added inventory: " + materialName + " +" + amount + "\n");

        ownedMaterialField.setText("");
        ownedAmountField.setText("");
    }

    private void saveData() {
        try {
            tracker.saveData("save.txt");
            outputArea.append("Saved data to save.txt\n");
        } catch (Exception e) {
            outputArea.append("Could not save data.\n");
        }
    }

    private void loadData() {
        try {
            tracker.loadData("save.txt");
            outputArea.append("Loaded data from save.txt\n");
        } catch (Exception e) {
            outputArea.append("Could not load data.\n");
        }
    }

    private void refreshDisplay() {
        catsArea.setText("Cats:\n");

        for (int i = 0; i < tracker.getBook().getItemCount(); i++) {
            Item item = tracker.getBook().getItem(i);
            catsArea.append(item.getName() + "\n");

            for (int j = 0; j < item.getTotalNeeded().size(); j++) {
                Material m = item.getTotalNeeded().get(j);
                catsArea.append("  - " + m.getName() + ": " + m.getCount() + "\n");
            }
        }

        inventoryArea.setText("Inventory:\n");

        ArrayList<Material> inventory = tracker.getOwnedMaterials();

        for (int i = 0; i < inventory.size(); i++) {
            inventoryArea.append(inventory.get(i).getName() + ": " + inventory.get(i).getCount() + "\n");
        }

        missingArea.setText("Total Missing:\n");

        ArrayList<Material> missing = tracker.calculateTotalMissing();

        for (int i = 0; i < missing.size(); i++) {
            missingArea.append(missing.get(i).getName() + ": " + missing.get(i).getCount() + "\n");
        }
    }

    public static void main(String[] args) {
        new MainGUI();
    }
}
