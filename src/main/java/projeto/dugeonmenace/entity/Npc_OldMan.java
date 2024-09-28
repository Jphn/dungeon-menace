/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package projeto.dugeonmenace.entity;

import java.awt.Rectangle;
import java.util.Random;
import projeto.dugeonmenace.GamePanel;

/**
 *
 * @author LucianoNeto
 */
public class Npc_OldMan extends Entity {

    public Npc_OldMan(GamePanel gp) {
        super(gp);

        direction = "down";
        speed = 1;
        
        solidArea = new Rectangle();
        solidArea.x = 8;
        solidArea.y = 16;
        solidAreaDefaultX = solidArea.x;
        solidAreaDefaultY = solidArea.y;
        solidArea.width = 32;
        solidArea.height = 32;
        getImage();
        setDialogue();
    }

    public void getImage() {
        up1 = setup("/npc/" + "oldman_up_1" + ".png", gp.tileSize, gp.tileSize);
        up2 = setup("/npc/" + "oldman_up_2" + ".png", gp.tileSize, gp.tileSize);
        down1 = setup("/npc/" + "oldman_down_1" + ".png", gp.tileSize, gp.tileSize);
        down2 = setup("/npc/" + "oldman_down_2" + ".png", gp.tileSize, gp.tileSize);
        left1 = setup("/npc/" + "oldman_left_1" + ".png", gp.tileSize, gp.tileSize);
        left2 = setup("/npc/" + "oldman_left_2" + ".png", gp.tileSize, gp.tileSize);
        right1 = setup("/npc/" + "oldman_right_1" + ".png", gp.tileSize, gp.tileSize);
        right2 = setup("/npc/" + "oldman_right_2" + ".png", gp.tileSize, gp.tileSize);
    }

    public void setDialogue() {
        dialogue[0] = "Hello lad.";
        dialogue[1] = "So you come to this \nisland to find some treasure?";
        dialogue[2] = "I used to be a great wizard but now... \nI´m a bit too old for taking a adventure.";
        dialogue[3] = "Well, good luck on you.";
    }

    @Override
    public void speak() {
        // Do this caracter specific stuff
        super.speak();
        onPath = true;
    }

    @Override
    public void setAction() {
        
        if (onPath == true) {
            // Assim ele vai para a casa
//            int goalCol = 12;
//            int goalRow = 9;

            // Assim ele segue o player
            int goalCol = (gp.player.worldX + gp.player.solidArea.x) / gp.tileSize;
            int goalRow = (gp.player.worldY + gp.player.solidArea.y) / gp.tileSize;
            
            searchPath(goalCol, goalRow);
        } else {
            actionLockCounter++;

            if (actionLockCounter == 120) {
                Random random = new Random();

                int i = random.nextInt(100) + 1; // pick a number from 1 to 100
                if (i <= 25) {
                    direction = "up";
                } else if (i > 25 && i <= 50) {
                    direction = "down";
                } else if (i > 50 && i <= 75) {
                    direction = "left";
                } else if (i > 75 && i <= 100) {
                    direction = "right";
                }
                actionLockCounter = 0;
            }
        }
    }
}