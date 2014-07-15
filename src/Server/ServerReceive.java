/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Server;

import Display.DisplayFrame;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author labo10
 */
public class ServerReceive extends Thread {

    DisplayFrame parent;
    private BufferedReader in;
    private String message = null;

    public ServerReceive(DisplayFrame parent) {
        this.parent = parent;
        try {
            in = new BufferedReader(new InputStreamReader(parent.getSocket().getInputStream()));
        } catch (IOException ex) {
            Logger.getLogger(ServerReceive.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void cutAndSave(String message) {
        String[] tmp = message.split("/");
        for (int i = 0; i < tmp.length; i++) {
            String[] detail = tmp[i].split(":");
            if (detail.length > 1) {
                if (detail[0].equalsIgnoreCase("T")) {
                    parent.setT(Integer.parseInt(detail[1]));
                } else if (detail[0].equalsIgnoreCase("PAL")) {
                    parent.setSizePalette(Integer.parseInt(detail[1]));
                } else if (detail[0].equalsIgnoreCase("RX") && parent.getServer() == 1) {
                    parent.getPal()[1].setPosX(Integer.parseInt(detail[1]));
                } else if (detail[0].equalsIgnoreCase("RX") && parent.getServer() == 2) {
                    parent.getPal()[0].setPosX(Integer.parseInt(detail[1]));
                } else if (detail[0].equalsIgnoreCase("RY") && parent.getServer() == 1) {
                    parent.getPal()[1].setPosY(Integer.parseInt(detail[1]));
                } else if (detail[0].equalsIgnoreCase("RY") && parent.getServer() == 2) {
                    parent.getPal()[0].setPosY(Integer.parseInt(detail[1]));
                } else if (detail[0].equalsIgnoreCase("BX")) {
                    parent.getBoule().setPosX(Integer.parseInt(detail[1]));
                } else if (detail[0].equalsIgnoreCase("BY")) {
                    parent.getBoule().setPosY(Integer.parseInt(detail[1]));
                } else if (detail[0].equalsIgnoreCase("J1")) {
                    parent.getDisplayP().setScoreJ1(Integer.parseInt(detail[1]));
                } else if (detail[0].equalsIgnoreCase("J2")) {
                    parent.getDisplayP().setScoreJ2(Integer.parseInt(detail[1]));
                }
            }
        }
    }

    @Override
    public void run() {
        parent.ConfigureListener();
        while (parent.isExMessage()) {
            try {
                cutAndSave(in.readLine());
            } catch (IOException ex) {
                parent.StopGame();
                parent.ChangePanel(12);
            }
            //HOST - receive palette position
            if (parent.getServer() == 1) {
                parent.getControl().checkPosition();
                //CLIENT - receive ball position
            }
            parent.getDisplayP().repaint();
            try {
                ServerReceive.sleep(parent.getT());
            } catch (InterruptedException ex) {
                Logger.getLogger(ServerReceive.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
}
