import java.awt.*;

abstract class Person{
    // field
    boolean isPlayer;
    boolean isATK = false;
    boolean jump = false;
    boolean falling = false;
    boolean done = false;
    int hp = 100;
    int x,y;
    int speed = 10;
    int jumpPower = 20;

    // constructor
    Person(boolean bool){
        isPlayer = bool;
    }

    // method
    void Right(){
        x += speed;
    }

    void Left(){
        x -= speed;
    }

    abstract void Walk();

    abstract void Jump();

    abstract void Attack();

    Rectangle getHitBox(){
        return new Rectangle(x-7, y+50, 65, 100);
    }
}