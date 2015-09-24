package Mobiles;

import Formes.Mobile;
import Pong.Couleur;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Polygon;
import java.awt.geom.Area;

public class Triangle extends Mobile {

    private int[] xPoints;// on utilise des tableaux de points pour tracer le triangle en mode polygone
    private int[] yPoints;

    // constructeur par initialisation
    public Triangle(int x1, int y1, Couleur color1, int w, int h, double orient, int vit, double a) {
        super(x1, y1, color1, w, h, orient, vit, a, 0);
        xPoints = new int[3];
        yPoints = new int[3];
    }
    
    //récupère 2 tableaux d'int de 3 points en x et y pour la création du triangle
    public int[] getTriXs() {
        xPoints[0] = (int) this.getX() + (this.getWidth() / 2);
        xPoints[1] = (int) this.getX() + this.getWidth();
        xPoints[2] = (int) this.getX();
        return xPoints;
    }

    public int[] getTriYs() {
        yPoints[0] = (int) this.getY() - this.getHeigth();
        yPoints[1] = yPoints[2] = (int) this.getY();
        return yPoints;
    }
    
    //cf Cercle
    @Override
    public void move() {
        super.move();
    }

    @Override
    public Area colisionBox() {
        return new Area(new Polygon(getTriXs(), getTriYs(), 3));
    }

    @Override
    public void draw(Graphics g) {
        g.setColor(new Color(this.getColor().getRed(), this.getColor().getGreen(), this.getColor().getBlue()));
        Polygon p = new Polygon(getTriXs(), getTriYs(), 3);
        g.fillPolygon(p);
    }

}
