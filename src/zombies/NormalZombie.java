package zombies;

/**
 * @author  Maedeh Mirzazadeh mohadese atyabi
 * normal zombie is a type of zombie. this class is representation of it!
 */
public class NormalZombie extends Zombie {
    private String normalZombieImage;
    //controls the speed of zombie when moving
    private int movingCounter;
    public NormalZombie(int posX, int posY){
        super(posX, posY);
        this.setDestroyPower(5);
        this.setLife(200);
        this.setSpeed(4);
        normalZombieImage = ".\\images\\Gifs\\normalZombie.gif";
    }

    /**
     * this method controls general location of a zombie based of its speed
     * it will move zombie 10 pixels if counter reaches to multiples of speed*1000
     */
    public void update(){
        if(movingCounter%400==0){
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

    public String getNormalZombieImage() {
        return normalZombieImage;
    }

}
