import java.util.ArrayList;

public class Book {
    //private variable
    private ArrayList<Item> book;
    private int itemCount;
    

    //constructor
    public Book(){
        this.book = new ArrayList<Item>();
        this.itemCount = 0;
    }

    //method

    public void addItem(Item item){
        book.add(item);
        itemCount++;
    }

    public Item getItem(int i){
        return book.get(i);
    }

    public int getItemCount(){
        return itemCount;
    }

    public void deleteItem(int i){
        book.remove(i);
        itemCount--;
    }

    public void clearBook(){
        book.clear();
        itemCount = 0;
    }

    
}
