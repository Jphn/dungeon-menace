/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package projeto.dugeonmenace.entity;

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

        up1 = setup("/player/" + "boy_up_1");
        up2 = setup("/player/" + "boy_up_2");
        down1 = setup("/player/" + "boy_down_1");
        down2 = setup("/player/" + "boy_down_2");
        left1 = setup("/player/" + "boy_left_1");
        left2 = setup("/player/" + "boy_left_2");
        right1 = setup("/player/" + "boy_right_1");
        right2 = setup("/player/" + "boy_right_2");

    }

    /**
     * Lembrando que o update atualiza 60 vezes por segundo
     *
     */
    public void update() {

        // MARCAÇÃO: talvez esse if seja muito desnecessário, talvez tenha uma maneira mais simples ?
        // mas a ideia é que o contador só aumenta se uma das teclas forem apertadas
        if (this.keyH.upPressed == true || this.keyH.leftPressed == true || this.keyH.downPressed == true || this.keyH.rigthPressed == true) {
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

            //IF COLLISION IS FALSE, PLAYER CAN MOVE
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

    public void interactNpc(int npcIndex) {
        if (npcIndex != 999) {
            if (gp.keyH.enterPressed == true) {
                gp.gameState = 3;
                gp.npc[npcIndex].speak();
            }

        }
        gp.keyH.enterPressed = false;
    }

    public void pickupObject(int objIndex) {
        if (objIndex != 999) {
            if (gp.obj[objIndex] != null) {

            }

        }
    }

    public void draw(Graphics2D g2) {

        //g2.setColor(Color.white);
        //g2.fillRect(this.x,this.y,gp.tileSize,gp.tileSize);
        // estou dizendo que o player é do tamanho do tile size
        // tile size é uma constante publica logo posso acessar desse pacote
        BufferedImage image = null;

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

        //Para mostrar colisão com o player !
        /*
        g2.setColor(Color.red);
        g2.drawRect(screenX + solidArea.x, screenY + solidArea.y, solidArea.width, solidArea.height);
        g2.dispose(); // boa pratica pra liberar memoria
         */
    }

}
