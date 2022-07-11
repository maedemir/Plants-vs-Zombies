/**
 * this class represents a giant wall nut in the game which blocks the pathway of the zombies.
 */
public class GiantWallNut extends Plant {

    public GiantWallNut(int startX, int startY) {
        super(150, 50, startX, startY);
        setLivingImage(".\\images\\Gifs\\walnut_full_life");
        setDyingImage(".\\images\\Gifs\\walnut_dead");
    }
}
