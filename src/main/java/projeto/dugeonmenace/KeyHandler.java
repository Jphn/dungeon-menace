/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package projeto.dugeonmenace;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.HashMap;
import java.util.Map;

/**
 *
 * @author LucianoNeto
 */
public class KeyHandler implements KeyListener {

    public boolean upPressed, downPressed, leftPressed, rigthPressed, enterPressed;
    public boolean checkDrawTime = false;
    GamePanel gp;
    
    private Map<Integer, Boolean> teclasPressionadas = new HashMap<>();

    public KeyHandler(GamePanel gp) {
        this.gp = gp;
    }

    @Override
    public void keyTyped(KeyEvent e) {
    }

    @Override
    public void keyPressed(KeyEvent e) {
        int code = e.getKeyCode(); // retorna o valor inteiro da tecla apertada
        
        //TITLE STATE
        if (gp.gameState == gp.titleState) {
            titleState(code);
        } else if (gp.gameState == gp.playState) { // PLAY STATE
            playState(code);
            //CHECK DRAW TIME
            if (code == KeyEvent.VK_T) {
                if (checkDrawTime == false) {
                    checkDrawTime = true;

                } else if (checkDrawTime == true) {
                    checkDrawTime = false;
                }
            }
        } // PAUSE STATE
        else if (code == KeyEvent.VK_P) {
            pauseState(code);
        } //DIALOGUE STATE
        else if (gp.gameState == gp.dialogueState) {
            dialogueState(code);
        }
        // CHARACTER STATE
        else if (gp.gameState == gp.characterState){
           characterState(code);
        }
    }
    @Override
    public void keyReleased(KeyEvent e) {
        int code = e.getKeyCode(); // retorna o valor inteiro da tecla solta

        if (code == KeyEvent.VK_W) {
            this.upPressed = false;
        }

        if (code == KeyEvent.VK_S) {
            this.downPressed = false;
        }

        if (code == KeyEvent.VK_A) {
            this.leftPressed = false;
        }

        if (code == KeyEvent.VK_D) {
            this.rigthPressed = false;
        }
    }
    public void titleState(int code){
        if (gp.ui.titleScreenState == 0) {
                if (code == KeyEvent.VK_W) {
                    this.upPressed = true;
                    gp.ui.comandNum -= 1;
                    if (gp.ui.comandNum < 0) {
                        gp.ui.comandNum = 3;
                    }
                }

                if (code == KeyEvent.VK_S) {
                    this.downPressed = true;
                    gp.ui.comandNum += 1;
                    if (gp.ui.comandNum > 3) {
                        gp.ui.comandNum = 0;
                    }
                }
                if (code == KeyEvent.VK_ENTER) {
                    this.enterPressed = true;
                    gp.playSE(1);
                    switch (gp.ui.comandNum) {
                        case 0:

                            gp.gameState = gp.playState;
                            break;
                        case 1:
                            gp.ui.titleScreenState = 1;
                            gp.ui.comandNum = 0;
                            break;

                        case 2:

                            break;
                        case 3:

                            System.exit(0);
                            break;
                    }

                }
            } else if (gp.ui.titleScreenState == 1) {
                System.out.println(gp.ui.titleScreenState);
                if (code == KeyEvent.VK_W) {
                    this.upPressed = true;
                    gp.ui.comandNum -= 1;
                    if (gp.ui.comandNum < 0) {
                        gp.ui.comandNum = 3;
                    }
                }

                if (code == KeyEvent.VK_S) {
                    this.downPressed = true;
                    gp.ui.comandNum += 1;
                    if (gp.ui.comandNum > 3) {
                        gp.ui.comandNum = 0;
                    }
                }
                if (code == KeyEvent.VK_ENTER) {
                    this.enterPressed = true;
                    gp.playSE(1);
                    switch (gp.ui.comandNum) {
                        case 0:
                            break;
                        case 1:
                            break;
                        case 2:
                            break;
                        case 3:
                            gp.ui.titleScreenState = 0;
                            break;
                    }

                }

            }
    
    }
    public void playState(int code){
         if (code == KeyEvent.VK_W) {
                this.upPressed = true;

            }

            if (code == KeyEvent.VK_S) {
                this.downPressed = true;
            }

            if (code == KeyEvent.VK_A) {
                this.leftPressed = true;
            }

            if (code == KeyEvent.VK_D) {
                this.rigthPressed = true;
            }
            
            //CHARACTER STATE
            if (code == KeyEvent.VK_C) {
                this.rigthPressed = true;
            }
            
            
            if (code == KeyEvent.VK_ENTER) {
                this.enterPressed = true;
            }
            
            
            if (code == KeyEvent.VK_P) {
                if (gp.gameState == gp.playState) {
                    gp.gameState = gp.pauseState;

                }
            }
    
    }
    public void pauseState(int code){
          if (gp.gameState == gp.playState) {
                gp.gameState = gp.pauseState;

            } else if (gp.gameState == gp.pauseState) {
                gp.gameState = gp.playState;
            }
    
    }
    public void dialogueState(int code){
         if (code == KeyEvent.VK_ENTER) {
                gp.gameState = gp.playState;
            }
    } 
    public void characterState(int code){
           if (code == KeyEvent.VK_C) {
                gp.gameState = gp.playState;
            }
    
    }
}
