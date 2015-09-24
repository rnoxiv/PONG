package Menus;

import Pong.Pong;
import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JOptionPane;
import javax.swing.JToolBar;

public class ToolBar {

    //Création de notre barre d'outils

    private JToolBar toolBar;

    //Les boutons de la barre d'outils
    private JButton play = new JButton(new ImageIcon(getClass().getResource("/Images/play.jpg"))),
            pause = new JButton(new ImageIcon(getClass().getResource("/Images/pause.jpg"))),
            add = new JButton(new ImageIcon(getClass().getResource("/Images/add.jpg")));

    private Color fondBouton = Color.white;

    public ToolBar() {
        toolBar = new JToolBar();

        toolBar.add(play);
        toolBar.add(pause);
        toolBar.addSeparator();
        toolBar.add(add);

        add.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent arg0) {
                Pong.pong.execute(false);
                AddBox box = new AddBox(null, "Add Mobile", true);
                BoxInfo info = box.showDialog();
                JOptionPane jop = new JOptionPane();
                jop.showMessageDialog(null, info.toString(), "Informations du mobile", JOptionPane.INFORMATION_MESSAGE);
                Pong.pong.execute(true);
            }
        });

        toolBar.setVisible(true);
    }

    public JToolBar getToolBar() {
        return toolBar;
    }
}
