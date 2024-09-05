/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package projeto.dugeonmenace.entity;

import java.awt.AlphaComposite;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.io.IOException;
import javax.imageio.ImageIO;
import projeto.dugeonmenace.GamePanel;
import projeto.dugeonmenace.UtilityTools;

/**
 *
 * @author LucianoNeto
 */
public class Entity {

    GamePanel gp;
    /**
     * Essa vai ser a classe pai das entidades - NPC - Player - Monster
     */

    public BufferedImage up1, up2, down1, down2, left1, left2, right1, right2;
    public BufferedImage attackUp1, attackUp2, attackDown1, attackDown2, attackLeft1, attackLeft2, attackRight1, attackRight2;
    
    public int solidAreaDefaultX, solidAreaDefaultY;

    public Rectangle solidArea = new Rectangle(0, 0, 48, 48);
    public Rectangle attackArea = new Rectangle(0, 0, 0, 0);

    String dialogue[] = new String[20];

    // ENTITY STATUS
    public int maxLife;
    public int life;
    public int speed;
    public int level;
    
    public int strength;
    public int dexterity;
    public int attack;
    public int defense;
    public int exp;
    public int nextLevelExp;
    
    public int coin;
    
    // ENTITY WEAPONS
    public Entity currentWeapon;
    public Entity currentShield;
    
    //ITEM ATTRIBUTES
    public int attackValue;
    public int defenseValue;
    
    //COUNTERS
    public int dyingCounter = 0;
    public int spriteCounter = 0;
    public int actionLockCounter = 0; // Contador relevante para os npcs
    public int invincibleCounter = 0;
    public int hpBarCounter = 0;

    //NOVOS ATRIBUTOS
    public BufferedImage image, image2, image3;
    public String name;
    public int type; // 0 = player, 1 = npc, 2=monster
    public boolean collision = false;
    
    // STATE
    public int worldX, worldY;
    public String direction = "down";
    public int spriteNumber = 1;
    public int dialogueIndex = 0;
    public boolean collisionOn = false;
    public boolean invincible = false;
    public boolean attacking = false;
    public boolean hpBarOn = false;
    
    // ALIVE AND DEATH
    public boolean alive = true;
    public boolean dying = false;
    
    
    public Entity(GamePanel gp) {
        this.gp = gp;
    }

    public void setAction() {
    }
    public void damageReaction(){
    
    
    }
    public void speak() {
        if (dialogue[dialogueIndex] == null) {
            dialogueIndex = 0;
        }
        gp.ui.currentDialogue = dialogue[dialogueIndex];
        dialogueIndex++;
        switch (gp.player.direction) {
            case "up":
                this.direction = "down";
                break;
            case "down":
                this.direction = "up";
                break;
            case "left":
                this.direction = "right";
                break;
            case "right":
                this.direction = "left";
                break;

        }
    }

    public void update() {
        setAction();

        collisionOn = false;
        gp.cChecker.checkTile(this);
        gp.cChecker.checkObject(this, false);
        //FAZENDO A ENTIDADE VERIFICAR SE ESTA PROXIMA DAS OUTRAS
        gp.cChecker.checkEntity(this, gp.npc);
        gp.cChecker.checkEntity(this, gp.monster);// TEMOS Q TIRAR A PRORPIA ENTIDADE DA LISTA
        boolean contactPlayer = gp.cChecker.checkPlayer(this);

        if (this.type == 2 && contactPlayer == true) {
            if (!gp.player.invincible) {
                //Entity can give damage when touching
                gp.playSE(6);
                gp.player.life -= 1;
                gp.player.invincible = true;
            }
        }

        if (collisionOn == false) {
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
        }
        
        spriteCounter++;
        if (spriteCounter > 12) { // quando atinge 12 frames ele muda o sprite
            if (spriteNumber == 1) {
                spriteNumber = 2;
            } 
            else if (spriteNumber == 2) {
                spriteNumber = 1;
            }
            spriteCounter = 0;
        }
        
        if (invincible == true) {
            invincibleCounter++;
            if (invincibleCounter > 40) { // FRAMES DE INVENCIBILIDADE DO PLAYER
                invincibleCounter = 0;
                invincible = false;
            }
        }
    }

    public void draw(Graphics2D g2) {

        BufferedImage image = null;

        int screenX = worldX - gp.player.worldX + gp.player.screenX;
        int screenY = worldY - gp.player.worldY + gp.player.screenY;

        /**
         * Oque esse if significa é que se o tile tiver nessa região ele será
         * pintado
         */
        if (worldX + gp.tileSize > gp.player.worldX - gp.player.screenX
                && worldX - gp.tileSize < gp.player.worldX + gp.player.screenX
                && worldY + gp.tileSize > gp.player.worldY - gp.player.screenY
                && worldY - gp.tileSize < gp.player.worldY + gp.player.screenY) {

            switch (direction) {
                case "up":
                    if (this.spriteNumber == 1) {
                        image = up1;
                    } else if (this.spriteNumber == 2) {
                        image = up2;
                    }

                    break;
                case "down":
                    if (this.spriteNumber == 1) {
                        image = down1;
                    } else if (this.spriteNumber == 2) {
                        image = down2;
                    }

                    break;
                case "left":
                    if (this.spriteNumber == 1) {
                        image = left1;
                    } else if (this.spriteNumber == 2) {
                        image = left2;
                    }

                    break;
                case "right":
                    if (this.spriteNumber == 1) {
                        image = right1;
                    } else if (this.spriteNumber == 2) {
                        image = right2;
                    }
                    break;
            }
            //Monster health
            if(type == 2 && hpBarOn){
                double oneScale = (double)gp.tileSize/this.maxLife;
                
                double hpBarValue = oneScale*this.life;
                
                g2.setColor(new Color(35,35,35));
                g2.fillRect(screenX-1, screenY-16, gp.tileSize+2, 12 );
                
                g2.setColor(Color.red);
                g2.fillRect(screenX, screenY-15, (int)hpBarValue, 10 );
                
                hpBarCounter++;
                
                if(hpBarCounter>600){
                    hpBarCounter=0;
                    hpBarOn = false;
                }
            }
            if (invincible == true) {
                hpBarOn =true;
                hpBarCounter=0;
                changeAlphaValue(g2,0.4F); // o player fica levemente
            //transparente
            }
            
            if(dying == true){
                dyingAnimation(g2);
            
             }
            g2.drawImage(image, screenX, screenY, null); // null ali pq aquilo aparentemente n vamos usar
            changeAlphaValue(g2,1F); // o player fica levemente
        }
    }

    
    public void dyingAnimation(Graphics2D g2){
        dyingCounter++;
        
        
        if(dyingCounter<=5){
            changeAlphaValue(g2,0);
        }else if(dyingCounter>5 && dyingCounter<=10){
            changeAlphaValue(g2,1);
         }else if(dyingCounter>10 && dyingCounter<=15){
            changeAlphaValue(g2,0);
         }else if(dyingCounter>15 && dyingCounter<=20){
            changeAlphaValue(g2,1);
         }else if(dyingCounter>25 && dyingCounter<=30){
            changeAlphaValue(g2,0);
         }else if(dyingCounter>30 && dyingCounter<=35){
            changeAlphaValue(g2,1);
         }else if(dyingCounter>35 && dyingCounter<=40){
            changeAlphaValue(g2,0);
         }else if(dyingCounter>40){
             dying = false;
             alive=false;
         }
        
    
    }
    public void changeAlphaValue(Graphics2D g2, float alphaValue){
    
        g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, alphaValue)); 
    }
    
    public BufferedImage setup(String imagePath, int width, int height) {
        UtilityTools uTool = new UtilityTools();
        BufferedImage image = null;

        try {
            image = ImageIO.read(getClass().getResource(imagePath));
            image = uTool.scaleImage(image, width, height);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return image;
    }
    
    
    
}