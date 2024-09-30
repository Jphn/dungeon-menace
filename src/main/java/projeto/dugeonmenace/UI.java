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
import projeto.dugeonmenace.objectsSprite.OBJ_Coin_Bronze;
import projeto.dugeonmenace.objectsSprite.OBJ_Heart;
import projeto.dugeonmenace.objectsSprite.OBJ_ManaCrystal;

/**
 *
 * @author LucianoNeto
 */
public class UI {
    GamePanel gp;
    Graphics2D g2;
    public Font pixelOperator;
    BufferedImage heartFull, heartHalf, heartBlank, crystalFull, crystalBlank, coinImage;
    
    public boolean messageOn = false;
//    int messageCounter = 0;
//    public String message = "";
    ArrayList<String> message = new ArrayList<>();
    ArrayList<Integer> messageCounter = new ArrayList<>();
    public String currentDialogue = "";
    public boolean gameFinished = false;
    public int playerSlotCol = 0;
    public int playerSlotRow = 0;
    
    public int npcSlotCol = 0;
    public int npcSlotRow = 0;
    
    
    
    // Ele chama só de subState
    int optionsSubState = 0;
    
    double playTime = 0;
    DecimalFormat decimalFormat = new DecimalFormat("#0");

    //TITLE SCREEN VARIABLES
    public int commandNum = 0;
    int titleScreenState = 0; // 0: primeira tela , 1: segunda tela
    
    //TRASITION
    int subState = 0;
    int counter =0;
    
    //TRADE
    public Entity npc;
    
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
        
        Entity bronzeCoin = new OBJ_Coin_Bronze(gp);
        
        coinImage = bronzeCoin.down1;
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
            //drawPlayerLife();
            drawDialogueScreen();
        }
        
        // CHARACHTER STATE
        if (gp.gameState == gp.characterState) {
            drawCharacterScreen();
            drawInventory(gp.player,true);
        }
        
        // OPTIONS STATE
        if (gp.gameState == gp.optionsState) {
            drawOptionsScreen();
        }
        
        // GAME OVER STATE
        if(gp.gameState == gp.gameOverState){
            drawGameOverScreen();
        }
        // TRASITION STATE
        if(gp.gameState == gp.trasitionState){
            drawTrasition();
        }
        // TRADE STATE
        if(gp.gameState == gp.tradeState){
            drawTradeScreen();
        }
        // SLEEP STATE
        if(gp.gameState == gp.sleepState){
            drawSleepScreen();
        }
        
    }
    
    public int getItemIndexOnSlot(int slotCol, int slotRow) {
        int itemIndex = slotCol + (slotRow * 5);
        return itemIndex;
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
    
    public void drawTrasition(){
        
        counter++;
        g2.setColor(new Color(0,0,0,counter*5));
        g2.fillRect(0, 0, gp.screenWidth, gp.screenHeight);
        if(counter == 50){
            counter=0;
            gp.gameState = gp.playState;
            gp.currentMap = gp.eHandler.tempMap;
            
            gp.player.worldX = gp.tileSize * gp.eHandler.tempCol;
            gp.player.worldY = gp.tileSize * gp.eHandler.tempRow;
            
            gp.eHandler.previousEventX = gp.player.worldX;
            gp.eHandler.previousEventY = gp.player.worldY;
            
        }
        
        
            
            
        
        
    }
    
    public void drawTitleScreen() {
            //Setting Background color
            g2.setColor(new Color(70, 120, 80));
            g2.fillRect(0, 0, gp.screenWidth, gp.screenHeight);
        
        if (titleScreenState == 0) {
            

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
            if (commandNum == 0) {
                g2.drawString(">", x - 20, y);
            }
            text = "CARACTER SELECT";
            x = getXforCenteredText(text);
            y += gp.tileSize;
            g2.drawString(text, x + 5, y);
            if (commandNum == 1) {
                g2.drawString(">", x - 20, y);
            }

            text = "LOAD GAME";
            x = getXforCenteredText(text);
            y += gp.tileSize;
            g2.drawString(text, x + 5, y);
            if (commandNum == 2) {
                g2.drawString(">", x - 20, y);
            }
            text = "QUIT";
            x = getXforCenteredText(text);
            y += gp.tileSize;
            g2.drawString(text, x + 5, y);
            if (commandNum == 3) {
                g2.drawString(">", x - 20, y);
            }
        } else if (titleScreenState == 1) {

            //CLASS SELECTION SCREEN
            g2.setColor(Color.white);
            g2.setFont(g2.getFont().deriveFont(Font.BOLD, 30F));

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
            if (commandNum == 0) {

                g2.drawString(">", x - 22, y);
            }

            text = "Thief";
            x = getXforCenteredText(text);
            y += gp.tileSize;
            g2.drawString(text, x, y);
            if (commandNum == 1) {
                g2.drawString(">", x - 22, y);
            }

            text = "Sorcerer";
            x = getXforCenteredText(text);
            y += gp.tileSize;
            g2.drawString(text, x, y);
            if (commandNum == 2) {
                g2.drawString(">", x - 22, y);
            }

            text = "Back";
            x = getXforCenteredText(text);
            y += gp.tileSize * 2;
            g2.setFont(g2.getFont().deriveFont(25F));
            g2.drawString(text, x, y);
            if (commandNum == 3) {
                g2.drawString(">", x - 22, y);
            }
        }
    }

    public void drawDialogueScreen() {
        //window
        int x = gp.tileSize * 3;
        int y = gp.tileSize / 2;
        int width = gp.screenWidth - (gp.tileSize * 6);
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
    
    public void drawSleepScreen(){
        counter++;
        
        if (counter < 120){
            gp.eManager.lighting.filterAlpha +=0.01f;
            if(gp.eManager.lighting.filterAlpha>1f){
                gp.eManager.lighting.filterAlpha = 1f;
            
            }
        }
        if(counter >= 120){
            gp.eManager.lighting.filterAlpha-=0.01f;
            if(gp.eManager.lighting.filterAlpha<0f){
                gp.eManager.lighting.filterAlpha=0f;
                counter = 0;
                gp.eManager.lighting.dayState = gp.eManager.lighting.day;
                
                gp.eManager.lighting.dayCounter=0;
                
                gp.gameState = gp.playState;
                
                gp.player.getImage();
            }
        
        }
    
    }
    
    public void drawInventory(Entity entity,boolean cursor){
         // FRAME
        int frameX = 0;
        int frameY = 0;
        int frameWidth = 0;
        int frameHeight = 0;
        int slotCol;
        int slotRow;
        
        if(entity == gp.player){
            // FRAME
            frameX = gp.tileSize * 12;
            frameY = gp.tileSize;
            frameWidth = gp.tileSize * 6;
            frameHeight = gp.tileSize * 5;
            slotCol = playerSlotCol;
            slotRow=playerSlotRow;
        
        }else{ // IF ISNT PLAYER
            // FRAME
            frameX = gp.tileSize * 2;
            frameY = gp.tileSize;
            frameWidth = gp.tileSize * 6;
            frameHeight = gp.tileSize * 5;
            slotCol = npcSlotCol;
            slotRow = npcSlotRow;
        
        }
            
        drawSubWindow(frameX, frameY, frameWidth, frameHeight);
        
        // SLOTS
        final int slotXstart = frameX + 20;
        final int slotYstart = frameY + 20;
        int slotX = slotXstart;
        int slotY = slotYstart;
        int slotSize = gp.tileSize + 3;
        
        // DRAW PLAYER'S ITEMS
        for (int i = 0; i < entity.inventory.size(); i ++) {

            // EQUIP CURSOR
            if(entity.inventory.get(i)==entity.currentWeapon || entity.inventory.get(i)==entity.currentShield
                    || entity.inventory.get(i)==entity.currentLight){
                
                g2.setColor(new Color(240,190,90));
                g2.fillRoundRect(slotX,slotY,gp.tileSize,gp.tileSize,10,10);
            
            }
            
            g2.drawImage(entity.inventory.get(i).down1, slotX, slotY, null);
            
            
            // DISPLAY AMOUNT
            if(entity == gp.player && entity.inventory.get(i).amount >1){
                int amountX;
                int amountY;
                
                String s = ""+entity.inventory.get(i).amount;
                
                amountX = getXforAlignToRightText(s,slotX+44); 
                amountY = slotY+gp.tileSize;
                //shadow 
                g2.setColor(new Color(60,60,60));
                g2.drawString(s, amountX, amountY);
                
                //Number
                
                g2.setColor(Color.white);
                g2.drawString(s, amountX-3, amountY-3);
            
            }
            slotX += slotSize;
            
            if (i == 4 || i == 9 || i == 14) {
                slotX = slotXstart;
                slotY += slotSize;
            }
        }
        
        // CURSOR
        if(cursor== true){
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
            int itemIndex = getItemIndexOnSlot(slotCol,slotRow);
        
            if (itemIndex < entity.inventory.size()) {
            
                drawSubWindow(dFrameX, dFrameY, dFrameWidth, dFrameHeight);
                for (String line : entity.inventory.get(itemIndex).description.split("\n")) {
                    g2.drawString(line, textX, textY);
                    textY += 32;
                }
            }
        }
    }
    
    public void drawTradeScreen(){
        switch(subState){
            case 0:tradeSelect();break;
            case 1:trade_Buy();break;
            case 2:trade_Sell();break;   
        }
        gp.keyH.enterPressed = false;
        
    }
    
    public void tradeSelect(){
        drawDialogueScreen();
        
        //Draw Window
        int x = gp.tileSize*15;
        int y = gp.tileSize*4 + (int)(gp.tileSize/2);
        int width =  gp.tileSize*3;
        int height = (int)  (gp.tileSize*3.5);
        drawSubWindow(x,y,width,height);
        
        //Draw Text
        
        x += gp.tileSize;
        y += gp.tileSize;
        g2.drawString("Buy", x, y);
        if(commandNum == 0){
            g2.drawString(">", x-24, y);
            if(gp.keyH.enterPressed == true){
                subState = 1;
            }
        }
        
        y += gp.tileSize;
        g2.drawString("Sell", x, y);
         if(commandNum == 1){
            g2.drawString(">", x-24, y);
            if(gp.keyH.enterPressed == true){
                subState = 2;
            }
        }
        
        y += gp.tileSize;
        g2.drawString("Leave", x, y);
         if(commandNum == 2){
            g2.drawString(">", x-24, y);
            if(gp.keyH.enterPressed == true){
                commandNum = 0;
                gp.gameState=gp.dialogueState;
                currentDialogue = "Come again, hehe!";
            }
        }
        
        y += gp.tileSize;
    }
    
    public void trade_Buy(){
        //draw player inventory
        drawInventory(gp.player,false);
        
        drawInventory(npc,true); ///Isso aqui está estranho
        
        //DRAW HINT WINDOW
        int x = gp.tileSize*2;
        int y = gp.tileSize*9;
        int width = gp.tileSize*6;
        int height = gp.tileSize*2;
        
        drawSubWindow(x,y,width,height);
        g2.drawString("[ESC] Back", x+24, y+60);
        //DRAW PLAYER COIN WINDOW
        x = gp.tileSize*12;
        y = gp.tileSize*9;
        width = gp.tileSize*6;
        height = gp.tileSize*2;
        
        drawSubWindow(x,y,width,height);
        g2.drawString("Your coins: "+gp.player.coin, x+24, y+60);
        
        
        //DRAW PRICE WINDOW
        int itemIndex = getItemIndexOnSlot(npcSlotCol,npcSlotRow);
        
        if(itemIndex < npc.inventory.size()){
            x = (int) (gp.tileSize*5.5);
            y = (int) (gp.tileSize*5.5);
            width = (int) (gp.tileSize*2.5);
            height = gp.tileSize;
            drawSubWindow(x,y,width,height);
            g2.drawImage(coinImage,x+10, y+8,32,32,null);
            
            int price = npc.inventory.get(itemIndex).price;
            
            String text = "" + price;
            x = getXforAlignToRightText(text,gp.tileSize*8-20);
            
            g2.drawString(text,x,y+34);
            
            //Buy an Item
            if(gp.keyH.enterPressed ){
                
                if(npc.inventory.get(itemIndex).price > gp.player.coin){
                    subState=0;
                    gp.gameState = gp.dialogueState;
                    currentDialogue = "You need more coins to buy that !";
                    drawDialogueScreen();
                    gp.gameState = gp.tradeState;
                }else if(gp.player.canObtainItem(npc.inventory.get(itemIndex))==true){
                    gp.player.coin -= npc.inventory.get(itemIndex).price;
                        
                    
                
                }else{
                    subState=0;
                    gp.gameState = gp.dialogueState;
                    currentDialogue = "You inventory is full !";
                    drawDialogueScreen();
                    gp.gameState = gp.tradeState;
                }
//                else if(gp.player.inventory.size() == gp.player.maxInventorySize){
//                    
//                    gp.gameState = gp.dialogueState;
//                    currentDialogue = "You inventory is full !";
//                    drawDialogueScreen();
//                    gp.gameState = gp.tradeState;
//
//                }else{
//                    gp.player.coin -= npc.inventory.get(itemIndex).price;
//                    gp.player.inventory.add(npc.inventory.get(itemIndex));
//                }
            
        }
        
        
        
        }
    }
    
    public void trade_Sell(){
        
        //DRAW PLAYER INVENTORY
        drawInventory(gp.player,true);
        
        //DRAW HINT WINDOW
        int x = gp.tileSize*2;
        int y = gp.tileSize*9;
        int width = gp.tileSize*6;
        int height = gp.tileSize*2;
        
        drawSubWindow(x,y,width,height);
        g2.drawString("[ESC] Back", x+24, y+60);
        //DRAW PLAYER COIN WINDOW
        x = gp.tileSize*12;
        y = gp.tileSize*9;
        width = gp.tileSize*6;
        height = gp.tileSize*2;
        
        drawSubWindow(x,y,width,height);
        g2.drawString("Your coins: "+gp.player.coin, x+24, y+60);
        
        
        //DRAW PRICE WINDOW
        int itemIndex = getItemIndexOnSlot(playerSlotCol,playerSlotRow);
        if(itemIndex < gp.player.inventory.size()){
            x = (int) (gp.tileSize*15.5);
            y = (int) (gp.tileSize*5.5);
            width = (int) (gp.tileSize*2.5);
            height = gp.tileSize;
            drawSubWindow(x,y,width,height);
            g2.drawImage(coinImage,x+10, y+8,32,32,null);
            
            int price = (gp.player.inventory.get(itemIndex).price/2);
            
            String text = "" + price;
            x = getXforAlignToRightText(text,gp.tileSize*18-20);
            
            g2.drawString(text,x,y+34);
            
            //Sell an Item
            if(gp.keyH.enterPressed){
            
            if(gp.player.inventory.get(itemIndex) == gp.player.currentWeapon 
                || gp.player.inventory.get(itemIndex) == gp.player.currentShield 
                || gp.player.inventory.get(itemIndex) == gp.player.currentLight ){
                commandNum = 0;
                subState = 0;
                gp.gameState = gp.dialogueState;
                currentDialogue = "You cannot sell an equipped item !";
                gp.gameState = gp.tradeState;
                
            
            }else{ 
                if(gp.player.inventory.get(itemIndex).amount> 1){
                    gp.player.inventory.get(itemIndex).amount--;
                    
                }else{
                     gp.player.inventory.remove(itemIndex);
                }
                gp.player.coin += price;
        }
  
        }
    
    }
    }
    
    public void drawGameOverScreen(){
       g2.setColor(new Color(0,0,0,150));
       g2.fillRect(0, 0, gp.screenWidth,gp.screenHeight);
       
       String text =  "Game Over";
       int x ;
       int y;
       
       // Texto
       g2.setColor(Color.white);
       g2.setFont(g2.getFont().deriveFont(Font.BOLD,50f));
       
       x = getXforCenteredText(text);
       y = gp.tileSize*4;
       
       g2.drawString(text, x, y);
       
       
       // RETRY
       g2.setFont(g2.getFont().deriveFont(Font.BOLD,35f));
       text =  "Retry";
       x = getXforCenteredText(text);
       y +=gp.tileSize*4;
       
       g2.drawString(text, x, y);
       if(commandNum==0){
        g2.drawString(">",x-20,y);
       }
       
       // Back to the title screen
       
       text =  "Quit";
       x = getXforCenteredText(text);
       y +=55;
       
       g2.drawString(text, x, y);
       if(commandNum==1){
        g2.drawString(">",x-20,y);
       }
       
       
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
    
    public void drawOptionsScreen() {
        g2.setColor(Color.white);
        g2.setFont(g2.getFont().deriveFont(32F));
        
        // SUB WINDOW
        int frameX = gp.tileSize * 6;
        int frameY = gp.tileSize;
        int frameWidth = gp.tileSize * 8;
        int frameHeight = gp.tileSize * 10;
        drawSubWindow(frameX, frameY, frameWidth, frameHeight);
        
        switch(optionsSubState) {
            case 0: options_top(frameX, frameY); break;
            case 1: options_fullScreenNotification(frameX, frameY); break;
            case 2: options_control(frameX, frameY); break;
            case 3: options_endGameConfirmation(frameX, frameY); break;
        }
        gp.keyH.enterPressed = false;
    }
    
    public void options_top(int frameX, int frameY) {
        int textX;
        int textY;
        
        // TITLE
        String text = "Options";
        textX = getXforCenteredText(text);
        textY = frameY + gp.tileSize;
        g2.drawString(text, textX, textY);
        
        // OPTION: FULL SCREEN ON/OFF
        textX = frameX + gp.tileSize;
        textY += gp.tileSize * 2;
        g2.drawString("Full Screen", textX, textY);
        if (commandNum == 0) {
            g2.drawString(">", textX - 25, textY);
            if (gp.keyH.enterPressed == true) {
                if (gp.fullScreenOn == false) {
                    gp.fullScreenOn = true;
                }
                else if (gp.fullScreenOn == true) {
                    gp.fullScreenOn = false;
                }
                optionsSubState = 1;
            }
        }
        
        // OPTION: MUSIC
        textY += gp.tileSize;
        g2.drawString("Music", textX, textY);
        if (commandNum == 1) {
            g2.drawString(">", textX - 25, textY);
        }
        
        // OPTION: SE
        textY += gp.tileSize;
        g2.drawString("SE", textX, textY);
        if (commandNum == 2) {
            g2.drawString(">", textX - 25, textY);
        }
        
        // OPTION: CONTROL
        textY += gp.tileSize;
        g2.drawString("Control", textX, textY);
        if (commandNum == 3) {
            g2.drawString(">", textX - 25, textY);
            if (gp.keyH.enterPressed == true) {
                optionsSubState = 2;
                commandNum = 0;
            }
        }
        
        // OPTION: END GAME
        textY += gp.tileSize;
        g2.drawString("End Game", textX, textY);
        if (commandNum == 4) {
            g2.drawString(">", textX - 25, textY);
            if (gp.keyH.enterPressed == true) {
                optionsSubState = 3;
                commandNum = 0;
            }
        }
        
        // OPTION: BACK
        textY += gp.tileSize * 2;
        g2.drawString("Back", textX, textY);
        if (commandNum == 5) {
            g2.drawString(">", textX - 25, textY);
            if (gp.keyH.enterPressed == true) {
                gp.gameState = gp.playState;
                commandNum = 0;
            }
        }
        
        // FULL SCREEN CHECK BOX
        textX = frameX + (int) (gp.tileSize * 4.5);
        textY = frameY + gp.tileSize * 2 + 14;
        g2.setStroke(new BasicStroke(3));
        g2.drawRect(textX, textY, 24, 24);
        if (gp.fullScreenOn == true) {
            g2.fillRect(textX, textY, 24, 24);
        }
        
        // MUSIC VOLUME
        textY += gp.tileSize;
        g2.drawRect(textX, textY, 120, 24); // (120/5) = 24
        int volumeWidth = 24 * gp.music.volumeScale;
        g2.fillRect(textX, textY, volumeWidth, 24);
        
        // SE VOLUME
        textY += gp.tileSize;
        g2.drawRect(textX, textY, 120, 24);
        volumeWidth = 24 * gp.soundEffects.volumeScale;
        g2.fillRect(textX, textY, volumeWidth, 24);
        
        
        gp.config.saveConfig(); //Salva as configurações toda vida que esse metodo é chamado
    }
    
    public void options_fullScreenNotification(int frameX, int frameY) {
        int textX = frameX + gp.tileSize;
        int textY = frameY + gp.tileSize * 3;
        
        currentDialogue = "The change will take \neffect after restarting \nthe game";
        
        for (String line: currentDialogue.split("\n")) {
            g2.drawString(line, textX, textY);
            textY += 40;
        }
        
        // BACK
        textX = frameX + gp.tileSize;
        textY = frameY + gp.tileSize * 9;
        g2.drawString("Back", textX, textY);
        if (commandNum == 0) {
            g2.drawString(">", textX - 25, textY);
            if (gp.keyH.enterPressed == true) {
                optionsSubState = 0;
            }
        }
    }
    
    public void options_control(int frameX, int frameY) {
        int textX; 
        int textY;
        
        // TITLE
        String text = "Control";
        textX = getXforCenteredText(text);
        textY = frameY + gp.tileSize;
        g2.drawString(text, textX, textY);
        
        textX = frameX + gp.tileSize;
        textY += gp.tileSize;
        g2.drawString("Move", textX, textY); textY += gp.tileSize;
        g2.drawString("Confirm/Attack", textX, textY); textY += gp.tileSize;
        g2.drawString("Shoot/Cast", textX, textY); textY += gp.tileSize;
        g2.drawString("Character Screen", textX, textY); textY += gp.tileSize;
        g2.drawString("Pause", textX, textY); textY += gp.tileSize;
        g2.drawString("Options", textX, textY); textY += gp.tileSize;
        
        textX = frameX + gp.tileSize * 6;
        textY = frameY + gp.tileSize * 2;
        g2.drawString("WASD", textX, textY); textY += gp.tileSize;
        g2.drawString("ENTER", textX, textY); textY += gp.tileSize;
        g2.drawString("F", textX, textY); textY += gp.tileSize;
        g2.drawString("C", textX, textY); textY += gp.tileSize;
        g2.drawString("P", textX, textY); textY += gp.tileSize;
        g2.drawString("ESC", textX, textY); textY += gp.tileSize;
        
        // BACK
        textX = frameX + gp.tileSize;
        textY = frameY + gp.tileSize * 9;
        g2.drawString("Back", textX, textY);
        if (commandNum == 0) {
            g2.drawString(">", textX - 25, textY);
            if (gp.keyH.enterPressed == true) {
                optionsSubState = 3;
            }
        }
    }
    
    public void options_endGameConfirmation(int frameX, int frameY) {
        int textX = frameX + gp.tileSize; 
        int textY = frameY + gp.tileSize * 3;
        
        currentDialogue = "Quit the game and \nreturn to the title screen";
        
        for(String line : currentDialogue.split("\n")) {
            g2.drawString(line, textX, textY);
            textY += 40;
        }
        
        // YES
        String text = "Yes";
        textX = getXforCenteredText(text);
        textY += gp.tileSize * 3;
        g2.drawString(text, textX, textY);
        
        if(commandNum == 0) {
            g2.drawString(">", textX - 25, textY);
            if(gp.keyH.enterPressed == true) {
                optionsSubState = 0;
                gp.gameState = gp.titleState;
                gp.resetGame(true);
            }
        } 
        
        // NO
        text = "No";
        textX = getXforCenteredText(text);
        textY += gp.tileSize;
        g2.drawString(text, textX, textY);
        
        if(commandNum == 1) {
            g2.drawString(">", textX - 25, textY);
            if(gp.keyH.enterPressed == true) {
                optionsSubState = 0;
                commandNum = 4;
            }
        } 
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
        int x = (gp.screenWidth / 2) - (length / 2);
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