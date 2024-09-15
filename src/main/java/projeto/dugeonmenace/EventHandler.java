/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package projeto.dugeonmenace;

/**
 *
 * @author LucianoNeto
 */
public class EventHandler {

    GamePanel gp;
    EventRect eventRect[][];
    int previousEventX, previousEventY;
    boolean canTouchEvent = true;

    public EventHandler(GamePanel gp) {
        this.gp = gp;

        /**
         * Aqui ele ta criando um pequeno
         *
         * quadrado 2x2
         *
         * no meio de um tile
         */
        eventRect = new EventRect[gp.maxWorldCol][gp.maxWorldRow];

        int col = 0;
        int row = 0;
        while (col < gp.maxWorldCol && row < gp.maxWorldRow) {
            eventRect[col][row] = new EventRect();

            eventRect[col][row].x = 23;
            eventRect[col][row].y = 23;

            eventRect[col][row].width = 2;
            eventRect[col][row].height = 2;

            eventRect[col][row].eventRectDefaultX = eventRect[col][row].x;
            eventRect[col][row].eventRectDefaultY = eventRect[col][row].y;

            col++;
            if (col == gp.maxWorldCol) {
                col = 0;
                row++;
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
            if (hit(27, 16, "right") == true) {
                damagePit(27, 16, gp.dialogueState);
            }
        }
        
        if (hit(27, 17, "right") == true) {
            teleport(gp.dialogueState);
        }
        
        if (hit(23, 12, "up") == true) {
            healingPoll(23, 12, gp.dialogueState);
        }
    }

    public void teleport(int gameState) {
        gp.player.worldX = 35 * gp.tileSize;
        gp.player.worldY = 8 * gp.tileSize;

        gp.gameState = gameState; // seta o jogo no modo dialogo
        gp.ui.currentDialogue = "Teleport !"; //adiciona ao currentDialogue essa string
    }

    public boolean hit(int col, int row, String reqDirection) {
        boolean hit = false;
        //Check if the player is 1 tile away

        gp.player.solidArea.x = gp.player.worldX + gp.player.solidArea.x;

        gp.player.solidArea.y = gp.player.worldY + gp.player.solidArea.y;

        eventRect[col][row].x = col * gp.tileSize + eventRect[col][row].x;
        eventRect[col][row].y = row * gp.tileSize + eventRect[col][row].y;

        if (gp.player.solidArea.intersects(eventRect[col][row]) && eventRect[col][row].eventDone == false) {
            if (gp.player.direction.contentEquals(reqDirection) || reqDirection.contentEquals("any")) {
                hit = true;

                previousEventX = gp.player.worldX;
                previousEventY = gp.player.worldY;
            }
        }
        
        gp.player.solidArea.x = gp.player.solidAreaDefaultX;
        gp.player.solidArea.y = gp.player.solidAreaDefaultY;
        eventRect[col][row].x = eventRect[col][row].eventRectDefaultX;
        eventRect[col][row].y = eventRect[col][row].eventRectDefaultY;

        return hit;
    }

    public void damagePit(int col, int row, int gameState) {
        gp.gameState = gameState; // seta o jogo no modo dialogo
        gp.player.attackCanceled = true;
        gp.playSE(6);
        gp.ui.currentDialogue = "You fall into a pit !"; //adiciona ao currentDialogue essa string
        gp.player.life -= 1; // diminui a vida do player
        //eventRect[col][row].eventDone = true; //Isso aqui faz o evento ocorrer somente uma vez
        canTouchEvent = false;
    }

    public void healingPoll(int col, int row, int gameState) {
        if (gp.keyH.enterPressed == true) {
            gp.gameState = gameState;
            gp.player.attackCanceled = true;
            gp.playSE(2);
            gp.ui.currentDialogue = "You drink the water. \nYour life and mana have been recoved.";
            gp.player.life = gp.player.maxLife;
            gp.player.mana = gp.player.maxMana;
            gp.aSetter.setMonster();
        }
    }
}
