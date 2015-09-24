package Formes;

import Pong.Couleur;
import Pong.Forme;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.geom.Area;

public class Mur extends Forme{
    
        private double coefficient;// la classe Mur derive de Forme donc elle possede les attributs de Forme plus les nouveaux attributs de Mur
        
	// constructeur par initialisation
	public Mur(int x1, int y1, Couleur color1, int w, int h, double orient, double coeff) {
		super(x1,y1,color1,w,h,orient);
		coefficient = coeff; 
		}

        public double getCoefficient(){return coefficient;}
        public void setCoefficient(double coeff){coefficient = coeff;}
        
        @Override
        public Area colisionBox(){
            return new Area(new Rectangle((int)getX()-1,(int)getY()-1,getWidth()+2,getHeigth()+2));
        }
        
        @Override
	public void draw(Graphics g) {
 		g.setColor(new java.awt.Color(super.getColor().getRed(),super.getColor().getGreen(),super.getColor().getBlue()));// ici, on utilise les get car dans la classe mere, nous avons choisi de laisser nos attributs prives
		g.fillRect((int)super.getX(), (int)super.getY(), super.getWidth(), super.getHeigth());
	}
}
