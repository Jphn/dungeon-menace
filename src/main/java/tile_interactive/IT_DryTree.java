/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package tile_interactive;


import java.awt.Color;
import projeto.dugeonmenace.GamePanel;
import projeto.dugeonmenace.entity.Entity;
/**
 *
 * @author natan
 */
public class IT_DryTree extends InteractiveTile {
    GamePanel gp;   

    public IT_DryTree(GamePanel gp, int col, int row) {
        super(gp, col, row);
        this.gp = gp;
        
        this.worldX = gp.tileSize * col;
        this.worldY = gp.tileSize * row;
        
        down1 = setup("/tiles_interactive/drytree.png", gp.tileSize, gp.tileSize);
        destructible = true;
        life = 3;
    }
    
    public boolean isCorrectItem(Entity entity) {
        boolean isCorrectItem = false;
        
        if (entity.currentWeapon.type == type_axe) {
            isCorrectItem = true;
        }
        
        return isCorrectItem;
    }
    
    public void playSE() {
        gp.playSE(11);
    }
    
    public InteractiveTile getDestroyedForm() {
        InteractiveTile tile = new IT_Trunk(gp, worldX/gp.tileSize, worldY/gp.tileSize);
        return tile;
    }
    
    public Color getParticleColor() {
        Color color = new Color(65, 50, 30);
        return color;
    }

    public int getParticleSize() {
        int size = 6; // as partículas teram 6 pixels de tamanho
        return size;
    }
    
    public int getParticleSpeed() {
        int speed = 1;
        return speed;
    }
    
    public int getParticleMaxLife() {
        int maxLife = 20;
        return maxLife;
    }
}