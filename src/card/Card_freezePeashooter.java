package card;

/**
 * this class extends from card and its representation of Card_freezePeashooter
 */
public class Card_freezePeashooter extends Card {
    public Card_freezePeashooter(int x, int y, boolean isUsed, int HardOrNormal){
        super(isUsed, x, y,".\\images\\Cards\\card_freezepeashooter.png", ".\\images\\Cards\\card_freezepeashooter_inactive.png");
        switch (HardOrNormal){
            case 0:
                setRebuildTime(7.5);
                break;
            case 1:
                setRebuildTime(30);
                break;
        }
    }
}
