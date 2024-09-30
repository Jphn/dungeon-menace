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
public class OBJ_Door extends Entity {
    public static final String objName ="Door";
    public OBJ_Door(GamePanel gp) {
        super(gp);
        name = objName;
        
        type = type_obstacle;
        
        unpickable = true;
        down1 = setup("/objectsSprite/door.png", gp.tileSize, gp.tileSize);

        this.collision = true;
        solidArea.x = 0;
        solidArea.y = 16;
        solidArea.width = 48;
        solidArea.height = 32;

        solidAreaDefaultX = solidArea.x;
        solidAreaDefaultY = solidArea.y;
        setDialogue();
    }
    
    public void setDialogue(){
        
        dialogues[0][0] = "You need a key to open this";
    }
    @Override
    public void interact(){
        startDialogue(this,0);
        
    
    }
}