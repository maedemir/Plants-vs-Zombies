package zombies;


/**
 * @author  Maedeh Mirzazadeh mohadese atyabi
 * BucketHead zombie is a type of zombie with bucket on it's head. this class is representation of it!
 */
public class BucketHeadZombie extends Zombie {
    private String BucketHeadImage;
    private int NormalOrHard;
    private int movingCounter=0;
    public BucketHeadZombie(int HardOrNormal, int posX, int posY){
        super(posX,posY);
        this.setLife(1300);
        this.NormalOrHard = HardOrNormal;
        BucketHeadImage = ".\\images\\Gifs\\bucketHeadZombie.gif";
        switch (HardOrNormal){
            case 0 :
                this.setSpeed(3.5);
                this.setDestroyPower(20);
                break;
            case 1:
                this.setSpeed(3);
                this.setDestroyPower(25);
                break;
        }
    }

    /**
     * this method shows if zombie still has bucket or not
     * @return true if it has bucket , false if not!
     */
    public boolean hasBucket(){
        if(this.getLife()>=200)
            return true;
        return false;
    }

    /**
     * this method controls general location of a zombie based of its speed
     * it will move zombie 10 pixels if counter reaches to multiples of speed*1000
     */
    public void update(){
        if((movingCounter%(NormalOrHard==0?350:300)==0)){
            if(getPosX()>0 && isRunning() ){
                setPosX(getPosX()-10);
            }
        }

    }

    /**
     * each time increase the timer 50 milliseconds
     */
    public void updateCounter(){
        movingCounter+=50;
        update();
    }

    public String getBucketHeadImage() {
        return BucketHeadImage;
    }


}
