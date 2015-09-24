package Pong;

import Menus.MenuBar;
import javax.swing.JFrame;

public class Main {

    public static void main(String[] args) {
        JFrame frame = new JFrame("PONG");
        JukeBox.init();
        frame.add(new Pong());
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        MenuBar menu = new MenuBar();
        frame.setJMenuBar(menu.getMenu());
        frame.setResizable(true);
        frame.pack();
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
        
    }

}
