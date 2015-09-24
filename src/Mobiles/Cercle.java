package Mobiles;

import Formes.Mobile;
import Pong.Couleur;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.geom.Area;
import java.awt.geom.Ellipse2D;

public class Cercle extends Mobile {

    private int radius;
    // constructeur par initialisation

    public Cercle(int x1, int y1, Couleur color1, int r, double o, int vit, double a,int d) {
        super(x1, y1, color1, r, r, o, vit, a, d);
        this.radius = r;
    }

    //Récupère l'équation de mouvement de la classe Mobile. Des spécifications sur le mouvement du cercle peuvent être ajoutées ici
    @Override
    public void move() {
        super.move();
    }
    
    @Override
    public Area colisionBox() {
        return new Area(new Ellipse2D.Double((double) (this.x), (double) (this.y), (double) this.radius + 2, (double) this.radius + 2));
    }
    
    @Override
    public void draw(Graphics g) {
        g.setColor(new Color(super.getColor().getRed(), super.getColor().getGreen(), super.getColor().getBlue())); // ici, on doit, pour les mÃªmes raisons, utiliser les get
        g.fillOval((int) super.getX(), (int) super.getY(), this.radius, this.radius);
    }

}
