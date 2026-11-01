import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.net.URL;
import java.util.Vector;

class Panel extends JPanel{
    // field
    int frameSelected = 0;
    int levelFake = 1;
    double idxHenshin = 1;

    String powerSelected = null;
    String location = "images/";

    Image curr = null;
    Image normal = null;
    Image left = null;
    Image right = null;

    Image iconRecovery =  loadImage(location + "Power0.png");
    Image iconImpact =  loadImage(location + "Power1.png");
    Image iconStream =  loadImage(location + "Power2.png");
    Image iconInazuma =  loadImage(location + "Power3.png");
    Image iconBlank =  loadImage(location + "PowerBlank.png");
    Image state1 =  loadImage(location + "State1.jpg");
    Image state2 =  loadImage(location + "State2.jpg");
    Image car = loadImage(location + "Car.png");
    Image drone = loadImage(location + "Drone.png");
    Image effect = null;

    boolean gameOver = false;
    boolean openDimen = false;
    boolean layout = false;
    boolean fire = false;

    Character lastKey = null;
    Player player = new Player();
    Enemy enemy = new Enemy();

    // polymorphism
    Object obj1 = new Car(900,402,135,150,5);
    Object obj2 = new Drone(0,50,50,50,2);

    Vector<Bullet> bullets = new Vector<>();
    Queue capsem = new Queue();
    HashTable ht = new HashTable();
    Missile missile = new Missile(5);
    Inazuma inazuma = new Inazuma();

    // constructor
    Panel(){
        curr = normal;

        setFocusable(true);

        addKeyListener(new KeyAdapter(){
            @Override
            public void keyPressed(KeyEvent e){

                if(!gameOver){
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
                    }else if(e.getKeyCode() == KeyEvent.VK_B){
                        if(openDimen == false){
                            openDimen = true;
                            return;
                        }

                        if(openDimen && layout){
                            openDimen = layout = false;
                            frameSelected = 0;
                            lastKey = null;
                            powerSelected = null;
                            return;
                        }

                        if(openDimen == true){
                            openDimen = false;
                            return;
                        }
                    }else if(e.getKeyCode() == KeyEvent.VK_ENTER){
                        openPocketDimen();
                        getItem();
                    }else if(e.getKeyCode() == KeyEvent.VK_1){
                        lastKey = '1';
                    }else if(e.getKeyCode() == KeyEvent.VK_2){
                        lastKey = '2';
                    }else if(e.getKeyCode() == KeyEvent.VK_3){
                        lastKey = '3';
                    }else if(e.getKeyCode() == KeyEvent.VK_4){
                        lastKey = '4';
                    }else if(e.getKeyCode() == KeyEvent.VK_5){
                        lastKey = '5';
                    }else if(e.getKeyCode() == KeyEvent.VK_6){
                        lastKey = '6';
                    }else if(e.getKeyCode() == KeyEvent.VK_7){
                        lastKey = '7';
                    }else if(e.getKeyCode() == KeyEvent.VK_8){
                        lastKey = '8';
                    }else if(e.getKeyCode() == KeyEvent.VK_9){
                        lastKey = '9';
                    }
                }

            }
        });

        Timer timer = new Timer(10,e-> {
            resetLevel();
            Tranform();
            Test();
            OBJ();
            Capsem();
            checkPlayerATK();
            checkEnemyATK();
            enmBot();
            isDone();
            Fire();
            Player();
            Bullet();
            inazuma();
            Layout();

            repaint();
        });

        timer.start();
    }

    // method
    void Test(){
        if(player.hp < 100){player.hp += (100 - player.hp);}
    }

    Image loadImage(String path){
        URL url = getClass().getResource(path);
        Image img = new ImageIcon(url).getImage();

        return  img;
    }

    void OBJ(){
        if(player.level == 2){
            if(!gameOver && player.level != player.maxLevel)
                obj1.move();
        }if(player.level >= 3){
            if(!gameOver && player.level != player.maxLevel)
                obj2.move();
        }
    }

    void Player(){
        if(player.playerDead())
            gameOver = true;

        if(player.jump == true)
            player.Jump();
    }

    void Bullet(){
        if(bullets.isEmpty())
            curr = normal;
    }

    void Layout(){
        if(layout){
            if(lastKey != null && lastKey == '1'){
                frameSelected = 1;
            }else if(lastKey != null && lastKey == '2'){
                frameSelected = 2;
            }else if(lastKey != null && lastKey == '3'){
                frameSelected = 3;
            }else if(lastKey != null && lastKey == '4'){
                frameSelected = 4;
            }else if(lastKey != null && lastKey == '5'){
                frameSelected = 5;
            }else if(lastKey != null && lastKey == '6'){
                frameSelected = 6;
            }else if(lastKey != null && lastKey == '7'){
                frameSelected = 7;
            }else if(lastKey != null && lastKey == '8'){
                frameSelected = 8;
            }else if(lastKey != null && lastKey == '9'){
                frameSelected = 9;
            }
        }
    }

    void resetLevel(){
        if(player.level == 4)
            player.level = 1;
    }

    void Tranform(){
        if(!inazuma.henshin){
            normal = player.normal;
            left = player.left;
            right = player.right;
        }else{
            normal = inazuma.inazuma;
            left = inazuma.InazumaWebL;
            right = inazuma.InazumaWebR;
        }
    }

    void getItem(){

        if(frameSelected >= 1 && frameSelected <= 9 && powerSelected != null){
            System.out.println(powerSelected);

            if(powerSelected == "Impact"){
                player.damage += 10;
                player.web += (20 - player.web);
                ht.Delete("Impact");

                return;
            }

            if(powerSelected == "Recovery"){
                if(player.hp < 100){
                    player.hp += 10;
                    ht.Delete("Recovery");
                }

                return;
            }

            if(powerSelected == "Stream"){
                if(player.stream)
                    return;

                player.stream = true;
                player.jumpPower += 200;

                ht.Delete("Stream");

                return;
            }

            if(powerSelected == "Inazuma"){
                if(inazuma.henshin)
                    return;

                inazuma.henshin = true;
                player.speed *= 10;
                player.hp += (100 - player.hp);

                ht.Delete("Inazuma");

                return;
            }
        }
    }

    void inazuma(){
        if(player.hp < 50 && inazuma.henshin){
            inazuma.henshin = false;
            inazuma.completed = 0;
            player.speed /= 10;
            curr = normal;
        }

        if(inazuma.henshin == true){
            if(inazuma.completed != 250){
                curr = inazuma.change;
                inazuma.completed += 1;
            }
        }
    }

    void enmBot(){
        if(!gameOver && player.level != player.maxLevel){
            enemy.enemyBot(player.level);
        }
    }

    void openPocketDimen(){
        if(openDimen){
            layout = true;
        }
    }

    void Fire(){
        if(player.level >= 3 && !fire){
            fire = true;

            missile.x = obj2.x;
            missile.y = obj2.y;

            //System.out.println(true);

        }else{
            if(!gameOver && player.level != player.maxLevel){
                missile.Move();
            }


            boolean fireCollision = missile.isCollision(missile.getHitBox(),player.getHitBox());

            if(fireCollision){
                player.hp -= 1;
            }

            if(missile.y >= 900){
                fire = false;
            }
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
            // capsem display
            // capsem.Display();

            Capsem cap = capsem.arr[capsem.front];

            // check collision
            boolean collsion = cap.isCollision(cap.getHitBox(),player.getHitBox());

            if(collsion){
                String power = cap.power;

                System.out.println(power);

                ht.Insert(power);

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
        }
        
        else if(enemy.isATK == true && enemy.shootBullet == true){
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

    void isDone(){
        if(enemy.done){
            enemy.hp -= player.damage*2;

            if(enemy.hp <= 0){

                int webCurr = player.web;
                int n = 20 - webCurr;

                player.web += n;
                player.damage += 5;
                player.speed += 1;
                player.jumpPower += 5;

                if(player.level != player.maxLevel){
                    player.level += 1;
                    levelFake += 1;
                }
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

        // state
        if(player.level == 1){
            g.drawImage(state1,0,0,900,580,this);
        }else{
            g.drawImage(state2,0,0,900,580,this);
        }

        // bullet player
        for(int i=0;i<bullets.size();i++){
            Bullet bullet = bullets.get(i);

            if(bullet.isTimeOut()){
                bullets.remove(i);
            }else{
                if(inazuma.henshin){
                    g.setColor(new Color(242, 255, 82));
                }else{
                    g.setColor(Color.RED);
                }
                
                g.fillRect(bullet.x,bullet.y,10,10);

                // hitbox bullet
                //g.setColor(Color.BLUE);
                //g.drawRect(bullet.x-3,bullet.y-3,15,15);

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

        // henshin
        if(inazuma.henshin && inazuma.completed != 250){
            if(idxHenshin > 16)
                idxHenshin = 1;

            Image effect = loadImage(location + "effects/glitter" +(int)idxHenshin+".png");

            g.drawImage(effect,player.x-27,player.y+40,100,100,this);

            idxHenshin+=0.5;
        }

        // player & enemy
        g.drawImage(curr,player.x,player.y,player.size,player.size,this);
        
        g.drawImage(enemy.enemy,enemy.x,enemy.y,enemy.size,enemy.size,this);


        // object
        if(player.level == 2){
            // hitbox object
            //g.setColor(Color.BLUE);
            //g.drawRect(obj1.x,obj1.y+75,obj1.width-9,obj1.height-80);

            g.drawImage(car,obj1.x,obj1.y,obj1.width,obj1.height,this);

            boolean donee = obj1.isCollision(obj1.getHitBox(),player.getHitBox()); // check object collision

            if(donee) {
                if(player.hp <= 0){
                    player.dead = true;
                }
                player.hp -= 1;
            }
        }

        if(player.level >= 3){
            g.drawImage(drone,obj2.x,obj2.y,obj2.width,obj2.height,this);

            if(fire){
                g.setColor(new Color(255, 117, 43));
                g.fillRect(missile.x,missile.y,10,10);
            }
        }

        // hitbox person
        //g.setColor(Color.RED);
        //g.drawRect(player.x-7,player.y+50,65,100);

        //g.setColor(Color.BLUE);
        //g.drawRect(enemy.x-7,enemy.y+50,65,100);

        // capsem
        if(capsem.status){
            Capsem cap = capsem.arr[capsem.front];
            g.drawImage(cap.capsem,cap.x,cap.y,cap.size,cap.size,this);

            // hitbox capsem
            //g.setColor(Color.BLUE);
            //g.drawRect(cap.x,cap.y,17,20);

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
        if(player.level == 1){
            g.setColor(Color.BLACK);
        }else{
            g.setColor(Color.WHITE);
        }
        g.drawString("Web Shooter " + player.web + "/" + player.maxWeb,10,20);
        g.drawString("Level " + levelFake,10,50);
        g.drawString("Streght " + player.damage,10,80);

        g.drawImage(iconBlank,10,100,40,40,this); // pocket dimension

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

            for(int j=1;j<=3;j++){
                dX = 0;
                dY = 100 * (j-1);

                for(int i=1;i<=3;i++){
                    g.setColor(Color.GRAY);
                    g.fillRect(200*i,100*j,85,85);

                    g.setColor(Color.WHITE);
                    g.fillRect(200*i,100*j,80,80);

                    g.setColor(Color.BLACK);
                    g.drawString(""+idx,200*i,100*j+10);


                    if(idx <= ht.cap && ht.arr[idx-1].head != null){
                        String powerName = ht.arr[idx-1].head.power;
                        Image icon = null;

                        if(powerName == "Recovery"){
                            icon = iconRecovery;
                        }else if(powerName == "Impact"){
                            icon = iconImpact;
                        }else if(powerName == "Stream"){
                            icon = iconStream;
                        }else if(powerName == "Inazuma"){
                            icon = iconInazuma;
                        }

                        g.drawImage(icon,222+dX,118+dY,45,40,this);

                        g.setColor(Color.RED);
                        g.drawString("x"+ht.Search(ht.arr[idx-1].head.power),260+dX,165+dY);

                    }

                    if(frameSelected == idx && ht.arr[idx-1].head != null){

                        powerSelected = ht.arr[idx-1].head.power;
                        g.setColor(Color.RED);
                        g.drawRect(222+dX,118+dY,40,40);
                    }

                    ++idx;
                    dX+=200;

                }
            }
        }

        if(gameOver){
            g.setColor(Color.RED);
            g.drawString("MISSION FAILED",400,100);
        }

        /*
        else if(player.level == player.maxLevel){
            g.setColor(Color.GREEN);
            g.drawString("MISSION COMPLETED",400,100);
        }
        */
    }
}