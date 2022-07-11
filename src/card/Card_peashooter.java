package card;
/**
 * this class extends from card and its representation of Card_Peashooter
 */
public class Card_peashooter extends Card {
    public Card_peashooter(int x, int y, boolean isUsed){
        super(isUsed, x, y,".\\images\\Cards\\card_peashooter.png", ".\\images\\Cards\\card_peashooter_inactive.png");
        setRebuildTime(7.5);
    }
}
