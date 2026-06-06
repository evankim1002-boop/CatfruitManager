import java.util.Scanner;

public class Main {
    
    


    public static void main(String[] args) {
        String userInput;
        Scanner scanner = new Scanner(System.in);
        System.out.println("Welcome to the crafting calculator!");
        System.out.println("What item would you like to craft?");
        userInput = scanner.nextLine();
        Item item = new Item(userInput);
        System.out.println("How many " + userInput + " would you like to craft?");
        int count = scanner.nextInt();
        item.addNeededCount(count);
        System.out.println("You will need " + item.getNeededCount() + " " + userInput + " to craft " + count + " " + userInput);
    }
}
