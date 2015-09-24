/*
 classe permettant la création d'un menu en haut de l'écran
 Il va offrir la possibilité de créer de nouveaux objets, de lancer les mobiles ou de les arreter
 */
package Menus;

import Formes.Mobile;
import Formes.Mur;
import Mobiles.Cercle;
import Pong.Couleur;
import Pong.Game;
import Pong.JukeBox;
import Pong.Pong;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;

public class MenuBar extends JFrame {

    private JMenuBar menuBar;
    private JButton addS;
    private JButton execute;
    private JButton pause;
    private JButton start;
    private JButton param;
    private JButton music;
    private JButton game;
    private JMenuItem space;

    private boolean started, on, inGame;

    public MenuBar() {
        menuBar = new JMenuBar();
        addS = new JButton("Add");
        execute = new JButton("Launch");
        pause = new JButton("Pause");
        start = new JButton("Menu");
        param = new JButton("Param");
        music = new JButton("Music");
        game = new JButton("Game");
        space = new JMenuItem("");
        
        JukeBox.load("/SFX/menuselect.mp3", "menuselect");
        JukeBox.load("/SFX/pongbg.mp3", "bg");
        JukeBox.load("/SFX/intro.mp3", "intro");

        started = false;
        on = true;
        inGame = false;

        addS.setFocusPainted(false);
        execute.setFocusPainted(false);
        pause.setFocusPainted(false);
        start.setFocusPainted(false);

        addS.setVisible(false);
        execute.setVisible(false);
        pause.setVisible(false);
        param.setVisible(false);
        game.setVisible(false);
        music.setVisible(false);

        JukeBox.loop("intro");

        start.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JukeBox.play("menuselect", 0);
                if (on & inGame) {
                    JukeBox.stop("bg");
                    JukeBox.loop("intro");
                }
                Pong.pong.setIntro(false);
                Pong.pong.getMobiles().clear();
                Pong.pong.getMurs().clear();
                Pong.pong.getGame().getPlayer().setVisible(false);
                Pong.pong.setMobiColli(true);
                Pong.pong.addWalls(Pong.pong.random, 0.1);
                Pong.pong.addWall(new Mur(340, 180, Pong.pong.random, 50, 150, 9, 1));
                Pong.pong.addMobile(new Cercle(200, 225, Pong.pong.r, 50, 7, 4, 135,1));
                Pong.pong.execute(false);
                Pong.pong.getGame().setGameOver(false);
                started = true;
                inGame = false;

                addS.setVisible(true);
                execute.setVisible(true);
                param.setVisible(true);
                game.setVisible(true);
                music.setVisible(true);
            }
        });

        addS.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (started && !inGame) {
                    JukeBox.play("menuselect", 0);
                    ArrayList<Mobile> mobis = Pong.pong.getMobiles();
                    boolean wasMoving = true;
                    for (int i = 0; i < mobis.size(); i++) {
                        wasMoving = mobis.get(i).getMove();
                    }
                    Pong.pong.execute(false);
                    AddBox box = new AddBox(null, "Add Mobile", true);
                    BoxInfo info = box.showDialog();
                    JOptionPane jop = new JOptionPane();
                    jop.showMessageDialog(null, info.toString(), "Informations du mobile", JOptionPane.INFORMATION_MESSAGE);
                    if (wasMoving) {
                        Pong.pong.execute(true);
                    }
                }
            }
        });

        execute.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (started && !inGame) {
                    JukeBox.play("menuselect", 0);
                    Pong.pong.execute(true);
                    execute.setVisible(false);
                    pause.setVisible(true);
                }
            }
        });

        music.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JukeBox.play("menuselect", 0);
                if (on && inGame) {
                    JukeBox.stop("bg");
                    on = false;
                } else if (!on && inGame) {
                    JukeBox.loop("bg");
                    on = true;
                } else if (on && !inGame) {
                    JukeBox.stop("intro");
                    on = false;
                } else {
                    JukeBox.loop("intro");
                    on = true;
                }
            }
        });
        
        game.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (started) {
                    JukeBox.play("menuselect", 0);
                    if (!inGame) {
                        JukeBox.stop("intro");
                        JukeBox.loop("bg");
                    }
                    addS.setVisible(false);
                    execute.setVisible(false);
                    pause.setVisible(false);
                    param.setVisible(false);

                    Pong.pong.setMobiColli(false);
                    Pong.pong.setGame(new Game(true));
                    inGame = true;
                }
            }
        });

        param.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (started && !inGame) {
                    JukeBox.play("menuselect", 0);
                    Pong.pong.execute(false);
                    StartBox start = new StartBox(null, "Param Mobiles", true);
                    BoxInfo info = start.showDialog();
                    JOptionPane jop = new JOptionPane();
                    jop.showMessageDialog(null, info.toString(), "Informations du mobile", JOptionPane.INFORMATION_MESSAGE);
                }
            }
        });

        pause.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (started) {
                    JukeBox.play("menuselect", 0);
                    Pong.pong.execute(false);
                    execute.setVisible(true);
                    pause.setVisible(false);
                }
            }
        });

        this.menuBar.add(start);
        this.menuBar.add(param);
        this.menuBar.add(addS);
        this.menuBar.add(space);
        this.menuBar.add(game);
        this.menuBar.add(execute);
        this.menuBar.add(pause);
        this.menuBar.add(music);
    }

    public JMenuBar getMenu() {
        return menuBar;
    }
}
