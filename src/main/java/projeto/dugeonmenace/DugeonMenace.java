package projeto.dugeonmenace;

import javax.swing.ImageIcon;
import javax.swing.JFrame;

/**
 *
 * @author LucianoNeto
 */
public class DugeonMenace {
    
    public static JFrame window;

    public static void main(String[] args) {

        window = new JFrame();
        window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        window.setResizable(false);
        window.setTitle("2D Dungeon Menace");
//        window.setUndecorated(true); // tira a barra superior do jogo
//        new DugeonMenace().setIcon;

        GamePanel gamePanel = new GamePanel();
        window.add(gamePanel); // como o gamePanel herda o JPanel, eu posso fazer isso e adicionar um JPanel ao JFrame
        
        gamePanel.config.loadConfig();
        if(gamePanel.fullScreenOn == true){
            window.setUndecorated(true);
        
        }
        window.pack();

        window.setLocationRelativeTo(null); // A tela vai ser iniciada no centro da tela por conta do null
        window.setVisible(true);

        gamePanel.setupGame();

        gamePanel.startGameThread();
    }
    
    public void setIcon() {
        ImageIcon icon = new ImageIcon(getClass().getClassLoader().getResource("/player/boy_down1.png"));
        window.setIconImage(icon.getImage());
    }
}