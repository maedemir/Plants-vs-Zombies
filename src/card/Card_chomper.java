package card;
/**
 * this class extends from card and its representation of Card_chomper
 */
public class Card_chomper extends Card{
    public Card_chomper(int x, int y, boolean isUsed, int HardOrNormal){
        super(isUsed, x, y,".\\images\\Cards\\card_chomper.png",
                ".\\images\\Cards\\card_chomper_inactive.png");
        switch (HardOrNormal) {
            case 0:  setRebuildTime(42);break;
            case 1 : setRebuildTime(50);break;
        }
    }
}
