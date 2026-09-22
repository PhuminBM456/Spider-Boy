import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.net.URL;
import java.util.Vector;

class Panel extends JPanel{
    // field
    String location = "images/";
    Image curr = null;
    Image normal = loadImage(location + "Main.png");
    Image left = loadImage(location + "WebL.png");
    Image right = loadImage(location + "WebR.png");

    Player player = new Player();
    Enemy enemy = new Enemy();

    Character lastKey = null;
    Vector<Bullet> bullets = new Vector<>();

    // constructor
    Panel(){
        curr = normal;

        setFocusable(true);

        addKeyListener(new KeyAdapter(){
            @Override
            public void keyPressed(KeyEvent e){
                if(e.getKeyCode() == KeyEvent.VK_RIGHT){
                    lastKey = 'R';
                    player.Right();
                }else if(e.getKeyCode() == KeyEvent.VK_LEFT){
                    lastKey = 'L';
                    player.Left();
                }else if(e.getKeyCode() == KeyEvent.VK_Q){
                    player.Attack();
                }else if(e.getKeyCode() == KeyEvent.VK_UP){
                    player.jump = true;
                }
            }
        });

        Timer timer = new Timer(10,e-> {
            if(enemy.done){
                enemy.hp -= 1;
                System.out.println(enemy.hp);

                enemy.done = false;
            }

            if(player.isATK == true){
                int x,y;
                x = player.x;
                y = player.y;

                if(lastKey == null || lastKey == 'R'){
                    bullets.add(new Bullet(x,y,7,60));
                }else if(lastKey == 'L'){
                    bullets.add(new Bullet(x,y,-7,-30));
                }

                changePic();

                player.isATK = false;
            }

            if(bullets.isEmpty())
                curr = normal;

            if(player.jump == true)
                player.Jump();

            //enemyBot();

            repaint();
        });

        timer.start();
    }

    // method
    Image loadImage(String path){
        URL url = getClass().getResource(path);
        Image img = new ImageIcon(url).getImage();

        return  img;
    }

    void enemyBot(){
        enemy.Walk();
        enemy.Jump();
    }

    void changePic(){
        if(lastKey == null || lastKey == 'R'){
            curr = right;
        }else{
            curr = left;
        }
    }

    @Override
    public void paintComponent(Graphics g){
        super.paintComponent(g);

        for(int i=0;i<bullets.size();i++){
            Bullet bullet = bullets.get(i);

            if(bullet.isTimeOut()){
                bullets.remove(i);
            }else{
                g.setColor(Color.RED);
                g.fillRect(bullet.x,bullet.y,10,10);

                // hitbox bullet
                g.setColor(Color.BLUE);
                g.drawRect(bullet.x-3,bullet.y-3,15,15);

                // check collision
                boolean done = bullet.isCollision(bullet.getHitBox(),enemy.getHitBox());

                if(done){
                    enemy.done = true;
                    bullets.remove(i);
                }

                bullet.Move();
            }
        }

        g.drawImage(curr,player.x,player.y,150,150,this);
        g.drawImage(enemy.enemy,enemy.x,enemy.y,150,150,this);

        // hitbox person
        g.setColor(Color.RED);
        g.drawRect(player.x-7,player.y+50,65,100);
        g.setColor(Color.BLUE);
        g.drawRect(enemy.x-7,enemy.y+50,65,100);

        g.setColor(Color.BLACK);
        g.fillRect(player.x-23,player.y+20,100,10);

        g.setColor(Color.GREEN);
        g.fillRect(player.x-23,player.y+20,player.hp,10);

        g.setColor(Color.BLACK);
        g.drawString("Web Shooter " + player.web + "/" + player.maxWeb,10,20);
    }
}