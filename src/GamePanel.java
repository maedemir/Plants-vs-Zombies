import
        card.Card;
import zombies.BucketHeadZombie;
import zombies.ConeHeadZombie;
import zombies.NormalZombie;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.Serializable;
import java.security.SecureRandom;

/**
 * this class represents the game panel of the game.
 * @author Mohadeseh Atyabi, Maedeh Mirzazadeh
 */
public class GamePanel extends JLayeredPane implements Serializable {

    private String background;
    private String sunflower;
    private String peaShooter;
    private String snowPeaShooter;
    private String cherryBomb;
    private String wallNut;
    private String sun;
    private String pea;
    private String freezePea;
    private String normalZombie;
    private String bucketZombie;
    private String coneHeadZombie;
    private String lawnMower;
    private String Chomper;
    private String eatingChomper;
    private Card card_sunflower;
    private Card card_peashooter;
    private Card card_freezePeashooter;
    private Card card_wallNut;
    private Card card_cherryBomb;
    private Card card_chomper;
    private Timer repaintTimer;
    private int sunScore = 0;
    private int NormalOrHard;
    private Defender[] defenders;
    private GameState gameState;
    //holds the counter of an active cherry
    private int cherryCounter = 0;
    //to hold the date
    private String dateOfGame;
    //for managing sounds of game
    private transient PlaySound playSound;
    //Client
    private transient Client client;

    public GamePanel(int NormalOrHard, boolean unmute, Client client){
        this.client = client;
        this.NormalOrHard = NormalOrHard;
        gameState = new GameState(NormalOrHard, this);
        setSize(1012,785);
        setImages();
        setDefenders();
        setCards();
        setLawnMowers();
        initTimers();
        startSound(unmute);
    }

    /**
     * starts the music of the game
     * @param unmute
     */
    public void startSound(boolean unmute){
        playSound = new PlaySound(unmute);
        playSound.playBackgroundSound();
    }

    /**
     * initializes image addresses
     */
    private void setImages(){
        background = ".\\images\\mainBG.png";
        sunflower =".\\images\\Gifs\\sun_flower.gif";
        peaShooter = ".\\images\\Gifs\\pea_shooter.gif";
        snowPeaShooter = ".\\images\\Gifs\\freezepeashooter.gif";
        cherryBomb = ".\\images\\Gifs\\newCherryBomb.gif";
        wallNut = ".\\images\\Gifs\\walnut_full_life.gif";
        sun = ".\\images\\Gifs\\sun.gif";
        pea =".\\images\\pea.gif";
        freezePea = ".\\images\\freezepea.gif";
        normalZombie = ".\\images\\Gifs\\zombie_normal.gif";
        bucketZombie = ".\\images\\Gifs\\bucketheadzombie.gif";
        coneHeadZombie = ".\\images\\Gifs\\coneheadzombie.gif";
        lawnMower = ".\\images\\Gifs\\lawn_mower.gif";
        Chomper = ".\\images\\Gifs\\chomper.gif";
        eatingChomper = ".\\images\\Gifs\\chomper_eating.gif";
    }

    /**
     * adds card of game state to the game panel and add their listener
     */
    private void setCards(){
        card_sunflower = gameState.getCards()[0];
        card_sunflower.addMouseListener(new CardHandler());
        add(card_sunflower,Integer.valueOf(1));

        card_peashooter = gameState.getCards()[1];
        card_peashooter.addMouseListener(new CardHandler());
        add(card_peashooter,Integer.valueOf(1));

        card_freezePeashooter = gameState.getCards()[2];
        card_freezePeashooter.addMouseListener(new CardHandler());
        add(card_freezePeashooter,Integer.valueOf(1));

        card_wallNut = gameState.getCards()[3];
        card_wallNut.addMouseListener(new CardHandler());
        add(card_wallNut,Integer.valueOf(1));

        card_cherryBomb = gameState.getCards()[4];
        card_cherryBomb.addMouseListener(new CardHandler());
        add(card_cherryBomb,Integer.valueOf(1));

        card_chomper= gameState.getCards()[5];
        card_chomper.addMouseListener(new CardHandler());
        add(card_chomper,Integer.valueOf(1));
    }

    /**
     * add lawn mowers of game state to the game panel
     */
    private void setLawnMowers(){
        for (LawnMower l : gameState.getLawnMowers()) {
            add(l,Integer.valueOf(1));
        }
    }

    /**
     * adds defenders of game state to the game panel
     * and sets their location , listener etc.
     */
    private void setDefenders(){
        defenders = gameState.getDefenders();
        for (int i=0 ; i<45 ; i++){
            Defender d = new Defender();
            d.setLocation(44 + (i % 9) * 100, 109 + (i / 9) * 120);
            d.addMouseListener(new PlantingHandler((i % 9), (i / 9)));
            defenders[i] = d;
            add(d, Integer.valueOf(0));
        }

    }

    /**
     * this method generates a random type of zombie in a random row and the add it to its related list
     */
    private void generateZombie(){
        SecureRandom randomRow = new SecureRandom();
        int row = randomRow.nextInt(5);
        SecureRandom randomZombieType = new SecureRandom();
        int index = randomZombieType.nextInt(3);
        switch (index){
            case 0:
                NormalZombie newZombie = new NormalZombie(1000,row);
                gameState.addZombie(newZombie,0);
                break;
            case 1:
                ConeHeadZombie newZombie2 = new ConeHeadZombie(NormalOrHard,
                        1000,row);
                gameState.addZombie(newZombie2,1);
                break;
            case 2:
                BucketHeadZombie newZombie3 = new BucketHeadZombie(NormalOrHard,
                        1000,row);
                gameState.addZombie(newZombie3,2);
                break;
        }
    }

    /**
     * this method is called only once in constructor , but it will repaint game panel each 50 milliseconds and also
     * chang the state of game each 50 milliseconds
     */

    public void initTimers(){
        //Repaint the panel
        repaintTimer = new Timer(50 , (ActionEvent e) -> {
            repaint();

            //adding to clock of system
            gameState.setClock((gameState.getClock()+ 50));

            //for adding new sun from sky
            addSunsFromSky();

            //for adding new sun to sunflower , pea to peashooter and freeze pea to freeze pea shooters
            for (Defender d : gameState.getDefenders()) {
                if(d.getAssignedDefender() instanceof Sunflower){
                    ((Sunflower) d.getAssignedDefender()).updateCounter();
                }
                if(d.getAssignedDefender() instanceof SnowPeaShooter){
                    ((SnowPeaShooter) d.getAssignedDefender()).updateCounter();
                }
                if(d.getAssignedDefender() instanceof PeaShooter){
                    ((PeaShooter) d.getAssignedDefender()).updateCounter();
                }
                if(d.getAssignedDefender() instanceof Chomper){
                    if(((Chomper) d.getAssignedDefender()).isEating()){
                        ((Chomper) d.getAssignedDefender()).updateCounter();
                        System.out.println("chomper is eating");
                    }
                }
            }

            //for moving zombies
            for(int i=0; i< 5 ; i++){
                for (NormalZombie z : gameState.getNormalZombies().get(i)) {
                    z.updateCounter();
                }

                for (ConeHeadZombie z : gameState.getConeHeadZombies().get(i)) {
                    z.updateCounter();
                }

                for (BucketHeadZombie z : gameState.getBucketHeadZombies().get(i)) {
                    z.updateCounter();
                }

            }


            //for moving active suns ( whether from the sky or sunflower)
            for (Sun1 temp : gameState.getActiveSuns()){
                temp.update();
            }

            //for rebuilding cards !
            int i=0;
            for (Card card : gameState.getCards()){
                card.setCanBeUsed(checkActiveCard(gameState.getLatestUsed(i), card));
                card.update();
                i++;
            }

            //for moving peas and freeze peas
            gameState.movePea();
            gameState.moveFreezePea();
            produceZombie();

            //moving lawn mowers
            LawnMower[] lawnMowers = gameState.getLawnMowers();
            for (i=0 ; i<5 ; i++) {
                if(lawnMowers[i]!=null && lawnMowers[i].isRunning()){
                    lawnMowers[i].update();
                    if(lawnMowers[i].getXPos()>=1000){
                        lawnMowers[i].setRunning(false);
                        lawnMowers[i] = null;
                    }
                }
            }

            //checking end of the game!
            if(gameState.getClock()==480000){
                repaintTimer.stop();
                JFrame topFrame = (JFrame) SwingUtilities.getWindowAncestor(this);
                playSound.stopBackground();
                topFrame.dispose();
                JOptionPane.showMessageDialog(null,"You won the game","",
                        JOptionPane.INFORMATION_MESSAGE);
                client.saveScore((NormalOrHard==0) ? 3 : 10);
                System.out.println("user score is:" +client.getUser().getScore());
                MenuFrame menuFrame = new MenuFrame(playSound.isUnmute(),client);
            }

            //checks game over
            if (!gameState.moveZombie()){
                repaintTimer.stop();
                JFrame topFrame = (JFrame) SwingUtilities.getWindowAncestor(this);
                playSound.stopBackground();
                playSound.playGameOver();
                PlaySound tempPlaySound = playSound;
                topFrame.dispose();
                JOptionPane.showMessageDialog(null,"Game Over!" +
                        "","",JOptionPane.INFORMATION_MESSAGE);
                client.saveScore((NormalOrHard==0) ? -1 : -3);
                System.out.println("user score is:" + client.getUser().getScore());
                MenuFrame menuFrame = new MenuFrame(playSound.isUnmute(),client);
                tempPlaySound.stopGameOver();
            }

        });
        repaintTimer.start();
    }


    /**
     * this method will produce zombies in proper time (based on clock) using generate zombie method
     */
    public void produceZombie(){
        if (gameState.getClock()==45000 || gameState.getClock()==145000 || gameState.getClock()==325000){
            playSound.playZombieComing();
        }
        if(gameState.getClock() >=50000 && gameState.getClock()<=150000){
            if(gameState.getClock()==gameState.getZombieProduceCounter()){
                generateZombie();
                if(gameState.getClock()==140000){
                    //difference between phase 1 and 2;
                    gameState.setZombieProduceCounter(10000);
                }
                gameState.setZombieProduceCounter(30000);
            }
        }else if(gameState.getClock()>150000 && gameState.getClock()<= 330000){
            if(gameState.getClock()==gameState.getZombieProduceCounter()){
                generateZombie();
                generateZombie();
                if(gameState.getClock()==330000){
                    //difference between phase 1 and 2;
                    gameState.setZombieProduceCounter(-5000);
                }
                gameState.setZombieProduceCounter(30000);
            }
        }else if(gameState.getClock()>330000 && gameState.getClock()<480000){
            if(gameState.getClock()==gameState.getZombieProduceCounter()){
                generateZombie();
                generateZombie();
                gameState.setZombieProduceCounter(25000);
            }
        }
    }

    /**
     * for producing and adding new sun to sky
     */
    public void  addSunsFromSky(){
        if(gameState.getClock()%25000 == 0 ){
            SecureRandom random = new SecureRandom();
            int xPosition = random.nextInt(850) + 40;
            Sun1 sun = new Sun1(xPosition , 107, random.nextInt(580)+107,  NormalOrHard);
            sun.addMouseListener(new SunHandler());
            gameState.addSun(sun);
            add(sun,Integer.valueOf(3));
        }
    }

    public void waite(){
       repaintTimer.stop();
    }

    public void resume(){
        repaintTimer.start();
    }

    /**
     * a class to handle clicking on the active suns
     */
    public class SunHandler extends MouseAdapter implements Serializable{
        @Override
        public void mouseClicked(MouseEvent e) {
            gameState.sunClicked((Sun1) e.getSource());
            playSound.playSunClicked();
            remove((Sun1) e.getSource());
        }
    }

    /**
     * a class to handle the mouse actions related to clicking on the cards
     */

    private class CardHandler extends MouseAdapter implements Serializable{
        @Override
        public void mouseClicked(MouseEvent e) {
            if (e.getSource().equals(card_sunflower)){
                if (checkActiveCard(gameState.getLatestUsed(0), card_sunflower)){
                    System.out.println("Sun Card");
                    gameState.setActiveBrushPlant(new Sunflower(NormalOrHard,0,0,gameState));
                }
            }
            else if (e.getSource().equals(card_cherryBomb)){
                if (checkActiveCard(gameState.getLatestUsed(4), card_cherryBomb)) {
                    System.out.println("Cherry Card");
                    gameState.setActiveBrushPlant(new CherryBomb(0, 0));
                }
            }
            else if (e.getSource().equals(card_freezePeashooter)){
                if (checkActiveCard(gameState.getLatestUsed(2), card_freezePeashooter)) {
                    System.out.println("Freeze Card");
                    gameState.setActiveBrushPlant(new SnowPeaShooter(0, 0, gameState));
                }
            }
            else if (e.getSource().equals(card_peashooter)){
                if (checkActiveCard(gameState.getLatestUsed(1), card_peashooter)) {
                    System.out.println("Pea Card");
                    gameState.setActiveBrushPlant(new PeaShooter(0, 0,gameState));
                }
            }
            else if (e.getSource().equals(card_wallNut)){
                if (checkActiveCard(gameState.getLatestUsed(3), card_wallNut)) {
                    System.out.println("Wallnut Card");
                    gameState.setActiveBrushPlant(new GiantWallNut(0, 0));
                }
            }
            else if (e.getSource().equals(card_chomper)){
                if (checkActiveCard(gameState.getLatestUsed(5), card_chomper)) {
                    System.out.println("chomper Card");
                    gameState.setActiveBrushPlant(new Chomper(0, 0,NormalOrHard));
                }
            }
        }
    }

    /**
     * This method shows if we can use the chosen card for planting or not.
     * (based on the time has been passed from the previous usage of the card till new usage)
     * @param activatedTime Previous time that the card has been used
     * @param card Chosen card
     * @return True if we can use it, false if not
     */
    private boolean checkActiveCard(long activatedTime, Card card){
        if ((gameState.getClock()- activatedTime)/1000.0 >= card.getRebuildTime()){
            return true;
        } else {
            return false;
        }
    }

    /**
     * this class handles clicking on the defenders to plant a plant
     */
    private class PlantingHandler extends MouseAdapter implements Serializable{
        int x,y;

        public PlantingHandler(int x, int y){
            this.x = x;
            this.y = y;
        }

        @Override
        public void mouseClicked(MouseEvent e) {
            gameState.planting(x,y);
        }
    }

    /**
     * paints the JPanels and images of the game whenever repaint method is called (in init timer method)
     */
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.drawImage(new ImageIcon(background).getImage(),0,0,null);
        g.setFont(new Font(g.getFont().getName(), Font.BOLD, 15));
        g.drawString(String.valueOf(gameState.getSunScore()),45,95);
        g.setFont(new Font("Nazario", Font.BOLD, 18));
        g.drawString(gameState.makeClock(gameState.getClock()),880,20);
        //Draw defenders
        defenders = gameState.getDefenders();
        for (int i=0 ; i<gameState.getDefenders().length ; i++){
            Defender d = defenders[i];
            if (d.getAssignedDefender() != null) {
                Plant plant = d.getAssignedDefender();
                if (plant instanceof PeaShooter) {
                    g.drawImage(new ImageIcon(peaShooter).getImage(), 60 + (i % 9) * 100, 129 + (i / 9) * 120, null);
                }
                else if (plant instanceof SnowPeaShooter) {
                    g.drawImage(new ImageIcon(snowPeaShooter).getImage(), 60 + (i % 9) * 100, 129 + (i / 9) * 120, null);
                }
                else if (plant instanceof Sunflower) {
                    g.drawImage(new ImageIcon(sunflower).getImage(), 60 + (i % 9) * 100, 129 + (i / 9) * 120, null);
                }
                else if (plant instanceof GiantWallNut) {
                    g.drawImage(new ImageIcon(wallNut).getImage(), 60 + (i % 9) * 100, 129 + (i / 9) * 120, null);
                }
                else if (plant instanceof CherryBomb) {
                    g.drawImage(new ImageIcon(cherryBomb).getImage(), 70 + (i % 9) * 100, 140 + (i / 9) * 120, null);
                    cherryCounter += 50;
                    //managing to remove cherry bomb after its explosion
                    gameState.cherryBomb(cherryCounter,(CherryBomb) plant);
                    if (cherryCounter == 3000){
                        defenders[i].setAssignedDefender(null);
                        cherryCounter = 0;
                    }
                }
                else if (plant instanceof Chomper) {
                    if(!((Chomper) plant).isEating()){
                        g.drawImage(new ImageIcon(Chomper).getImage(), 70 + (i % 9) * 100, 110 + (i / 9) * 120, null);
                    }else {
                        g.drawImage(new ImageIcon(eatingChomper).getImage(), 70 + (i % 9) * 100, 110 + (i / 9) * 120, null);

                    }
                }
            }
        }
        for(int i=0; i<5 ; i++) {

            for (NormalZombie z : gameState.getNormalZombies().get(i)) {
                g.drawImage(new ImageIcon(z.getNormalZombieImage()).getImage(), z.getPosX(), z.getPosY() * 120 + 105,
                        null);
            }

            for (ConeHeadZombie z : gameState.getConeHeadZombies().get(i)) {
                g.drawImage(new ImageIcon(z.getConeHeadZombieImage()).getImage(), z.getPosX(), z.getPosY() * 120 + 105
                        , null);
            }

            for (BucketHeadZombie z : gameState.getBucketHeadZombies().get(i)) {
                g.drawImage(new ImageIcon(z.getBucketHeadImage()).getImage(), z.getPosX(), z.getPosY() * 120 + 105
                        , null);
            }
        }

        for (Pea p : gameState.getActivePea()) {
            g.drawImage(new ImageIcon(p.getImage()).getImage(),p.getStartX(),p.getStartY(),null);
        }

        for(FreezePea p : gameState.getActiveFreezePea()){
            g.drawImage(new ImageIcon(p.getImage()).getImage(),p.getStartX(),p.getStartY(),null);
        }

    }

    public PlaySound getPlaySound(){ return playSound; }

    public void setDateOfGame(String dateOfGame){ this.dateOfGame = dateOfGame; }

    public String getDateOfGame(){ return dateOfGame; }

    public String getNormalOrHard(){
        return  (NormalOrHard==0) ? "Normal" : "Hard";
    }

    public SunHandler getSunHandler(){ return new SunHandler(); }

}
