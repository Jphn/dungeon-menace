/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package projeto.dugeonmenace.monster;

import java.util.Random;
import projeto.dugeonmenace.GamePanel;
import projeto.dugeonmenace.entity.Entity;
import projeto.dugeonmenace.entity.Projectile;
import projeto.dugeonmenace.objectsSprite.OBJ_Coin_Bronze;
import projeto.dugeonmenace.objectsSprite.OBJ_Heart;
import projeto.dugeonmenace.objectsSprite.OBJ_ManaCrystal;
import projeto.dugeonmenace.objectsSprite.OBJ_Rock;

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
        type = type_monster; //monstro
        maxLife = 4;
        life = maxLife;
        attack = 3;
        defense = 0;
        exp = 2;
        
        projectile = new OBJ_Rock(gp);

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
        
        //---- PROJECTILE SHOOT 
        int i = new Random().nextInt(100)+1;
        
        if(i > 99 && projectile.alive == false && shotAvailableCounter == 30){
            projectile.set(worldX, worldY, direction, true, this); // pra testar o hit da pedra, fixa a direction como gp.player.direction
            gp.projectileList.add(projectile);
            shotAvailableCounter = 0;
        }
        //---- PROJECTILE SHOOT 
        
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
    
    public void damageReaction() {
        actionLockCounter=0;       
        this.direction= gp.player.direction;
    }
    
    public void checkDrop() {
        int i = new Random().nextInt(100) + 1;
        
        if (i < 50) {
            dropItem(new OBJ_Coin_Bronze(gp));
        }
        
        if (i >= 50 && i < 75) {
            dropItem(new OBJ_Heart(gp));
        }
        
        if (i >= 75 && i < 100) {
            dropItem(new OBJ_ManaCrystal(gp));
        }
    }
}