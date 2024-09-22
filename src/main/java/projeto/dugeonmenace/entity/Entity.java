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
import java.util.ArrayList;
import javax.imageio.ImageIO;
import projeto.dugeonmenace.GamePanel;
import projeto.dugeonmenace.UtilityTools;

/**
 *
 * @author LucianoNeto
 */
public class Entity {

    public GamePanel gp;
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
    
    public int maxMana;
    public int mana;
    public int ammo;
    
    public int strength;
    public int dexterity;
    public int attack;
    public int defense;
    public int exp;
    public int nextLevelExp;
    
    public int coin;
    public int price;
    
    // ENTITY WEAPONS
    public Entity currentWeapon;
    public Entity currentShield;
    public Entity currentLight;
    
    // Projectile
    public Projectile projectile;
    public int useCost;
    
    //ITEM ATTRIBUTES
    public int value;
    public int attackValue;
    public int defenseValue;
    public String description = "";
    
    public int lightRadius; // aqui é para o caso de diferentes fontes de luz gerarem diferentes areas luminosas (teremos isso)
    
    //COUNTERS
    public int dyingCounter = 0;
    public int spriteCounter = 0;
    public int actionLockCounter = 0; // Contador relevante para os npcs
    public int invincibleCounter = 0;
    public int hpBarCounter = 0;
    public int shotAvailableCounter = 0;

    //NOVOS ATRIBUTOS
    public BufferedImage image, image2, image3;
    public String name;
    
    public boolean collision = false;
    
    // ENTITY INVENTORY
    public ArrayList<Entity> inventory = new ArrayList<>();
    public final int maxInventorySize=20; 
    
    
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
    
    // TYPE
    public int type; // 0 = player, 1 = npc, 2=monster
    public final int type_player = 0;
    public final int type_npc = 1;
    public final int type_monster = 2;
    public final int type_sword = 3;
    public final int type_axe = 4;
    public final int type_shield = 5;
    public final int type_consumable = 6;
    public final int type_pickupOnly = 7;
    
    
    public final int type_light = 9;
    
    public final int type_unpickable = 10; // Eu que fiz esse para o caso da porta
    
    public Entity(GamePanel gp) {
        this.gp = gp;
    }
    
    public void setAction() {}
    
    public void use(Entity entity){}
    
    public void damageReaction(){}
    
    public void damagePlayer(int attack){
         if (!gp.player.invincible) {
                //Entity can give damage when touching
                gp.playSE(6);
                
                int damage = attack - gp.player.defense;
                if (damage < 0) {
                    damage = 0;
                }
                gp.player.life -= damage;
                gp.player.invincible = true;
            }
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

    public void checkDrop() {}
    
    public void dropItem(Entity droppedItem) {
        for (int i = 0; i < gp.obj[gp.currentMap].length; i++) {
            if (gp.obj[gp.currentMap][i] == null) {
                gp.obj[gp.currentMap][i] = droppedItem;
                gp.obj[gp.currentMap][i].worldX = worldX; // local onde o monstro morreu
                gp.obj[gp.currentMap][i].worldY = worldY;
                break;
            }
        }
    }
    
    public Color getParticleColor() {
        Color color = null;
        return color;
    }

    public int getParticleSize() {
        int size = 0; // as partículas teram 6 pixels de tamanho
        return size;
    }
    
    public int getParticleSpeed() {
        int speed = 0;
        return speed;
    }
    
    public int getParticleMaxLife() {
        int maxLife = 0;
        return maxLife;
    }
    
    public void generateParticle(Entity generator, Entity target) {
        Color color = generator.getParticleColor();
        int size = generator.getParticleSize();
        int speed = generator.getParticleSpeed();
        int maxLife = generator.getParticleMaxLife();
        
        Particle p1 = new Particle(gp, target, color, size, speed, maxLife, -2, -1); // TOP LEFT
        Particle p2 = new Particle(gp, target, color, size, speed, maxLife, 2, -1); // TOP RIGHT
        Particle p3 = new Particle(gp, target, color, size, speed, maxLife, -2, 1); // DOWN LEFT
        Particle p4 = new Particle(gp, target, color, size, speed, maxLife, 2, 1); // DOWN RIGHT
        
        gp.particleList.add(p1);
        gp.particleList.add(p2);
        gp.particleList.add(p3);
        gp.particleList.add(p4);
    }
            
    public void update() {
        setAction();

        collisionOn = false;
        gp.cChecker.checkTile(this);
        gp.cChecker.checkObject(this, false);
        //FAZENDO A ENTIDADE VERIFICAR SE ESTA PROXIMA DAS OUTRAS
        gp.cChecker.checkEntity(this, gp.npc);
        gp.cChecker.checkEntity(this, gp.monster);// TEMOS Q TIRAR A PRORPIA ENTIDADE DA LISTA
        gp.cChecker.checkEntity(this, gp.iTile);
        boolean contactPlayer = gp.cChecker.checkPlayer(this);

        if (this.type == 2 && contactPlayer == true) {
           damagePlayer(attack);
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
        if (spriteCounter > 24) { // quando atinge 12 frames ele muda o sprite
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
            if (invincibleCounter > 60) { // FRAMES DE INVENCIBILIDADE DO PLAYER
                invincibleCounter = 0;
                invincible = false;
            }
        }
        
        if (shotAvailableCounter < 30) {
            shotAvailableCounter++;
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
        int i = 5;
        
        if (dyingCounter <= i) {changeAlphaValue(g2, 0f);}
        if (dyingCounter > i && dyingCounter <= i * 2) {changeAlphaValue(g2, 1f);}
        if (dyingCounter > i * 2 && dyingCounter <= i * 3) {changeAlphaValue(g2, 0f);}
        if (dyingCounter > i * 3 && dyingCounter <= i * 4) {changeAlphaValue(g2, 1f);}
        if (dyingCounter > i * 4 && dyingCounter <= i * 5) {changeAlphaValue(g2, 0f);}
        if (dyingCounter > i * 5 && dyingCounter <= i * 6) {changeAlphaValue(g2, 1f);}
        if (dyingCounter > i * 6 && dyingCounter <= i * 7) {changeAlphaValue(g2, 0f);}
        if (dyingCounter > i * 7 && dyingCounter <= i * 8) {changeAlphaValue(g2, 1f);}
        if (dyingCounter > i * 8) {
            alive = false;
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