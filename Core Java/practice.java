class College{
    public static void main(String args[]){
        Classroom obj;
        obj = new Whiteboard();

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
