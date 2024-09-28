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
    
    public void update() {
        super.update();
        
        int xDistance = Math.abs(worldX - gp.player.worldX);
        int yDistance = Math.abs(worldY - gp.player.worldY);
        int tileDistance = (xDistance + yDistance) / gp.tileSize;
        
        if (onPath == false && tileDistance < 5) {
            int i = new Random().nextInt(100) + 1;
            if ( i > 50) {
                onPath = true;
            }    
        }
        
        // Para de seguir o player
//        if (onPath == true && tileDistance > 20) {
//            onPath = false;
//        }
    }

    public void setAction() {
        if (onPath == true) {
            // Assim ele segue o player
            int goalCol = (gp.player.worldX + gp.player.solidArea.x) / gp.tileSize;
            int goalRow = (gp.player.worldY + gp.player.solidArea.y) / gp.tileSize;
            
            searchPath(goalCol, goalRow);
            
            //---- PROJECTILE SHOOT 
            int i = new Random().nextInt(200) + 1;
        
            if(i > 197 && projectile.alive == false && shotAvailableCounter == 30) {
                projectile.set(worldX, worldY, direction, true, this); // pra testar o hit da pedra, fixa a direction como gp.player.direction
                gp.projectileList.add(projectile);
                shotAvailableCounter = 0;
            }
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
    
    public void damageReaction() {
        actionLockCounter=0;       
        this.direction= gp.player.direction;
        onPath = true;
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