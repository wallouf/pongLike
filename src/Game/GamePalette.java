/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Game;

/**
 *
 * @author labo10
 */
public class GamePalette {

    private int posX;
    private int posY;
    private int largeur;
    private int longueur;

    public GamePalette() {
        posX = 0;
        posY = 0;
        largeur = 10;
        longueur = 200;
    }

    public GamePalette(int X, int Y) {
        posX = X;
        posY = Y;
        largeur = 10;
        longueur = 200;
    }

    public int getLargeur() {
        return largeur;
    }

    public void setLargeur(int largeur) {
        this.largeur = largeur;
    }

    public int getLongueur() {
        return longueur;
    }

    public void setLongueur(int longueur) {
        this.longueur = longueur;
    }

    public int getPosX() {
        return posX;
    }

    public void setPosX(int posX) {
        this.posX = posX;
    }

    public int getPosY() {
        return posY;
    }

    public void setPosY(int posY) {
        this.posY = posY;
    }
}
