/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Game;

import Display.DisplayFrame;

/**
 *
 * @author labo10
 */
public class GamePositionControl {

    private int limitLeft;
    private int limitRight;
    private int limitDown;
    private int limitUp;
    private boolean upToDown = true;
    private boolean leftToRight = true;
    private int increment = 1;
    
    DisplayFrame parent;

    public GamePositionControl(DisplayFrame parent) {
        this.parent = parent;
    }

    public void checkPosition() {
        //BORDURE
        //hauteur
        if ((parent.getBoule().getPosY() + parent.getBoule().getRayon() + increment) >= parent.getDisplayP().getHeight() && upToDown) {
            upToDown = false;
        } else if ((parent.getBoule().getPosY() - increment) <= 0 && !upToDown) {
            upToDown = true;
        }
        //cote
        if ((parent.getBoule().getPosX() + parent.getBoule().getRayon() + increment) >= parent.getDisplayP().getWidth() && leftToRight) {
            leftToRight = false;
            parent.getDisplayP().setScoreJ1(parent.getDisplayP().getScoreJ1() + 1);
        } else if ((parent.getBoule().getPosX() - increment) <= 0 && !leftToRight) {
            leftToRight = true;
            parent.getDisplayP().setScoreJ2(parent.getDisplayP().getScoreJ2() + 1);
        }
        //CHECK parent.getDisplayP() 0
        if (parent.getPal() != null && parent.getPal()[0] != null) {
            //viens vers player 1
            if (!leftToRight) {
                //descend
                if (upToDown) {
                    if (((parent.getBoule().getPosX() - increment) <= (parent.getPal()[0].getPosX() + parent.getPal()[0].getLargeur())) && ((((parent.getBoule().getPosY() + increment + parent.getBoule().getRayon()) >= (parent.getPal()[0].getPosY()))) && ((parent.getBoule().getPosY() + increment) <= (parent.getPal()[0].getPosY() + parent.getPal()[0].getLongueur())))) {
                        leftToRight = true;
                    }
                    //remonte
                } else {
                    if (((parent.getBoule().getPosX() - increment) <= (parent.getPal()[0].getPosX() + parent.getPal()[0].getLargeur())) && ((((parent.getBoule().getPosY() - increment + parent.getBoule().getRayon()) >= (parent.getPal()[0].getPosY()))) && ((parent.getBoule().getPosY() - increment) <= (parent.getPal()[0].getPosY() + parent.getPal()[0].getLongueur())))) {
                        leftToRight = true;
                    }
                }
            }
        }
        //CHECK parent.getDisplayP() 1
        if (parent.getPal() != null && parent.getPal()[1] != null) {
            //viens vers player 1
            if (leftToRight) {
                //descend
                if (upToDown) {
                    if (((parent.getBoule().getPosX() + increment + parent.getBoule().getRayon()) >= (parent.getPal()[1].getPosX())) && ((((parent.getBoule().getPosY() + increment + parent.getBoule().getRayon()) >= (parent.getPal()[1].getPosY()))) && ((parent.getBoule().getPosY() + increment) <= (parent.getPal()[1].getPosY() + parent.getPal()[1].getLongueur())))) {
                        leftToRight = false;
                    }
                    //remonte
                } else {
                    if (((parent.getBoule().getPosX() + increment + parent.getBoule().getRayon()) >= (parent.getPal()[1].getPosX())) && ((((parent.getBoule().getPosY() - increment + parent.getBoule().getRayon()) >= (parent.getPal()[1].getPosY()))) && ((parent.getBoule().getPosY() - increment) <= (parent.getPal()[1].getPosY() + parent.getPal()[1].getLongueur())))) {
                        leftToRight = false;
                    }
                }
            }
        }



        //INCREMENT HERE    
        if (leftToRight) {
            parent.getBoule().setPosX(parent.getBoule().getPosX() + increment);
        } else {
            parent.getBoule().setPosX(parent.getBoule().getPosX() - increment);
        }
        if (upToDown) {
            parent.getBoule().setPosY(parent.getBoule().getPosY() + increment);
        } else {
            parent.getBoule().setPosY(parent.getBoule().getPosY() - increment);
        }
    }

    public boolean isLeftToRight() {
        return leftToRight;
    }

    public void setLeftToRight(boolean leftToRight) {
        this.leftToRight = leftToRight;
    }

    public boolean isUpToDown() {
        return upToDown;
    }

    public void setUpToDown(boolean upToDown) {
        this.upToDown = upToDown;
    }

    public int getLimitDown() {
        return limitDown;
    }

    public void setLimitDown(int limitDown) {
        this.limitDown = limitDown;
    }

    public int getLimitLeft() {
        return limitLeft;
    }

    public void setLimitLeft(int limitLeft) {
        this.limitLeft = limitLeft;
    }

    public int getLimitRight() {
        return limitRight;
    }

    public void setLimitRight(int limitRight) {
        this.limitRight = limitRight;
    }

    public int getLimitUp() {
        return limitUp;
    }

    public void setLimitUp(int limitUp) {
        this.limitUp = limitUp;
    }

    public int getIncrement() {
        return increment;
    }

    public void setIncrement(int increment) {
        this.increment = increment;
    }
}
