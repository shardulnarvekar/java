class Hostel{
    public static void main(String args[]){

        Bed obj;
        obj = new Bed();

        obj.show();
        obj.sleep();

        obj.setKeys(10);
        System.out.println(obj.getColour());
    }
}

class Room{

    private int keys = 100;
    private String colour = "White";


    public void setKeys(int k){
        keys=k;

        System.out.println(k);
    }

    public String getColour(){
        colour="pink";
        return colour;
    }


    public void show(){
        System.out.println("Roooommm");
    }

    //constructors can be used to assign values, we we dont have it initially, it has same name as class name
    // public Room(){
    //     keys=100;
    //     colour="White";
    // }
    // There is no void in constructor
}

class Bed extends Room{
    public void sleep(){
        System.out.println("Napping");
    }
}