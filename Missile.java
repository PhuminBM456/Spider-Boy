import java.awt.*;

class Missile{
    // field
    int x,y,speed;

    // constructor
    Missile(int speed){
        this.speed = speed;
        //System.out.println(x);
    }

    // method
    void Move(){
        y += speed;
    }

    boolean isCollision(Rectangle bullet,Rectangle enm){
        return bullet.intersects(enm);
    }

    Rectangle getHitBox(){
        return new Rectangle(x-3, y-3, 15, 15);
    }
}