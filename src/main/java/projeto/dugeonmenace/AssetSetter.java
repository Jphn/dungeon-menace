/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package projeto.dugeonmenace;

import projeto.dugeonmenace.objectsSprite.OBJ_Boots;
import projeto.dugeonmenace.objectsSprite.OBJ_Chest;
import projeto.dugeonmenace.objectsSprite.OBJ_Door;
import projeto.dugeonmenace.objectsSprite.OBJ_Key;

/**
 *
 * @author LucianoNeto
 */
public class AssetSetter {

    GamePanel gp;

    public AssetSetter(GamePanel gp) {
        this.gp = gp;

    }

    public void setObject() {
        gp.obj[0] = new OBJ_Key();
        gp.obj[0].worldX = 23 * gp.tileSize;
        gp.obj[0].worldY = 7 * gp.tileSize;

        gp.obj[1] = new OBJ_Key();
        gp.obj[1].worldX = 23 * gp.tileSize;
        gp.obj[1].worldY = 35 * gp.tileSize;

        gp.obj[2] = new OBJ_Door();
        gp.obj[2].worldX = 12 * gp.tileSize;
        gp.obj[2].worldY = 22 * gp.tileSize;

        gp.obj[3] = new OBJ_Door();
        gp.obj[3].worldX = 10 * gp.tileSize;
        gp.obj[3].worldY = 11 * gp.tileSize;

        gp.obj[5] = new OBJ_Door();
        gp.obj[5].worldX = 8 * gp.tileSize;
        gp.obj[5].worldY = 28 * gp.tileSize;

        gp.obj[6] = new OBJ_Chest();
        gp.obj[6].worldX = 10 * gp.tileSize;
        gp.obj[6].worldY = 7 * gp.tileSize;

        gp.obj[7] = new OBJ_Key();
        gp.obj[7].worldX = 38 * gp.tileSize;
        gp.obj[7].worldY = 8 * gp.tileSize;

        gp.obj[8] = new OBJ_Boots();
        gp.obj[8].worldX = 37 * gp.tileSize;
        gp.obj[8].worldY = 42 * gp.tileSize;

    }
}
