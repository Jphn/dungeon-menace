/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package projeto.dugeonmenace;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.text.DecimalFormat;
import projeto.dugeonmenace.objectsSprite.OBJ_Key;

/**
 *
 * @author LucianoNeto
 */
public class UI {

    GamePanel gp;
    Font arial_40, arial_80B;
    BufferedImage keyImage;
    public boolean messageOn = false;
    int messageCounter = 0;
    public boolean gameFinished = false;

    double playTime = 0;
    DecimalFormat decimalFormat = new DecimalFormat("#0.00");

    public String message = "";

    public UI(GamePanel gp) {

        this.gp = gp;
        arial_40 = new Font("Arial", Font.PLAIN, 40);
        arial_80B = new Font("Arial", Font.BOLD, 80);
        OBJ_Key key = new OBJ_Key();
        keyImage = key.image;

    }

    public void draw(Graphics2D g2) {

        if (gameFinished) {
            g2.setFont(arial_40);
            g2.setColor(Color.white);
            String text = "You found the treasure !";
            int textLength = (int) g2.getFontMetrics().getStringBounds(text, g2).getWidth();// return text length

            int x = (gp.screenWidth / 2) - textLength / 2;
            int y = (gp.screenHeight / 2) - (gp.tileSize * 3);
            g2.drawString(text, x, y);

            g2.setFont(arial_80B);
            g2.setColor(Color.yellow);

            String text1 = "Parabéns";
            int textLength1 = (int) g2.getFontMetrics().getStringBounds(text1, g2).getWidth();// return text length

            int x1 = (gp.screenWidth / 2) - textLength1 / 2;
            int y1 = (gp.screenHeight / 2) + (gp.tileSize * 3);
            g2.drawString(text1, x1, y1);

            g2.setFont(arial_40);
            g2.setColor(Color.yellow);

            String text2 = "Tempo de jogo: " + decimalFormat.format(playTime);
            int textLength2 = (int) g2.getFontMetrics().getStringBounds(text2, g2).getWidth();// return text length

            int x2 = (gp.screenWidth / 2) - textLength2 / 2;
            int y2 = (gp.screenHeight / 2) + (gp.tileSize * 5);
            g2.drawString(text2, x2, y2);

            gp.gameThread = null;
        } else {

            g2.setFont(arial_40);

            g2.setColor(Color.white);
            g2.drawImage(keyImage, gp.tileSize / 2, gp.tileSize / 2, gp.tileSize, gp.tileSize, null);
            g2.drawString("x " + gp.player.hasKey, 74, 65);

            //TIME
            playTime += (double) 1 / 60;
            g2.drawString("Time: " + decimalFormat.format(playTime), gp.tileSize * 11, 65);

            // MESSAGE
            if (messageOn == true) {
                g2.setFont(g2.getFont().deriveFont(20F));
                g2.drawString(message, gp.tileSize, gp.tileSize * 5);
                messageCounter++;
                if (messageCounter > 80) {
                    messageCounter = 0;
                    messageOn = false;
                }

            }

        }

    }

    public void showMessage(String text) {
        message = text;
        messageOn = true;
    }

}
