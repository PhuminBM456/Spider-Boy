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

    Bullet bullet = null;

    // constructor
    Enemy(){
        enemy = normal;
        this.x = 820;
        this.y = 400;
        jumpPower = 200;
        speed = 1;
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
            speed += 1;
            damage += 5;
            hp = 100;
            jumpPower += 1;
        }

        dead = false;
    }

    void enemyBot(int currLevel){
        if(hp <= 0){
            dead = true;
            transform();
            return;
        }

        Walk();
        Attack();

        if(currLevel >= 2){
            Jump();
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