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
public class OBJ_Shield_Blue extends Entity{
    
    public OBJ_Shield_Blue(GamePanel gp) {
        super(gp);
        
        name = "Blue shield";
        type = type_shield;
        down1= setup("/objectsSprite/shield_blue.png", gp.tileSize, gp.tileSize);
        defenseValue =2;
        
        price = 150;
        description = "[" + name + "]\nA shiny blue shield.";
    }
    
}
