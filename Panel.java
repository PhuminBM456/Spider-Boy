import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.net.URL;

class Panel extends JPanel{
    // field
    String location = "images/";
    Image curr = loadImage(location + "Main.png");
    Image left = loadImage(location + "WebL.png");
    Image right = loadImage(location + "WebR.png");
    Player player = new Player();
    char lastKey;

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
                    player.ATK();
                }else if(e.getKeyCode() == KeyEvent.VK_UP){
                    player.jump = true;
                }
            }
        });

        Timer timer = new Timer(10,e-> {
            if(player.isATK == true){
                changePic();



                player.isATK = false;
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

        g.drawImage(curr,player.x,player.y,150,150,this);

        //g.setColor(Color.RED);
        //g.fillRect(0,0,10,10);
    }
}