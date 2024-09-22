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
public class OBJ_Axe extends Entity {
        
    public OBJ_Axe(GamePanel gp) {
        super(gp);
        
        name = "Woodcutter's Axe";
        type = type_axe;
        down1= setup("/objectsSprite/axe.png",gp.tileSize,gp.tileSize);
        attackValue = 2;
        attackArea.width=30;
        attackArea.height=30;
        price = 75;
        
        description = "[" + name + "]\nA bit rusty but still \ncan cut some trees.";
    }
        
}
