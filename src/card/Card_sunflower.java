package card;
/**
 * this class extends from card and its representation of Card_sunflower
 */
public class Card_sunflower extends Card {
    public Card_sunflower(int x, int y, boolean isUsed){
        super(isUsed, x, y,".\\images\\Cards\\card_sunflower.png", ".\\images\\Cards\\card_sunflower_inactive.png");
        setRebuildTime(7.5);
    }
}
