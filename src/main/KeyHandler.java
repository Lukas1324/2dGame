package main;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class KeyHandler implements KeyListener {
    GamePanel gp;
    public KeyHandler(GamePanel gp){
        this.gp = gp;
    }
    public  boolean upPressed, downPressed, leftPressed, rightPressed, enterPressed;
    int enterCounter = 0;
    //Debug
    public boolean checkDrawTime;
    @Override
    public void keyTyped(KeyEvent e) {

    }

    @Override
    public void keyPressed(KeyEvent e) {
        int code = e.getKeyCode();

        //TitleScreenState
        if(gp.gameState == gp.titleScreenState){
            if(gp.ui.subTitleScreenState == 0){
                if(code == KeyEvent.VK_DOWN){
                    gp.ui.numTitelScreen++;
                    if(gp.ui.numTitelScreen > 2){
                        gp.ui.numTitelScreen = 0;
                    }
                }
                if(code == KeyEvent.VK_UP){
                    gp.ui.numTitelScreen--;
                    if(gp.ui.numTitelScreen < 0){
                        gp.ui.numTitelScreen = 2;
                    }
                }
                if(code == KeyEvent.VK_ENTER){
                    if(gp.ui.numTitelScreen == 0){
                        gp.ui.subTitleScreenState = 1;
                    } else if (gp.ui.numTitelScreen == 1) {
                        //Load Game
                    } else if (gp.ui.numTitelScreen == 2) {
                        System.exit(0);
                    }
                }
            } else if (gp.ui.subTitleScreenState == 1) {
                if(code == KeyEvent.VK_DOWN){
                    gp.ui.numTitelScreen++;
                    if(gp.ui.numTitelScreen > 4){
                        gp.ui.numTitelScreen = 0;
                    }
                }
                if(code == KeyEvent.VK_UP){
                    gp.ui.numTitelScreen--;
                    if(gp.ui.numTitelScreen < 0){
                        gp.ui.numTitelScreen = 4;
                    }
                }
                if(code == KeyEvent.VK_ENTER){

                    if(gp.ui.numTitelScreen == 0){
                        gp.gameState = gp.playState;
                    } else if (gp.ui.numTitelScreen == 1) {
                        gp.gameState = gp.playState;
                    } else if (gp.ui.numTitelScreen == 2) {
                        gp.gameState = gp.playState;
                    }else if (gp.ui.numTitelScreen == 3) {
                        gp.gameState = gp.playState;
                    }else if (gp.ui.numTitelScreen == 4) {
                        gp.ui.subTitleScreenState = 0;
                    }

                }
            }

        }


        //PlayState
        if (gp.gameState == gp.playState){
            if (code == KeyEvent.VK_DOWN){
                downPressed = true;
            }if (code == KeyEvent.VK_UP){
                upPressed = true;
            }if (code == KeyEvent.VK_LEFT){
                leftPressed = true;
            }if (code == KeyEvent.VK_RIGHT){
                rightPressed = true;
            }if (code == KeyEvent.VK_U){
                if (checkDrawTime == true){
                    checkDrawTime = false;
                }else {
                    checkDrawTime = true;
                }
            }if (code == KeyEvent.VK_P){
                gp.gameState = gp.pauseState;
            }
            if (code == KeyEvent.VK_ENTER){
                enterPressed = true;
            }
        }

        //PauseState
        else if (gp.gameState == gp.pauseState){
            if (code == KeyEvent.VK_P){
                gp.gameState = gp.playState;
            }
        }

        //DialogState
        else if (gp.gameState == gp.diaglogState){
            if (code == KeyEvent.VK_ENTER){
                gp.gameState = gp.playState;
            }
        }




    }

    @Override
    public void keyReleased(KeyEvent e) {
        int code = e.getKeyCode();

        if (code == KeyEvent.VK_DOWN){
            downPressed = false;
        }if (code == KeyEvent.VK_UP){
            upPressed = false;
        }if (code == KeyEvent.VK_LEFT){
            leftPressed = false;
        }if (code == KeyEvent.VK_RIGHT){
            rightPressed = false;
        }
    }
}
