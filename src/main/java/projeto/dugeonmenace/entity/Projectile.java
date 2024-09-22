/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package projeto.dugeonmenace.entity;

import projeto.dugeonmenace.GamePanel;

/**
 *
 * @author T-GAMER
 */
public class Projectile extends Entity {
    Entity user;
    public Projectile(GamePanel gp) {
        super(gp); 
    }
    
    public void set(int worldX, int worldY, String direction, boolean alive, Entity user){
        this.worldX = worldX;
        this.worldY = worldY;
        this.direction = direction;
        this.alive = alive;
        this.user = user;
        this.life = this.maxLife; // reset the proj. life in every shoot    
    }
    
    public void update(){
        if(user == gp.player){
            int monsterIndex = gp.cChecker.checkEntity(this, gp.monster);
            if (monsterIndex != 999) {
                gp.player.damageMonster(monsterIndex, this.attack);
                generateParticle(user.projectile, gp.monster[gp.currentMap][monsterIndex]);
                alive = false; /// Projectiile hit the target and disapear
            }
        }
        
        if(user != gp.player){
            boolean contactPlayer = gp.cChecker.checkPlayer(this);
            if(gp.player.invincible == false && contactPlayer == true){
                damagePlayer(this.attack);
                generateParticle(user.projectile, gp.player);
                alive = false;
            }
        }
        
        switch (direction) {
                    case "up":
                        this.worldY -= this.speed;    // Em java os valores em X aumentam para a direita,
                        //e em Y aumentam quando diminui
                        break;
                    case "down":
                        this.worldY += this.speed;
                        break;
                    case "left":
                        this.worldX -= this.speed;
                        break;
                    case "right":
                        this.worldX += this.speed;
                        break;
                }
        
        /// Depois de alguns game loops(frames) o projetil some
        life--;
        if (life <= 0) {
            alive = false;
        }
        
        spriteCounter++;
        if (spriteCounter > 12) { // quando atinge 12 frames ele muda o sprite
            if (spriteNumber == 1) {
                spriteNumber = 2;
            } else if (spriteNumber == 2) {
                spriteNumber = 1;
            }
            
            spriteCounter = 0;
        }
    }
    
    public boolean haveResource(Entity user) {
        boolean haveResource = false;
        return haveResource;
    }
    
    public void subtractResource(Entity user) {}
}