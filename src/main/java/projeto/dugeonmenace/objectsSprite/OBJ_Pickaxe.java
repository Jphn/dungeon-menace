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
public class OBJ_Pickaxe extends Entity {
    public static final String objName = "Pickaxe";

    public OBJ_Pickaxe(GamePanel gp)
    {
        super(gp);

        type = type_pickaxe;
        name = objName;
        down1 = setup("/objectsSprite/pickaxe2.png",gp.tileSize,gp.tileSize);
        attackValue = 1;
        attackArea.width = 26;
        attackArea.height= 26;
        description = "[" + name + "]\nYou will dig it!";
        price = 75;
        knockBackPower = 1;
        motion1_duration = 10;
        motion2_duration = 20;
    }

}
