package projeto.dugeonmenace.enviroment;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import projeto.dugeonmenace.GamePanel;

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

/**
 *
 * @author T-GAMER
 */
public class EnviromentManager {
    GamePanel gp;
    public Lighting lighting; 

    public EnviromentManager(GamePanel gp) {
        this.gp = gp;
        setup();
    }
    public void setup(){
        lighting = new Lighting(gp,350);
    }
    
    public void draw(Graphics2D g2){
        lighting.draw(g2);
    }
    
}
