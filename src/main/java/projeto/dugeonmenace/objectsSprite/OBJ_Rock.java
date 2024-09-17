/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package projeto.dugeonmenace.objectsSprite;

import projeto.dugeonmenace.GamePanel;
import projeto.dugeonmenace.entity.Entity;

import projeto.dugeonmenace.entity.Projectile;

/**
 *
 * @author T-GAMER
 */
public class OBJ_Rock extends Projectile {
    GamePanel gp;
    public OBJ_Rock (GamePanel gp) {
        super(gp);
        this.gp = gp;
        
        name = "Rock";
        speed = 8;
        maxLife = 80;
        life = maxLife;
        attack = 2;
        useCost = 1; // 1 de mana é gasto quando atira
        alive = false;
        getImage();
    }
    
    public void getImage(){
            up1 = setup("/projectile/" + "rock_down_1" + ".png", gp.tileSize, gp.tileSize);
            up2 = setup("/projectile/" + "rock_down_1" + ".png", gp.tileSize, gp.tileSize);
            down1 = setup("/projectile/" + "rock_down_1" + ".png", gp.tileSize, gp.tileSize);
            down2 = setup("/projectile/" + "rock_down_1" + ".png", gp.tileSize, gp.tileSize);
            left1 = setup("/projectile/" + "rock_down_1" + ".png", gp.tileSize, gp.tileSize);
            left2 = setup("/projectile/" + "rock_down_1" + ".png", gp.tileSize, gp.tileSize);
            right1 = setup("/projectile/" + "rock_down_1" + ".png", gp.tileSize, gp.tileSize);
            right2 = setup("/projectile/" + "rock_down_1" + ".png", gp.tileSize, gp.tileSize);
    }
    
    public boolean haveResource(Entity user) {
        boolean haveResource = false;
        if (user.ammo >= useCost) {
            haveResource = true;
        }
        return haveResource;
    }
    
    public void subtractResource(Entity user) {
        user.ammo -= useCost;
    }
}