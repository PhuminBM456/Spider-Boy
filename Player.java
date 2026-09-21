class Player extends Person{
    // constructor
    Player(){
        super(true);

        this.x = 20;
        this.y = 400;
    }

    // method
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