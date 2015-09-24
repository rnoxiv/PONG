package Formes;

import Pong.Couleur;
import Pong.Forme;
import java.awt.Graphics;
import java.awt.geom.Area;

public abstract class Mobile extends Forme{
    
    private int maxVitesse = 10;
    private static final int minVitesse = 0;
    
    private int degat;
    
    private double a;
    
    private boolean move;
    private double vitesse; // la classe Mobile derive de Forme donc elle possede les attributs de Forme plus l'attribut vitesse

    public Mobile(int x, int y, Couleur c, int w, int h, double orient, int v, double _a, int d){
        super(x,y,c,w,h,orient);
        this.vitesse=v;
        this.move = false;
        this.a = _a;
        this.degat = d;
    }
    
    public double getVitesse(){return vitesse;}
    public int getMaxVitesse(){return maxVitesse;}
    public boolean getMove(){return move;}
    public double getAngle(){
        if(this.a >= 360) this. a -= 360;
        else if(this.a< 0) this.a = 360 + a;
        return a;
    }
    public double getAngleRad(){return Math.toRadians(this.getAngle());}
    public int getDegat(){return degat;}
    
    public void setDegat(int d){this.degat = d;}
    public void setAngle(double _a){this.a =_a;}
    public void setVitesse(double v){vitesse = v;}
    public void setMaxVitesse(int v){maxVitesse = v;}
    public void setMove(boolean b){move = b;}
    
    //permet de changer l'angle d'un mobile et l'orientation lors d'une collision
    public void changeAngle(double o,String t){
        if(t == "mur" && (this.getOrientation() == NORTH || this.getOrientation()==SOUTH || this.getOrientation()==WEST || this.getOrientation() == EAST)){
            this.changeOrientation(this.getOrientation());
        }else if(t == "mobile"){
            this.changeMobOrientation(o);
            this.setAngle(this.getAngle()+180);
        }else{
            this.changeMurOrientation(o);
        }
    }
    
    //change orientation lors d'une collision avec un autre mobile
    public void changeMobOrientation(double o){
        if(o == NORTH_WEST){
                this.setOrientation(SOUTH_EAST);
        }else if(o == SOUTH_WEST){
                this.setOrientation(NORTH_EAST);
        }else if(o == NORTH_EAST){
                this.setOrientation(SOUTH_WEST);
        }else if(o == SOUTH_EAST){
                this.setOrientation(NORTH_WEST);
        }
    }
    
    //change orientation lors d'une collision avec un mur si le mobile à un angle autre que 0, 90, 180, 270, 360 degrés
    public void changeMurOrientation(double o){
        if(o == NORTH){
            if(this.getOrientation() == NORTH_WEST){
                this.setOrientation(SOUTH_WEST);
                this.setAngle(this.getAngle()+90);
            }else if(this.getOrientation()==NORTH_EAST){
                this.setOrientation(SOUTH_EAST);
                this.setAngle(this.getAngle()-90);
            }
        }else if(o == SOUTH){
            if(this.getOrientation() == SOUTH_WEST){
                this.setOrientation(NORTH_WEST);
                this.setAngle(this.getAngle()-90);
            }else if(this.getOrientation()==SOUTH_EAST){
                this.setOrientation(NORTH_EAST);
                this.setAngle(this.getAngle()+90);
            }
        }else if(o == WEST){
            if(this.getOrientation()== NORTH_WEST){
                this.setOrientation(NORTH_EAST);
                this.setAngle(this.getAngle()-90);
            }else if(this.getOrientation()== SOUTH_WEST){
                this.setOrientation(SOUTH_EAST);
                this.setAngle(this.getAngle()+90);
            }
        }else if(o == EAST){
            if(this.getOrientation()== NORTH_EAST){
                this.setOrientation(NORTH_WEST);
                this.setAngle(this.getAngle()+90);
            }else if(this.getOrientation()== SOUTH_EAST){
                this.setOrientation(SOUTH_WEST);
                this.setAngle(this.getAngle()-90);
            }
        }
    }
    
    //change orientation et angle
    public void changeOrientation(double o){
            if(o == NORTH){this.setOrientation(SOUTH);
            }else if(o == SOUTH){this.setOrientation(NORTH);
            }else if(o == WEST)this.setOrientation(EAST);
            else this.setOrientation(WEST);
            this.setAngle(a+180);
    }
     
    //permet de déplacer le mobile selon son angle + limite vitesse mobile
    public void move(){
        this.setX((float)(this.getX()+this.getVitesse()*(Math.cos(this.getAngleRad()))));
        this.setY((float)(this.getY()-this.getVitesse()*(Math.sin(this.getAngleRad()))));
        
        if(this.getVitesse() <= minVitesse)this.setVitesse(minVitesse);
        else if(this.getVitesse() >= maxVitesse)this.setVitesse(maxVitesse);
        else this.setVitesse(this.getVitesse() - 0.0005);
    }
    
    
    //2 méthodes abstracts qui seront surdéfinies par les classes filles
    @Override
    public abstract Area colisionBox();
    
    @Override
    public abstract void draw(Graphics g);
   
}
