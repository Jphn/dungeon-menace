/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package projeto.dugeonmenace.entity;

import projeto.dugeonmenace.GamePanel;

/**
 *
 * @author natan
 */
public class PlayerDummy  extends Entity {
    public static final String npcName = "Dummy";
    
    public PlayerDummy(GamePanel gp) {
        super(gp);
        name = npcName;
        getImage();
    } 
    
    public void getImage() {
        up1 = setup("/player/" + "boy_up_1" + ".png", gp.tileSize, gp.tileSize);
        up2 = setup("/player/" + "boy_up_2" + ".png", gp.tileSize, gp.tileSize);
        down1 = setup("/player/" + "boy_down_1" + ".png", gp.tileSize, gp.tileSize);
        down2 = setup("/player/" + "boy_down_2" + ".png", gp.tileSize, gp.tileSize);
        left1 = setup("/player/" + "boy_left_1" + ".png", gp.tileSize, gp.tileSize);
        left2 = setup("/player/" + "boy_left_2" + ".png", gp.tileSize, gp.tileSize);
        right1 = setup("/player/" + "boy_right_1" + ".png", gp.tileSize, gp.tileSize);
        right2 = setup("/player/" + "boy_right_2" + ".png", gp.tileSize, gp.tileSize);
    }
}