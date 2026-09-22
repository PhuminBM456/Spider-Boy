class Player extends Person{
    // field
    final int maxWeb = 20;
    int web = 20;

    // constructor
    Player(){
        super(true);

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

    @Override
    void Walk(){}
}