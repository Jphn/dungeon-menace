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
public class OBJ_Key extends Entity {
    public OBJ_Key(GamePanel gp) {
        super(gp);
        name = "Key";
        type = type_consumable;
        price = 100;
        down1 = setup("/objectsSprite/key.png", gp.tileSize, gp.tileSize);
        description = "[" + name + "]\nIr opens a door.";
        
        stackable = true;
        
    }
    
    
    public boolean use(Entity entity){
        gp.gameState = gp.dialogueState;
        
        int objIndex = getDetected(entity,gp.obj,"Door");
        
        if(objIndex != 999){
            gp.ui.currentDialogue = "You use the " + name+ " and open the door";
            gp.playSE(3);
            
            gp.obj[gp.currentMap][objIndex]= null;
            return true;
            
        }else{
            gp.ui.currentDialogue = "You can't use this here";
            return false;
        }
    
    }
}