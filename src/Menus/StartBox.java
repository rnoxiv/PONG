package Menus;

import Formes.Mobile;
import Formes.Mur;
import Mobiles.Cercle;
import Mobiles.Triangle;
import Pong.Pong;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.geom.Area;
import java.util.ArrayList;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class StartBox extends Box {

    private JLabel vitLabel, widthLabel, heigthLabel, xLabel, yLabel, orLabel;
    private JComboBox vit1, or1;
    private JTextField width1, heigth1, posX1, posY1;
    private JComboBox vit2, or2;
    private JTextField width2, heigth2, posX2, posY2;

    public StartBox(JFrame parent, String title, boolean modal) {
        super(parent, title, modal);
        this.setSize(550, 270);
        this.setLocationRelativeTo(null);
        this.setResizable(false);
        this.setDefaultCloseOperation(JDialog.DO_NOTHING_ON_CLOSE);
        this.initComponent();
    }

    public BoxInfo showDialog() {
        this.sendData = false;
        this.setVisible(true);
        return this.info;
    }

    private void initComponent() {
        JPanel panIcon = new JPanel();
        panIcon.setBackground(Color.white);
        panIcon.setLayout(new BorderLayout());

        //Param du cercle
        JPanel panCircle = new JPanel();
        panCircle.setBackground(Color.white);
        panCircle.setPreferredSize(new Dimension(400, 80));
        panCircle.setBorder(BorderFactory.createTitledBorder("Cercle"));
        posX1 = new JTextField("200");
        posY1 = new JTextField("225");
        width1 = new JTextField("50");
        heigth1 = new JTextField("50");
        xLabel = new JLabel("Position x");
        yLabel = new JLabel("Position y");
        widthLabel = new JLabel("Width");
        heigthLabel = new JLabel("Height");
        vit1 = new JComboBox();
        vit1.addItem("1");
        vit1.addItem("2");
        vit1.addItem("3");
        vit1.addItem("4");
        vit1.addItem("5");
        vitLabel = new JLabel("Vitesse : ");
        or1 = new JComboBox();
        or1.addItem("NORTH");
        or1.addItem("NORTH-EAST");
        or1.addItem("EAST");
        or1.addItem("SOUTH-EAST");
        or1.addItem("SOUTH");
        or1.addItem("SOUTH-WEST");
        or1.addItem("WEST");
        or1.addItem("NORTH-WEST");
        orLabel = new JLabel("Orientation:");
        panCircle.add(xLabel);
        panCircle.add(posX1);
        panCircle.add(yLabel);
        panCircle.add(posY1);
        panCircle.add(widthLabel);
        panCircle.add(width1);
        panCircle.add(heigthLabel);
        panCircle.add(heigth1);
        panCircle.add(vitLabel);
        panCircle.add(vit1);
        panCircle.add(orLabel);
        panCircle.add(or1);

        //Param du triangle
        JPanel panTriangle = new JPanel();
        panTriangle.setBackground(Color.white);
        panTriangle.setPreferredSize(new Dimension(400, 80));
        panTriangle.setBorder(BorderFactory.createTitledBorder("Triangle"));
        posX2 = new JTextField("450");
        posY2 = new JTextField("250");
        width2 = new JTextField("50");
        heigth2 = new JTextField("50");
        xLabel = new JLabel("Position x");
        yLabel = new JLabel("Position y");
        widthLabel = new JLabel("Width");
        heigthLabel = new JLabel("Height");
        vit2 = new JComboBox();
        vit2.addItem("1");
        vit2.addItem("2");
        vit2.addItem("3");
        vit2.addItem("4");
        vit2.addItem("5");
        vitLabel = new JLabel("Vitesse : ");
        or2 = new JComboBox();
        or2.addItem("NORTH");
        or2.addItem("NORTH-EAST");
        or2.addItem("EAST");
        or2.addItem("SOUTH-EAST");
        or2.addItem("SOUTH");
        or2.addItem("SOUTH-WEST");
        or2.addItem("WEST");
        or2.addItem("NORTH-WEST");
        orLabel = new JLabel("Orientation:");
        panTriangle.add(xLabel);
        panTriangle.add(posX2);
        panTriangle.add(yLabel);
        panTriangle.add(posY2);
        panTriangle.add(widthLabel);
        panTriangle.add(width2);
        panTriangle.add(heigthLabel);
        panTriangle.add(heigth2);
        panTriangle.add(vitLabel);
        panTriangle.add(vit2);
        panTriangle.add(orLabel);
        panTriangle.add(or2);

        //création du panel avec ses composants
        JPanel content = new JPanel();
        content.setBackground(Color.white);
        content.add(panCircle);
        content.add(panTriangle);

        JPanel control = new JPanel();
        JButton okBouton = new JButton("OK");

        okBouton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent arg0) {

                boolean onObject = false;

                // on va vérifier ici que les nouveaux mobiles paramétrés ne rentrent pas en contact avec un mur
                //Pour cela on va créer une collision box avec les données des 2 nouveaux mobiles puis on loop sur l'array contenant les murs en vérifiant les collisions
                ArrayList<Mobile> mobis = pong.getMobiles();
                ArrayList<Mur> murs = pong.getMurs();

                Area rc = new Area(new Rectangle(Integer.parseInt(posX1.getText()), Integer.parseInt(posY1.getText()), Integer.parseInt(width1.getText()), Integer.parseInt(heigth1.getText())));
                Area rt = new Area(new Rectangle(Integer.parseInt(posX2.getText()), Integer.parseInt(posY2.getText()), Integer.parseInt(width2.getText()), Integer.parseInt(heigth2.getText())));

                for (int j = 0; j < murs.size(); j++) {
                    Area r2 = murs.get(j).colisionBox();
                    if (pong.Collision(rc, r2) || pong.Collision(rt, r2)) {
                        onObject = true;
                    }
                }

                info = new BoxInfo(onObject);

                //si aucune collision n'a été détecté, on crée les 2 nouveaux mobiles
                if (!onObject) {
                    Pong.pong.getMobiles().clear();
                    double ori1;
                    double ori2;
                    double a1;
                    double a2;
                    ori1 = getThisOrientation((String) or1.getSelectedItem());
                    a1 = getThisAngle(ori1);
                    ori2 = getThisOrientation((String) or2.getSelectedItem());
                    a2 = getThisAngle(ori2);

                    pong.addMobile(new Cercle(Integer.parseInt(posX1.getText()), Integer.parseInt(posY1.getText()), pong.r, Integer.parseInt(width1.getText()), ori1, Integer.parseInt((String) vit1.getSelectedItem()), a1,0));
                    pong.addMobile(new Triangle(Integer.parseInt(posX2.getText()), Integer.parseInt(posY2.getText()), pong.v, Integer.parseInt(width2.getText()), Integer.parseInt(heigth2.getText()), ori2, Integer.parseInt((String) vit2.getSelectedItem()), a2));
                }
                setVisible(false);
            }
        });

        JButton cancelBouton = new JButton("Annuler");
        cancelBouton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent arg0) {
                setVisible(false);
            }
        });

        control.add(okBouton);
        control.add(cancelBouton);

        this.getContentPane().add(panIcon, BorderLayout.WEST);
        this.getContentPane().add(content, BorderLayout.CENTER);
        this.getContentPane().add(control, BorderLayout.SOUTH);
    }
}
