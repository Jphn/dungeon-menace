/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package projeto.dugeonmenace.entity;

import java.awt.AlphaComposite;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import projeto.dugeonmenace.*;
import projeto.dugeonmenace.objectsSprite.OBJ_Fireball;
import projeto.dugeonmenace.objectsSprite.OBJ_Key;
import projeto.dugeonmenace.objectsSprite.OBJ_Shield_Wood;
import projeto.dugeonmenace.objectsSprite.OBJ_Sword_Normal;

/**
 *
 * @author LucianoNeto
 */
public class Player extends Entity {

    KeyHandler keyH;

    //Onde desenha o player na tela
    public final int screenX;
    public final int screenY;
    public int standCounter;
    public int spriteCounter;
    public boolean attackCanceled = false; 
    public boolean lightUpdated = false;
    
    public ArrayList<Entity> inventory = new ArrayList<>();
    public final int maxInventorySize = 20;
    int i;
    
    

    public Player(GamePanel gp, KeyHandler keyH) {
        super(gp);

        this.keyH = keyH;
        this.collisionOn = true;

        this.solidArea = new Rectangle(8, 16, 32, 32); // um retangulo menor que o tile do player

        solidAreaDefaultX = solidArea.x;
        solidAreaDefaultY = solidArea.y;

        /*
            Aqui subtraimos por metade do tileSize para ficar exatamente no meio da tela, sem isso o personagem
            Acaba ficando um pouco para o lado
         */
        screenX = (gp.screenWidth) / 2 - (gp.tileSize / 2);
        screenY = (gp.screenHeight) / 2 - (gp.tileSize / 2);

        setDefaultValues(); // Será que isso é errado ?
        getPlayerImage();
        getPlayerAttackImage();
        setItems();
    }

    public void setDefaultValues() {
        this.worldX = gp.tileSize * 23; // essa é a posição do player no mapa mundi
        this.worldY = gp.tileSize * 21; // posso definir só números aqui se eu quiser
        this.speed = 4;
        this.direction = "down";

        //Player status
        this.maxLife = 6;
        this.life = maxLife; // 6 de vida = 3 corações
        this.level = 1;
        this.maxMana = 4;
        this.ammo = 10;
        this.mana = maxMana;
        this.strength = 1;
        this.dexterity = 1;
        this.exp = 0;
        this.nextLevelExp = 5;
        this.coin = 0;
        
        this.currentWeapon = new OBJ_Sword_Normal(gp);
        this.currentShield= new OBJ_Shield_Wood(gp);
        this.projectile = new OBJ_Fireball(gp);
        
        attack = getAttack();
        defense = getDefense();
    }
    
    public void setItems() {
        inventory.add(currentWeapon);
        inventory.add(currentShield);
    }
    
    public int getAttack(){
        attackArea = currentWeapon.attackArea;
        return attack = strength  * currentWeapon.attackValue;
    }
    
    public int getDefense(){
        return defense = dexterity * currentShield.defenseValue;
    }
    
    public void getPlayerImage() {
        up1 = setup("/player/" + "boy_up_1" + ".png", gp.tileSize, gp.tileSize);
        up2 = setup("/player/" + "boy_up_2" + ".png", gp.tileSize, gp.tileSize);
        down1 = setup("/player/" + "boy_down_1" + ".png", gp.tileSize, gp.tileSize);
        down2 = setup("/player/" + "boy_down_2" + ".png", gp.tileSize, gp.tileSize);
        left1 = setup("/player/" + "boy_left_1" + ".png", gp.tileSize, gp.tileSize);
        left2 = setup("/player/" + "boy_left_2" + ".png", gp.tileSize, gp.tileSize);
        right1 = setup("/player/" + "boy_right_1" + ".png", gp.tileSize, gp.tileSize);
        right2 = setup("/player/" + "boy_right_2" + ".png", gp.tileSize, gp.tileSize);
    }
    
    public void getPlayerAttackImage() {
        if(currentWeapon.type == type_sword){
            attackUp1 = setup("/player/boy_attack_up_1.png", gp.tileSize, gp.tileSize * 2);
            attackUp2 = setup("/player/boy_attack_up_2.png", gp.tileSize, gp.tileSize * 2);
            attackDown1 = setup("/player/boy_attack_down_1.png", gp.tileSize, gp.tileSize * 2);
            attackDown2 = setup("/player/boy_attack_down_2.png", gp.tileSize, gp.tileSize * 2);
            attackLeft1 = setup("/player/boy_attack_left_1.png", gp.tileSize * 2, gp.tileSize);
            attackLeft2 = setup("/player/boy_attack_left_2.png", gp.tileSize * 2, gp.tileSize);
            attackRight1 = setup("/player/boy_attack_right_1.png", gp.tileSize * 2, gp.tileSize);
            attackRight2 = setup("/player/boy_attack_right_2.png", gp.tileSize * 2, gp.tileSize);
        }else if(currentWeapon.type == type_axe){
            
            attackUp1 = setup("/player/" + "boy_axe_up_1" + ".png", gp.tileSize, gp.tileSize* 2);
            attackUp2 = setup("/player/" + "boy_axe_up_2" + ".png", gp.tileSize, gp.tileSize* 2);
            attackDown1 = setup("/player/" + "boy_axe_down_1" + ".png", gp.tileSize, gp.tileSize* 2);
            attackDown2 = setup("/player/" + "boy_axe_down_2" + ".png", gp.tileSize, gp.tileSize* 2);
            attackLeft1 = setup("/player/" + "boy_axe_left_1" + ".png", gp.tileSize* 2, gp.tileSize);
            attackLeft2 = setup("/player/" + "boy_axe_left_2" + ".png", gp.tileSize* 2, gp.tileSize);
            attackRight1 = setup("/player/" + "boy_axe_right_1" + ".png", gp.tileSize* 2, gp.tileSize);
            attackRight2 = setup("/player/" + "boy_axe_right_2" + ".png", gp.tileSize* 2, gp.tileSize);
        }    
    }

    /**
     * Lembrando que o update atualiza 60 vezes por segundo
     *
     */
    public void update() {

        // MARCAÇÃO: talvez esse if seja muito desnecessário, talvez tenha uma maneira mais simples ?
        // mas a ideia é que o contador só aumenta se uma das teclas forem apertadas
        
        if(attacking == true) {
            attacking();
        } else if (this.keyH.upPressed == true || this.keyH.leftPressed == true || this.keyH.downPressed == true
                || this.keyH.rigthPressed == true || this.keyH.enterPressed == true || keyH.enterPressed == true) {
            if (this.keyH.upPressed == true) {
                this.direction = "up";

            } else if (this.keyH.downPressed == true) {
                this.direction = "down";

            } else if (this.keyH.leftPressed == true) {
                this.direction = "left";

            } else if (this.keyH.rigthPressed == true) {
                this.direction = "right";

            }
            //CHECK TILE COLLISION
            collisionOn = false;
            gp.cChecker.checkTile(this);//Como o player é sub de entity o metodo checktile pode receber o player
            
            //CHECK OBJECT COLLISION
            int objIndex = gp.cChecker.checkObject(this, true);
            pickupObject(objIndex);

            //CHECK NPC COLLISION
            int npcIndex = gp.cChecker.checkEntity(this, gp.npc);
            interactNpc(npcIndex);

            //CHECK MONSTER COLLISION
            int monsterIndex = gp.cChecker.checkEntity(this, gp.monster);
            contactMonster(monsterIndex);
            
            // CHECK INTERACTIVE TILE COLLISION
            int iTileIndex = gp.cChecker.checkEntity(this, gp.iTile);

            //CHECK EVENT COLLISION
            gp.eHandler.checkEvent();

            //IF COLLISION IS FALSE, PLAYER CAN MOVE
            if (collisionOn == false && keyH.enterPressed == false) {
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
            if(keyH.enterPressed && attackCanceled == false){
                gp.playSE(7);
                attacking = true; 
                spriteCounter = 0;
            }
            
            attackCanceled = false;
            gp.keyH.enterPressed = false;

            spriteCounter++;
            if (spriteCounter > 10) { // quando atinge 12 frames ele muda o sprite
                if (spriteNumber == 1) {
                    spriteNumber = 2;
                } else if (spriteNumber == 2) {
                    spriteNumber = 1;
                }
                spriteCounter = 0;
            } else {
                standCounter++;
                if (standCounter == 20) {
                    spriteNumber = 1;
                    standCounter = 0;
                }
            }
        }
        
        if (gp.keyH.shotKeyPressed == true && projectile.alive == false 
                && shotAvailableCounter == 30 && projectile.haveResource(this) == true){
            
            projectile.set(worldX, worldY, direction, true, this);
            projectile.subtractResource(this);
            gp.projectileList.add(projectile);
            shotAvailableCounter = 0;
            gp.playSE(10);
        }
        
        if (invincible == true){
            invincibleCounter++;
            if(invincibleCounter > 60){
                invincible = false;
                invincibleCounter = 0;
            }
        }   
        
        if (shotAvailableCounter < 30){
            shotAvailableCounter++;
        }
        
        if (life > maxLife) {
            life = maxLife;
        }
        
        if (mana > maxMana) {
            mana = maxMana;
        }
    }

    public void interactNpc(int i) {
        if (gp.keyH.enterPressed == true) {
            if (i != 999) {
                attackCanceled = true;
                gp.gameState = gp.dialogueState;
                gp.npc[i].speak();       
            } 
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
            switch(direction) {
                case "up": worldY -= attackArea.height; break;
                case "down": worldY += attackArea.height; break;
                case "left": worldX -= attackArea.width; break;
                case "right": worldX += attackArea.width; break;
            }
            
            // attackArea becomes solidArea
            solidArea.width = attackArea.width;
            solidArea.height = attackArea.height;
            
            // Check monster collision with the updated worldX, worldY and solidArea
            int monsterIndex = gp.cChecker.checkEntity(this, gp.monster);
            damageMonster(monsterIndex,this.attack);
            
            int iTileIndex = gp.cChecker.checkEntity(this, gp.iTile);
            damageInteractiveTile(iTileIndex);
            
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
    
    public void pickupObject(int i) {
        if (i != 999) {
            
            // PICKUP ONLY ITEMS
            if (gp.obj[i].type == type_pickupOnly) {
                gp.obj[i].use(this);
                gp.obj[i] = null;
            }
            // INVENTORY ITEMS
            else {
                String text;
                if(inventory.size() != maxInventorySize) {
                    inventory.add(gp.obj[i]);
                    gp.playSE(1);
                    text = "Got a "+ gp.obj[i].name + "!";
                } else{
                    text = "You cannot carry any more ! ";
                }
                gp.ui.addMessage(text); 
                gp.obj[i] = null; 
            }
        }
    }
    
    public void damageMonster(int i, int attack) {
        if (i != 999) {
            if (gp.monster[i].invincible == false) {
                int damage = attack - gp.monster[i].defense;
                if (damage < 0) {
                    damage = 0;
                }
                gp.monster[i].life -= damage;
                gp.ui.addMessage(damage + " damage!");
                
                gp.monster[i].invincible = true;
                gp.monster[i].damageReaction();
                
                if (gp.monster[i].life <= 0) {
                    gp.monster[i].dying = true;
                    gp.ui.addMessage("Killed the " + gp.monster[i].name + "!");
                    gp.ui.addMessage("Exp + " + gp.monster[i].exp);
                    exp += gp.monster[i].exp;
                    checkUpLevel();
                }
            }
        }
    }

    public void draw(Graphics2D g2) {

        //g2.setColor(Color.white);
        //g2.fillRect(this.x,this.y,gp.tileSize,gp.tileSize);
        // estou dizendo que o player é do tamanho do tile size
        // tile size é uma constante publica logo posso acessar desse pacote
        BufferedImage image = null;
        int tempScreenX = screenX;
        int tempScreenY = screenY;

        switch (direction) {
            case "up":
                if (attacking == false) {
                    if (spriteNumber == 1) {image = up1;}
                    if (spriteNumber == 2) {image = up2;}
                }
                if (attacking == true) {
                    tempScreenY = screenY - gp.tileSize;
                    if (spriteNumber == 1) {image = attackUp1;}
                    if (spriteNumber == 2) {image = attackUp2;}
                }
                break;
               
            case "down": 
                if (attacking == false) {
                    if (spriteNumber == 1) {image = down1;}
                    if (spriteNumber == 2) {image = down2;}
                }
                if (attacking == true) {
                    if (spriteNumber == 1) {image = attackDown1;}
                    if (spriteNumber == 2) {image = attackDown2;}
                }
                break;
                
            case "left":
                if (attacking == false) {
                    if (spriteNumber == 1) {image = left1;}
                    if (spriteNumber == 2) {image = left2;} 
                }
                if (attacking == true) {
                    tempScreenX = screenX - gp.tileSize;
                    if (spriteNumber == 1) {image = attackLeft1;}
                    if (spriteNumber == 2) {image = attackLeft2;}
                }
                break;
            case "right":
                if (attacking == false) {
                    if (spriteNumber == 1) {image = right1;}
                    if (spriteNumber == 2) {image = right2;}
                }
                if (attacking == true) {
                    if (spriteNumber == 1) {image = attackRight1;}
                    if (spriteNumber == 2) {image = attackRight2;}
                }
                break;
        }

        if (invincible == true) {
            g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 0.4f)); // o player fica levemente
            //transparente
        }
        
        
        g2.drawImage(image, tempScreenX, tempScreenY, null); // null ali pq aquilo aparentemente n vamos usar
        //reset alpha
        g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 1f));

        //DEBUG
//        g2.setFont(new Font("Arial", Font.PLAIN, 26));
//        g2.setColor(Color.white);
//        g2.drawString("Invincible: " + invincibleCounter, 10, 400); //Para mostrar colisão com o player !
        /*
        g2.setColor(Color.red);
        g2.drawRect(screenX + solidArea.x, screenY + solidArea.y, solidArea.width, solidArea.height);
        g2.dispose(); // boa pratica pra liberar memoria
         */
        
    }

    private void contactMonster(int i) {
        if (i != 999) {
            if (invincible == false && gp.monster[i].dying == false){
                gp.playSE(5);
                int damage = gp.monster[i].attack - defense;
                if (damage < 0) {
                    damage = 0;
                }
                life -= damage;
                invincible = true;
            }   
        }
    }
    
    public void checkUpLevel() {
        if (exp >= nextLevelExp) {
            level++; 
            nextLevelExp = nextLevelExp * 2;
            maxLife += 2;
            strength++;
            dexterity++;
            attack = getAttack();
            defense = getDefense();
            
            gp.gameState = gp.dialogueState;
            gp.ui.currentDialogue = "You are level: " + level + " now!\n"
                    + "You feel stronger!";
        }
    }
    
    public void selectItem(){
        int itemIndex = gp.ui.getItemIndexOnSlot();
        
        if(itemIndex < inventory.size()){
            Entity selectedItem = inventory.get(itemIndex);
            
            if(selectedItem.type == type_sword || selectedItem.type == type_axe ){
                currentWeapon = selectedItem;
                attack = getAttack();// atualiza o ataque do player com o da nova arma
                getPlayerAttackImage(); // atualiza sprite de ataque do player
            }
            
            if(selectedItem.type == type_shield){
                currentShield = selectedItem;
                defense = getDefense();// atualiza o atack do player com o da nova arma
                
            }
            if(selectedItem.type == type_consumable){
                selectedItem.use(this);
                inventory.remove(itemIndex);
            }
            if(selectedItem.type == type_light){
                if(currentLight == selectedItem){
                    currentLight = null;
                }else{
                    currentLight = selectedItem;
                }
                lightUpdated =true;
                
            }
            
        }
    }
    
    public void damageInteractiveTile(int i) {
        if (i != 999 && gp.iTile[i].destructible == true && gp.iTile[i].isCorrectItem(this) == true
                && gp.iTile[i].invincible == false) {
            gp.iTile[i].playSE();
            gp.iTile[i].life--;
            gp.iTile[i].invincible = true;
            
            if (gp.iTile[i].life == 0) {
                gp.iTile[i] = gp.iTile[i].getDestroyedForm();
            }
        }
    }
}