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
public class OBJ_Sword_Normal extends Entity{
            public static final String objName = "Normal sword";

    
    public OBJ_Sword_Normal(GamePanel gp) {
        super(gp);
        name = objName;
        type = type_sword;
        price = 45;
        down1= setup("/objectsSprite/sword_normal.png", gp.tileSize, gp.tileSize);
        attackValue = 1;
        attackArea.width = 36;
        attackArea.height = 36;
        knockBackPower = 2;
        description = "[" + name + "]\nAn old sword.";
    }
}