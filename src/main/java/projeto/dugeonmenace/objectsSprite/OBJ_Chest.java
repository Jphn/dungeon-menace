/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package projeto.dugeonmenace.objectsSprite;

import projeto.dugeonmenace.GamePanel;
import projeto.dugeonmenace.entity.Entity;

/**
 *
 * @author LucianoNeto
 */
public class OBJ_Chest extends Entity {
    
    boolean opened = false;
    Entity loot;
    
    public OBJ_Chest(GamePanel gp,Entity loot) {
        super(gp);
        
        this.loot = loot;
        
        collision =true;
        
        type = type_obstacle;
        name = "Chest";

        image = setup("/objectsSprite/chest.png", gp.tileSize, gp.tileSize);
        image2 = setup("/objectsSprite/chest_opened.png", gp.tileSize, gp.tileSize);
        down1 = image;
        
        solidArea.x = 4;
        solidArea.y = 4;
        solidArea.width = 40;
        solidArea.height=32;
        solidAreaDefaultX = solidArea.x;
        solidAreaDefaultY = solidArea.y;
        
        
    }
    
    public void interact(){
        gp.gameState = gp.dialogueState;
        if(opened == false){
            gp.playSE(3);
            StringBuilder sb = new StringBuilder();
            sb.append("You open the chest and find a "+loot.name+ "!");
            
            if(gp.player.canObtainItem(loot)==false){
                sb.append("\n...But you cannot carry any more!");
            }else{
                 sb.append("\nYou obtain the " + loot.name+" !");
                
                 down1 = image2;
                 opened = true;
                 
            }
            gp.ui.currentDialogue = sb.toString();
            
        }else {
            gp.ui.currentDialogue = "It's empty";
        }
        
    
    }
}