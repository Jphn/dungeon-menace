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
     
    public OBJ_Potion_Red(GamePanel gp) {
        super(gp);
        this.gp = gp;
        
        type = type_consumable;
        name = "Red potion";
        value = 5;
        price = 25;
        down1 = setup("/objectsSprite/potion_red.png", gp.tileSize, gp.tileSize);
        description = "[" + name + "]\nHeals your life by "+ value+".";
        
    }
    public void use(Entity entity){
        gp.gameState = gp.dialogueState;
        gp.ui.currentDialogue = "You drink the "+ name+ " !\n"+
                "Your life has been recovered by "+ value +" !";
        
        entity.life += value;
        gp.playSE(2);
    }
}