/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package tile_interactive;

import projeto.dugeonmenace.GamePanel;
import projeto.dugeonmenace.entity.Entity;

/**
 *
 * @author natan
 */
public class InteractiveTile extends Entity {
    GamePanel gp;
    public boolean destructible = false;
    
    public InteractiveTile(GamePanel gp, int col, int row) {
        super(gp);
        this.gp = gp;
    }   
    
    public boolean isCorrectItem(Entity entity) {
        boolean isCorrectItem = false;
        return isCorrectItem;
    }
    
    public void playSE() {}
    
    public InteractiveTile getDestroyedForm() {
        InteractiveTile tile = null;
        return tile;
    }
    
    public void update() {
        if (invincible == true) {
            invincibleCounter++;
            if (invincibleCounter > 20) {
                invincibleCounter = 0;
                invincible = false;
            }
        }
    }
}