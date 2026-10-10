class Drone extends Object{
    // field
    boolean move = false;

    // constructor
    Drone(int x,int y,int width,int height,int speed){
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
        this.speed = speed;
    }

    // method
    @Override
    void move(){
        if(move == false){
            if(x > 0){
                Left();
            }else{
                move = true;
            }
        }else{
            if(x < 820){
                Right();
            }else{
                x = 820;
                move = false;
            }
        }
    }

    void Right(){
        x += speed;
    }

    void Left(){
        x -= speed;
    }
}