import javax.swing.*;
import java.awt.*;
import java.net.URL;
import java.util.Random;

class Capsem{
    // field
    Random rand = new Random();

    String location = "images/";
    String power;

    Image capsem = null;
    Image normal = null;

    int size,x,y,speed;

    // constructor
    Capsem(){
        int random = rand.nextInt(901);
        int idx = rand.nextInt(2);

        switch (idx){
            case 0:
                power = "Heal";
                break;
            case 1:
                power = "Impact";
                break;
        }

        normal = loadImage(location + "Power" + idx + ".png");
        capsem = normal;
        x = random;
        y = 0;
        size = 20;
        speed = 1;
    }

    // method
    Image loadImage(String path){
        URL url = getClass().getResource(path);
        Image img = new ImageIcon(url).getImage();

        return  img;
    }

    void Move(){
        y+=speed;
    }

    Rectangle getHitBox(){
        return new Rectangle(x-7, y+50, 25, 25);
    }

    boolean isCollision(Rectangle a,Rectangle b){
        return a.intersects(b);
    }

    boolean isOnGround(){
        return y >= 600;
    }
}