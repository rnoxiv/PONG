package Formes;

import Pong.Couleur;
import Pong.Forme;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.geom.Area;

public class Player extends Forme {
    
    private boolean visible;
    
    private static final int maxLive = 1;
    private int live;
    
    // constructeur par initialisation
    public Player(int x1, int y1, Couleur color1, int w, int h, double orient) {
        super(x1, y1, color1, w, h, orient);
        live = maxLive;
        visible = false;
    }

    @Override
    public Area colisionBox() {
        return new Area(new Rectangle((int) getX() - 3, (int) getY() - 3, getWidth() + 6, getHeigth() + 6));
    }

    @Override
    public void draw(Graphics g) {
        g.setColor(new java.awt.Color(super.getColor().getRed(), super.getColor().getGreen(), super.getColor().getBlue()));// ici, on utilise les get car dans la classe mere, nous avons choisi de laisser nos attributs prives
        g.fillRect((int) super.getX(), (int) super.getY(), super.getWidth(), super.getHeigth());
    }
    
    //récupère la position de la souris et la passe au joueur. Limite la position selon la souris
    public void move(float dx, float dy){
        
        float limRight = Pong.Pong.pong.WIDTH - getWidth() - 23;
        float limLeft = 23;
        float limDown = Pong.Pong.pong.HEIGHT - getHeigth() - 23;
        float limUp = 23;
        
        if(dx<limLeft) dx = limLeft;
        else if(dx>limRight) dx = limRight;
        if(dy<limUp) dy = limUp;
        if(dy>limDown) dy = limDown;
        
        setX(dx);
        setY(dy);
        
    }
    
    //baisse la vie du joueur lors d'une collision avec un mobile
    public void isTouched(int d){live -= d;}
    
    public int getLive(){return live;}
    public boolean isVisible(){return visible;}
    public void setVisible(boolean v){visible = v;}
    public void setLive(int l){live = l;}
    
    public int getMaxLive(){return maxLive;}
}
