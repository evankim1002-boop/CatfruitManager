public class Material {
    //declare variable
    private String name;
    private int count;
    
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

    public void addCount(int loop){
        for(int i = 0; i < loop; i++){
            count++;
        }
    }

    public void deleteCount(int loop){
        for(int i = 0; i < loop; i++){
            count--;
        }
    }
}
