package Pong;

/*
 classe permettant de créer le jeu intégré au pong.
 Est utilisé par la classe pong
 */
import Formes.Mobile;
import Formes.Player;
import Mobiles.Cercle;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.geom.Area;
import java.util.Random;
import javax.swing.Timer;

public class Game {

    private Pong pong;
    private Player player;
    private double numBall;
    private boolean gameOver, win;
    private Timer timer;
    private static final int MINVITESSE = 4;

    //constructeur
    public Game(boolean b) {
        pong = Pong.pong;

        player = new Player(430, 22, pong.b, 40, 40, 1);
        player.setVisible(b);
        if (b) {
            init();
        }
    }

    //intiialise les bases du jeu
    public void init() {
        pong.getMurs().clear();
        pong.getMobiles().clear();
        pong.addWalls(new Couleur(255, 0, 0), 0.3);
        pong.addMobile(new Cercle(200, 225, Pong.pong.r, 50, 7, MINVITESSE, 135, 1));
        pong.execute(true);
        numBall = 1;
        gameOver = win = false;
    }

    //update du jeu appelée régulièrement
    public void update() {
        //que l'on perde ou l'on gagne, on supprime tous les mobiles
        if (gameOver) {
            pong.getMobiles().clear();
            pong.addMobile(new Cercle(pong.WIDTH / 2 - 25, pong.HEIGHT / 2 - 150, pong.r, 50, 9, 5, 135, 0));
        }

        if (win) {
            pong.getMobiles().clear();
            pong.addMobile(new Cercle(pong.WIDTH / 2 - 15, pong.HEIGHT / 2 - 150, pong.r, 50, 7, 9, 135, 0));
        }
        
        //si le joueur est visible, on lance l'update du jeu
        if (player.isVisible()) {
            
            //si le joueur est mort, on arrete le jeu
            if (player.getLive() <= 0) {
                player.setVisible(false);
                gameOver = true;
            }

            Area playerArea = player.colisionBox();

            for (int i = 0; i < pong.getMobiles().size(); i++) {
                Mobile last = pong.getMobiles().get(pong.getMobiles().size()-1);
                //timer mis en place pour rendre un nouveau mobile capable de toucher le joueur au bout d'1 seconde
                timer = new Timer(1000, new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent arg0) {
                        last.setDegat(1);
                        last.setColor(pong.r);
                    }
                });
                timer.start();
                Area mobi = pong.getMobiles().get(i).colisionBox();
                int maxVitesse = pong.getMobiles().get(0).getMaxVitesse();
                double vitesse = pong.getMobiles().get(0).getVitesse();
                if (pong.Collision(playerArea, mobi)) {
                    try {
                        Thread.sleep(50);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    player.isTouched(pong.getMobiles().get(i).getDegat());
                }
                
                //condition pour gagner le jeu
                if (vitesse >= maxVitesse && pong.getMobiles().size() > 9) {
                    win = true;
                    player.setVisible(false);
                }
                
                //condition pour créer un nouveau mobile
                if (vitesse >= maxVitesse && pong.getMobiles().size() <= 9) {
                    //double ori = randNum(1, 8);
                    double ang = randAngle(numBall);
                    int rad = randNum(25, 50);
                    int x = randNum(30+rad,pong.WIDTH-30-rad);
                    int y = randNum(30+rad,pong.HEIGHT-30-rad);
                    pong.addMobile(new Cercle(x, y, pong.b, rad, numBall, MINVITESSE, ang, 0));
                    resetVitesse();
                    numBall++;
                }
            }
        }
    }

    public void draw(Graphics g) {
        if (player.isVisible()) {
            player.draw(g);
        }
        if (gameOver) {
            gameOver(g);
        } else if (win) {
            win(g);
        }
    }

    //permet de bouger le joueur grâce à la souris
    public void movePlayer(MouseEvent e) {
        player.move((float) e.getX() - player.getWidth() / 2, (float) e.getY() - player.getHeigth() / 2);
    }

    //remet la vitesse des mobiles à la vitesse minimum
    public void resetVitesse() {
        for (int i = 0; i < pong.getMobiles().size(); i++) {
            pong.getMobiles().get(i).setVitesse(MINVITESSE);
        }
    }

    //génère aléatoirement un nombre entre min et max
    public int randNum(int min, int max) {
        Random rand = new Random();
        return (rand.nextInt((max - min) + 1) + min);
    }

    //récupère l'angle selon l'orientation
    protected double randAngle(double o) {
        double angle = 0;
        switch ((int) o) {
            case 1:
                angle = 90;
                break;
            case 2:
                angle = 270;
                break;
            case 3:
                angle = 180;
                break;
            case 4:
                angle = 0;
                break;
            case 5:
                angle = 45;
                break;
            case 6:
                angle = 315;
                break;
            case 7:
                angle = 135;
                break;
            case 8:
                angle = 225;
                break;
        }
        return angle;
    }

    public Player getPlayer() {
        return player;
    }

    public boolean getGameOver() {
        return gameOver;
    }

    public void setGameOver(boolean b) {
        gameOver = b;
    }

    public void setPlayer(Player p) {
        player = p;
    }

    public void mouseMoved(MouseEvent e) {
        if (player.isVisible()) {
            movePlayer(e);
        }
    }

    public void mouseEntered(MouseEvent e) {
        if (player.isVisible()) {
            pong.execute(true);
        }
    }

    public void mouseExited(MouseEvent e) {
        if (player.isVisible()) {
            pong.execute(false);
        }
    }

    //appelée lors de la victoire : affiche texte WIN
    public void win(Graphics g) {
        g.setFont(new Font("TimesRoman", Font.PLAIN, 100));
        g.setColor(new Color(32, 178, 170));
        g.drawString("WIN", pong.WIDTH / 2 - 100, pong.HEIGHT / 2);
    }

    //appelée lors d'un Game Over : affiche texte GAME OVER
    public void gameOver(Graphics g) {
        g.setFont(new Font("TimesRoman", Font.PLAIN, 100));
        g.setColor(new Color(210, 105, 30));
        g.drawString("GAME OVER", 50, pong.HEIGHT / 2);
    }

}
