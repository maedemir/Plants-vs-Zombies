/**
 * this class is a representation of pea shooters in the game !
 */

public class PeaShooter extends Plant {
    private GameState gameState ;
    private int counter;
    public PeaShooter(int startX, int startY, GameState gameState) {
        super(70, 100, startX, startY);
        this.gameState = gameState;
        setLivingImage(".\\images\\Gifs\\peashooter");
        setDyingImage(".\\images\\Gifs\\pea_shooter_dying");
    }

    /**
     * updates the counter of a new pea to the game state active peas
     */
    public void updateCounter(){
        counter+=50;
        addPea();
    }

    /**
     * add a new pea to active peas of game state if the counter is a multiply of 1000
     */
    public void addPea(){
        if(counter%1000 == 0){
            Pea pea = new Pea(115 + this.getX() * 100, 135 + this.getY() * 120);
            gameState.addPea(pea);
        }
    }
}
