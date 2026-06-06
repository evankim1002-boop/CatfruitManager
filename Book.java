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

    public ArrayList<Item> getAllItems(){
        return book;
    }

    public Item getItemByName(String name){
        for(int i = 0; i < book.size(); i++){
            if(book.get(i).getName().equals(name)){
                return book.get(i);
            }
        }
        return null;
    }

    public boolean itemExists(String name){
        for(int i = 0; i < book.size(); i++){
            if(book.get(i).getName().equals(name)){
                return true;
            }
        }
        return false;
    }

    public void deleteItemByName(String name){
        for(int i = 0; i < book.size(); i++){
            if(book.get(i).getName().equals(name)){
                book.remove(i);
                itemCount--;
                return;
            }
        }
    }

    public void deleteItemByIndex(int i){
        book.remove(i);
        itemCount--;
    }

    public ArrayList<Item> getCompletedItems(){
        ArrayList<Item> completed = new ArrayList<Item>();
        for(int i = 0; i < book.size(); i++){
            if(book.get(i).isComplete()){
                completed.add(book.get(i));
            }
        }
        return completed;
    }

    public ArrayList<Item> getIncompleteItems(){
        ArrayList<Item> incomplete = new ArrayList<Item>();
        for(int i = 0; i < book.size(); i++){
            if(!book.get(i).isComplete()){
                incomplete.add(book.get(i));
            }
        }
        return incomplete;
    }


}
