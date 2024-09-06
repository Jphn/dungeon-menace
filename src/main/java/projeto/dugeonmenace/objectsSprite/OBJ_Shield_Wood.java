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
public class OBJ_Shield_Wood extends Entity{

    public OBJ_Shield_Wood(GamePanel gp) {
        super(gp);
        name = "Wooden shield";
        down1= setup("/objectsSprite/shield_wood.png", gp.tileSize, gp.tileSize);
        defenseValue =1;
    }
}