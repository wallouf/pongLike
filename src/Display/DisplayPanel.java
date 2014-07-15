/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Display;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JPanel;

/**
 *
 * @author labo10
 */
public class DisplayPanel extends JPanel {

    private boolean keyL = false;
    private boolean keyR = false;
    private Movepal[] move = new Movepal[2];
    private int scoreJ1 = 0;
    private int scoreJ2 = 0;
    private int firstSet = 0;
    
    DisplayFrame parent;

    public DisplayPanel(DisplayFrame parent, int height, int widht) {
        this.parent = parent;
        this.setSize(widht, height);
        this.setBackground(Color.BLACK);
        firstSet = 0;
        this.setVisible(false);
    }
    
    public DisplayPanel(DisplayFrame parent){
        this.parent = parent;
        this.setSize(994,445);
        this.setVisible(false);
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
//On décide d'une couleur de fond pour notre rectangle
        g.setColor(Color.black);
//On dessine celui-ci afin qu'il prenne tout la surface
        g.fillRect(0, 0, this.getWidth(), this.getHeight());
//On redéfinit une couleur pour notre rond
        g.setColor(Color.white);
        g.fillOval(parent.getBoule().getPosX(), parent.getBoule().getPosY(), parent.getBoule().getRayon(), parent.getBoule().getRayon());
        if (parent.getPal() != null) {
            for (int i = 0; i < parent.getPal().length; i++) {
                g.fillRect(parent.getPal()[i].getPosX(), parent.getPal()[i].getPosY(), parent.getPal()[i].getLargeur(), parent.getPal()[i].getLongueur());
            }
        }
        if (firstSet == 0) {
            Font font = new Font("Courier", Font.BOLD, 40);
            g.setFont(font);
        }
        g.drawString("" + scoreJ1, (this.getWidth() / 2) - 30, 35);
        g.drawString("" + scoreJ2, (this.getWidth() / 2) + 30, 35);
    }

    public void listenKeys(int key) {
        if (parent.getPal() != null && parent.getPal().length > 0) {
            if ((key == 40 || key == 38) && (parent.getServer()==0 || parent.getServer()==2)) {
                move[1] = new Movepal(key, 1);
                move[1].start();
            } else if ((key == 90 || key == 83) && (parent.getServer()==0 || parent.getServer()==1)) {
                move[0] = new Movepal(key, 0);
                move[0].start();
            }
        }
    }

    class Movepal extends Thread {

        int key;
        int side = 0;

        public Movepal(int key, int side) {
            this.key = key;
            this.side = side;
        }

        @Override
        public void run() {
            //au moins un coup
            int compt = 0;
            if (side == 0) {
                while (!keyL || compt < 0) {
                    if (parent.getPal() != null && parent.getPal().length > 0 && parent.getPal()[0] != null) {
                        if (key == 90) {
                            if (parent.getPal()[0].getPosY() - 2 >= 0) {
                                parent.getPal()[0].setPosY(parent.getPal()[0].getPosY() - 2);
                            }
                        } else if (key == 83) {
                            if (parent.getPal()[0].getPosY() + parent.getPal()[0].getLongueur() + 2 <= getHeight()) {
                                parent.getPal()[0].setPosY(parent.getPal()[0].getPosY() + 2);
                            }
                        }
                        try {
                            this.sleep(10);
                        } catch (InterruptedException ex) {
                            Logger.getLogger(DisplayPanel.class.getName()).log(Level.SEVERE, null, ex);
                        }
                    } else {
                        return;
                    }
                }
            } else if (side == 1) {
                while (!keyR || compt < 0) {
                    if (parent.getPal() != null && parent.getPal().length > 0 && parent.getPal()[1] != null) {
                        if (key == 38) {
                            if (parent.getPal()[1].getPosY() - 2 >= 0) {
                                parent.getPal()[1].setPosY(parent.getPal()[1].getPosY() - 2);
                            }
                        } else if (key == 40) {
                            if (parent.getPal()[1].getPosY() + parent.getPal()[1].getLongueur() + 2 <= getHeight()) {
                                parent.getPal()[1].setPosY(parent.getPal()[1].getPosY() + 2);
                            }
                        }
                        try {
                            this.sleep(10);
                        } catch (InterruptedException ex) {
                            Logger.getLogger(DisplayPanel.class.getName()).log(Level.SEVERE, null, ex);
                        }
                    } else {
                        return;
                    }
                }
            }
        }
    }

    public boolean isKeyR() {
        return keyR;
    }

    public void setKeyR(boolean keyR) {
        this.keyR = keyR;
    }

    public boolean isKeyL() {
        return keyL;
    }

    public void setKeyL(boolean keyL) {
        this.keyL = keyL;
    }

    public int getScoreJ1() {
        return scoreJ1;
    }

    public void setScoreJ1(int scoreJ1) {
        this.scoreJ1 = scoreJ1;
    }

    public int getScoreJ2() {
        return scoreJ2;
    }

    public void setScoreJ2(int scoreJ2) {
        this.scoreJ2 = scoreJ2;
    }
}