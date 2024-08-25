/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package projeto.dugeonmenace;

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
    DecimalFormat decimalFormat = new DecimalFormat("#0.00");

    public String message = "";

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
        if (gp.gameState == gp.playState) {

        } else if (gp.gameState == gp.pauseState) {
            //game pausado, a tela de espera deve abrir
            drawPauseScreen();

        }

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
