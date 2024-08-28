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
public class OBJ_Heart extends Entity {

    public OBJ_Heart(GamePanel gp) {
        super(gp);
        name = "Heart";

        image = setup("/objectsSprite/heart_full.png", gp.tileSize, gp.tileSize);
        image2 = setup("/objectsSprite/heart_half.png", gp.tileSize, gp.tileSize);
        image3 = setup("/objectsSprite/heart_blank.png", gp.tileSize, gp.tileSize);

        this.collision = true;
    }
}