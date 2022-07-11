
/**
 * this class represents a sunflower in the game which produces sun.
 * this class is a kind of plat (extends plant class)
 * whenever a sunflower is planted, it starts producing sun itself.
 */
public class Sunflower extends Plant {
    private int NormalOrHard;
    private int sunProduceRate;
    private GameState gameState;
    //it handles the time to produce sun based on hardness of the game
    private int counter;

    public Sunflower(int NormalOrHard, int startX, int startY, GameState gameState) {
        super(50, 50, startX, startY);
        counter=50;
        this.gameState = gameState;
        setLivingImage(".\\images\\Gifs\\sun_flower");
        setDyingImage(".\\images\\Gifs\\sun_flower_dying");
        this.NormalOrHard = NormalOrHard;
        if (NormalOrHard==0){
            sunProduceRate = 20;
        } else if (NormalOrHard==1){
            sunProduceRate = 25;
        }
    }

    /**
     * updates the counter of the sunflower and calls the addSun method to produce sun.
     * this method is called every 50 milliseconds, so the counter will be increased 50 milliseconds.
     */
    public void updateCounter(){
        counter+=50;
        addSun();
    }

    /**
     * this method checks whether it is the time to produce sun or not.
     * the producing time is set based on hardness of the game.
     */
    public void addSun(){
        if(counter%(sunProduceRate*1000)==0){
            Sun1 sunProduced = new Sun1(60 + getX() * 100, 110 + getY() * 120,
                    165 + getY() * 120, NormalOrHard);
            gameState.addActiveSuns(sunProduced);
        }
    }

    public int getSunProduceRate() {
        return sunProduceRate;
    }
}
