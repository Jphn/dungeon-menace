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
public class OBJ_Door_Iron  extends Entity {
    GamePanel gp;
    public static final String objName = "Iron Door";
    
    public OBJ_Door_Iron(GamePanel gp) {
        super(gp);
        this.gp = gp;
        type = type_obstacle;
        name = objName;
        down1 = setup("/objectsSprite/door_iron.png", gp.tileSize, gp.tileSize);
        collision = true;
        
        solidArea.x = 0;
        solidArea.y = 16;
        solidArea.width = 48;
        solidArea.height = 32;
        solidAreaDefaultX = solidArea.x;
        solidAreaDefaultY = solidArea.y;
        price = 35;
        setDialogue(); 
    }
    
    public void setDialogue() {
        dialogues[0][0] = "It won't budge.";
    }
    
    public void interact() {
        startDialogue(this, 0);
    }
}