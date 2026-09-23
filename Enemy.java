import javax.swing.*;
import java.awt.*;
import java.net.URL;

class Enemy extends Person{
    // field
    String location = "images/";

    Image enemy = null;
    Image normal = loadImage(location + "EnemyMain.png");
    Image left = loadImage(location + "EnemyWebL.png");
    Image right = loadImage(location + "EnemyWebR.png");

    boolean shootBullet = false;
    boolean walk = false;
    boolean dead = false;

    Bullet bullet = null;

    // constructor
    Enemy(){
        super(false);

        enemy = normal;
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
    void Attack(){
        isATK = true;
    }

    void Walk(){
        if(walk == false){
            if(x > 0){
                Left();
                enemy = left;
            }else{
                walk = true;
            }
        }else{
            if(x < 820){
                enemy = right;
                Right();
            }else{
                x = 820;
                walk = false;
            }
        }

    }

    void transform(){
        if(dead){

        }
    }

    void enemyBot(){
        if(hp <= 0){
            dead = true;
            return;
        }

        Walk();
        Attack();
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