/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package projeto.dugeonmenace;

import projeto.dugeonmenace.entity.Entity;
import projeto.dugeonmenace.objectsSprite.*;


/**
 *
 * @author T-GAMER
 */
public class EntityGenerator {
    GamePanel gp;

    public EntityGenerator(GamePanel gp) {
        this.gp = gp;
    }
    
    public Entity getObject(String itemName){
        Entity obj = null;
        
        
        switch(itemName){
            case OBJ_Axe.objName: obj = new OBJ_Axe(gp);break;
            case OBJ_Boots.objName: obj = new OBJ_Boots(gp);break;
            case OBJ_Key.objName: obj = new OBJ_Key(gp);break;
            case OBJ_Lantern.objName: obj = new OBJ_Lantern(gp);break;
            case OBJ_Potion_Red.objName: obj = new OBJ_Potion_Red(gp);break;
            case OBJ_Shield_Blue.objName: obj = new OBJ_Shield_Blue(gp);break;
            case OBJ_Shield_Wood.objName: obj = new OBJ_Shield_Wood(gp);break;
            case OBJ_Sword_Normal.objName: obj = new OBJ_Sword_Normal(gp);break;
            case OBJ_Pickaxe.objName: obj = new OBJ_Pickaxe(gp);break;
            case OBJ_Tent.objName: obj = new OBJ_Tent(gp);break;
            case OBJ_Door.objName: obj = new OBJ_Door(gp);break;
            case OBJ_Chest.objName: obj = new OBJ_Chest(gp);break;
            case OBJ_Coin_Bronze.objName: obj = new OBJ_Coin_Bronze(gp);break;
            case OBJ_ManaCrystal.objName: obj = new OBJ_ManaCrystal(gp);break;
            case OBJ_Rock.objName: obj = new OBJ_Rock(gp);break;
        }
        
        return obj;
    }
    
}
