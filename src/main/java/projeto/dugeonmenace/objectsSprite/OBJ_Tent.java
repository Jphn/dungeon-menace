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
public class OBJ_Tent extends Entity{
    public static final String objName = "Tent";

    public OBJ_Tent(GamePanel gp) {
        super(gp);
        
        type = type_consumable;
        name = objName;
        
        price = 300;
        
        
        
        down1 = setup("/objectsSprite/tent.png", gp.tileSize, gp.tileSize);
        description = "[" + name + "]\nYou can sleep until\nnext morning.";
        
        stackable= true;
    }
    public boolean use(Entity entity){
        
        gp.gameState = gp.sleepState;
        gp.playSE(14);
        gp.player.life = gp.player.maxLife;
        gp.player.mana = gp.player.maxMana;
        gp.player.getSleepingImage(down1);
        
        
        return true;
    }
}
