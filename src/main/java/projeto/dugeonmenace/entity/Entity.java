/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package projeto.dugeonmenace.entity;

import java.awt.AlphaComposite;
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
    
    public int speed;

    public BufferedImage up1, up2, down1, down2, left1, left2, right1, right2;
    public BufferedImage attackUp1, attackUp2, attackDown1, attackDown2, attackLeft1, attackLeft2, attackRight1, attackRight2;

    public int spriteCounter = 0;
   

    public int actionLockCounter = 0; // Contador relevante para os npcs

    public Rectangle solidArea = new Rectangle(0, 0, 48, 48);
    public Rectangle attackArea = new Rectangle(0, 0, 0, 0);
    public int solidAreaDefaultX, solidAreaDefaultY;

    public int invincibleCounter = 0;

    String dialogue[] = new String[20];

    // CHARACTER STATUS
    public int maxLife;
    public int life;

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
    
    public Entity(GamePanel gp) {
        this.gp = gp;
    }

    public void setAction() {
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

            if (invincible == true) {
            g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 0.4f)); // o player fica levemente
            //transparente
            }
            
            g2.drawImage(image, screenX, screenY, null); // null ali pq aquilo aparentemente n vamos usar

        }
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