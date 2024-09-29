/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package projeto.dugeonmenace.tile;

import java.awt.AlphaComposite;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import projeto.dugeonmenace.GamePanel;

/**
 *
 * @author T-GAMER
 */
public class Map extends TileManager {
    
    BufferedImage worldMap[];
    public boolean miniMapOn = false;
    int mapSpriteCounter = 0;
    public Map(GamePanel gp) {
        super(gp);
        
        createWorldMap();
        
        
    }
    
    public void createWorldMap(){
        worldMap = new BufferedImage[gp.maxMap];
        
        int worldMapWidth = gp.tileSize*gp.maxWorldCol;
        
        int worldMapHeight = gp.tileSize*gp.maxWorldRow;
        
        for(int i = 0; i< gp.maxMap;i++){
            
            worldMap[i]= new BufferedImage(worldMapWidth,worldMapHeight, BufferedImage.TYPE_INT_ARGB);
            Graphics2D g2 = (Graphics2D)worldMap[i].createGraphics();
            
            int col = 0;
            int row = 0;
            
            while(col<gp.maxWorldCol && row<gp.maxWorldRow){
            
                int tileNum = mapTileNumber[i][col][row];
                
                int x = gp.tileSize*col;
                int y = gp.tileSize*row;
                
                g2.drawImage(tile[tileNum].image,x,y,null);
                
                col++;
                
                if(col == gp.maxWorldCol){
                    col = 0;
                    row++;
                    
                }
                
                
                
                
            }
            
        }
    
    }
    
    public void drawFullMapScreen(Graphics2D g2){
        
        // Background color
        g2.setColor(Color.black);
        g2.fillRect(0, 0, gp.screenWidth, gp.screenHeight);
        
        // Draw map
        int width = 500;
        int height = 500;
        int x = gp.screenWidth/2 - width/2;
        int y = gp.screenHeight/2 - height/2;
              
        g2.drawImage(worldMap[gp.currentMap],x,y,width,height,null);
        
        // Draw Player
        
        double scale = (double)(gp.tileSize*gp.maxWorldCol)/width;
        int playerX =(int)(x+gp.player.worldX/scale);
        int playerY =(int)(y+gp.player.worldY/scale);
        int playerSize=(int)(gp.tileSize/2);
        
        if(mapSpriteCounter<100){
            g2.drawImage(gp.player.down1, playerX, playerY,playerSize,playerSize,null);
        }else if(mapSpriteCounter<200){
            g2.drawImage(gp.player.down2, playerX, playerY,playerSize,playerSize,null);
        }else{
            g2.drawImage(gp.player.down1, playerX, playerY,playerSize,playerSize,null);
            mapSpriteCounter=0;
        }
        
        mapSpriteCounter++;
        
        
        
        
        // Hint 
        g2.setFont(gp.ui.pixelOperator.deriveFont(32f));
        g2.setColor(Color.white);
        g2.drawString("Press M to close", 750, 550);
        
        
        
    }
    
    public void drawMiniMap(Graphics2D g2){
        
        if(miniMapOn == true){
            // Draw map
            int width = 200;
            int height = 200;
            int x = gp.screenWidth - width-50;
            int y = 50;
            
            g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER,1f));
            g2.drawImage(worldMap[gp.currentMap],x,y,width,height,null);
            
            // Draw Player
        
            double scale = (double)(gp.tileSize*gp.maxWorldCol)/width;
            int playerX =(int)(x+gp.player.worldX/scale);
            int playerY =(int)(y+gp.player.worldY/scale);
            int playerSize=(int)(gp.tileSize/4);
            
            // EU QUE FIZ, ANIMAÇÂO NO MINIMAPA
            if(mapSpriteCounter<100){
                g2.drawImage(gp.player.down1, playerX-6, playerY-6,playerSize,playerSize,null);
            }else if(mapSpriteCounter<200){
                g2.drawImage(gp.player.down2, playerX-6, playerY-6,playerSize,playerSize,null);
            }else{
                g2.drawImage(gp.player.down1, playerX-6, playerY-6,playerSize,playerSize,null);
                mapSpriteCounter=0;
            }
            mapSpriteCounter++;
        }
    }
    
}
