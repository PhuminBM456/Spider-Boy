import javax.swing.*;
import java.awt.*;
import java.net.URL;

class Enemy extends Person{
    // field
    String location = "images/";
    Image enemy = null;
    Image curr = loadImage(location + "EnemyMain.png");

    boolean walk = false;

    // constructor
    Enemy(){
        super(false);

        enemy = curr;
        this.x = 820;
        this.y = 400;
        jumpPower = 200;
        speed = 2;
    }

    // method
    Image loadImage(String path){
        URL url = getClass().getResource(path);
        Image img = new ImageIcon(url).getImage();

        return  img;
    }

    @Override
    void Attack(){}

    @Override
    void Walk(){
        if(walk == false){
            if(x > 0){
                Left();
            }else{
                walk = true;
            }
        }else{
            if(x < 820){
                Right();
            }else{
                x = 820;
                walk = false;
            }
        }

    }

    @Override
    void Jump(){
        if(falling == false){
            if(y > 400-jumpPower){
                y -= 1;
            }else{
                falling = true;
            }
        }else{
            if(y < 400){
                //System.out.println(y);
                y += 1;
            }else{
                y = 400;
                falling = false;
                jump = false;
            }
        }
    }
}