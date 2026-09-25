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
    Image iconHeal =  loadImage(location + "Power0.png");
    Image iconBlank =  loadImage(location + "Power3.png");

    boolean gameOver = false;
    boolean openDimen = false;
    boolean layout = false;

    Character lastKey = null;

    Player player = new Player();
    Enemy enemy = new Enemy();

    Vector<Bullet> bullets = new Vector<>();
    Queue capsem = new Queue();
    HashTable ht = new HashTable();

    // constructor
    Panel(){
        curr = normal;

        setFocusable(true);

        addKeyListener(new KeyAdapter(){
            @Override
            public void keyPressed(KeyEvent e){

                if(!gameOver && player.level != player.maxLevel){
                    if(e.getKeyCode() == KeyEvent.VK_RIGHT){
                        lastKey = 'R';
                        player.Right();
                    }else if(e.getKeyCode() == KeyEvent.VK_LEFT){
                        lastKey = 'L';
                        player.Left();
                    }else if(e.getKeyCode() == KeyEvent.VK_Q){
                        player.Attack();
                    }else if(e.getKeyCode() == KeyEvent.VK_UP){
                        Jump();
                    }else if(e.getKeyCode() == KeyEvent.VK_R){
                        Heal();
                    }else if(e.getKeyCode() == KeyEvent.VK_B){
                        if(openDimen == false){
                            openDimen = true;
                            return;
                        }

                        if(openDimen && layout){
                            openDimen = layout = false;
                            return;
                        }

                        if(openDimen == true){
                            openDimen = false;
                            return;
                        }
                    }else if(e.getKeyCode() == KeyEvent.VK_ENTER){
                        openPocketDimen();
                    }
                }

            }
        });

        Timer timer = new Timer(10,e-> {
            Capsem();
            checkPlayerATK();
            checkEnemyATK();
            //enmBot();
            isDone();
            getItem();


            if(player.playerDead())
                gameOver = true;

            if(bullets.isEmpty())
                curr = normal;

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

    void enmBot(){
        if(!gameOver && player.level != player.maxLevel)
            enemy.enemyBot(player.level);
    }

    void Heal(){
        int hp,n;

        hp = player.hp;
        n = ht.Search("Heal");

        if(n!=0){
            if(20 + hp > 100){
                player.hp += (100 - player.hp);
            }else{
                player.hp += 20;
            }

            ht.Delete("Heal");
        }
    }

    void openPocketDimen(){
        if(openDimen){
            layout = true;
        }
    }

    void Jump(){
        player.jump = true;
    }

    void Capsem(){

        if(!capsem.status){
            if(capsem.isEmpty()) {
                capsem.Enqueue(new Capsem());

            }else{
                capsem.Enqueue(new Capsem());
                Capsem temp = capsem.Dequeue();
                capsem.Enqueue(temp);
            }

            capsem.status = true;
        }

        if(capsem.status){
            //capsem.Display();

            Capsem cap = capsem.arr[capsem.front];

            // check collision
            boolean collsion = cap.isCollision(cap.getHitBox(),player.getHitBox());

            if(collsion){
                String power = cap.power;

                if(power == "Recovery"){
                    //if(player.hp < 100){
                        //player.hp += (100 - player.hp);
                    //}else{
                        ht.Insert(power);
                    //}
                }else if(power == "Impact"){
                    player.damage += 5;
                }

                capsem.Dequeue();
                capsem.status = false;

                return;
            }

            if(cap.isOnGround()){
                capsem.status = false;
                capsem.arr[capsem.front].y = 0;

                return;
            }

            cap.Move();

        }
    }

    void checkPlayerATK(){
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
    }

    void checkEnemyATK(){
        if(enemy.isATK == true && enemy.shootBullet == false){
            int d = enemy.x - player.x;
            enemy.shootBullet = true;

            if(d > 0){
                enemy.enemy = enemy.left;
                enemy.bullet = new Bullet(enemy.x,enemy.y,-7,-30);
            }else{
                enemy.enemy = enemy.right;
                enemy.bullet = new Bullet(enemy.x,enemy.y,7,60);
            }
        }else if(enemy.isATK == true && enemy.shootBullet == true){
            if(enemy.bullet.isTimeOut()){
                enemy.isATK = enemy.shootBullet = false;
                enemy.bullet = null;

                return;
            }

            // check collision
            Bullet bullet = enemy.bullet;
            boolean done = bullet.isCollision(bullet.getHitBox(),player.getHitBox());

            if(done){
                player.done = true;
            }

            enemy.bullet.Move();
        }
    }

    void changePic(){
        if(lastKey == null || lastKey == 'R'){
            curr = right;
        }else{
            curr = left;
        }
    }

    void getItem(){

    }

    void isDone(){
        if(enemy.done){
            enemy.hp -= player.damage;

            if(enemy.hp <= 0){

                int webCurr = player.web;
                int n = 20 - webCurr;

                player.web += n;
                player.damage += 5;
                player.speed += 1;
                player.jumpPower += 5;

                if(player.level != player.maxLevel-1)
                    player.level += 1;
            }

            //System.out.println(enemy.hp);

            enemy.done = false;
        }

        if(player.done && enemy.shootBullet){
            player.hp -= enemy.damage;

            if(player.hp <= 0){
                player.dead = true;
            }

            enemy.isATK = player.done = enemy.shootBullet = false;

            //System.out.println(enemy.shootBullet);
        }
    }

    @Override
    public void paintComponent(Graphics g){
        super.paintComponent(g);

        // bullet player
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

        // bullet enemy
        if(enemy.shootBullet){
            g.setColor(Color.BLUE);
            g.fillRect(enemy.bullet.x,enemy.bullet.y,10,10);
        }


        g.drawImage(curr,player.x,player.y,player.size,player.size,this);
        g.drawImage(enemy.enemy,enemy.x,enemy.y,enemy.size,enemy.size,this);

        // hitbox person
        g.setColor(Color.RED);
        g.drawRect(player.x-7,player.y+50,65,100);

        g.setColor(Color.BLUE);
        g.drawRect(enemy.x-7,enemy.y+50,65,100);

        // capsem
        if(capsem.status){
            Capsem cap = capsem.arr[capsem.front];
            g.drawImage(cap.capsem,cap.x,cap.y,cap.size,cap.size,this);

            g.setColor(Color.BLUE); // hitbox capsem
            g.drawRect(cap.x,cap.y,17,20);

        }

        // hp
        g.setColor(Color.BLACK);
        g.fillRect(player.x-23,player.y+20,100,10);
        g.setColor(Color.GREEN);
        g.fillRect(player.x-23,player.y+20,player.hp,10);

        if(!enemy.dead){
            g.setColor(Color.BLACK);
            g.fillRect(enemy.x-23,enemy.y+20,100,10);
            g.setColor(Color.GREEN);
            g.fillRect(enemy.x-23,enemy.y+20,enemy.hp,10);
        }

        // text
        g.setColor(Color.BLACK);
        g.drawString("Web Shooter " + player.web + "/" + player.maxWeb,10,20);
        g.drawString("Level " + player.level,10,50);
        g.drawString("Streght " + player.damage,10,80);

        g.drawImage(iconBlank,10,100,40,40,this); // pocket dimension
        g.setColor(Color.BLACK);

        if(lastKey != null && lastKey == 'B'){
            g.drawString("ENTER",50,120);
        }else{
            g.drawString("B",50,120);
        }

        if(openDimen){ // selected
            g.setColor(Color.RED);
            g.drawRect(7,100,40,40);
        }

        if(layout){ // table
            int idx,dX,dY;

            idx = 1;
            dY = 0;

            for(int j=1;j<=3;j++){
                dX = 0;

                for(int i=1;i<=3;i++){
                    g.setColor(Color.GRAY);
                    g.fillRect(200*i,100*j,85,85);

                    g.setColor(Color.WHITE);
                    g.fillRect(200*i,100*j,80,80);

                    g.setColor(Color.BLACK);
                    g.drawString(""+idx++,200*i,100*j+10);


                    g.drawImage(iconHeal,222+dX,118+dY,40,40,this);
                    dX+=200;

                    //g.setColor(Color.RED);
                    //g.drawString("x"+ht.Search("Recovery"),260,170);
                }

                dY+=100;
            }

            //g.drawImage(iconHeal,222,118+dY,40,40,this);
        }

        if(gameOver){
            g.setColor(Color.RED);
            g.drawString("MISSION FAILED",400,100);
        }
    }
}