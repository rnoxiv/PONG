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

public class AddBox extends Box {

    private JLabel typeLabel, vitLabel, orLabel, widthLabel, heigthLabel, xLabel, yLabel;
    private JComboBox type, vit, or;
    private JTextField width, heigth, posX, posY;

    public AddBox(JFrame parent, String title, boolean modal) {
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

        //Le type
        JPanel panType = new JPanel();
        panType.setBackground(Color.white);
        panType.setPreferredSize(new Dimension(220, 60));
        panType.setBorder(BorderFactory.createTitledBorder("Type de l'objet crée"));
        type = new JComboBox();
        type.addItem("Circle");
        type.addItem("Triangle");
        type.addItem("Wall");
        typeLabel = new JLabel("Type : ");
        panType.add(typeLabel);
        panType.add(type);

        //La vitesse
        JPanel panVit = new JPanel();
        panVit.setBackground(Color.white);
        panVit.setPreferredSize(new Dimension(220, 60));
        panVit.setBorder(BorderFactory.createTitledBorder("Vitesse de l'objet(si mobile)"));
        vit = new JComboBox();
        vit.addItem("1");
        vit.addItem("2");
        vit.addItem("3");
        vit.addItem("4");
        vit.addItem("5");
        vitLabel = new JLabel("Vitesse : ");
        panVit.add(vitLabel);
        panVit.add(vit);

        //La taille en width
        JPanel panWidth = new JPanel();
        panWidth.setBackground(Color.white);
        panWidth.setPreferredSize(new Dimension(220, 60));
        panWidth.setBorder(BorderFactory.createTitledBorder("Width"));
        widthLabel = new JLabel("Taille : ");
        width = new JTextField("50");
        width.setPreferredSize(new Dimension(90, 25));
        panWidth.add(widthLabel);
        panWidth.add(width);

        //La taille en heigth
        JPanel panHeigth = new JPanel();
        panHeigth.setBackground(Color.white);
        panHeigth.setPreferredSize(new Dimension(220, 60));
        panHeigth.setBorder(BorderFactory.createTitledBorder("Heigth"));
        heigthLabel = new JLabel("Taille : ");
        heigth = new JTextField("50");
        heigth.setPreferredSize(new Dimension(90, 25));
        panHeigth.add(heigthLabel);
        panHeigth.add(heigth);

        //La position en x et y
        JPanel panPos = new JPanel();
        panPos.setBackground(Color.white);
        panPos.setPreferredSize(new Dimension(220, 60));
        panPos.setBorder(BorderFactory.createTitledBorder("Position de l'objet"));
        posX = new JTextField("100");
        posY = new JTextField("100");
        xLabel = new JLabel("Position x");
        yLabel = new JLabel("Position y");
        panPos.add(xLabel);
        panPos.add(posX);
        panPos.add(yLabel);
        panPos.add(posY);

        //L'orientation
        JPanel panOr = new JPanel();
        panOr.setBackground(Color.white);
        panOr.setPreferredSize(new Dimension(220, 60));
        panOr.setBorder(BorderFactory.createTitledBorder("Orientation de l'objet"));
        or = new JComboBox();
        or = new JComboBox();
        or.addItem("NORTH");
        or.addItem("NORTH-EAST");
        or.addItem("EAST");
        or.addItem("SOUTH-EAST");
        or.addItem("SOUTH");
        or.addItem("SOUTH-WEST");
        or.addItem("WEST");
        or.addItem("NORTH-WEST");
        orLabel = new JLabel("Orientation : ");
        panOr.add(orLabel);
        panOr.add(or);

        //création du panel avec ses composants
        JPanel content = new JPanel();
        content.setBackground(Color.white);
        content.add(panType);
        content.add(panVit);
        content.add(panWidth);
        content.add(panHeigth);
        content.add(panPos);
        content.add(panOr);

        JPanel control = new JPanel();
        JButton okBouton = new JButton("OK");

        okBouton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent arg0) {

                //On vérifie en premier lieu si le mobile/mur crée n'est pas positionné sur un mur ou un autre mobile
                boolean onObject = false;

                ArrayList<Mobile> mobis = pong.getMobiles();
                ArrayList<Mur> murs = pong.getMurs();

                Area r = new Area(new Rectangle(Integer.parseInt(posX.getText()), Integer.parseInt(posY.getText()), Integer.parseInt(width.getText()), Integer.parseInt(heigth.getText())));

                for (int i = 0; i < mobis.size(); i++) {
                    Area r1 = mobis.get(i).colisionBox();
                    if (pong.Collision(r, r1)) {
                        onObject = true;
                    }
                }
                for (int j = 0; j < murs.size(); j++) {
                    Area r2 = murs.get(j).colisionBox();
                    if (pong.Collision(r, r2)) {
                        onObject = true;
                    }
                }

                info = new BoxInfo((String) type.getSelectedItem(), (String) vit.getSelectedItem(), width.getText(), heigth.getText(), posX.getText(), posY.getText(), onObject);

                //si il n'y a pas de collision, on crée le mobile/mur et on l'ajoute dans son Array
                if (!onObject) {
                    double ori = 9;
                    double a = 0;
                    double c = 0.5;
                    if ((String) type.getSelectedItem() != "Wall") {
                        ori = getThisOrientation((String) or.getSelectedItem());
                        a = getThisAngle(ori);
                    }

                    switch ((String) type.getSelectedItem()) {
                        case "Circle":
                            pong.addMobile(new Cercle(Integer.parseInt(posX.getText()), Integer.parseInt(posY.getText()), pong.r, Integer.parseInt(width.getText()), ori, Integer.parseInt((String) vit.getSelectedItem()), a,0));
                            break;
                        case "Triangle":
                            pong.addMobile(new Triangle(Integer.parseInt(posX.getText()), Integer.parseInt(posY.getText()), pong.v, Integer.parseInt(width.getText()), Integer.parseInt(heigth.getText()), ori, Integer.parseInt((String) vit.getSelectedItem()), a));
                            break;
                        case "Wall":
                            pong.addWall(new Mur(Integer.parseInt(posX.getText()), Integer.parseInt(posY.getText()), pong.random, Integer.parseInt(width.getText()),
                                    Integer.parseInt(heigth.getText()), ori, c));
                            break;
                    }
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
