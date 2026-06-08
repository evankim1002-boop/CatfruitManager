import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;

public class MainGUI {
    private Tracker tracker;

    private JFrame frame;
    private JTextField catNameField;
    private JTextField materialNameField;
    private JTextField requiredAmountField;
    private JTextField ownedMaterialField;
    private JTextField ownedAmountField;
    private JTextArea outputArea;

    public MainGUI() {
        tracker = new Tracker();

        frame = new JFrame("Battle Cats Catfruit Tracker");
        frame.setSize(700, 500);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLayout(new BorderLayout());

        JPanel topPanel = new JPanel(new GridLayout(2, 3));

        catNameField = new JTextField();
        JButton addCatButton = new JButton("Add Cat");

        materialNameField = new JTextField();
        requiredAmountField = new JTextField();
        JButton addRequirementButton = new JButton("Add Requirement");

        topPanel.add(new JLabel("Cat Name:"));
        topPanel.add(catNameField);
        topPanel.add(addCatButton);

        topPanel.add(new JLabel("Material Required:"));
        topPanel.add(materialNameField);
        topPanel.add(requiredAmountField);

        frame.add(topPanel, BorderLayout.NORTH);

        JPanel middlePanel = new JPanel(new GridLayout(1, 2));

        JPanel inventoryPanel = new JPanel(new GridLayout(3, 2));

        ownedMaterialField = new JTextField();
        ownedAmountField = new JTextField();
        JButton updateInventoryButton = new JButton("Update Inventory");

        inventoryPanel.add(new JLabel("Owned Material:"));
        inventoryPanel.add(ownedMaterialField);
        inventoryPanel.add(new JLabel("Owned Amount:"));
        inventoryPanel.add(ownedAmountField);
        inventoryPanel.add(updateInventoryButton);

        JPanel buttonPanel = new JPanel(new GridLayout(4, 1));

        JButton viewMissingButton = new JButton("View Missing For Cat");
        JButton viewTotalMissingButton = new JButton("View Total Missing");
        JButton viewInventoryButton = new JButton("View Inventory");
        JButton clearButton = new JButton("Clear Output");

        buttonPanel.add(addRequirementButton);
        buttonPanel.add(viewMissingButton);
        buttonPanel.add(viewTotalMissingButton);
        buttonPanel.add(viewInventoryButton);

        middlePanel.add(inventoryPanel);
        middlePanel.add(buttonPanel);

        frame.add(middlePanel, BorderLayout.CENTER);

        outputArea = new JTextArea();
        outputArea.setEditable(false);

        JScrollPane scrollPane = new JScrollPane(outputArea);
        frame.add(scrollPane, BorderLayout.SOUTH);

        addCatButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                addCat();
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

        frame.setVisible(true);
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
        catNameField.setText("");
    }

    private void addRequirement() {
        String catName = catNameField.getText();
        String materialName = materialNameField.getText();

        if (catName.equals("") || materialName.equals("")) {
            outputArea.append("Enter cat name and material name.\n");
            return;
        }

        int amount = Integer.parseInt(requiredAmountField.getText());

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

        int amount = Integer.parseInt(ownedAmountField.getText());

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

    public static void main(String[] args) {
        new MainGUI();
    }
}