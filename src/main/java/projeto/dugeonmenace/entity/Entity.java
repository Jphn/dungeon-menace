/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package projeto.dugeonmenace.entity;

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
    public int worldX, worldY;
    public int speed;

    public BufferedImage up1, up2, down1, down2, left1, left2, right1, right2;

    public String direction;

    public int spriteCounter = 0;
    public int spriteNumber = 1;

    public int actionLockCounter = 0; // Contador relevante para os npcs

    public Rectangle solidArea = new Rectangle(0, 0, 48, 48);
    public int solidAreaDefaultX, solidAreaDefaultY;

    public boolean collisionOn = false;

    public Entity(GamePanel gp) {
        this.gp = gp;
    }

    public void setAction() {
    }

    public void update() {
        setAction();

        collisionOn = false;
        gp.cChecker.checkTile(this);
        gp.cChecker.checkObject(this, false);
        gp.cChecker.checkPlayer(this);

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

            g2.drawImage(image, screenX, screenY, null); // null ali pq aquilo aparentemente n vamos usar

        }
    }

    public BufferedImage setup(String imagePath) {
        UtilityTools uTool = new UtilityTools();

        BufferedImage image = null;

        try {
            image = ImageIO.read(getClass().getResource(imagePath + ".png"));
            image = uTool.scaleImage(image, gp.tileSize, gp.tileSize);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return image;
    }

}
