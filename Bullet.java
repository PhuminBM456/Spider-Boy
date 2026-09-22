import java.awt.*;

class Bullet{
    // field
    int x,y,speed,endPos;

    // constructor
    Bullet(int x,int y,int speed,int d){
        this.x = x+d;
        this.y = y+112;
        this.speed = speed;
        //System.out.println(x);
    }

    // method
    void Move(){
        x += speed;
    }

    boolean isTimeOut(){
        if(x >= 900 || x <= 0)
            return true;

        return  false;
    }

    boolean isCollision(Rectangle bullet,Rectangle enm){
        return bullet.intersects(enm);
    }

    Rectangle getHitBox(){
        return new Rectangle(x-3, y-3, 15, 15);
    }
}