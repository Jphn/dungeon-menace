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
    
    public static final String objName ="Chest";  
    
    public OBJ_Chest(GamePanel gp) {
        super(gp);
        
       
        
        collision =true;
        
        type = type_obstacle;
        name = objName;

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
    public void setDialogue(){
        dialogues[0][0] = "You open the chest and find a "+loot.name+ "!"+"\n...But you cannot carry any more!";
        dialogues[1][0] = "You open the chest and find a "+loot.name+ "!"+"\nYou obtained the "+ loot.name+" !";
        dialogues[2][0] = "It's empty";
    }
    public void setLoot(Entity loot){
        this.loot = loot;
        setDialogue();
    }
    public void interact(){
        
        if(opened == false){
            gp.playSE(3);
            
            
            if(gp.player.canObtainItem(loot)==false){
                startDialogue(this,0);
            }else{
                 startDialogue(this,1);
                
                 down1 = image2;
                 opened = true;
                 
            }
            
            
            
        }else {
            
            startDialogue(this,2);
        }
        
    
    }
}