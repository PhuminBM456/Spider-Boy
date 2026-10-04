class Player extends Person{
    // field
    final int maxWeb = 20;
    int web = 20;
    boolean takeDamage = false;
    boolean stream = false;

    // constructor
    Player(){
        this.x = 35;
        this.y = 400;
    }

    // method
    @Override
    void Attack(){
        if(web == 0)
            return;

        isATK = true;

        if(web!=0)
            web-=1;
    }

    @Override
    void Jump(){
        if(falling == false){
            if(y > 400-jumpPower){
                if(stream){
                    y -= 8;
                }else{
                    y -= 4;
                }

            }else{
                falling = true;
            }
        }else{
            if(y < 400){
                //System.out.println(y);

                if(stream){
                    y += 1;
                }else{
                    y += 4;
                }
            }else{
                y = 400;
                falling = false;
                jump = false;

                if(stream){
                    jumpPower -= 200;
                    stream = false;
                }
            }
        }
    }

    boolean playerDead(){
        return dead == true;
    }
}