public class Material {
    //declare variable
    private String name;
    private int count;
    private int neededCount;
    
    //constructor
    public Material(String name){
        this.name = name;
        this.count = 0;
    }

    //methods

    public String getName(){
        return name;
    }

    public int getCount(){
        return count;
    }

    public void addCount(int i){
        count += i;
    }

    public void deleteCount(int i){
        count -= i;
    }

    public void setCount(int i){
        count = i;
    }

    public boolean hasEnough(int i){
        return count >= i;
    }

    public int getMissingAmount(int itemCount, int required){
    if(itemCount >= required){
        return 0;
    } else {
        return required - itemCount;
    }
}
    public int getNeededCount(){
        return neededCount;
    }

    public void setNeededCount(int i){
        neededCount = i;
    }


}
