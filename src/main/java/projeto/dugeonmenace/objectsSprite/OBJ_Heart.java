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
public class OBJ_Heart extends Entity {

    public OBJ_Heart(GamePanel gp) {
        super(gp);
        this.gp = gp;
        
        type = type_pickupOnly;
        name = "Heart";
        value = 2;
        
        down1 = setup("/objectsSprite/heart_full.png", gp.tileSize, gp.tileSize);
        image = setup("/objectsSprite/heart_full.png", gp.tileSize, gp.tileSize);
        image2 = setup("/objectsSprite/heart_half.png", gp.tileSize, gp.tileSize);
        image3 = setup("/objectsSprite/heart_blank.png", gp.tileSize, gp.tileSize);
    }
    
    public boolean use(Entity entity) {
        gp.playSE(2);
        gp.ui.addMessage("Life +" + value);
        entity.life += value;
        
        return true;
    }
}