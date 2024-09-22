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
public class OBJ_Lantern extends Entity{
    
    public OBJ_Lantern(GamePanel gp) {
        super(gp);
        
        this.name= "Lantern";
        
        type = type_light;
        this.down1 = setup("/objectsSprite/lantern.png",gp.tileSize,gp.tileSize);
        
        description = "[" + name + "]\nIlluminates yours \nsurroundings.";
        
        price = 200;
        
        lightRadius = 250;
        
    }
    
}
