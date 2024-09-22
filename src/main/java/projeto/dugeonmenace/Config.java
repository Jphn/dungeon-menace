/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package projeto.dugeonmenace;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author T-GAMER
 */
public class Config {
    GamePanel gp;

    public Config(GamePanel gp) {
        this.gp = gp;
    }
            
    public void saveConfig(){
        
        try {
            BufferedWriter bw = new BufferedWriter(new FileWriter("config.txt"));
            
            //FULL SCREEN
            if(gp.fullScreenOn == true){
                bw.write("On");
            }
            if(gp.fullScreenOn == false){
                bw.write("Off");
            }
            bw.newLine();
            
            //MUSIC VOLUME
            bw.write(String.valueOf(gp.music.volumeScale));
            bw.newLine();
            
            // SE VOLUME
            bw.write(String.valueOf(gp.soundEffects.volumeScale));
            bw.newLine();
            
            bw.close();
            
        } catch (IOException ex) {
            Logger.getLogger(Config.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void loadConfig(){
    
        try {
            BufferedReader br = new BufferedReader(new FileReader("config.txt"));
            String s = br.readLine();
            
            if(s.equals("On")){
                gp.fullScreenOn = true;
                
            } else if(s.equals("Off")){
                gp.fullScreenOn = false;
            }
            
            //Music volume
            s= br.readLine();//Lê a proxima linha
            
            gp.music.volumeScale = Integer.parseInt(s);
            
            //SE volume
            s= br.readLine();//Lê a proxima linha
            
            gp.soundEffects.volumeScale = Integer.parseInt(s);
            
            br.close();
            
        } catch (FileNotFoundException ex) {
            Logger.getLogger(Config.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(Config.class.getName()).log(Level.SEVERE, null, ex);
        }
    
    }
    
    
}
