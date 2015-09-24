package Menus;

import Pong.Pong;
import javax.swing.JDialog;
import javax.swing.JFrame;

public class Box extends JDialog {

    protected final Pong pong;
    protected boolean sendData;
    protected BoxInfo info = new BoxInfo();

    public Box(JFrame parent, String title, boolean modal) {
        super(parent, title, modal);
        pong = Pong.pong;
    }

    //permet de récupérer la valeur de l'orientation à partir d'un String
    protected double getThisOrientation(String o) {
        double orientation = 0;
        switch (o) {
            case "NORTH":
                orientation = 1;
                break;
            case "SOUTH":
                orientation = 2;
                break;
            case "WEST":
                orientation = 3;
                break;
            case "EAST":
                orientation = 4;
                break;
            case "NORTH-EAST":
                orientation = 5;
                break;
            case "SOUTH-EAST":
                orientation = 6;
                break;
            case "NORTH-WEST":
                orientation = 7;
                break;
            case "SOUTH-WEST":
                orientation = 8;
                break;
        }
        return orientation;
    }

    //permet de récupérer la valeur de l'angle à partir de l'orientation
    protected double getThisAngle(double o) {
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
}
