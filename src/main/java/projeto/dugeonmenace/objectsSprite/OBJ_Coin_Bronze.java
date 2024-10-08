/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package projeto.dugeonmenace.objectsSprite;

import projeto.dugeonmenace.GamePanel;
import projeto.dugeonmenace.entity.Entity;

/**
 *
 * @author natan
 */
public class OBJ_Coin_Bronze extends Entity {
    public OBJ_Coin_Bronze(GamePanel gp) {
        super(gp);
        this.gp = gp;
        
        type = type_pickupOnly;
        name = "Bronze coin";
        value = 1;
        down1 = setup("/objectsSprite/coin_bronze.png", gp.tileSize, gp.tileSize);
    }
    
    public void use(Entity entity){
        gp.playSE(1);
        gp.ui.addMessage("Coin +" + value);
        gp.player.coin += value;
    }
}