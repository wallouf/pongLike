/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Display;

import Game.GameBool;
import Game.GamePalette;
import Game.GamePositionControl;
import Game.GameThread;
import Server.ServerReceive;
import Server.ServerSend;
import java.awt.BorderLayout;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyListener;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JFrame;
import javax.swing.JOptionPane;

/**
 *
 * @author labo10
 */
public class DisplayFrame extends JFrame {

    private GameBool boule;
    private DisplayPanel displayP;
    private boolean animState = false;
    private GameThread anim;
    private GamePositionControl control;
    private GamePalette pal[];
    private int lev = 0;
    private int siz = 0;
    private KeyListener keyListen;
    private int sizePalette = 200;
    private long t = 10;
    private int oldPanel = 0;
    private DisplayBar displayB;
    //MENU PART
    private DisplayMenuMain displayMenuMain;
    private DisplayMenuNewGame displayNewGame;
    private DisplayMenuNewGameLan displayNewGameLan;
    private DisplayMenuNewGameLanJoin displayNewGameLanJoin;
    private DisplayMenuOption displayMenuOption;
    //server
    private int server = 0;
    private ServerSocket socketserver;
    private Socket socket;
    private boolean exMessage = false;
    private ServerSend sMessage;
    private ServerReceive rMessage;
    
    private int portLan = 2009;

    public DisplayFrame() {
        initAllComponent();
    }

    public void updateSizePalette() {
        if (pal != null) {
            for (int i = 0; i < pal.length; i++) {
                if (pal[i] != null) {
                    pal[i].setLongueur(sizePalette);
                }
            }
        }
        displayP.repaint();
    }

    public final void CreatePartyComponent(int rayon) {
        boule = new GameBool(-rayon, -rayon, rayon);
        pal = new GamePalette[2];
        pal[0] = new GamePalette(10, ((this.getHeight() / 2) - (100)));
        pal[1] = new GamePalette(this.getWidth() - 25, ((this.getHeight() / 2) - (100)));
        control = new GamePositionControl(this);
    }

    public final void initAllComponent() {
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException | InstantiationException | IllegalAccessException | javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(DisplayFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        this.setTitle("BoOlGaMeS !");
        this.setSize(1000, 520);
        this.setResizable(false);
        this.setLocationRelativeTo(null);
        InitAllPanel();
        this.setLayout(new BorderLayout());
        this.getContentPane().add(displayMenuMain, BorderLayout.CENTER);
        this.getContentPane().add(displayB, BorderLayout.NORTH);
        CreatePartyComponent(20);
        this.setVisible(true);
    }

    public void InitAllPanel() {
        displayB = new DisplayBar(this);
        displayP = new DisplayPanel(this);
        displayNewGame = new DisplayMenuNewGame(this);
        displayNewGameLan = new DisplayMenuNewGameLan(this);
        displayNewGameLanJoin = new DisplayMenuNewGameLanJoin(this);
        displayMenuMain = new DisplayMenuMain(this);
        displayMenuOption = new DisplayMenuOption(this);
        displayMenuMain.setVisible(true);

        keyListen = new KeyAdapter() {

            @Override
            public void keyPressed(java.awt.event.KeyEvent evt) {
                if (evt.getKeyCode() == 38 || evt.getKeyCode() == 40) {
                    displayP.setKeyR(false);
                }
                if (evt.getKeyCode() == 90 || evt.getKeyCode() == 83) {
                    displayP.setKeyL(false);
                }
                displayP.listenKeys(evt.getKeyCode());
            }

            @Override
            public void keyReleased(java.awt.event.KeyEvent evt) {
                if (evt.getKeyCode() == 38 || evt.getKeyCode() == 40) {
                    displayP.setKeyR(true);
                }
                if (evt.getKeyCode() == 90 || evt.getKeyCode() == 83) {
                    displayP.setKeyL(true);
                }
            }
        };
    }

    public void ChangePanel(int panel) {
        if (panel != oldPanel) {
            switch (oldPanel) {
                case 0:
                    displayMenuMain.setVisible(false);
                    this.getContentPane().remove(displayMenuMain);
                    break;
                case 1:
                    displayNewGame.setVisible(false);
                    this.getContentPane().remove(displayNewGame);
                    break;
                case 2:
                    displayMenuOption.setVisible(false);
                    this.getContentPane().remove(displayMenuOption);
                    break;
                case 11:
                    displayP.setVisible(false);
                    this.getContentPane().remove(displayP);
                    break;
                case 12:
                    displayNewGameLan.setVisible(false);
                    this.getContentPane().remove(displayNewGameLan);
                    break;
                case 122:
                    displayNewGameLanJoin.setVisible(false);
                    this.getContentPane().remove(displayNewGameLanJoin);
                    break;
            }
            switch (panel) {
                case 0:
                    this.getContentPane().add(displayMenuMain, BorderLayout.CENTER);
                    displayMenuMain.setVisible(true);
                    break;
                case 1:
                    this.getContentPane().add(displayNewGame, BorderLayout.CENTER);
                    displayNewGame.setVisible(true);
                    break;
                case 2:
                    displayMenuOption.prepareDisplay();
                    this.getContentPane().add(displayMenuOption, BorderLayout.CENTER);
                    displayMenuOption.setVisible(true);
                    break;
                case 11:
                    this.getContentPane().add(displayP, BorderLayout.CENTER);
                    displayP.setVisible(true);
                    break;
                case 12:
                    this.getContentPane().add(displayNewGameLan, BorderLayout.CENTER);
                    displayNewGameLan.setVisible(true);
                    break;
                case 122:
                    this.getContentPane().add(displayNewGameLanJoin, BorderLayout.CENTER);
                    displayNewGameLanJoin.setVisible(true);
                    break;
            }
            oldPanel = panel;
            this.repaint();
        }
    }

    public final void ConfigureListener() {
        displayB.setFocusable(false);
        displayP.setFocusable(true);
        displayP.requestFocus();
        displayP.addKeyListener(keyListen);

    }

    public void removeListener() {
        displayP.removeKeyListener(keyListen);

    }

    public void Initposition() {
        boule.setPosX(-boule.getRayon());
        boule.setPosY(-boule.getRayon());
    }

    public void StopGame() {
        animState = false;
        if (server == 1) {
            closeSocket();
        } else if (server == 2) {
            closeToIp();
        }
        Initposition();
        removeListener();
    }

    public void LaunchLocalGame() {
        if (oldPanel != 11) {
            this.ChangePanel(11);
        }
        server = 0;
        ConfigureListener();
        if (anim != null && anim.isAlive()) {
            try {
                animState = false;
                anim.join();
            } catch (InterruptedException ex) {
                Logger.getLogger(DisplayFrame.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        anim = new GameThread(this);
        anim.start();
    }

    public void LaunchServerGame(boolean host, String ip) {
        if (host) {
            server = 1;
            if (oldPanel != 11) {
                this.ChangePanel(11);
            }
            createSocket();
        } else {
            server = 2;
            if (connectToIp(ip)) {
                if (oldPanel != 11) {
                    this.ChangePanel(11);
                }
            } else {
                JOptionPane.showMessageDialog(this, "Problem : Cannot reach Ip : " + ip + "\nPlease try again");
                StopGame();
                ChangePanel(12);
            }

        }
    }

    public void createSocket() {
        try {
            JOptionPane.showMessageDialog(this, "Waiting connection ...");
            socketserver = new ServerSocket(portLan, 5);
            socket = socketserver.accept();
            JOptionPane.showMessageDialog(this, "New player connect !");
            launchMessageExchange();
        } catch (IOException ex) {
            System.err.println("server open:" + ex);
            StopGame();
            ChangePanel(12);
        }
    }

    public void launchMessageExchange() {
        exMessage = true;
        sMessage = new ServerSend(this);
        sMessage.start();
        rMessage = new ServerReceive(this);
        rMessage.start();
    }

    public void closeSocket() {
        try {
            exMessage = false;
            socketserver.close();
            socket.close();
        } catch (IOException ex) {
            System.err.println("server close:" + ex);
            StopGame();
            ChangePanel(12);
        }
    }

    public boolean connectToIp(String IP) {
        try {
            socket = new Socket(IP, portLan);
            launchMessageExchange();
        } catch (IOException ex) {
            System.err.println("Connection open:" + ex);
            StopGame();
            ChangePanel(122);
            return false;
        }
        return true;
    }

    public void closeToIp() {
        try {
            exMessage = false;
            socket.close();
        } catch (Exception ex) {
            System.err.println("Connection close:" + ex);
            StopGame();
            ChangePanel(122);
        }
    }

    public void setSize(int size) {
        switch (size) {
            case 2:
                sizePalette = 50;
                siz = 2;
                break;
            case 1:
                sizePalette = 150;
                siz = 1;
                break;
            case 0:
                sizePalette = 200;
                siz = 0;
                break;
        }
        updateSizePalette();
    }

    public void setLevel(int level) {
        switch (level) {
            case 2:
                t = 4;
                control.setIncrement(10);
                lev = 2;
                break;
            case 1:
                t = 4;
                control.setIncrement(6);
                lev = 1;
                break;
            case 0:
                t = 6;
                control.setIncrement(4);
                lev = 0;
                break;
        }
    }

    public GameThread getAnim() {
        return anim;
    }

    public void setAnim(GameThread anim) {
        this.anim = anim;
    }

    public boolean isAnimState() {
        return animState;
    }

    public void setAnimState(boolean animState) {
        this.animState = animState;
    }

    public GameBool getBoule() {
        return boule;
    }

    public void setBoule(GameBool boule) {
        this.boule = boule;
    }

    public GamePositionControl getControl() {
        return control;
    }

    public void setControl(GamePositionControl control) {
        this.control = control;
    }

    public DisplayPanel getDisplayP() {
        return displayP;
    }

    public void setDisplayP(DisplayPanel displayP) {
        this.displayP = displayP;
    }

    public GamePalette[] getPal() {
        return pal;
    }

    public void setPal(GamePalette[] pal) {
        this.pal = pal;
    }

    public int getSizePalette() {
        return sizePalette;
    }

    public void setSizePalette(int sizePalette) {
        this.sizePalette = sizePalette;
        updateSizePalette();
    }

    public long getT() {
        return t;
    }

    public void setT(long t) {
        this.t = t;
    }

    public DisplayBar getDisplayB() {
        return displayB;
    }

    public void setDisplayB(DisplayBar displayB) {
        this.displayB = displayB;
    }

    public DisplayMenuMain getDisplayMenuMain() {
        return displayMenuMain;
    }

    public void setDisplayMenuMain(DisplayMenuMain displayMenuMain) {
        this.displayMenuMain = displayMenuMain;
    }

    public DisplayMenuNewGame getDisplayNewGame() {
        return displayNewGame;
    }

    public void setDisplayNewGame(DisplayMenuNewGame displayNewGame) {
        this.displayNewGame = displayNewGame;
    }

    public int getOldPanel() {
        return oldPanel;
    }

    public void setOldPanel(int oldPanel) {
        this.oldPanel = oldPanel;
    }

    public int getLev() {
        return lev;
    }

    public void setLev(int lev) {
        this.lev = lev;
    }

    public int getSiz() {
        return siz;
    }

    public void setSiz(int siz) {
        this.siz = siz;
    }

    public int getServer() {
        return server;
    }

    public void setServer(int server) {
        this.server = server;
    }

    public boolean isExMessage() {
        return exMessage;
    }

    public void setExMessage(boolean exMessage) {
        this.exMessage = exMessage;
    }

    public Socket getSocket() {
        return socket;
    }

    public void setSocket(Socket socket) {
        this.socket = socket;
    }

    public ServerSocket getSocketserver() {
        return socketserver;
    }

    public void setSocketserver(ServerSocket socketserver) {
        this.socketserver = socketserver;
    }

    public int getPortLan() {
        return portLan;
    }

    public void setPortLan(int portLan) {
        this.portLan = portLan;
    }
    
}

