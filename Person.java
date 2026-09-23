import java.awt.*;

abstract class Person{
    // field
    boolean isPlayer;
    boolean isATK = false;
    boolean jump = false;
    boolean falling = false;
    boolean done = false;
    boolean dead = false;
    final int maxLevel = 5;
    int level = 1;
    int hp = 100;
    int x,y;
    int speed = 10;
    int jumpPower = 200;
    int damage = 10;
    int size = 150;

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

    abstract void Jump();

    abstract void Attack();

    Rectangle getHitBox(){
        return new Rectangle(x-7, y+50, 65, 100);
    }

    boolean isCollision(Rectangle a,Rectangle b){
        return a.intersects(b);
    }
}