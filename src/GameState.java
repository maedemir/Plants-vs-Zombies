import card.*;
import zombies.BucketHeadZombie;
import zombies.ConeHeadZombie;
import zombies.NormalZombie;
import zombies.Zombie;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;

/**
 * game state shows state of game !
 * it is consist of plants , zombies and other stuff in the board game
 * this class is mainly used within game panel (which also is the main class to control the game)
 * @author Maede Mirzazadeh , Mohadese Atyabi
 */
public class GameState implements Serializable {
    private int sunScore;
    private HashMap<Integer,ArrayList<NormalZombie>> NormalZombies;
    private HashMap<Integer,ArrayList<ConeHeadZombie>>ConeHeadZombies;
    private HashMap<Integer,ArrayList<BucketHeadZombie>> BucketHeadZombies;
    private Defender[] defenders;
    private ArrayList<Sun1> activeSuns;
    private LawnMower[] lawnMowers;
    private ArrayList<Pea> activePeas;
    private ArrayList<FreezePea> activeFreezePeas;
    //when you click on a active card , its correspondent plant will be saved  here till you plant it sw.
    private Plant activeBrushPlant;
    private int NormalOrHard;
    //shows latest usage of each card
    private long[] latestUsed;
    private Card[] cards;
    private GamePanel gamePanel;
    //clock shows how much time has been passed from the beginning of the game;
    private int clock;
    //When it reaches 20, plants that hit the zombie will loose a part of their life(based on zombie damage)
    private int secondCounter1=0;
    private int secondCounter2=0;
    private int secondCounter3=0;

    //for timing producing new zombies!
    private int zombieProduceCounter = 50000;

    public GameState(int NormalOrHard, GamePanel gamePanel){
        this.gamePanel = gamePanel;
        clock=50;
        sunScore = 100;
        this.NormalOrHard = NormalOrHard;
        activeBrushPlant = null;

        latestUsed = new long[6];
        defenders = new Defender[45];
        cards = new Card[6];
        lawnMowers = new LawnMower[5];

        NormalZombies = new HashMap<>();
        ConeHeadZombies = new HashMap<>();
        BucketHeadZombies = new HashMap<>();
        activePeas = new ArrayList<>();
        activeFreezePeas = new ArrayList<>();
        activeSuns = new ArrayList<>();

        for (int i = 0; i < 5; i++) {
            NormalZombies.put(i,new ArrayList<NormalZombie>());
            ConeHeadZombies.put(i,new ArrayList<ConeHeadZombie>());
            BucketHeadZombies.put(i,new ArrayList<BucketHeadZombie>());
        }

        for (int i=0 ; i<45 ; i++){
            defenders[i] = new Defender();
        }

        //placing initial elements
        setCards();
        setLawnMowers();
    }

    /**
     * this method set the initial place of cards in panel and set their first latest usage to a negative number
     * so that when you first click on a card , it is usable
     */
    private void setCards(){
        cards[0] = new Card_sunflower(110,8,false);
        cards[1] = new Card_peashooter(175,8,false);
        cards[2] = new Card_freezePeashooter(240,8,false,NormalOrHard);
        cards[3] = new Card_wallNut(305,8,false);
        cards[4] = new Card_cherryBomb(370,8,false,NormalOrHard);
        cards[5] = new Card_chomper(435,8,false,NormalOrHard);
        //we initialize latest used to negative long so that cards are active for first usage in new game!
        latestUsed[0] = -(long) (cards[0].getRebuildTime()*1000);
        latestUsed[1] = -(long) (cards[1].getRebuildTime()*1000);
        latestUsed[2] = -(long) (cards[2].getRebuildTime()*1000);
        latestUsed[3] = -(long) (cards[3].getRebuildTime()*1000);
        latestUsed[4] = -(long) (cards[4].getRebuildTime()*1000);
        latestUsed[5] = -(long) (cards[5].getRebuildTime()*1000);
    }

    /**
     * sets initial location of lawn mowers
     */

    private void setLawnMowers(){
        lawnMowers[0] = new LawnMower(130, this);
        lawnMowers[1] = new LawnMower(253, this);
        lawnMowers[2] = new LawnMower(376, this);
        lawnMowers[3] = new LawnMower(499, this);
        lawnMowers[4] = new LawnMower(622, this);
    }

    public Card[] getCards() {
        return cards;
    }


    /**
     * this method sets active brush plant if you have enough sun score to use the card
     * @param plant plant to be planted
     */
    public void setActiveBrushPlant(Plant plant){
        if (getSunScore() >= plant.getNeededSun()){
            activeBrushPlant = plant;
        }
    }

    
    /**
     * Adds a sun to the list
     * @param sun Sun to be added
     */
    public void addSun(Sun1 sun){ activeSuns.add(sun); }

    /**
     * for adding a new zombie to list of zombies
     * @param z new zombie to be added
     */
    public void addZombie(Zombie z, int index){
        switch (index){
            case 0:
                NormalZombies.get(z.getPosY()).add((NormalZombie)z);
                break;
            case 1:
                ConeHeadZombies.get(z.getPosY()).add((ConeHeadZombie)z);
                break;
            case 2:
                BucketHeadZombies.get(z.getPosY()).add((BucketHeadZombie)z);
                break;
        }
    }

    /**
     * when you click at a sun it will increase total score and sun will be removed after
     * @param sun clicked sun
     */
    public void sunClicked(Sun1 sun){
        sunScore += 25;
        activeSuns.remove(sun);
    }

    /**
     * add new sun to list of active suns(when a sun is produced this method is called)
     * @param sun1 sun to be added
     */
    public void addActiveSuns(Sun1 sun1){
        activeSuns.add(sun1);
        gamePanel.add(sun1, Integer.valueOf(1));
        sun1.addMouseListener(gamePanel.getSunHandler());
    }


    /**
     * this method is called whenever a planting is going to happen and plant the active brush in given location
     * (if it is not null of course) and then sets latest usage of each card and decrease sunscore
     * @param x x location of planting
     * @param y y location of planting
     * @return true if planting is done ! false if correspondent defender in not empty cell
     */
    public boolean planting(int x, int y){
        if(defenders[x+y*9].getAssignedDefender()!=null){
            return false;
        }
        if (activeBrushPlant != null){
            if (activeBrushPlant instanceof Sunflower) {
                if (getSunScore() >= 50) {
                    latestUsed[0] =clock;
                    Sunflower sunflower = new Sunflower(NormalOrHard,x,y,this);
                    sunflower.addSun();
                    defenders[x + y * 9].setAssignedDefender(sunflower);
                    sunScore -= sunflower.getNeededSun();
                }
            }
            if (activeBrushPlant instanceof PeaShooter) {
                if (getSunScore() >= 100) {
                    latestUsed[1] = clock;
                    PeaShooter peaShooter = new PeaShooter(x,y,this);
                    peaShooter.addPea();
                    defenders[x + y * 9].setAssignedDefender(peaShooter);
                    sunScore -= peaShooter.getNeededSun();
                }
            }

            if (activeBrushPlant instanceof SnowPeaShooter) {
                if (getSunScore() >= 175) {
                    latestUsed[2] = clock;
                    SnowPeaShooter snowPeaShooter = new SnowPeaShooter(x,y,this);
                    snowPeaShooter.addFreezePea();
                    defenders[x + y * 9].setAssignedDefender(snowPeaShooter);
                    sunScore -= snowPeaShooter.getNeededSun();
                }
            }

            if (activeBrushPlant instanceof GiantWallNut) {
                if (getSunScore() >= 50) {
                    latestUsed[3] = clock;
                    GiantWallNut giantWallNut = new GiantWallNut(x,y);
                    defenders[x + y * 9].setAssignedDefender(giantWallNut);
                    sunScore -= giantWallNut.getNeededSun();
                }
            }

            if (activeBrushPlant instanceof CherryBomb) {
                if (getSunScore() >= 150) {
                    latestUsed[4] = clock;
                    CherryBomb cherryBomb = new CherryBomb(x,y);
                    defenders[x + y * 9].setAssignedDefender(cherryBomb);
                    sunScore -= cherryBomb.getNeededSun();
                }
            }
            if (activeBrushPlant instanceof Chomper) {
                if (getSunScore() >= 150) {
                    latestUsed[5] = clock;
                    Chomper chomper = new Chomper(x,y,NormalOrHard);
                    defenders[x + y * 9].setAssignedDefender(chomper);
                    sunScore -= chomper.getNeededSun();
                }
            }

        }
        activeBrushPlant = null;
        return true;
    }


    /**
     * this method controls movement of each active pea
     * when a pea reaches the zombie it will damage zombie and the pea will be removed
     * when a pea reached to end of panel without hitting a zombie it also will be removed!
     */

    public void movePea(){
        int row ;
        Iterator<Pea> peaIterator = activePeas.iterator();
        boolean isRemoved;
        while (peaIterator.hasNext()) {
            Pea p = peaIterator.next();
            isRemoved=false;
            row = (p.getStartY()-135)/120;
            if(!isRemoved){
                Iterator<NormalZombie> normalZombieIterator = NormalZombies.get(row).iterator();
                while (normalZombieIterator.hasNext()) {
                    NormalZombie z = normalZombieIterator.next();
                    if(Math.abs(z.getPosX()-p.getStartX())<16){
                        if(!z.decreaseLife(p.getDamage())){
                            normalZombieIterator.remove();
                        }
                        System.out.println("life normal:"+z.getLife());
                        peaIterator.remove();
                        isRemoved = true;
                        break;
                    }
                }
            }

            if(!isRemoved){
                Iterator<ConeHeadZombie> coneHeadZombieIterator = ConeHeadZombies.get(row).iterator();
                while (coneHeadZombieIterator.hasNext()){
                    ConeHeadZombie z = coneHeadZombieIterator.next();
                    if(Math.abs(z.getPosX()-p.getStartX())<16){
                        z.decreaseLife(p.getDamage());
                        //checks if zombie has cone or not
                        if(!z.hasCone()){
                            NormalZombie newZombie = new NormalZombie(z.getPosX(), z.getPosY());
                            newZombie.setLife(z.getLife());
                            NormalZombies.get(row).add(newZombie);
                            coneHeadZombieIterator.remove();
                        }
                        System.out.println("life cone :"+z.getLife());
                        peaIterator.remove();
                        isRemoved=true;
                        break;

                    }
                }
            }

            if(!isRemoved){
                Iterator<BucketHeadZombie> bucketHeadZombieIterator = BucketHeadZombies.get(row).iterator();
                while (bucketHeadZombieIterator.hasNext()) {
                    BucketHeadZombie z = bucketHeadZombieIterator.next();
                    if(Math.abs(z.getPosX()-p.getStartX())<16){
                        z.decreaseLife(p.getDamage());
                        //checks if zombie has Bucket or not
                        if(!z.hasBucket()){
                            NormalZombie newZombie = new NormalZombie(z.getPosX(), z.getPosY());
                            newZombie.setLife(z.getLife());
                            NormalZombies.get(row).add(newZombie);
                            bucketHeadZombieIterator.remove();
                        }
                        System.out.println("life bucket :"+z.getLife());
                        peaIterator.remove();
                        isRemoved=true;
                        break;
                    }
                }
            }

            //updates location of pea
            p.update();
            // peas that reach to the end to panel will remove

            if(p.getStartX()>=1000){
                peaIterator.remove();
            }
        }
    }


    /**
     * this method controls movement of each active freeze pea
     * when a freeze pea reaches the zombie it will damage zombie and freeze pea will be removed
     * when a freeze  pea reached to end of panel without hitting a zombie it also will be removed!
     */

    public void moveFreezePea(){
        int row ;
        boolean isRemoved;
        Iterator<FreezePea> freezePeaIterator = activeFreezePeas.iterator();

        while (freezePeaIterator.hasNext()) {

            FreezePea p = freezePeaIterator.next();
            isRemoved = false;
            row = (p.getStartY()-135)/120;

            if (!isRemoved) {
                Iterator<NormalZombie> normalZombieIterator = NormalZombies.get(row).iterator();
                while (normalZombieIterator.hasNext()) {
                    NormalZombie z = normalZombieIterator.next();
                    if (Math.abs(z.getPosX() - p.getStartX()) < 16) {
                        if (!z.decreaseLife(p.getDamage())) {
                            normalZombieIterator.remove();
                        }
                        System.out.println("life normal:" + z.getLife());
                        freezePeaIterator.remove();
                        isRemoved=true;
                        break;
                    }
                }
            }

            if (!isRemoved) {
                Iterator<ConeHeadZombie> coneHeadZombieIterator = ConeHeadZombies.get(row).iterator();
                while (coneHeadZombieIterator.hasNext()) {
                    ConeHeadZombie z = coneHeadZombieIterator.next();
                    if (Math.abs(z.getPosX() - p.getStartX()) < 16) {
                        z.decreaseLife(p.getDamage());
                        //checks if zombie has cone or not
                        if (!z.hasCone()) {
                            NormalZombie newZombie = new NormalZombie(z.getPosX(), z.getPosY());
                            newZombie.setLife(z.getLife());
                            NormalZombies.get(row).add(newZombie);
                            coneHeadZombieIterator.remove();
                        }
                        System.out.println("life cone :" + z.getLife());
                        freezePeaIterator.remove();
                        isRemoved=true;
                        break;

                    }
                }
            }

            if (!isRemoved) {
                Iterator<BucketHeadZombie> bucketHeadZombieIterator = BucketHeadZombies.get(row).iterator();
                while (bucketHeadZombieIterator.hasNext()) {
                    BucketHeadZombie z = bucketHeadZombieIterator.next();
                    if (Math.abs(z.getPosX() - p.getStartX()) < 16) {
                        z.decreaseLife(p.getDamage());
                        //checks if zombie has Bucket or not
                        if (!z.hasBucket()) {
                            NormalZombie newZombie = new NormalZombie(z.getPosX(), z.getPosY());
                            newZombie.setLife(z.getLife());
                            NormalZombies.get(row).add(newZombie);
                            bucketHeadZombieIterator.remove();
                        }
                        System.out.println("life bucket :" + z.getLife());
                        freezePeaIterator.remove();
                        isRemoved=true;
                        break;
                    }
                }
            }
            // freeze peas that reach to the end to panel will remove
            p.update();
            if(p.getStartX()>=1000){
                freezePeaIterator.remove();
            }
        }

    }

    /**
     * this method is for handling general movement of zombies;
     * first it checks if zombie had reached the end of yard , if it did , uses lawn mowers to kill zombie
     * @return  if there is no lawn mower to do this , it return false (which means game over state )
     * else it return true
     */
    public boolean moveZombie(){

        for (int i=0; i<5 ; i++){
            Iterator<NormalZombie> normalZombieIterator = NormalZombies.get(i).iterator();
            while (normalZombieIterator.hasNext()) {
                NormalZombie z = normalZombieIterator.next();
                if (z.getPosX()==0){
                    if (lawnMowers[z.getPosY()]!=null) {
                        gamePanel.getPlaySound().playLawnmower();
                        lawnMowers[z.getPosY()].setRunning(true);
                        normalZombieIterator.remove();
                    }else {
                        return false;
                    }
                }

                for (int j = z.getPosY()*9 ; j < 9*(z.getPosY()+1) ; j++) {
                    if(defenders[j].getAssignedDefender()!=null && defenders[j].isInsideDefender(z.getPosX())){
                        if(defenders[j].getAssignedDefender() instanceof Chomper){
                            Chomper chomper = (Chomper) defenders[j].getAssignedDefender();
                            if(!chomper.isEating()){
                                normalZombieIterator.remove();
                                chomper.setEating(true);
                                chomper.setCounter(0);
                                chomper.setDigestTime(z.getLife()/chomper.getDamage());
                            }
                        }
                        z.setRunning(false);
                        if(z.getEatingCounter()==20){
                            gamePanel.getPlaySound().playChomp();
                            if(!defenders[j].getAssignedDefender().subtractLife(z.getDestroyPower())){
                                defenders[j].setAssignedDefender(null);
                                z.setRunning(true);
                                gamePanel.getPlaySound().stopChomp();
                            }
                            z.resetCounter();
                        }
                        z.addEatingCounter();
                    }else if(defenders[j].getAssignedDefender()==null &&
                            defenders[j].isInsideDefender(z.getPosX())&&
                    !z.isRunning()){
                        z.setRunning(true);
                        gamePanel.getPlaySound().stopChomp();
                        z.resetCounter();
                    }

                }
            }

            Iterator<ConeHeadZombie> coneHeadZombieIterator = ConeHeadZombies.get(i).iterator();
            while (coneHeadZombieIterator.hasNext()) {
                ConeHeadZombie z = coneHeadZombieIterator.next();
                if (z.getPosX()==0){
                    if (lawnMowers[z.getPosY()]!=null) {
                        gamePanel.getPlaySound().playLawnmower();
                        lawnMowers[z.getPosY()].setRunning(true);
                        coneHeadZombieIterator.remove();
                    }else {
                        return false;
                    }
                }

                for (int j = z.getPosY()*9 ; j < 9*(z.getPosY()+1) ; j++) {
                    if(defenders[j].getAssignedDefender()!=null && defenders[j].isInsideDefender(z.getPosX())){
                        if(defenders[j].getAssignedDefender() instanceof Chomper){
                            Chomper chomper = (Chomper) defenders[j].getAssignedDefender();
                            if(!chomper.isEating()){
                                coneHeadZombieIterator.remove();
                                chomper.setEating(true);
                                chomper.setCounter(0);
                                chomper.setDigestTime(z.getLife()/chomper.getDamage());
                            }
                        }
                        z.setRunning(false);
                        if(z.getEatingCounter()==20){
                            gamePanel.getPlaySound().playChomp();
                            if(!defenders[j].getAssignedDefender().subtractLife(z.getDestroyPower())){
                                defenders[j].setAssignedDefender(null);
                                z.setRunning(true);
                                gamePanel.getPlaySound().stopChomp();
                            }
                            z.resetCounter();
                        }
                        z.addEatingCounter();
                    }else if(defenders[j].getAssignedDefender()==null &&
                            defenders[j].isInsideDefender(z.getPosX())&&
                            !z.isRunning()){
                        z.setRunning(true);
                        gamePanel.getPlaySound().stopChomp();
                        z.resetCounter();
                    }
                }

            }

            Iterator<BucketHeadZombie> bucketHeadZombieIterator = BucketHeadZombies.get(i).iterator();
            while (bucketHeadZombieIterator.hasNext()) {
                BucketHeadZombie z = bucketHeadZombieIterator.next();
                if (z.getPosX()==0){
                    if (lawnMowers[z.getPosY()]!=null) {
                        gamePanel.getPlaySound().playLawnmower();
                        lawnMowers[z.getPosY()].setRunning(true);
                        bucketHeadZombieIterator.remove();
                    } else {
                        return false;
                    }
                }

                for (int j = z.getPosY()*9 ; j < 9*(z.getPosY()+1) ; j++) {
                    if(defenders[j].getAssignedDefender()!=null && defenders[j].isInsideDefender(z.getPosX())){
                        if(defenders[j].getAssignedDefender() instanceof Chomper){
                            Chomper chomper = (Chomper) defenders[j].getAssignedDefender();
                            if(!chomper.isEating()){
                                bucketHeadZombieIterator.remove();
                                chomper.setEating(true);
                                chomper.setCounter(0);
                                chomper.setDigestTime(z.getLife()/chomper.getDamage());
                            }
                        }
                        z.setRunning(false);
                        if(z.getEatingCounter()==20){
                            gamePanel.getPlaySound().playChomp();
                            if(!defenders[j].getAssignedDefender().subtractLife(z.getDestroyPower())){
                                defenders[j].setAssignedDefender(null);
                                z.setRunning(true);
                                gamePanel.getPlaySound().stopChomp();
                            }
                            z.resetCounter();
                        }
                        z.addEatingCounter();
                    }else if(defenders[j].getAssignedDefender()==null &&
                            defenders[j].isInsideDefender(z.getPosX())&&
                            !z.isRunning()){
                        z.setRunning(true);
                        gamePanel.getPlaySound().stopChomp();
                        z.resetCounter();
                    }

                }
            }
        }
        return true;
    }


    /**
     * controls explosion of a planted cherry bomb using a timer
     * when timer reaches 1000 milliseconds all the zombies in a 3*3 area around cherry bomb
     * @param cherryCounter counter of exploding the bomb
     * @param cherryBomb planted cherry bomb
     *
     */

    public void cherryBomb(int cherryCounter, CherryBomb cherryBomb){
        if (cherryCounter == 1000){
            int row = cherryBomb.getY();
            int posX = (cherryBomb.getX()-1)*100 + 44;

            for (int i=(row>=1 ? row-1 : row); i<(row<=3 ? row+2 : row+1) ; i++){
                Iterator<NormalZombie> normalZombieIterator = NormalZombies.get(i).iterator();
                while (normalZombieIterator.hasNext()) {
                    NormalZombie z = normalZombieIterator.next();
                    System.out.println(z.getPosX() - posX);
                    if (z.getPosX() - posX >= 0 && z.getPosX() - posX <= 250) {
                        normalZombieIterator.remove();
                    }
                }

                Iterator<ConeHeadZombie> coneHeadZombieIterator = ConeHeadZombies.get(i).iterator();
                while (coneHeadZombieIterator.hasNext()) {
                    ConeHeadZombie z = coneHeadZombieIterator.next();
                    System.out.println(z.getPosX() - posX);
                    if (z.getPosX() - posX >= 0 && z.getPosX() - posX <= 250) {
                        coneHeadZombieIterator.remove();
                    }
                }

                Iterator<BucketHeadZombie> bucketHeadZombieIterator = BucketHeadZombies.get(i).iterator();
                while (bucketHeadZombieIterator.hasNext()) {
                    BucketHeadZombie z = bucketHeadZombieIterator.next();
                    System.out.println(z.getPosX() - posX);
                    if (z.getPosX() - posX >= 0 && z.getPosX() - posX <= 250) {
                        bucketHeadZombieIterator.remove();
                    }
                }
            }

        }
    }

    /**
     * this method turn the total milliseconds that have been passed from the beginning of the game to a string that
     * shows the clock
     * @param totalSecs total milliseconds that have been passed from the beginning
     * @return string that shows the clock
     */
    public String makeClock(int totalSecs){
        int hours = totalSecs / 3600000;
        int minutes = (totalSecs % 3600000) / 60000;
        int seconds = (totalSecs % 60000)/1000;
        return String.format("%02d:%02d:%02d", hours, minutes, seconds);
    }

    public HashMap<Integer, ArrayList<NormalZombie>> getNormalZombies() {
        return NormalZombies;
    }

    public HashMap<Integer, ArrayList<ConeHeadZombie>> getConeHeadZombies() {
        return ConeHeadZombies;
    }

    public HashMap<Integer, ArrayList<BucketHeadZombie>> getBucketHeadZombies() {
        return BucketHeadZombies;
    }

    public int getClock() {
        return clock;
    }

    public void setClock(int clock) {
        this.clock = clock;
    }

    public int getZombieProduceCounter() {
        return zombieProduceCounter;
    }

    public void setZombieProduceCounter(int zombieProduceCounter) {
        this.zombieProduceCounter += zombieProduceCounter;
    }

    public int getSunScore(){ return sunScore; }

    public void addPea(Pea pea){ activePeas.add(pea); }

    public ArrayList<Pea> getActivePea(){ return activePeas; }

    public void addFreezePea(FreezePea pea){ activeFreezePeas.add(pea); }

    public ArrayList<FreezePea> getActiveFreezePea(){ return activeFreezePeas; }

    public ArrayList<Sun1> getActiveSuns(){ return activeSuns; }

    public long getLatestUsed(int index){ return latestUsed[index]; }

    public LawnMower[] getLawnMowers() {
        return lawnMowers;
    }
    public Defender[] getDefenders(){ return defenders; }
}
