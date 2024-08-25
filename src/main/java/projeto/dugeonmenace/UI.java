/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package projeto.dugeonmenace;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.text.DecimalFormat;

/**
 *
 * @author LucianoNeto
 */
public class UI {

    GamePanel gp;
    Graphics2D g2;

    Font arial_40, arial_80B;
//    BufferedImage keyImage;
    public boolean messageOn = false;
    int messageCounter = 0;
    public boolean gameFinished = false;

    double playTime = 0;
    DecimalFormat decimalFormat = new DecimalFormat("#0");

    public String message = "";

    public String currentDialogue = "";

    //TITLE SCREEN VARIABLES
    int comandNum = 0;
    int titleScreenState = 0; // 0: primeira tela , 1: segunda tela

    public UI(GamePanel gp) {

        this.gp = gp;
        arial_40 = new Font("Arial", Font.PLAIN, 40);
        arial_80B = new Font("Arial", Font.BOLD, 80);
        //       OBJ_Key key = new OBJ_Key(gp);
//        keyImage = key.image;

    }

    public void draw(Graphics2D g2) {
        this.g2 = g2;

        g2.setFont(arial_40);
        g2.setColor(Color.white);
        if (gp.gameState == gp.titleState) {
            drawTitleScreen();

        } else if (gp.gameState == gp.playState) {

        } else if (gp.gameState == gp.pauseState) {
            //game pausado, a tela de espera deve abrir
            drawPauseScreen();

        } else if (gp.gameState == gp.dialogueState) {
            drawDialogueScreen();
        }
    }

    public void drawTitleScreen() {
        if (titleScreenState == 0) {
            //Setting Background color
            g2.setColor(new Color(70, 120, 80));
            g2.fillRect(0, 0, gp.screenWidth, gp.screenHeight);

            // TITLE NAME
            g2.setFont(g2.getFont().deriveFont(Font.PLAIN, 80F));
            String text = "Blue Boy Adventure";

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

        g2.setFont(g2.getFont().deriveFont(Font.PLAIN, 30F));
        x += gp.tileSize;
        y += gp.tileSize;
        for (String line : currentDialogue.split("\n")) {

            g2.drawString(line, x, y);
            y += 40;
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
        int x = (gp.screenWidth / 2) - length / 2;

        return x;
    }

    public void showMessage(String text) {
        message = text;
        messageOn = true;
    }

}
