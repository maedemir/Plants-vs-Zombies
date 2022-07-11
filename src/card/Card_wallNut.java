package card;
/**
 * this class extends from card and its representation of Card_wallNut
 */
public class Card_wallNut extends Card {
    public Card_wallNut(int x, int y, boolean isUsed){
        super(isUsed, x, y,".\\images\\Cards\\card_wallnut.png", ".\\images\\Cards\\card_wallnut_inactive.png");
        setRebuildTime(30);
    }
}
