package card;

/**
 * this class extends from card and its representation of Card_cherryBomb
 */
public class Card_cherryBomb extends Card {
    public Card_cherryBomb(int x, int y, boolean isUsed, int HardOrNormal){
        super(isUsed, x, y,".\\images\\Cards\\card_cherrybomb.png",
                ".\\images\\Cards\\card_cherrybomb_inactive.png");
        switch (HardOrNormal) {
            case 0:  setRebuildTime(30);break;
            case 1 : setRebuildTime(45);break;
        }
    }
}
