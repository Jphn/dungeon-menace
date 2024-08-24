/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package projeto.dugeonmenace;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

/**
 *
 * @author LucianoNeto
 */
public class KeyHandler implements KeyListener {
    public boolean upPressed,downPressed,leftPressed,rigthPressed;
    
    
    @Override
    public void keyTyped(KeyEvent e) {  
    }

    @Override
    public void keyPressed(KeyEvent e) {
        int code = e.getKeyCode(); // retorna o valor inteiro da tecla apertada
        
        if(code == KeyEvent.VK_W){
            this.upPressed = true;
        }
        
        if(code == KeyEvent.VK_S){
            this.downPressed = true;
        }
        
        if(code == KeyEvent.VK_A){
            this.leftPressed = true;
        }
        
        if(code == KeyEvent.VK_D){
            this.rigthPressed = true;
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {
        int code = e.getKeyCode(); // retorna o valor inteiro da tecla solta
        
        if(code == KeyEvent.VK_W){
            this.upPressed = false;
        }
        
        if(code == KeyEvent.VK_S){
            this.downPressed = false;
        }
        
        if(code == KeyEvent.VK_A){
            this.leftPressed =false;
        }
        
        if(code == KeyEvent.VK_D){
            this.rigthPressed = false;
        }
        
    }
    
}
