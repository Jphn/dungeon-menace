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

    public boolean upPressed, downPressed, leftPressed, rigthPressed, enterPressed, shotKeyPressed, spaceKeyPressed;
    public boolean showDebugText = false;
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
        } 
        
        // PLAY STATE
        else if (gp.gameState == gp.playState) {
            playState(code);
        } 
        
        // PAUSE STATE
        else if (gp.gameState == gp.pauseState) {
            pauseState(code);
        } 
        
        // DIALOGUE STATE
        else if (gp.gameState == gp.dialogueState) {
            dialogueState(code);
        }
        
        // CHARACTER STATE
        else if (gp.gameState == gp.characterState) {
           characterState(code);
        }
        
        // OPTIONS STATE
        else if (gp.gameState == gp.optionsState) {
           optionsState(code);
        }
        // GAME OVER STATE
        else if (gp.gameState == gp.gameOverState) {
           gameOverState(code);
        }
        //  TRADE STATE
        else if (gp.gameState == gp.tradeState) {
           
           tradeState(code);
        }
        // MAP STATE
        else if (gp.gameState == gp.mapState) {
           
           mapState(code);
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
        
        if (code == KeyEvent.VK_F) {
            this.shotKeyPressed = false;
        }
        
        if (code == KeyEvent.VK_ENTER) {
            this.enterPressed = false;
        }
        
        if (code == KeyEvent.VK_SPACE) {
            this.spaceKeyPressed = false;
        }
        
        
        
    }
    
    public void titleState(int code){
        if (gp.ui.titleScreenState == 0) {
                if (code == KeyEvent.VK_W) {
                    this.upPressed = true;
                    gp.ui.commandNum -= 1;
                    if (gp.ui.commandNum < 0) {
                        gp.ui.commandNum = 3;
                    }
                }

                if (code == KeyEvent.VK_S) {
                    this.downPressed = true;
                    gp.ui.commandNum += 1;
                    if (gp.ui.commandNum > 3) {
                        gp.ui.commandNum = 0;
                    }
                }
                if (code == KeyEvent.VK_ENTER) {
                    this.enterPressed = true;
                    gp.playSE(1); // coin sound
                    
                    
                    switch (gp.ui.commandNum) {
                        case 0:
                            gp.gameState = gp.playState;
                            gp.stopMusic(); // pausa a música Porque está entrando no jogo 
                            gp.playMusic(0);
                            break;
                        case 1:
                            gp.ui.titleScreenState = 1;
                            gp.ui.commandNum = 0;
                            break;
                        case 2:
                            gp.saveLoad.load();
                            gp.gameState = gp.playState;
                            gp.stopMusic(); // pausa a música Porque está entrando no jogo 
                            gp.playMusic(0);
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
                    gp.ui.commandNum -= 1;
                    if (gp.ui.commandNum < 0) {
                        gp.ui.commandNum = 3;
                    }
                }

                if (code == KeyEvent.VK_S) {
                    this.downPressed = true;
                    gp.ui.commandNum += 1;
                    if (gp.ui.commandNum > 3) {
                        gp.ui.commandNum = 0;
                    }
                }
                if (code == KeyEvent.VK_ENTER) {
                    this.enterPressed = true;
                    gp.playSE(1);
                    switch (gp.ui.commandNum) {
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
    
    public void playState(int code) {
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
        
        // GUARD
        if (code == KeyEvent.VK_SPACE) {
            this.spaceKeyPressed = true;
        }
        
        
        // FIREBALL
        if (code == KeyEvent.VK_F) {
            this.shotKeyPressed = true;
        }
        
        // CHARACTER STATE
        if (code == KeyEvent.VK_C) {
            gp.gameState = gp.characterState;
        }
            
        if (code == KeyEvent.VK_ENTER) {
            this.enterPressed = true;
        }
        
        // OPTIONS STATE
        if (code == KeyEvent.VK_ESCAPE) {
            gp.gameState = gp.optionsState;
        }
            
        // PAUSE STATE
        if (code == KeyEvent.VK_P) {
            if (gp.gameState == gp.playState) {
                gp.gameState = gp.pauseState;
            }
        }
        
        //MAP STATE
        if (code == KeyEvent.VK_M) {
            gp.gameState = gp.mapState;
            this.enterPressed = false;
        }
        
        if(code == KeyEvent.VK_X){
            if(gp.map.miniMapOn == false){
                gp.map.miniMapOn = true;
            }else if(gp.map.miniMapOn == true){
                gp.map.miniMapOn = false;
            }
        
        }
        
            
        // DEBUG
        if (code == KeyEvent.VK_T) {
            if (showDebugText == false) {
                showDebugText = true;

            } else if (showDebugText == true) {
                showDebugText = false;
            }
         }
            
        if (code == KeyEvent.VK_R) {
            switch(gp.currentMap){
                case 0:
                    gp.tileM.loadMap("/maps/worldV3.txt",0);break;
                case 1:
                    gp.tileM.loadMap("/maps/interior01.txt",1);break;
            
            }
            
        }
    }
    
    public void pauseState(int code) {
        if (gp.gameState == gp.playState) {
            gp.gameState = gp.pauseState;
        } 
        
        else if (gp.gameState == gp.pauseState) {
             gp.gameState = gp.playState;
        }
    }
    
    public void dialogueState(int code) {
        if (code == KeyEvent.VK_ENTER) {
            gp.gameState = gp.playState;
        }
    } 
    
    public void tradeState(int code){
        
        if (code == KeyEvent.VK_ENTER) {
            this.enterPressed = true;
            gp.playSE(1); // coin sound


            switch (gp.ui.commandNum) {
                case 0:

                    break;
                case 1:

                    break;
               case 2:
                    gp.gameState = gp.playState;
                    break;

            }
        }
        
        if(gp.ui.subState==0){
            if (code == KeyEvent.VK_W) {
                        this.upPressed = true;
                        gp.ui.commandNum -= 1;
                        if (gp.ui.commandNum < 0) {
                            gp.ui.commandNum = 2;
                        }
                    }

            if (code == KeyEvent.VK_S) {
                 this.downPressed = true;
                 gp.ui.commandNum += 1;
                 if (gp.ui.commandNum > 2) {
                        gp.ui.commandNum = 0;
                 }
            }

            
        } 
        if(gp.ui.subState==1){
            npcInventory(code);
            if(code == KeyEvent.VK_ESCAPE){
                gp.ui.subState = 0;
                
            
            }
            
        
        }
        if(gp.ui.subState==2){
            playerInventory(code);
            if(code == KeyEvent.VK_ESCAPE){
                gp.ui.subState = 0;
                
            
            }
            
        
        }
                
                
    
    }
   
    public void characterState(int code) {
        if (code == KeyEvent.VK_C) {
            gp.gameState = gp.playState;
        }
        
        playerInventory(code);
        
        if (code == KeyEvent.VK_ENTER){
            gp.player.selectItem();
        }
    }   
    
    public void mapState(int code){
        
        if(code == KeyEvent.VK_M){
            gp.gameState = gp.playState;
        }
    }
    
    public void playerInventory(int code){
        if (code == KeyEvent.VK_W) {
            if (gp.ui.playerSlotRow != 0) {
                gp.ui.playerSlotRow--;
                gp.playSE(9);
            }
        }
        
        if (code == KeyEvent.VK_A) {
            if (gp.ui.playerSlotCol != 0) {
                gp.ui.playerSlotCol--;
                gp.playSE(9);
            }
        }
        
        if (code == KeyEvent.VK_S) {
            if (gp.ui.playerSlotRow != 3) {
                gp.ui.playerSlotRow++;
                gp.playSE(9);
            }
        }
        
        if (code == KeyEvent.VK_D) {
            if (gp.ui.playerSlotCol != 4) {
                gp.ui.playerSlotCol++;
                gp.playSE(9);
            }
        }
    
    }
     
    public void npcInventory(int code){
        if (code == KeyEvent.VK_W) {
            if (gp.ui.npcSlotRow != 0) {
                gp.ui.npcSlotRow--;
                gp.playSE(9);
            }
        }
        
        if (code == KeyEvent.VK_A) {
            if (gp.ui.npcSlotCol != 0) {
                gp.ui.npcSlotCol--;
                gp.playSE(9);
            }
        }
        
        if (code == KeyEvent.VK_S) {
            if (gp.ui.npcSlotRow != 3) {
                gp.ui.npcSlotRow++;
                gp.playSE(9);
            }
        }
        
        if (code == KeyEvent.VK_D) {
            if (gp.ui.npcSlotCol != 4) {
                gp.ui.npcSlotCol++;
                gp.playSE(9);
            }
        }
    
    }
     
    public void optionsState(int code) {
        if (code == KeyEvent.VK_ESCAPE) {
            gp.gameState = gp.playState;
        }
        
        if (code == KeyEvent.VK_ENTER) {
            enterPressed = true;
        }
        
        int maxCommandNum = 0;
        switch(gp.ui.optionsSubState) {
            case 0: maxCommandNum = 5; break;
            case 3: maxCommandNum = 1; break;
        }
        
        if (code == KeyEvent.VK_W) {
            gp.ui.commandNum--;
            gp.playSE(9);
            if (gp.ui.commandNum < 0) {
                gp.ui.commandNum = maxCommandNum;
            }
        }
        
        if (code == KeyEvent.VK_S) {
            gp.ui.commandNum++;
            gp.playSE(9);
            if (gp.ui.commandNum > maxCommandNum) {
                gp.ui.commandNum = 0;
            }
        }
        
        if (code == KeyEvent.VK_A) {
            if (gp.ui.optionsSubState == 0) {
                if (gp.ui.commandNum == 1 && gp.music.volumeScale > 0) {
                    gp.music.volumeScale--;
                    gp.music.checkVolume();
                    gp.playSE(9);
                }
                if (gp.ui.commandNum == 2 && gp.soundEffects.volumeScale > 0) {
                    gp.soundEffects.volumeScale--;
                    gp.playSE(9);
                }
            }
        }
        
        if (code == KeyEvent.VK_D) {
            if (gp.ui.optionsSubState == 0) {
                if (gp.ui.commandNum == 1 && gp.music.volumeScale < 5) {
                    gp.music.volumeScale++;
                    gp.music.checkVolume();
                    gp.playSE(9);
                }
                if (gp.ui.commandNum == 2 && gp.soundEffects.volumeScale < 5) {
                    gp.soundEffects.volumeScale++;
                    gp.playSE(9);
                }
            }
        }
    }
    
    public void gameOverState(int code){
        if (code == KeyEvent.VK_W) {
                    this.upPressed = true;
                    gp.ui.commandNum -= 1;
                    if (gp.ui.commandNum < 0) {
                        gp.ui.commandNum = 1;
                    }
                }

                if (code == KeyEvent.VK_S) {
                    this.downPressed = true;
                    gp.ui.commandNum += 1;
                    if (gp.ui.commandNum > 1) {
                        gp.ui.commandNum = 0;
                    }
                }
                
                if (code == KeyEvent.VK_ENTER) {
                    this.enterPressed = true;
                    gp.playSE(1); // coin sound
                    
                    
                    switch (gp.ui.commandNum) {
                        case 0:
                            gp.stopMusic(); // pausa a música Porque está entrando no jogo 
                            gp.resetGame(false);
                            gp.gameState = gp.playState;
                            gp.playMusic(0);
                            break;
                        case 1:
                            gp.gameState = gp.titleState;
                            gp.resetGame(true);
                            
                            break;
                       
                        
                    }
                }
    
    }
}