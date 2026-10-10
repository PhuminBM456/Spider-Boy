import java.awt.*;

class Object{
    // field
    int x,y,width,height,speed;

    void move(){
        x-=speed;

        if(x <= -100)
            x = 900;
    }

    Rectangle getHitBox(){
        return new Rectangle(x, y+75, width-9, height-80);
    }

    boolean isCollision(Rectangle a,Rectangle b){
        return a.intersects(b);
    }
}