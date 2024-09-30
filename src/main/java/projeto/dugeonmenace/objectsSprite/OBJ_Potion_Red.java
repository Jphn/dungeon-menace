/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package projeto.dugeonmenace.objectsSprite;

import projeto.dugeonmenace.GamePanel;
import projeto.dugeonmenace.entity.Entity;

/**
 *
 * @author T-GAMER
 */
public class OBJ_Potion_Red extends Entity {
         public static final String objName = "Red potion";

    public OBJ_Potion_Red(GamePanel gp) {
        super(gp);
        
        
        type = type_consumable;
        name = objName;
        value = 5;
        price = 25;
        stackable = true;
        
        
        down1 = setup("/objectsSprite/potion_red.png", gp.tileSize, gp.tileSize);
        description = "[" + name + "]\nHeals your life by "+ value+".";
        
    }
    public void setDialogue(){
    dialogues[0][0] = "You drink the "+ name+ " !\n"+
                "Your life has been recovered by "+ value +" !";
    
    }
    
    public boolean use(Entity entity){
        startDialogue(this,0);
        
        
        entity.life += value;
        gp.playSE(2);
        return true;
    }
}