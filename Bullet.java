class Bullet{
    // field
    int x,y,speed,endPos;

    // constructor
    Bullet(int x,int y,int speed){
        this.x = x+60;
        this.y = y+112;
        this.speed = speed;
        endPos = this.x + 400;
    }

    // method
    void Move(){
        x += speed;
    }

    boolean isTimeOut(){
        if(x >= endPos)
            return true;

        return false;
    }
}