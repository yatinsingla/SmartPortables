import java.util.ArrayList;
import java.util.List;
import java.io.Serializable;

public class Accessory implements Serializable {
    String id;
    String image;
    String name;
    String condition;
    int price;

    public Accessory(String id, String image, String name, String condition, int price){
        this.id = id;
        this.image = image;
        this.name = name;
        this.condition = condition;
        this.price = price;
    }

    public Accessory(){

    }

    public void setId(String id){
        this.id = id;
    }

    public String getId(){
        return id;
    }

    public void setImage(String image){
        this.image = image;
    }

    public String getImage(){
        return image;
    }

    public void setName(String name){
        this.name = name;
    }

    public String getName(){
        return name;
    }

    public void setCondition(String condition){
        this.condition = condition;
    }

    public String getCondition(){
        return condition;
    }

    public void setPrice(int price){
        this.price = price;
    }

    public int getPrice(){
        return price;
    }

}

