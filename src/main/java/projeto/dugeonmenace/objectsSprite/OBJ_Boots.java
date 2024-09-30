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
public class OBJ_Boots extends Entity {
    public static final String objName ="Boots";  
    public OBJ_Boots(GamePanel gp) {
        super(gp);
        name = objName;

        down1 = setup("/objectsSprite/boots.png", gp.tileSize, gp.tileSize);
        
         price = 50;
    }
}