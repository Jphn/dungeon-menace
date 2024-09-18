/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package projeto.dugeonmenace;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Font;
import java.awt.FontFormatException;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;
import java.text.DecimalFormat;
import java.util.ArrayList;
import projeto.dugeonmenace.entity.Entity;
import projeto.dugeonmenace.objectsSprite.OBJ_Heart;
import projeto.dugeonmenace.objectsSprite.OBJ_ManaCrystal;

/**
 *
 * @author LucianoNeto
 */
public class UI {
    GamePanel gp;
    Graphics2D g2;
    Font pixelOperator;
    BufferedImage heartFull, heartHalf, heartBlank, crystalFull, crystalBlank;
    
    public boolean messageOn = false;
//    int messageCounter = 0;
//    public String message = "";
    ArrayList<String> message = new ArrayList<>();
    ArrayList<Integer> messageCounter = new ArrayList<>();
    public String currentDialogue = "";
    public boolean gameFinished = false;
    public int slotCol = 0;
    public int slotRow = 0;
    
    double playTime = 0;
    DecimalFormat decimalFormat = new DecimalFormat("#0");

    //TITLE SCREEN VARIABLES
    int comandNum = 0;
    int titleScreenState = 0; // 0: primeira tela , 1: segunda tela

    public UI(GamePanel gp) {

        this.gp = gp;
        
        try {
            InputStream is = getClass().getResourceAsStream("/fonts/PixelOperator.ttf");
            pixelOperator = Font.createFont(Font.TRUETYPE_FONT, is);
        } catch (FontFormatException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        
        // OBJ_Key key = new OBJ_Key(gp);
        // keyImage = key.image;
        
        // CREATE HUD OBJECT
        Entity heart = new OBJ_Heart(gp);
        this.heartFull = heart.image;
        this.heartHalf = heart.image2;
        this.heartBlank = heart.image3;
        
        Entity crystal = new OBJ_ManaCrystal(gp);
        crystalFull = crystal.image;
        crystalBlank = crystal.image2;
    }

    public void draw(Graphics2D g2) {
        this.g2 = g2;

        g2.setFont(pixelOperator);
        g2.setColor(Color.white);
        
        /**
         * Essa região é diferente ao UI durante
         *
         * os diferentes estados de jogo
         */
        
        // TITLE STATE
        if (gp.gameState == gp.titleState) {
            drawTitleScreen();
        } 
        
        // PLAY STATE
        if (gp.gameState == gp.playState) {
            drawPlayerLife();
            drawMessage();
        }
        
        // PAUSE STATE
        if (gp.gameState == gp.pauseState) {
            //game pausado, a tela de espera deve abrir
            drawPlayerLife(); //decidir se vamos esconder durante o pause
            drawPauseScreen();
        }   

        if (gp.gameState == gp.dialogueState) {
            drawPlayerLife();
            drawDialogueScreen();
        }
        
        // CHARACHTER STATE
        if (gp.gameState == gp.characterState) {
            drawCharacterScreen();
            drawInventory();
        }
    }

    public void drawPlayerLife() {
        int x = gp.tileSize / 2;
        int y = gp.tileSize / 2;
        int i = 0;

        //DRAW BLANK HEART
        while (i < gp.player.maxLife / 2) { //2 lifes = 1 heart
            g2.drawImage(heartBlank, x, y, null);
            i++;
            x += gp.tileSize;
        }

        //RESET
        x = gp.tileSize / 2;
        y = gp.tileSize / 2;
        i = 0;
        //DRAW CURRENT LIFE
        while (i < gp.player.life) { //1 lifes = 1/2 heart
            g2.drawImage(heartHalf, x, y, null);
            i++;
            if (i < gp.player.life) {
                g2.drawImage(heartFull, x, y, null);
            }
            i++;
            x += gp.tileSize;
        }
        
        // DRAW MAX MANA
        x = (gp.tileSize / 2) - 5;
        y = (int) (gp.tileSize * 1.5);
        i = 0;
        while(i < gp.player.maxMana) {
            g2.drawImage(crystalBlank, x, y, null);
            i++;
            x += 35;
        } 
        
        // DRAW MANA
        x = (gp.tileSize / 2) - 5;
        y = (int) (gp.tileSize * 1.5);
        i = 0;
        while(i < gp.player.mana) {
            g2.drawImage(crystalFull, x, y, null);
            i++;
            x += 35;
        } 
    }

    public void drawTitleScreen() {
        if (titleScreenState == 0) {
            //Setting Background color
            g2.setColor(new Color(70, 120, 80));
            g2.fillRect(0, 0, gp.screenWidth, gp.screenHeight);

            // TITLE NAME
            g2.setFont(g2.getFont().deriveFont(Font.PLAIN, 80F));
            String text = "Dungeon Menace";

            int x = getXforCenteredText(text);
            int y = gp.tileSize * 3;

            //SHADOW
            g2.setColor(new Color(0, 0, 0, 210));
            g2.drawString(text, x + 5, y + 5);

            //MAIN COLOR
            g2.setColor(Color.WHITE);
            g2.drawString(text, x, y);

            //BLUE BOY IMAGE
            x = (gp.screenWidth / 2) - gp.tileSize;
            y += gp.tileSize * 2;
            g2.drawImage(gp.player.down1, x, y, gp.tileSize * 2, gp.tileSize * 2, null);

            //MENU
            g2.setFont(g2.getFont().deriveFont(Font.BOLD, 30F));

            text = "NEW GAME";
            x = getXforCenteredText(text);
            y += gp.tileSize * 3;
            g2.drawString(text, x + 5, y);
            if (comandNum == 0) {
                g2.drawString(">", x - 20, y);
            }
            text = "CARACTER SELECT";
            x = getXforCenteredText(text);
            y += gp.tileSize;
            g2.drawString(text, x + 5, y);
            if (comandNum == 1) {
                g2.drawString(">", x - 20, y);
            }

            text = "LOAD GAME";
            x = getXforCenteredText(text);
            y += gp.tileSize;
            g2.drawString(text, x + 5, y);
            if (comandNum == 2) {
                g2.drawString(">", x - 20, y);
            }
            text = "QUIT";
            x = getXforCenteredText(text);
            y += gp.tileSize;
            g2.drawString(text, x + 5, y);
            if (comandNum == 3) {
                g2.drawString(">", x - 20, y);
            }
        } else if (titleScreenState == 1) {

            //CLASS SELECTION SCREEN
            g2.setColor(Color.white);
            g2.setFont(g2.getFont().deriveFont(42F));

            String text = "Select your class";
            int x = getXforCenteredText(text);
            int y = gp.tileSize * 3;
            g2.drawString(text, x, y);

            g2.setColor(Color.white);
            g2.setFont(g2.getFont().deriveFont(30F));

            text = "Fighter";
            x = getXforCenteredText(text);
            y += gp.tileSize * 3;
            g2.drawString(text, x, y);
            if (comandNum == 0) {

                g2.drawString(">", x - 22, y);
            }

            text = "Thief";
            x = getXforCenteredText(text);
            y += gp.tileSize;
            g2.drawString(text, x, y);
            if (comandNum == 1) {
                g2.drawString(">", x - 22, y);
            }

            text = "Sorcerer";
            x = getXforCenteredText(text);
            y += gp.tileSize;
            g2.drawString(text, x, y);
            if (comandNum == 2) {
                g2.drawString(">", x - 22, y);
            }

            text = "Back";
            x = getXforCenteredText(text);
            y += gp.tileSize * 2;
            g2.setFont(g2.getFont().deriveFont(25F));
            g2.drawString(text, x, y);
            if (comandNum == 3) {
                g2.drawString(">", x - 22, y);
            }
        }
    }

    public void drawDialogueScreen() {
        //window
        int x = gp.tileSize * 2;
        int y = gp.tileSize / 2;
        int width = gp.screenWidth - (gp.tileSize * 4);
        int height = gp.tileSize * 4;
        drawSubWindow(x, y, width, height);

        g2.setFont(g2.getFont().deriveFont(Font.PLAIN, 32F));
        x += gp.tileSize;
        y += gp.tileSize;
        
        for (String line : currentDialogue.split("\n")) {
            g2.drawString(line, x, y);
            y += 40;
        }
    }
    
    public void drawInventory(){
        // FRAME
        int frameX = gp.tileSize * 12;
        int frameY = gp.tileSize;
        int frameWidth = gp.tileSize * 6;
        int frameHeight = gp.tileSize * 5;
        drawSubWindow(frameX, frameY, frameWidth, frameHeight);
        
        // SLOTS
        final int slotXstart = frameX + 20;
        final int slotYstart = frameY + 20;
        int slotX = slotXstart;
        int slotY = slotYstart;
        int slotSize = gp.tileSize + 3;
        
        // DRAW PLAYER'S ITEMS
        for (int i = 0; i < gp.player.inventory.size(); i ++) {

            // EQUIP CURSOR
            if(gp.player.inventory.get(i)==gp.player.currentWeapon || gp.player.inventory.get(i)==gp.player.currentShield){
                g2.setColor(new Color(240,190,90));
                g2.fillRoundRect(slotX,slotY,gp.tileSize,gp.tileSize,10,10);
            
            
            }
            g2.drawImage(gp.player.inventory.get(i).down1, slotX, slotY, null);
            slotX += slotSize;
            
            if (i == 4 || i == 9 || i == 14) {
                slotX = slotXstart;
                slotY += slotSize;
            }
        }
        
        // CURSOR
        int cursorX = slotXstart + (slotSize * slotCol);
        int cursorY = slotYstart + (slotSize * slotRow);
        int cursorWidth = gp.tileSize;
        int cursorHeight = gp.tileSize;
        
        // DRAW CURSOR
        g2.setColor(Color.white);
        g2.setStroke(new BasicStroke(3));
        g2.drawRoundRect(cursorX, cursorY, cursorWidth, cursorHeight, 10, 10);
        
        // DESCRIPTION FRAME
        int dFrameX = frameX;
        int dFrameY = frameY + frameHeight;
        int dFrameWidth = frameWidth;
        int dFrameHeight = gp.tileSize * 3;
        
        
        // DRAW DESCRIPTION TEXT
        int textX = dFrameX + 20;
        int textY = dFrameY + gp.tileSize;
        g2.setFont(g2.getFont().deriveFont(30F));
        int itemIndex = getItemIndexOnSlot();
        
        if (itemIndex < gp.player.inventory.size()) {
            
            drawSubWindow(dFrameX, dFrameY, dFrameWidth, dFrameHeight);
            for (String line : gp.player.inventory.get(itemIndex).description.split("\n")) {
                g2.drawString(line, textX, textY);
                textY += 32;
            }
        }
    }
    
    public int getItemIndexOnSlot() {
        int itemIndex = slotCol + (slotRow * 5);
        return itemIndex;
    }
    
    public void drawCharacterScreen() {
        final int frameX = gp.tileSize * 2;
        final int frameY = gp.tileSize;
        final int frameWidth = gp.tileSize * 5;
        final int frameHeight = gp.tileSize * 10;
        drawSubWindow(frameX, frameY, frameWidth, frameHeight);
        
        g2.setColor(Color.white);
        g2.setFont(g2.getFont().deriveFont(32F));
        
        int textX = frameX + 20;
        int textY = frameY + gp.tileSize;
        final int lineHeight = 35;
        
        g2.drawString("Level", textX, textY); textY += lineHeight;
        g2.drawString("Life", textX, textY); textY += lineHeight;
        g2.drawString("Mana", textX, textY); textY += lineHeight;
        g2.drawString("Strenght", textX, textY); textY += lineHeight;
        g2.drawString("Dexterity", textX, textY); textY += lineHeight;
        g2.drawString("Attack", textX, textY); textY += lineHeight;
        g2.drawString("Defense", textX, textY); textY += lineHeight;
        g2.drawString("Exp", textX, textY); textY += lineHeight;
        g2.drawString("Next Level", textX, textY); textY += lineHeight;
        g2.drawString("Coin", textX, textY); textY += lineHeight + 10;
        g2.drawString("Weapon", textX, textY); textY += lineHeight + 15;
        g2.drawString("Shield", textX, textY); textY += lineHeight;
        
        // VALUES
        int tailX = (frameX + frameWidth) - 30; 
        textY = frameY + gp.tileSize;
        String value;
        
        value = String.valueOf(gp.player.level);
        textX = getXforAlignToRightText(value, tailX);
        g2.drawString(value, textX, textY);
        textY += lineHeight;
        
        value = String.valueOf(gp.player.life + "/" + gp.player.maxLife);
        textX = getXforAlignToRightText(value, tailX);
        g2.drawString(value, textX, textY);
        textY += lineHeight;
        
        value = String.valueOf(gp.player.mana + "/" + gp.player.maxMana);
        textX = getXforAlignToRightText(value, tailX);
        g2.drawString(value, textX, textY);
        textY += lineHeight;
        
        value = String.valueOf(gp.player.strength);
        textX = getXforAlignToRightText(value, tailX);
        g2.drawString(value, textX, textY);
        textY += lineHeight;
        
        value = String.valueOf(gp.player.dexterity);
        textX = getXforAlignToRightText(value, tailX);
        g2.drawString(value, textX, textY);
        textY += lineHeight;
        
        value = String.valueOf(gp.player.attack);
        textX = getXforAlignToRightText(value, tailX);
        g2.drawString(value, textX, textY);
        textY += lineHeight;
        
        value = String.valueOf(gp.player.defense);
        textX = getXforAlignToRightText(value, tailX);
        g2.drawString(value, textX, textY);
        textY += lineHeight;
        
        value = String.valueOf(gp.player.exp);
        textX = getXforAlignToRightText(value, tailX);
        g2.drawString(value, textX, textY);
        textY += lineHeight;
        
        value = String.valueOf(gp.player.nextLevelExp);
        textX = getXforAlignToRightText(value, tailX);
        g2.drawString(value, textX, textY);
        textY += lineHeight;
        
        value = String.valueOf(gp.player.coin);
        textX = getXforAlignToRightText(value, tailX);
        g2.drawString(value, textX, textY);
        textY += lineHeight;
        
        g2.drawImage(gp.player.currentWeapon.down1, tailX - gp.tileSize, textY - 24, null); 
        textY += gp.tileSize;
        
        g2.drawImage(gp.player.currentShield.down1, tailX - gp.tileSize, textY - 24, null); 
    }

    public void drawSubWindow(int x, int y, int width, int height) {
        Color c = new Color(0, 0, 0, 210);//O quarto valor é opacidade, também chamado de ALPHA
        g2.setColor(c);
        g2.fillRoundRect(x, y, width, height, 35, 35);
        c = new Color(255, 255, 255);
        g2.setColor(c);
        g2.setStroke(new BasicStroke(5));
        g2.drawRoundRect(x + 5, y + 5, width - 9, height - 9, 25, 25);
    }

    public void drawPauseScreen() {
        String text = "PAUSED";
        int x = getXforCenteredText(text);
        int y = gp.screenHeight / 2;

        g2.drawString(text, x, y);
    }

    public int getXforCenteredText(String text) {
        int length = (int) g2.getFontMetrics().getStringBounds(text, g2).getWidth();
        int x = (gp.screenWidth / 2) - length / 2;
        return x;
    }
    
    public int getXforAlignToRightText(String text, int tailX) {
        int length = (int) g2.getFontMetrics().getStringBounds(text, g2).getWidth();
        int x = tailX - length;
        return x;
    }

    public void addMessage(String text) {
        message.add(text);
        messageCounter.add(0);
    }
    
    public void drawMessage() {
        int messageX = gp.tileSize;
        int messageY = gp.tileSize * 4;
        g2.setFont(g2.getFont().deriveFont(32F));
        
        for (int i = 0; i < message.size(); i++) {
            if (message.get(i) != null) {
                // Efeito de sombreamento
                g2.setColor(Color.black);
                g2.drawString(message.get(i), messageX + 2, messageY + 2);
                
                g2.setColor(Color.white);
                g2.drawString(message.get(i), messageX, messageY);
                
                int counter = messageCounter.get(i) + 1;
                messageCounter.set(i, counter);
                messageY += 50;
                
                if (messageCounter.get(i) > 180 ) {
                    message.remove(i);
                    messageCounter.remove(i);
                } 
            }
        }
    }
}