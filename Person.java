abstract class Person{
    // field
    boolean isPlayer;
    boolean isATK = false;
    boolean jump = false;
    boolean falling = false;
    int HP = 100;
    int x,y;
    int speed = 10;
    int jumpPower = 30;

    // constructor
    Person(boolean bool){
        isPlayer = bool;
    }

    // method
    void Attack(){
        isATK = true;
    };

    void Right(){
        x += speed;
    }

    void Left(){
        x -= speed;
    }

    abstract void Jump();
}