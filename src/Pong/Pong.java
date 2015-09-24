package Pong;

import Formes.Mobile;
import Formes.Mur;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.geom.Area;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.Random;
import javax.swing.JFrame;
import javax.swing.JPanel;

public class Pong extends JPanel implements Runnable, MouseListener, MouseMotionListener {

    // dimensions
    public static final int WIDTH = 700;
    public static final int HEIGHT = 500;
    public static final int WALLSIZE = 20;

    // game thread
    private Thread thread;
    private boolean running;
    private static final int FPS = 60;
    private static final long targetTime = 1000 / FPS;

    // image
    private BufferedImage image;
    private Graphics2D g;

    public static final String NAME = "PONG";

    public Random rand;

    //Utilitaires
    public Couleur r, v, b, random;
    private long start, elapsed, wait;
    private boolean executed, mobiColli, intro;
    private Game game;

    //Liste des objets
    private ArrayList<Mobile> mobiles;
    private ArrayList<Mur> murs;

    public static Pong pong;

    //Constructeur du JPanel
    public Pong() {
        super();
        setPreferredSize(new Dimension(WIDTH,HEIGHT));
        setFocusable(true);
        requestFocusInWindow();
        addMouseListener(this);
        addMouseMotionListener(this);

    }
    
    
    
    @Override
    public void addNotify() {
        super.addNotify();
        if (thread == null) {
            thread = new Thread(this);
            thread.start();
        }
    }

    //initialise tous les éléments nécessaires au bon fonctionnement du programme
    public void init() {
        image = new BufferedImage(WIDTH, HEIGHT, BufferedImage.TYPE_INT_RGB);
        g = (Graphics2D) image.getGraphics();

        JukeBox.load("/SFX/collisionv1.mp3", "collision");

        pong = this;
        rand = new Random();
        mobiles = new ArrayList<>();// on initialise le vector mobiles
        murs = new ArrayList<>();// on initialise le vector shapes

        game = new Game(false);

        // on cree le rouge et le vert des mobiles ainsi qu'une couleur random pour les murs.
        r = new Couleur(255, 0, 0);
        v = new Couleur(0, 255, 0);
        b = new Couleur(0, 0, 255);
        random = new Couleur(rand.nextInt(255), rand.nextInt(255), rand.nextInt(255));

        executed = false;
        running = true;

        mobiColli = true;

        intro = true;
    }

    //la Game-Loop
    @Override
    public void run() {
        init();

        // game loop
        while (running) {
            start = System.nanoTime();
            update();
            draw();
            drawToScreen();

            elapsed = System.nanoTime() - start;

            wait = targetTime - elapsed / 1000000;
            if (wait < 0) {
                wait = 5;
            }

            try {
                Thread.sleep(wait);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    //méthode appelée à chaque boucle gérant les mouvements et les collisions des objets
    public void update() {

        if (executed) {
            for (int i = 0; i < mobiles.size(); i++) {
                mobiles.get(i).setMove(true);
            }
            moveAll();
        } else {
            for (int i = 0; i < mobiles.size(); i++) {
                mobiles.get(i).setMove(false);
            }
        }

        game.update();

        for (int i = 0; i < mobiles.size(); i++) {
            Area area1 = mobiles.get(i).colisionBox();
            double ori1 = mobiles.get(i).getOrientation();
            double vit1 = mobiles.get(i).getVitesse();
            double a1 = mobiles.get(i).getAngle();

            for (int j = 0; j < murs.size(); j++) {
                Area area2 = murs.get(j).colisionBox();
                if (Collision(area1, area2)) {
                    JukeBox.play("collision");
                    mobiles.get(i).changeAngle(murs.get(j).getOrientation(), "mur");
                    mobiles.get(i).setVitesse(mobiles.get(i).getVitesse() + murs.get(j).getCoefficient());
                }
            }

            if (mobiColli) {
                for (int k = i + 1; k < mobiles.size(); k++) {
                    Area area3 = mobiles.get(k).colisionBox();
                    double ori3 = mobiles.get(k).getOrientation();
                    double vit3 = mobiles.get(k).getVitesse();
                    double a2 = mobiles.get(k).getAngle();

                    if (Collision(area1, area3)) {

                        JukeBox.play("collision");
                        if (vit1 > vit3) {
                            mobiles.get(k).setVitesse(vit3 + vit1 / 2);
                            mobiles.get(k).setOrientation(ori1);
                            mobiles.get(k).setAngle(a1);
                            mobiles.get(i).setVitesse(vit1 - vit3 / 4);
                            mobiles.get(i).changeAngle(ori1, "mobile");
                        } else if (vit3 > vit1) {
                            mobiles.get(i).setVitesse(vit3 + vit1 / 2);
                            mobiles.get(i).setOrientation(ori3);
                            mobiles.get(i).setAngle(a2);
                            mobiles.get(k).setVitesse(vit1 - vit3 / 4);
                            mobiles.get(k).changeAngle(ori3, "mobile");
                        } else {
                            mobiles.get(i).changeAngle(ori1, "mobile");
                            mobiles.get(k).changeAngle(ori3, "mobile");
                        }
                    }
                }
            }
        }
    }

    //permet de récupérer les éléments à afficher à l'écran
    public void draw() {
        //draw Background
        g.setColor(Color.black);
        g.fillRect(0, 0, WIDTH, HEIGHT);

        if (intro) {
            drawName();
        } else {

            game.draw(g);

            //draw walls
            for (int i = 0; i < murs.size(); i++) {
                murs.get(i).draw(g);
            }// on parcourt murs et pour chaque element de celui-ci, on appelle la fonction draw de sa classe

            //draw shapes (circle or triangle)
            for (int i = 0; i < mobiles.size(); i++) {
                mobiles.get(i).draw(g);
            }// on parcourt mobiles et pour chaque element de celui-ci, on appelle la fonction draw de sa classe

        }
    }

    //permet d'afficher les objets à l'écran
    public void drawToScreen() {
        Graphics g2 = getGraphics();
        g2.drawImage(image, 0, 0, WIDTH, HEIGHT, null);
        g2.dispose();
    }

    public void drawName() {
        g.setFont(new Font("TimesRoman", Font.PLAIN, 100));
        g.setColor(new Color(210, 105, 30));
        g.drawString(NAME, pong.WIDTH / 2 - 150, pong.HEIGHT / 2);
        g.setFont(new Font("TimesRoman", Font.PLAIN, 20));
        g.drawString("by David SABATIER & Baptiste THÉPAULT", 25,pong.HEIGHT - 25);
    }

    //permet de lancer ou d'arrêter le pong
    public void execute(Boolean b) {
        executed = b;
    }

    //permet de rajouter un mobile à son array
    public void addMobile(Mobile m) {
        mobiles.add(m);
    }

    //permet de rajouter un mur à son array
    public void addWall(Mur m) {
        Mur m1 = new Mur((int) m.getX() - 2, (int) m.getY(), m.getColor(), 2, m.getHeigth(), 4, 0.05);
        Mur m2 = new Mur((int) m.getX() + m.getWidth(), (int) m.getY(), m.getColor(), 2, m.getHeigth(), 3, 0.05);
        Mur m3 = new Mur((int) m.getX() - 2, (int) m.getY() - 2, m.getColor(), m.getWidth() + 4, 5, 2, 0.05);
        Mur m4 = new Mur((int) m.getX() - 2, (int) m.getY() + m.getHeigth(), m.getColor(), m.getWidth() + 4, 2, 1, 0.05);

        murs.add(m);
        murs.add(m1);
        murs.add(m2);
        murs.add(m3);
        murs.add(m4);
    }

    //permet d'initialiser les 4 murs entourant le terrain
    public void addWalls(Couleur c, double coeff) {
        Mur m1 = new Mur(0, 0, c, WIDTH, WALLSIZE, 1, coeff);
        Mur m2 = new Mur(WIDTH - WALLSIZE, 0, c, WALLSIZE, HEIGHT, 4, coeff);
        Mur m3 = new Mur(0, 0, c, WALLSIZE, HEIGHT, 3, coeff);
        Mur m4 = new Mur(0, HEIGHT - WALLSIZE, c, WIDTH, WALLSIZE, 2, coeff);

        murs.add(m1);
        murs.add(m2);
        murs.add(m3);
        murs.add(m4);
    }

    //permet de faire bouger tous les mobiles
    public void moveAll() {
        for (int i = 0; i < this.mobiles.size(); i++) {
            if (mobiles.get(i).getMove()) {
                this.mobiles.get(i).move();// on fait une boucle qui va parcourir chaque element du vector shapes et appeler pour chaque element la methode move qui lui convient
            }
        }
    }

    //permet de vérifier si collision il y a eu! VIM (very important method)
    public boolean Collision(Area area1, Area area2) {
        boolean collide = false;

        Area collide1 = new Area(area1);
        collide1.subtract(area2);

        if (!collide1.equals(area1)) {
            collide = true;
        }

        Area collide2 = new Area(area2);
        collide2.subtract(area1);

        if (!collide2.equals(area2)) {
            collide = true;
        }
        return collide;
    }

    //retourne l'array des mobiles
    public ArrayList<Mobile> getMobiles() {
        return mobiles;
    }

    //retourne l'array des murs
    public ArrayList<Mur> getMurs() {
        return murs;
    }

    public Game getGame() {
        return game;
    }
    
    public boolean getIntro(){return intro;}
    
    public void setIntro(boolean b){ intro = b;}
    
    public void setGame(Game g) {
        game = g;
    }

    public void setMobiColli(boolean b) {
        mobiColli = b;
    }
    
    //permet de changer de couleur un mobile grâce à un clic droit
    public void changeColor(MouseEvent e) {
        Area r = new Area(new Rectangle(e.getX(), e.getY(), 1, 1));
        for (int i = 0; i < mobiles.size(); i++) {
            Area r1 = mobiles.get(i).colisionBox();
            if (Collision(r, r1)) {
                mobiles.get(i).setColor(new Couleur(rand.nextInt(255), rand.nextInt(255), rand.nextInt(255)));
            }
        }
    }
    
    
    /*
    Gestion des évènements de la souris
    */
    @Override
    public void mouseEntered(MouseEvent e) {
        game.mouseEntered(e);
    }

    @Override
    public void mouseExited(MouseEvent e) {
        game.mouseExited(e);
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        if (e.getButton() == MouseEvent.BUTTON3) {
            changeColor(e);
        }
    }

    @Override
    public void mousePressed(MouseEvent e) {
    }

    @Override
    public void mouseReleased(MouseEvent e) {
    }

    @Override
    public void mouseMoved(MouseEvent e) {
        game.mouseMoved(e);
    }

    @Override
    public void mouseDragged(MouseEvent e) {
    }

}
