package projeto.dugeonmenace;

import javax.swing.JFrame;

/**
 *
 * @author LucianoNeto
 */
public class DugeonMenace {

    public static void main(String[] args) {

        JFrame window = new JFrame();
        window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        window.setResizable(false);
        window.setTitle("2D Dungeon Menace");

        GamePanel gamePanel = new GamePanel();
        window.add(gamePanel); // como o gamePanel herda o JPanel, eu posso fazer isso e adicionar um JPanel ao JFrame

        window.pack();

        window.setLocationRelativeTo(null); // A tela vai ser iniciada no centro da tela por conta do null
        window.setVisible(true);

        gamePanel.setupGame();

        gamePanel.startGameThread();
    }
}
