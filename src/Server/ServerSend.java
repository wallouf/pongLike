/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Server;

import Display.DisplayFrame;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author labo10
 */
public class ServerSend extends Thread {

    DisplayFrame parent;
    private PrintWriter out;
    private String message = null;

    public ServerSend(DisplayFrame parent) {
        this.parent = parent;
        try {
            out = new PrintWriter(parent.getSocket().getOutputStream());
        } catch (IOException ex) {
            parent.StopGame();
            parent.ChangePanel(12);
        }
    }

    @Override
    public void run() {
        try {
            ServerSend.sleep(200);
        } catch (InterruptedException ex) {
            Logger.getLogger(ServerSend.class.getName()).log(Level.SEVERE, null, ex);
        }
        int cp = 0;
        while (parent.isExMessage()) {
            //HOST - send ball position
            if (parent.getServer() == 1) {
                if (cp < 5) {
                    out.println("T:" + parent.getT() + "/PAL:" + parent.getSizePalette() + "/BX:" + parent.getBoule().getPosX() + "/BY:" + parent.getBoule().getPosY() + "/RX:" + parent.getPal()[0].getPosX() + "/RY:" + parent.getPal()[0].getPosY() + "/J1:" + parent.getDisplayP().getScoreJ1() + "/J2:" + parent.getDisplayP().getScoreJ2());
                    cp++;
                } else {
                    out.println("BX:" + parent.getBoule().getPosX() + "/BY:" + parent.getBoule().getPosY() + "/RX:" + parent.getPal()[0].getPosX() + "/RY:" + parent.getPal()[0].getPosY() + "/J1:" + parent.getDisplayP().getScoreJ1() + "/J2:" + parent.getDisplayP().getScoreJ2());
                }
                out.flush();
                //CLIENT - send palette position
            } else if (parent.getServer() == 2) {
                out.println("RX:" + parent.getPal()[1].getPosX() + "/RY:" + parent.getPal()[1].getPosY());
                out.flush();
            }
            try {
                ServerSend.sleep(parent.getT());
            } catch (InterruptedException ex) {
                Logger.getLogger(ServerSend.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
}
