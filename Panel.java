import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.net.URL;
import java.util.Vector;

class Panel extends JPanel{
    // field
    String location = "images/";
    Image curr = loadImage(location + "Main.png");
    Image left = loadImage(location + "WebL.png");
    Image right = loadImage(location + "WebR.png");
    Player player = new Player();
    char lastKey;
    Vector<Bullet> bullets = new Vector<>();

    // constructor
    Panel(){

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
            if(player.isATK == true){
                int x,y;
                x = player.x;
                y = player.y;

                bullets.add(new Bullet(x,y,7));
                player.isATK = false;
                changePic();
            }

            if(player.jump == true)
                player.Jump();

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

    void changePic(){
        if(lastKey == 'R'){
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
                bullet.Move();
            }
        }

        g.drawImage(curr,player.x,player.y,150,150,this);
    }
}