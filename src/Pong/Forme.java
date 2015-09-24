package Pong;

import java.awt.Graphics;
import java.awt.geom.Area;

public abstract class Forme {

    // on declare ici les attributs de la classe Forme
    
    protected static final double NORTH = 1;
    protected static final double SOUTH = 2;
    protected static final double WEST = 3;
    protected static final double EAST = 4;
    protected static final double NORTH_EAST = 5;
    protected static final double SOUTH_EAST = 6;
    protected static final double NORTH_WEST = 7;
    protected static final double SOUTH_WEST = 8;
    protected static final double RANDOMWALL = 9;
    
    protected float x;
    protected float y;
    protected Couleur color;
    protected int width;
    protected int heigth;
    protected double orientation;

    //Constructeur de forme, on initialise les attributs de la classe
    public Forme(int x1, int y1, Couleur color1, int width1, int heigth1, double orientation1) {
        x = x1;
        y = y1;
        color = color1;
        width = width1;
        heigth = heigth1;
        orientation = orientation1;
    }

    // puisque les attributs sont prives, les get et set doivent etre proteges
    protected float getX() {
        return x;
    }

    protected float getY() {
        return y;
    }

    protected Couleur getColor() {
        return color;
    }

    protected int getWidth() {
        return width;
    }

    protected int getHeigth() {
        return heigth;
    }

    protected double getOrientation() {
        return orientation;
    }

    protected void setX(float x1) {
        x = x1;
    }

    protected void setY(float y1) {
        y = y1;
    }

    protected void setColor(Couleur c1) {
        this.color = c1;
    }

    protected void setWidth(int width1) {
        width = width1;
    }

    protected void setHeigth(int heigth1) {
        heigth = heigth1;
    }

    protected void setOrientation(double o) {
        orientation = o;
    }

    public abstract Area colisionBox();

    public abstract void draw(Graphics g);// on declare une methode draw abstracte, qui sera surdefinie dans les classes filles

}
