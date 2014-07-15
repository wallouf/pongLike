/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Game;

/**
 *
 * @author labo10
 */
public class GameBool {
    private int posX;
    private int posY;
    private int rayon;
    
    public GameBool(){
        posX=0;
        posY=0;
        rayon=10;
    }
    
    public GameBool(int posX, int posY, int rayon){
        this.posX=posX;
        this.posY=posY;
        this.rayon=rayon;
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

    public int getRayon() {
        return rayon;
    }

    public void setRayon(int rayon) {
        this.rayon = rayon;
    }
    
}
