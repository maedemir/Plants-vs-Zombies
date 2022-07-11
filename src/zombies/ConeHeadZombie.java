package zombies;

/**
 * @author  Maedeh Mirzazadeh mohadese atyabi
 * ConeHead zombie is a kind of zombie with cone on his head. this class is representation of it!
 */
public class ConeHeadZombie extends Zombie {
    private String coneHeadZombieImage;
    //controls the speed of zombie when moving
    private int movingCounter;
    //shows level of toughness of game
    private int NormalOrHard;
    public ConeHeadZombie(int HardOrNormal, int posX, int posY){
        super(posX,posY);
        this.setLife(560);
        this.NormalOrHard = HardOrNormal;
        coneHeadZombieImage = ".\\images\\Gifs\\coneHeadZombie.gif";
        switch (HardOrNormal){
            case 0 :
                this.setSpeed(3.5);
                this.setDestroyPower(10);
                break;
            case 1:
                this.setSpeed(3);
                this.setDestroyPower(15);
                break;
        }
    }

    /**
     * this method shows if zombie's cone is completely destroyed or not
     * @return true if it's not completely damaged , false if cone is totally ruined
     */
    public boolean hasCone(){
        if(getLife()>=200)
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

    public String getConeHeadZombieImage() {
        return coneHeadZombieImage;
    }


}

