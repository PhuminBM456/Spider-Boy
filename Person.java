import java.awt.*;

abstract class Person{
    // field
    boolean isPlayer;
    boolean isATK = false;
    boolean jump = false;
    boolean falling = false;
    int hp = 100;
    int x,y;
    int speed = 10;
    int jumpPower = 20;

    // constructor
    Person(boolean bool){
        isPlayer = bool;
    }

    // method
    abstract void Attack();

    void Right(){
        x += speed;
    }

    void Left(){
        x -= speed;
    }

    abstract void Walk();

    abstract void Jump();
}