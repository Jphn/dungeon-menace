/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package projeto.dugeonmenace.monster;

import java.util.Random;
import projeto.dugeonmenace.GamePanel;
import projeto.dugeonmenace.entity.Entity;
import projeto.dugeonmenace.objectsSprite.OBJ_Coin_Bronze;
import projeto.dugeonmenace.objectsSprite.OBJ_Heart;
import projeto.dugeonmenace.objectsSprite.OBJ_ManaCrystal;
import projeto.dugeonmenace.objectsSprite.OBJ_Rock;

/**
 *
 * @author T-GAMER
 */
public class MON_Orc extends Entity {
    
    public MON_Orc(GamePanel gp) {
        super(gp);
        
        
        name = "Orc";
        defaultSpeed = 1;
        speed = defaultSpeed;
        type = type_monster; //monstro
        maxLife = 10;
        life = maxLife;
        attack = 5;
        defense = 1;
        exp = 10;
        knockBackPower =5;
        

        solidArea.x = 4;
        solidArea.y = 4;
        solidArea.width = 40;
        solidArea.height = 44;
        solidAreaDefaultX = solidArea.x;
        solidAreaDefaultY = solidArea.y;
        
        attackArea.height = 48;
        attackArea.width = 48;
        
        
        getImage();
        
        getAttackImage();
    }
    public void getImage() {
        up1 = setup("/monster/orc_up_1.png", gp.tileSize, gp.tileSize);
        up2 = setup("/monster/orc_up_2.png", gp.tileSize, gp.tileSize);

        down1 = setup("/monster/orc_down_1.png", gp.tileSize, gp.tileSize);
        down2 = setup("/monster/orc_down_2.png", gp.tileSize, gp.tileSize);

        left1 = setup("/monster/orc_left_1.png", gp.tileSize, gp.tileSize);
        left2 = setup("/monster/orc_left_2.png", gp.tileSize, gp.tileSize);

        right1 = setup("/monster/orc_right_1.png", gp.tileSize, gp.tileSize);
        right2 = setup("/monster/orc_right_2.png", gp.tileSize, gp.tileSize);
    }
    
    public void getAttackImage() {
            attackUp1 = setup("/monster/orc_attack_up_1.png", gp.tileSize, gp.tileSize * 2);
            attackUp2 = setup("/monster/orc_attack_up_2.png", gp.tileSize, gp.tileSize * 2);
            attackDown1 = setup("/monster/orc_attack_down_1.png", gp.tileSize, gp.tileSize * 2);
            attackDown2 = setup("/monster/orc_attack_down_2.png", gp.tileSize, gp.tileSize * 2);
            attackLeft1 = setup("/monster/orc_attack_left_1.png", gp.tileSize * 2, gp.tileSize);
            attackLeft2 = setup("/monster/orc_attack_left_2.png", gp.tileSize * 2, gp.tileSize);
            attackRight1 = setup("/monster/orc_attack_right_1.png", gp.tileSize * 2, gp.tileSize);
            attackRight2 = setup("/monster/orc_attack_right_2.png", gp.tileSize * 2, gp.tileSize);
    }
    
    @Override
    public void setAction(){
        if(onPath==true){
            // Para de seguir o player
            checkStopChasingOrNot(gp.player,15,100);
            
            
            // Assim ele segue o player
            //Busca a direção para ir           
            searchPath(getGoalCol(gp.player), getGoalRow(gp.player));
            
            
            
        }else{
            //CHECK IF IT STARTS CHASING
            checkStartChasingOrNot(gp.player,5,100);
            
            //Get a random direction
            getRandomDirection();
             
        }
        
        if(attacking == false){
            checkAttackOrNot(30,gp.tileSize*2,gp.tileSize);
        
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
