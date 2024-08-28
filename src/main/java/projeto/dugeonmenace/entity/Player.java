/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package projeto.dugeonmenace.entity;

import java.awt.AlphaComposite;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import projeto.dugeonmenace.*;

/**
 *
 * @author LucianoNeto
 */
public class Player extends Entity {

    KeyHandler keyH;

    //Onde desenha o player na tela
    public final int screenX;
    public final int screenY;
    //public int hasKey = 0;
    public int standCounter;
    public int spriteCounter;
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
        
        attackArea.width = 36;
        attackArea.height = 36;

        setDefaultValues(); // Será que isso é errado ?

        getPlayerImage();
        
        getPlayerAttackImage();
    }

    public void setDefaultValues() {
        this.worldX = gp.tileSize * 23; // essa é a posição do player no mapa mundi
        this.worldY = gp.tileSize * 21; // posso definir só números aqui se eu quiser
        this.speed = 4;
        this.direction = "down";

        //Player status
        this.maxLife = 6;
        this.life = maxLife; // 6 de vida = 3 corações
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
        attackUp1 = setup("/player/boy_attack_up_1.png", gp.tileSize, gp.tileSize * 2);
        attackUp2 = setup("/player/boy_attack_up_2.png", gp.tileSize, gp.tileSize * 2);
        attackDown1 = setup("/player/boy_attack_down_1.png", gp.tileSize, gp.tileSize * 2);
        attackDown2 = setup("/player/boy_attack_down_2.png", gp.tileSize, gp.tileSize * 2);
        attackLeft1 = setup("/player/boy_attack_left_1.png", gp.tileSize * 2, gp.tileSize);
        attackLeft2 = setup("/player/boy_attack_left_2.png", gp.tileSize * 2, gp.tileSize);
        attackRight1 = setup("/player/boy_attack_right_1.png", gp.tileSize * 2, gp.tileSize);
        attackRight2 = setup("/player/boy_attack_right_2.png", gp.tileSize * 2, gp.tileSize);
        
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
        }   
        if (this.keyH.upPressed == true || this.keyH.leftPressed == true || this.keyH.downPressed == true
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
    }

    public void interactNpc(int i) {
        if (gp.keyH.enterPressed == true) {
            if (i != 999) {
            
                gp.gameState = gp.dialogueState;
                gp.npc[i].speak();
            
        } else {
                attacking = true;
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
            damageMonster(monsterIndex);
            
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
            
        }
    }
    
    public void damageMonster(int i) {
        if (i != 999) {
            if (gp.monster[i].invincible == false) {
                gp.monster[i].life -= 1;
                gp.monster[i].invincible = true;
                
                if (gp.monster[i].life <= 0) {
                    gp.monster[i] = null;
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

    private void contactMonster(int index) {

        if (index != 999 && invincible == false) {
            life -= 1;
            invincible = true;
        }
    }
}