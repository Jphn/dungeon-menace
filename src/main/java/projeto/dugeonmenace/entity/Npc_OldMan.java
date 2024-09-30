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
        
        dialogueSet = -1; //para começar do primeiro dialogo
        
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
        dialogues[0][0] = "Hello lad.";
        dialogues[0][1] = "So you come to this \nisland to find some treasure?";
        dialogues[0][2] = "I used to be a great wizard but now... \nI´m a bit too old for taking a adventure.";
        dialogues[0][3] = "Well, good luck on you.";
        
        
        dialogues[1][0] = "If you become tired, rest at the water";
        dialogues[1][1] = "However, the monsters reapear if you rest.\nBe aware !";
        dialogues[1][2] = "Don´t push yourself so hard.";
        
        dialogues[2][0]= "I wonder how i can open that door...";
        
    }

    @Override
    public void speak() {
        // Do this caracter specific stuff

        facePlayer();
        startDialogue(this,dialogueSet);
        
        dialogueSet++;
        
        if(dialogues[dialogueSet][0]==null){
            
            dialogueSet= 0;
            ///dialogueSet--;// Trava na última mensagem, é legal pra npc genérico
        }
        
        //onPath = true;
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