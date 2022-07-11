/**
 * this class represents a snow pea shooter in the game which produces freeze pea each seconds.
 * when a snow pea shooter is planted n the yard, it starts producing freeze pea itself.
 */
public class SnowPeaShooter extends Plant {
    private GameState gameState ;
    private int counter;

    public SnowPeaShooter(int startX, int startY, GameState gameState) {
        super(100, 175, startX, startY);
        this.gameState = gameState;
        setLivingImage(".\\images\\Gifs\\freezepeashooter");
    }

    /**
     * updates the counter of a new freeze pea to the game state active freeze peas
     */
    public void updateCounter(){
        counter+=50;
        addFreezePea();
    }

    /**
     * add a new freeze pea to active freeze peas of game state if the counter is a multiply of 1000
     */
    public void addFreezePea(){
        if(counter%(1000)==0){
            FreezePea freezePea = new FreezePea(115 + this.getX() * 100, 135 + this.getY() * 120);
            gameState.addFreezePea(freezePea);
        }
    }
}
