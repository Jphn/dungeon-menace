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
import java.util.Random;
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
    public BufferedImage attackUp1, attackUp2, attackDown1, attackDown2, attackLeft1, attackLeft2, attackRight1,
            attackRight2;
    public BufferedImage guardUp, guardDown, guardLeft, guardRight;

    public int solidAreaDefaultX, solidAreaDefaultY;

    public Rectangle solidArea = new Rectangle(0, 0, 48, 48);
    public Rectangle attackArea = new Rectangle(0, 0, 0, 0);

    public String dialogues[][] = new String[20][20];
    public int dialogueSet = 0;

    // ENTITY STATUS
    public int maxLife;
    public int life;
    public int defaultSpeed;
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

    // ADVANCED COMBAT
    public Entity attacker;

    // Projectile
    public Projectile projectile;
    public int useCost;

    // ITEM ATTRIBUTES
    public int value;
    public int attackValue;
    public int defenseValue;
    public String description = "";
    public int knockBackPower = 0;
    public int lightRadius; // aqui é para o caso de diferentes fontes de luz gerarem diferentes areas
                            // luminosas (teremos isso)
    public boolean stackable = false;
    public int amount = 1;
    public int motion1_duration;
    public int motion2_duration;

    // COUNTERS
    public int dyingCounter = 0;
    public int spriteCounter = 0;
    public int actionLockCounter = 0; // Contador relevante para os npcs
    public int invincibleCounter = 0;
    public int hpBarCounter = 0;
    public int shotAvailableCounter = 0;
    public int knockBackCounter = 0;

    // NOVOS ATRIBUTOS
    public BufferedImage image, image2, image3;
    public String name;
    public boolean collision = false;

    // ENTITY INVENTORY
    public ArrayList<Entity> inventory = new ArrayList<>();
    public final int maxInventorySize = 20;
    // CHEST AND LOOT
    public boolean opened = false;
    public Entity loot;

    // STATE
    public int worldX, worldY;
    public String direction = "down";
    public String knockBackDirection;
    public int spriteNumber = 1;
    public int dialogueIndex = 0;
    public boolean collisionOn = false;
    public boolean invincible = false;
    public boolean attacking = false;
    public boolean hpBarOn = false;
    public boolean onPath = false;
    public boolean knockBack = false;
    public boolean guarding = false;
    public boolean transparent = false;

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
    public final int type_obstacle = 8;
    public final int type_light = 9;
    public final int type_pickaxe = 10;

    // Variáveis para o PARRY
    public int guardCounter = 0;
    public int offBalanceCounter = 0;
    public boolean offBalance = false;
    public boolean unpickable = false; // Eu que fiz esse para o caso da porta

    // BOSS
    public boolean inRage = false;
    public boolean boss;
    public boolean sleep = false;
    public boolean temp = false;
    public boolean drawing = true;

    public Entity(GamePanel gp) {
        this.gp = gp;
    }

    public void damagePlayer(int attack) {
        if (!gp.player.invincible) {

            int damage = attack - gp.player.defense;

            // Get direction of this attacker
            String canGuardDirection = getOppositeDirection(direction);

            if (gp.player.guarding == true && gp.player.direction.equals(canGuardDirection)) {
                // Parry

                if (gp.player.guardCounter < 10) {
                    damage = 0;
                    gp.playSE(16);
                    setKnockBack(this, gp.player, knockBackPower);
                    offBalance = true;
                    spriteCounter = -60;

                } else {
                    // Normal guard
                    damage /= 3; // divide o dano por 3
                    gp.playSE(15);
                }
            } else {
                // Entity can give damage when touching
                gp.playSE(6);

                if (damage < 1) {
                    damage = 1;
                }
            }

            if (damage != 0) {
                gp.player.transparent = true;
                setKnockBack(gp.player, this, knockBackPower);
            }

            gp.player.life -= damage;
            gp.player.invincible = true;
        }
    }

    public void update() {
        if (sleep == false) {
            if (knockBack == true) {
                checkCollision();

                if (collisionOn == true) {
                    knockBackCounter = 0;
                    knockBack = false;
                    speed = defaultSpeed;

                } else if (collisionOn == false) {

                    switch (knockBackDirection) {
                        case "up":
                            this.worldY -= this.speed;
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

                knockBackCounter++;
                if (knockBackCounter == 10) {
                    knockBackCounter = 0;
                    knockBack = false;
                    speed = defaultSpeed;
                }

            } else if (attacking == true) {
                attacking();

            } else {
                setAction();
                checkCollision();

                if (collisionOn == false) {
                    switch (direction) {
                        case "up":
                            this.worldY -= this.speed;
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
                    } else if (spriteNumber == 2) {
                        spriteNumber = 1;
                    }
                    spriteCounter = 0;
                }
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

            if (offBalance == true) {
                offBalanceCounter++;
                if (offBalanceCounter > 60) {
                    offBalance = false;
                    offBalanceCounter = 0;
                }
            }
        }
    }

    public void facePlayer() {
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

    public void speak() {
    }

    public void startDialogue(Entity entity, int setNum) {

        gp.gameState = gp.dialogueState;
        gp.ui.npc = entity;
        dialogueSet = setNum;
    }

    public void checkDrop() {
    }

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

    public void setKnockBack(Entity target, Entity attacker, int knockBackPower) {
        this.attacker = attacker;

        target.knockBackDirection = attacker.direction;
        target.speed += knockBackPower;
        target.knockBack = true;
    }

    public String getOppositeDirection(String direction) {
        String oppositeDirection = "";
        switch (direction) {
            case "up":
                oppositeDirection = "down";
                break;
            case "down":
                oppositeDirection = "up";
                break;
            case "left":
                oppositeDirection = "right";
                break;
            case "right":
                oppositeDirection = "left";
                break;
        }
        return oppositeDirection;
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

    public void resetCounter() {
        dyingCounter = 0;
        spriteCounter = 0;
        actionLockCounter = 0;
        invincibleCounter = 0;
        hpBarCounter = 0;
        shotAvailableCounter = 0;
        knockBackCounter = 0;
        guardCounter = 0;
        offBalanceCounter = 0;
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

    public void checkShootOrNot(int rate, int shotInterval) {
        int i = new Random().nextInt(rate);

        if (i == 0 && projectile.alive == false && shotAvailableCounter == shotInterval) {
            projectile.set(worldX, worldY, direction, true, this); // pra testar o hit da pedra, fixa a direction como
                                                                   // gp.player.direction

            for (int ii = 0; ii < gp.projectile[1].length; ii++) {
                if (gp.projectile[gp.currentMap][ii] == null) {
                    gp.projectile[gp.currentMap][ii] = projectile;
                    break;
                }
            }
            shotAvailableCounter = 0;
        }
    }

    public void checkStartChasingOrNot(Entity target, int distance, int rate) {

        if (getTileDistance(target) < distance) {
            int i = new Random().nextInt(rate); /// cria um novo numero 60 x por segundo( talvez exageiro ?)
            if (i == 0) {
                onPath = true;
            }
        }
    }

    public void checkStopChasingOrNot(Entity target, int distance, int rate) {

        if (getTileDistance(target) > distance) {
            int i = new Random().nextInt(rate); /// cria um novo numero 60 x por segundo( talvez exageiro ?)
            if (i == 0) {
                onPath = false;
            }
        }

    }

    // Na verdade pela nomeclatura isso não pode ser getRandomDirection, deve ser
    // setRandomDirection
    public void getRandomDirection() {
        actionLockCounter++;

        if (actionLockCounter == 120) {
            Random random = new Random();

            int i = random.nextInt(100) + 1; // pick a number from 1 to 100
            if (i <= 25) {
                direction = "up";
            } else if (i > 25 && i <= 50) {
                direction = "down";
            } else if (i > 50 && i <= 75) {
                direction = "left";
            } else if (i > 75 && i <= 100) {
                direction = "right";

            }
            actionLockCounter = 0;
        }

    }

    public void checkAttackOrNot(int rate, int straight, int horizontal) {
        boolean targetInRange = false;

        int xDis = getXdistance(gp.player);
        int yDis = getYdistance(gp.player);

        switch (direction) {
            case "up":
                if (gp.player.getCenterY() < getCenterY() && yDis < straight && xDis < horizontal) {
                    targetInRange = true;

                }
                break;
            case "down":
                if (gp.player.getCenterY() > getCenterY() && yDis < straight && xDis < horizontal) {
                    targetInRange = true;

                }
                break;
            case "left":

                if (gp.player.getCenterX() < getCenterX() && xDis < straight && yDis < horizontal) {
                    targetInRange = true;

                }
                break;
            case "right":
                if (gp.player.getCenterX() > getCenterX() && xDis < straight && yDis < horizontal) {
                    targetInRange = true;

                }
                break;
        }
        if (targetInRange == true) {
            // check if initiates an attack
            int i = new Random().nextInt(rate);
            if (i == 0) {
                attacking = true;
                spriteNumber = 1;
                spriteCounter = 0;
                shotAvailableCounter = 0;

            }
        }

    }

    public void checkCollision() {
        collisionOn = false;
        gp.cChecker.checkTile(this);
        gp.cChecker.checkObject(this, false);
        // FAZENDO A ENTIDADE VERIFICAR SE ESTA PROXIMA DAS OUTRAS
        gp.cChecker.checkEntity(this, gp.npc);
        gp.cChecker.checkEntity(this, gp.monster);// TEMOS Q TIRAR A PRORPIA ENTIDADE DA LISTA
        gp.cChecker.checkEntity(this, gp.iTile);
        boolean contactPlayer = gp.cChecker.checkPlayer(this);

        if (this.type == 2 && contactPlayer == true) {
            damagePlayer(attack);
        }
    }

    public void draw(Graphics2D g2) {
        BufferedImage image = null;

        /**
         * Oque esse if significa é que se o tile tiver nessa região ele será
         * pintado
         */
        if (inCamera() == true) {

            int tempScreenX = getScreenX();
            int tempScreenY = getScreenY();

            switch (direction) {
                case "up":
                    if (attacking == false) {
                        if (spriteNumber == 1) {
                            image = up1;
                        }
                        if (spriteNumber == 2) {
                            image = up2;
                        }
                    }
                    if (attacking == true) {
                        tempScreenY = getScreenY() - up1.getHeight();
                        if (spriteNumber == 1) {
                            image = attackUp1;
                        }
                        if (spriteNumber == 2) {
                            image = attackUp2;
                        }
                    }
                    break;

                case "down":
                    if (attacking == false) {
                        if (spriteNumber == 1) {
                            image = down1;
                        }
                        if (spriteNumber == 2) {
                            image = down2;
                        }
                    }
                    if (attacking == true) {
                        if (spriteNumber == 1) {
                            image = attackDown1;
                        }
                        if (spriteNumber == 2) {
                            image = attackDown2;
                        }
                    }
                    break;

                case "left":
                    if (attacking == false) {
                        if (spriteNumber == 1) {
                            image = left1;
                        }
                        if (spriteNumber == 2) {
                            image = left2;
                        }
                    }
                    if (attacking == true) {
                        tempScreenX = getScreenX() - left1.getWidth();
                        if (spriteNumber == 1) {
                            image = attackLeft1;
                        }
                        if (spriteNumber == 2) {
                            image = attackLeft2;
                        }
                    }
                    break;
                case "right":
                    if (attacking == false) {
                        if (spriteNumber == 1) {
                            image = right1;
                        }
                        if (spriteNumber == 2) {
                            image = right2;
                        }
                    }
                    if (attacking == true) {
                        if (spriteNumber == 1) {
                            image = attackRight1;
                        }
                        if (spriteNumber == 2) {
                            image = attackRight2;
                        }
                    }
                    break;
            }

            if (invincible == true) {
                hpBarOn = true;
                hpBarCounter = 0;
                changeAlphaValue(g2, 0.4F); // o player fica levemente
                // transparente
            }

            if (dying == true) {
                dyingAnimation(g2);
            }

            g2.drawImage(image, tempScreenX, tempScreenY, null); // null ali pq aquilo aparentemente n vamos usar
            changeAlphaValue(g2, 1F); // o player fica levemente
        }
    }

    public void moveTowardPlayer(int interval) {
        actionLockCounter++;

        if (actionLockCounter > interval) {
            if (getXdistance(gp.player) > getYdistance(gp.player)) // if entity far to the player on X axis moves right
                                                                   // or left
            {
                if (gp.player.getCenterX() < getCenterX()) // Player is left side, entity moves to left
                {
                    direction = "left";
                } else {
                    direction = "right";
                }
            } else if (getXdistance(gp.player) < getYdistance(gp.player)) // if entity far to the player on Y axis moves
                                                                          // up or down
            {
                if (gp.player.getCenterY() < getCenterY()) // Player is up side, entity moves to up
                {
                    direction = "up";
                } else {
                    direction = "down";
                }
            }
            actionLockCounter = 0;
        }
    }

    public void interact() {
    }

    public void getRandomDirection(int interval) {
        actionLockCounter++;

        if (actionLockCounter > interval) {
            Random random = new Random();
            int i = random.nextInt(100) + 1; // pick up a number from 1 to 100
            if (i <= 25) {
                direction = "up";
            }
            if (i > 25 && i <= 50) {
                direction = "down";
            }
            if (i > 50 && i <= 75) {
                direction = "left";
            }
            if (i > 75 && i <= 100) {
                direction = "right";
            }
            actionLockCounter = 0; // reset
        }
    }

    public void attacking() {
        spriteCounter++;

        if (spriteCounter <= 5) {
            spriteNumber = 1;
        }

        if (spriteCounter > 5 && spriteCounter <= 25) {
            spriteNumber = 2;

            // Save the current worldX, worldY, solidArea
            int currentWorldX = worldX;
            int currentWorldY = worldY;
            int solidAreaWidth = solidArea.width;
            int solidAreaHeight = solidArea.height;

            // Adjust player's worldX/Y for the attackArea
            switch (direction) {
                case "up":
                    worldY -= attackArea.height;
                    break;
                case "down":
                    worldY += attackArea.height;
                    break;
                case "left":
                    worldX -= attackArea.width;
                    break;
                case "right":
                    worldX += attackArea.width;
                    break;
            }

            // attackArea becomes solidArea
            solidArea.width = attackArea.width;
            solidArea.height = attackArea.height;

            if (type == type_monster) {
                if (gp.cChecker.checkPlayer(this) == true) {
                    damagePlayer(attack);

                }
            } else {
                // Check monster collision with the updated worldX, worldY and solidArea
                int monsterIndex = gp.cChecker.checkEntity(this, gp.monster);
                gp.player.damageMonster(monsterIndex, this, this.attack, currentWeapon.knockBackPower);

                int iTileIndex = gp.cChecker.checkEntity(this, gp.iTile);
                gp.player.damageInteractiveTile(iTileIndex);

                int projectileIndex = gp.cChecker.checkEntity(this, gp.projectile);
                gp.player.damageProjectile(projectileIndex);
            }

            // After checking collision resotre the original data
            worldX = currentWorldX;
            worldY = currentWorldY;
            solidArea.width = solidAreaWidth;
            solidArea.height = solidAreaHeight;
        }

        if (spriteCounter > 25) {
            spriteNumber = 1;
            spriteCounter = 0;
            attacking = false;
        }
    }

    public void setAction() {
    }

    public int getDetected(Entity user, Entity[][] target, String targetName) {

        int index = 999;

        // check th surrounding object
        int nextWorldX = user.getLeftX();
        int nextWorldY = user.getTopY();

        switch (user.direction) {
            case "up":
                nextWorldY = user.getTopY() - gp.player.speed;
                break;
            case "down":
                nextWorldY = user.getBottomY() + gp.player.speed;
                break;
            case "left":
                nextWorldX = user.getLeftX() - gp.player.speed;
                break;
            case "right":
                nextWorldX = user.getRightX() + gp.player.speed;
                break;

        }
        int col = nextWorldX / gp.tileSize;

        int row = nextWorldY / gp.tileSize;

        // Mexi no for troquei 1 por currentMap
        for (int i = 0; i < target[gp.currentMap].length; i++) {
            if (target[gp.currentMap][i] != null) {
                if (target[gp.currentMap][i].getCol() == col
                        && target[gp.currentMap][i].getRow() == row
                        && target[gp.currentMap][i].name.equals(targetName)) {

                    index = i;
                    break;
                }
            }
        }

        return index;
    }

    public boolean use(Entity entity) {
        return false;
    }

    public void damageReaction() {
    }

    public void dyingAnimation(Graphics2D g2) {
        dyingCounter++;
        int i = 5;

        if (dyingCounter <= i) {
            changeAlphaValue(g2, 0f);
        }
        if (dyingCounter > i && dyingCounter <= i * 2) {
            changeAlphaValue(g2, 1f);
        }
        if (dyingCounter > i * 2 && dyingCounter <= i * 3) {
            changeAlphaValue(g2, 0f);
        }
        if (dyingCounter > i * 3 && dyingCounter <= i * 4) {
            changeAlphaValue(g2, 1f);
        }
        if (dyingCounter > i * 4 && dyingCounter <= i * 5) {
            changeAlphaValue(g2, 0f);
        }
        if (dyingCounter > i * 5 && dyingCounter <= i * 6) {
            changeAlphaValue(g2, 1f);
        }
        if (dyingCounter > i * 6 && dyingCounter <= i * 7) {
            changeAlphaValue(g2, 0f);
        }
        if (dyingCounter > i * 7 && dyingCounter <= i * 8) {
            changeAlphaValue(g2, 1f);
        }
        if (dyingCounter > i * 8) {
            alive = false;
        }
    }

    public void changeAlphaValue(Graphics2D g2, float alphaValue) {
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

    public void searchPath(int goalCol, int goalRow) {
        int startCol = (worldX + solidArea.x) / gp.tileSize;
        int startRow = (worldY + solidArea.y) / gp.tileSize;
        gp.pFinder.setNodes(startCol, startRow, goalCol, goalRow, this);
        if (gp.pFinder.search() == true) {
            // Next WorldX and WorldY
            int nextX = gp.pFinder.pathList.get(0).col * gp.tileSize;
            int nextY = gp.pFinder.pathList.get(0).row * gp.tileSize;

            // Entity's solidArea position
            int enLeftX = worldX + solidArea.x;
            int enRightX = worldX + solidArea.x + solidArea.width;
            int enTopY = worldY + solidArea.y;
            int enBottomY = worldY + solidArea.y + solidArea.height;

            // TOP PATH
            if (enTopY > nextY && enLeftX >= nextX && enRightX < nextX + gp.tileSize) {
                direction = "up";
            } // BOTTOM PATH
            else if (enTopY < nextY && enLeftX >= nextX && enRightX < nextX + gp.tileSize) {
                direction = "down";
            } // RIGHT - LEFT PATH
            else if (enTopY >= nextY && enBottomY < nextY + gp.tileSize) {
                // either left or right
                // LEFT PATH
                if (enLeftX > nextX) {
                    direction = "left";
                }
                // RIGHT PATH
                if (enLeftX < nextX) {
                    direction = "right";
                }
            } // OTHER EXCEPTIONS
            else if (enTopY > nextY && enLeftX > nextX) {
                // up or left
                direction = "up";
                checkCollision();
                if (collisionOn == true) {
                    direction = "left";
                }
            } else if (enTopY > nextY && enLeftX < nextX) {
                // up or right
                direction = "up";
                checkCollision();
                if (collisionOn == true) {
                    direction = "right";
                }
            } else if (enTopY < nextY && enLeftX > nextX) {
                // down or left
                direction = "down";
                checkCollision();
                if (collisionOn == true) {
                    direction = "left";
                }
            } else if (enTopY < nextY && enLeftX < nextX) {
                // down or right
                direction = "down";
                checkCollision();
                if (collisionOn == true) {
                    direction = "right";
                }
            }
            // for following player, disable this. It should be enabled when npc walking to
            // specified location
            // int nextCol = gp.pFinder.pathList.get(0).col;
            // int nextRow = gp.pFinder.pathList.get(0).row;
            // if(nextCol == goalCol && nextRow == goalRow)
            // {
            // onPath = false;
            // }
        }
    }

    public int getXdistance(Entity target) {
        int xDistance = Math.abs(getCenterX() - target.worldX);
        return xDistance;
    }

    public int getYdistance(Entity target) {
        int yDistance = Math.abs(getCenterY() - target.worldY);
        return yDistance;
    }

    public int getTileDistance(Entity target) {
        int tileDistance = (getXdistance(target) + getYdistance(target)) / gp.tileSize;
        return tileDistance;
    }

    public int getGoalCol(Entity target) {
        int goalCol = (target.worldX + target.solidArea.x) / gp.tileSize;
        return goalCol;
    }

    public int getGoalRow(Entity target) {
        int goalRow = (target.worldY + target.solidArea.y) / gp.tileSize;
        return goalRow;
    }

    public int getCenterX() {
        int centerX = worldX + left1.getWidth() / 2;
        return centerX;
    }

    public int getCenterY() {
        int centerY = worldY + up1.getWidth() / 2;
        return centerY;
    }

    public int getLeftX() {
        return worldX + solidArea.x;
    }

    public int getRightX() {
        return worldX + solidArea.x + solidArea.width;
    }

    public int getTopY() {
        return worldY + solidArea.y;
    }

    public int getBottomY() {
        return worldY + solidArea.y + solidArea.height;
    }

    public int getCol() {
        return (worldX + solidArea.x) / gp.tileSize;
    }

    public int getRow() {
        return (worldY + solidArea.y) / gp.tileSize;
    }

    public void setLoot(Entity loot) {
    }

    public boolean inCamera() {
        boolean inCamera = false;
        if (worldX + gp.tileSize * 5 > gp.player.worldX - gp.player.screenX
                && worldX - gp.tileSize < gp.player.worldX + gp.player.screenX
                && worldY + gp.tileSize * 5 > gp.player.worldY - gp.player.screenY
                && worldY - gp.tileSize < gp.player.worldY + gp.player.screenY) {
            inCamera = true;
        }
        return inCamera;
    }

    public int getScreenX() {
        int screenX = worldX - gp.player.worldX + gp.player.screenX;
        return screenX;
    }

    public int getScreenY() {
        int screenY = worldY - gp.player.worldY + gp.player.screenY;
        return screenY;
    }
}