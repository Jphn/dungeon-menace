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
        defaultSpeed = 1;
        speed = defaultSpeed;
        type = type_monster; //monstro
        maxLife = 3;
        life = maxLife;
        attack = 2;
        defense = 0;
        exp = 3;
        
        //projectile = new OBJ_Rock(gp);

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
        
             
        if(onPath==true){
            // Para de seguir o player
            checkStopChasingOrNot(gp.player,15,100);
            
            
            // Assim ele segue o player
            //Busca a direção para ir           
            searchPath(getGoalCol(gp.player), getGoalRow(gp.player));
            
            //---- PROJECTILE SHOOT 
            //checkShootOrNot(200,30);
        }else{
            //CHECK IF IT STARTS CHASING
            checkStartChasingOrNot(gp.player,5,100);
            
            //Get a random direction
            getRandomDirection();
            
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