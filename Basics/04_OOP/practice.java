class College{
    public static void main(String args[]){
        Classroom obj;
        obj = new Whiteboard(); //we are creating a whiteboard object and storing it inside classroom reference
        //this is allowed because Whiteboard Extends classroom

        //obj we are using to access Classroom
        

        obj.show(10);
        System.out.println(Whiteboard.nigga);
    }
}

class Classroom{
    public void show(){
        System.out.println("Classroom");
    }

    public void show(int k){
        System.out.println(k);
    }
}

class Whiteboard extends Classroom{
    public void show(){
        System.out.println("Whiteboard");
    }

    static String nigga="Jeel";

}

//static shiii belongs to the class onlyyy, non-static belongs to the object, thus, to call static nigga, we use Whiteboad.nigga
