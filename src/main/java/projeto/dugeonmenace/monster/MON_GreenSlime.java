/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package projeto.dugeonmenace.monster;

import java.util.Random;
import projeto.dugeonmenace.GamePanel;
import projeto.dugeonmenace.entity.Entity;

/**
 *
 * @author LucianoNeto
 */
public class MON_GreenSlime extends Entity {

    GamePanel gp;
    
    public MON_GreenSlime(GamePanel gp) {
        super(gp);
        
        this.gp = gp;
        
        name = "Green Slime";

        speed = 1;

        type = 2; //monstro

        maxLife = 4;

        life = maxLife;

        solidArea.x = 3;

        solidArea.y = 18;
        solidArea.width = 42;

        solidArea.height = 30;

        solidAreaDefaultX = solidArea.x;
        solidAreaDefaultY = solidArea.y;
        getImage();

    }

    public void getImage() {
        up1 = setup("/monster/greenslime_down_1.png", gp.tileSize, gp.tileSize);
        up2 = setup("/monster/greenslime_down_2.png", gp.tileSize, gp.tileSize);

        down1 = setup("/monster/greenslime_down_1.png", gp.tileSize, gp.tileSize);
        down2 = setup("/monster/greenslime_down_2.png", gp.tileSize, gp.tileSize);

        left1 = setup("/monster/greenslime_down_1.png", gp.tileSize, gp.tileSize);
        left2 = setup("/monster/greenslime_down_2.png", gp.tileSize, gp.tileSize);

        right1 = setup("/monster/greenslime_down_1.png", gp.tileSize, gp.tileSize);
        right2 = setup("/monster/greenslime_down_2.png", gp.tileSize, gp.tileSize);

    }

    public void setAction() {
        actionLockCounter++;

        if (actionLockCounter == 130) {
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
        spriteCounter++;
        if (spriteCounter > 12) { // quando atinge 12 frames ele muda o sprite
            if (spriteNumber == 1) {
                spriteNumber = 2;
            } else if (spriteNumber == 2) {
                spriteNumber = 1;
            }
            spriteCounter = 0;
        }/*
        else {
            standCounter++;
            if (standCounter == 20) {
                spriteNumber = 1;
                standCounter = 0;
            }
        }*/
    }
    
    public void damageReaction(){
        actionLockCounter=0;
        
        this.direction= gp.player.direction;
        
        
    }
}
