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
    public static final String objName ="Key";
    public OBJ_Key(GamePanel gp) {
        super(gp);
        name = objName;
        type = type_consumable;
        price = 100;
        down1 = setup("/objectsSprite/key.png", gp.tileSize, gp.tileSize);
        description = "[" + name + "]\nIr opens a door.";
        
        stackable = true;
        setDialogue();
    }
    public void setDialogue(){
        dialogues[0][0] = "You use the " + name+ " and open the door";
        dialogues[1][0] = "You can't use this here";
    }
    
    public boolean use(Entity entity){
        
        
        int objIndex = getDetected(entity,gp.obj,"Door");
        
        if(objIndex != 999){
            startDialogue(this,0);
            gp.playSE(3);
            
            gp.obj[gp.currentMap][objIndex]= null;
            return true;
            
        }else{
            startDialogue(this,1);
            
            return false;
        }
    
    }
}