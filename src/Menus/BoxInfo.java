package Menus;

public class BoxInfo {
  private String type,nom,width, heigth, posX, posY;
  private boolean collide,param;

  public BoxInfo(){};
  public BoxInfo(String t, String n, String w, String h, String x, String y,boolean c){
    this.nom = n;
    this.type = t;
    this.width = w;
    this.heigth = h;
    this.posX = x;
    this.posY = y;
    this.collide = c;
    this.param = false;
  }

  public BoxInfo(boolean c){
      this(null,null,null,null,null,null,c);
      this.param = true;
  }
  
  public String toString(){
    String str;
    if(this.type != null && this.nom != null && this.width != null && this.heigth != null && this.posX != null && this.posY != null && !collide){
      str = "Création de l'objet";
      str += "Type : " + this.type + "\n";
      str += "Nom : " + this.nom + "\n";
      str += "Width : " + this.width + "\n";
      str += "Heigth : " + this.heigth + "\n";
      str += "Position : " + this.posX + "\n";
      str += "Taille : " + this.posY + "\n";
    }
    else if(collide){
      str = "Objet non crée car positionnement sur un autre objet";
    }else if(param){
      str = "Cercle et Triangle bien paramétrés";
    }else{
        str = "Objet non crée";
    }
    return str;
  }
}