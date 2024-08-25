/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package projeto.dugeonmenace.entity;

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
        getImage();
        setDialogue();
    }

    public void getImage() {

        up1 = setup("/npc/" + "oldman_up_1");
        up2 = setup("/npc/" + "oldman_up_2");
        down1 = setup("/npc/" + "oldman_down_1");
        down2 = setup("/npc/" + "oldman_down_2");
        left1 = setup("/npc/" + "oldman_left_1");
        left2 = setup("/npc/" + "oldman_left_2");
        right1 = setup("/npc/" + "oldman_right_1");
        right2 = setup("/npc/" + "oldman_right_2");

    }

    public void setDialogue() {
        dialogue[0] = "Hello lad.";
        dialogue[1] = "So you come to this \nisland to find some tresure?";
        dialogue[2] = "I used to be a great wizard but now... \nI´m a bit too old for taking a adventure.";
        dialogue[3] = "Well, good luck on you.";

    }

    @Override
    public void speak() {
        //Do this caracter specific stuff

        super.speak();

    }

    @Override
    public void setAction() {
        actionLockCounter++;

        if (actionLockCounter == 240) {
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
