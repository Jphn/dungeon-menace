/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package projeto.dugeonmenace;

import projeto.dugeonmenace.entity.Entity;

/**
 *
 * @author LucianoNeto
 */
public class EventHandler {

    GamePanel gp;
    EventRect eventRect[][][];
    int previousEventX, previousEventY;
    boolean canTouchEvent = true;
    
    int tempMap, tempCol, tempRow;

    public EventHandler(GamePanel gp) {
        this.gp = gp;

        /**
         * Aqui ele ta criando um pequeno
         *
         * quadrado 2x2
         *
         * no meio de um tile
         */
        eventRect = new EventRect[gp.maxMap][gp.maxWorldCol][gp.maxWorldRow];

        int col = 0;
        int row = 0;
        int map = 0;
        
        while (map < gp.maxMap && col < gp.maxWorldCol && row < gp.maxWorldRow) {
            eventRect[map][col][row] = new EventRect();

            eventRect[map][col][row].x = 23;
            eventRect[map][col][row].y = 23;

            eventRect[map][col][row].width = 2;
            eventRect[map][col][row].height = 2;

            eventRect[map][col][row].eventRectDefaultX = eventRect[map][col][row].x;
            eventRect[map][col][row].eventRectDefaultY = eventRect[map][col][row].y;

            col++;
            if (col == gp.maxWorldCol) {
                col = 0;
                row++;
                
                if(row == gp.maxWorldRow){
                    row = 0;
                    map++;
                }
            }
        }
    }

    public void checkEvent() {
        int xDistance = Math.abs(gp.player.worldX - previousEventX);
        int yDistance = Math.abs(gp.player.worldY - previousEventY);
        int distance = Math.max(xDistance, yDistance);

        if (distance > gp.tileSize) {
            canTouchEvent = true;
        }
        if (canTouchEvent) {
            if (hit(0,27, 16, "right") == true) {
                damagePit(gp.dialogueState);
            }
            
            else if (hit(0,23, 12, "up") == true) {
            healingPoll(gp.dialogueState);
            }
        
            else if (hit(0,10, 39, "any") == true) {
                teleport(1,12,12,gp.playState);
            }
            
            else if (hit(1,12, 12, "any") == true) {
                teleport(0,10,39,gp.playState);
            }   
            else if (hit(1,12, 9, "up") == true) {
                speak(gp.npc[1][0]);
            } 
        }
    }

    public void teleport(int map,int col, int row,int gameState) {
        
        gp.gameState = gp.trasitionState;
        
        tempMap = map;
        tempCol = col;
        tempRow = row;
        /*
        gp.player.worldX = col * gp.tileSize;
        gp.player.worldY = row * gp.tileSize;
        gp.currentMap = map;
         
        
        if(gp.gameState == gp.dialogueState){
            gp.ui.currentDialogue = "Teleport !"; //adiciona ao currentDialogue essa string
        }
        previousEventX= gp.player.worldX;
        previousEventY=gp.player.worldY;
        */
        canTouchEvent = false;
        
        gp.playSE(13);
        
    }

    public boolean hit(int map,int col, int row, String reqDirection) {
        boolean hit = false;
        //Check if the player is 1 tile away
        
        if(map == gp.currentMap){
        gp.player.solidArea.x = gp.player.worldX + gp.player.solidArea.x;

        gp.player.solidArea.y = gp.player.worldY + gp.player.solidArea.y;
        
        eventRect[map][col][row].x = col * gp.tileSize + eventRect[map][col][row].x;
        eventRect[map][col][row].y = row * gp.tileSize + eventRect[map][col][row].y;

        if (gp.player.solidArea.intersects(eventRect[map][col][row]) && eventRect[map][col][row].eventDone == false) {
            if (gp.player.direction.contentEquals(reqDirection) || reqDirection.contentEquals("any")) {
                hit = true;

                previousEventX = gp.player.worldX;
                previousEventY = gp.player.worldY;
            }
        }
        
        gp.player.solidArea.x = gp.player.solidAreaDefaultX;
        gp.player.solidArea.y = gp.player.solidAreaDefaultY;
        eventRect[map][col][row].x = eventRect[map][col][row].eventRectDefaultX;
        eventRect[map][col][row].y = eventRect[map][col][row].eventRectDefaultY;
        }
        return hit;
    }

    public void damagePit( int gameState) {
        gp.gameState = gameState; // seta o jogo no modo dialogo
        gp.player.attackCanceled = true;
        gp.playSE(6);
        gp.ui.currentDialogue = "You fall into a pit !"; //adiciona ao currentDialogue essa string
        gp.player.life -= 1; // diminui a vida do player
        //eventRect[col][row].eventDone = true; //Isso aqui faz o evento ocorrer somente uma vez
        canTouchEvent = false;
    }

    public void healingPoll( int gameState) {
        if (gp.keyH.enterPressed == true) {
            gp.gameState = gameState;
            gp.player.attackCanceled = true;
            gp.playSE(2);
            gp.ui.currentDialogue = "You drink the water. \nYour life and mana have been recoved."
                    + "\n(The progress has been saved)";
            gp.player.life = gp.player.maxLife;
            gp.player.mana = gp.player.maxMana;
            gp.aSetter.setMonster();
            
            gp.saveLoad.save();
        }
    }
    
    public void speak(Entity entity){
        
        if(gp.keyH.enterPressed==true){
            gp.gameState = gp.dialogueState;
            gp.player.attackCanceled=true;
            entity.speak();
            canTouchEvent = false;
        }
        
        
    }
}
