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
public class OBJ_Star extends Entity{
    public static final String objName ="Star";  
    public OBJ_Star(GamePanel gp) {
        super(gp);
        
        this.name = objName;
        image = setup("/objectsSprite/star.png",gp.tileSize,gp.tileSize);
        
    }
    
}
