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

    GamePanel gp;

    public Npc_OldMan(GamePanel gp) {
        super(gp);

        direction = "down";
        speed = 1;
        getImage();
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
