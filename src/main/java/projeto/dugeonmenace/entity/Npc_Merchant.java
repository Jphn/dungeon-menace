/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package projeto.dugeonmenace.entity;

import java.awt.Rectangle;
import java.util.Random;
import projeto.dugeonmenace.GamePanel;
import projeto.dugeonmenace.objectsSprite.*;

/**
 *
 * @author T-GAMER
 */
public class Npc_Merchant extends Entity {
    
    public Npc_Merchant(GamePanel gp) {
        super(gp);
        direction = "down";
        speed = 0;
        
        solidArea = new Rectangle();
        solidArea.x=8;
        solidArea.y=16;
        solidAreaDefaultX = solidArea.x;
        solidAreaDefaultY = solidArea.y;
        solidArea.width=32;
        solidArea.height=32;
        
        getImage();
        setItems();
        setDialogue();
    }
    
    
     public void getImage() {

              
        up1 = setup("/npc/" + "merchant_down_1" + ".png", gp.tileSize, gp.tileSize);
        up2 = setup("/npc/" + "merchant_down_2" + ".png", gp.tileSize, gp.tileSize);
        down1 = setup("/npc/" + "merchant_down_1" + ".png", gp.tileSize, gp.tileSize);
        down2 = setup("/npc/" + "merchant_down_2" + ".png", gp.tileSize, gp.tileSize);
        left1 = setup("/npc/" + "merchant_down_1" + ".png", gp.tileSize, gp.tileSize);
        left2 = setup("/npc/" + "merchant_down_2" + ".png", gp.tileSize, gp.tileSize);
        right1 = setup("/npc/" + "merchant_down_1" + ".png", gp.tileSize, gp.tileSize);
        right2 = setup("/npc/" + "merchant_down_2" + ".png", gp.tileSize, gp.tileSize);

    }

    public void setDialogue() {
        
        dialogues[0][0] = "He he, so you found me.\nI have some good stuff.\nDo you want to trade ?";
        dialogues[1][0] ="Come again, hehe!";
        dialogues[2][0] = "You need more coins to buy that !";
        dialogues[3][0] = "You inventory is full !";
        dialogues[4][0] = "You cannot sell an equipped item !";
    }
    
    public void setItems(){
        inventory.add(new OBJ_Potion_Red(gp));
        inventory.add(new OBJ_Key(gp));
        inventory.add(new OBJ_Axe(gp));
        inventory.add(new OBJ_Sword_Normal(gp));
        inventory.add(new OBJ_Shield_Blue(gp));
        
    
    }
    
    @Override
    public void speak() {
        //Do this caracter specific stuff
        facePlayer();
        gp.gameState = gp.tradeState;
        gp.ui.npc = this;
    }
}
