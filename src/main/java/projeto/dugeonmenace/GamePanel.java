/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package projeto.dugeonmenace;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import javax.swing.JPanel;
import projeto.dugeonmenace.entity.Player;
import projeto.dugeonmenace.objectsSprite.SuperObject;
import projeto.dugeonmenace.tile.TileManager;

/**
 *
 * @author LucianoNeto
 */
public class GamePanel extends JPanel implements Runnable { // A ideia é funcionar como uma Game Screen

    // Game settings
    final int originalTileSize = 16; // significa que o Tile vai ser 16x16
    //16x16 fica muito pequeno por conta do tamanho atual das resoluções das telas
    // Scaling
    final int scale = 3; // dessa forma o tile vai poder ser aumentado

    public final int tileSize = originalTileSize * scale;//para 16 x 3= 48x48

    public final int maxScreenCol = 16;
    public final int maxScreenRow = 12;
    public final int screenWidth = tileSize * maxScreenCol; // 768 pixels
    public final int screenHeight = tileSize * maxScreenRow; //576 pixels
    // o Ratio disso é 4:3

    // WORLD SETTINGS
    public final int maxWorldCol = 50;
    public final int maxWorldRow = 50;
    public final int worldWidth = tileSize * maxWorldCol;
    public final int worldHeight = tileSize * maxWorldRow;

    //FPS
    int FPS = 60;

    // Tile manager
    TileManager tileM = new TileManager(this);
    KeyHandler keyH = new KeyHandler();
    Thread gameThread; //

    //SOUND
    Sound music = new Sound();
    Sound soundEffects = new Sound();

    // COLISÃO
    public CollisionChecker cChecker = new CollisionChecker(this);
    public AssetSetter aSetter = new AssetSetter(this);

    //UI
    public UI ui = new UI(this);

    public Player player = new Player(this, keyH);
    public SuperObject obj[] = new SuperObject[10]; // significa que vamos mostrar até 10 objetos ao mesmo tempo

    public GamePanel() {
        this.setPreferredSize(new Dimension(screenWidth, screenHeight));
        this.setBackground(Color.black);
        this.setDoubleBuffered(true);// Se estiver true, todos os desenhos desse componente vão ser feitos em
        // um buffer em uma tela a parte
        // resumidamente isso vai melhorar a perfomance da renderização
        this.addKeyListener(keyH);
        this.setFocusable(true); // dessa forma esse game panel pode "focar" em receber key inputs

    }

    public void startGameThread() {
        gameThread = new Thread(this);
        gameThread.start();

    }

    public void setupGame() {
        aSetter.setObject();

        playMusic(0);
    }

    //Run utilizando Delta para a atualização do draw
    @Override
    public void run() {

        double drawInterval = 1000000000 / FPS; // equivalente a 0.01666 segundo
        double delta = 0;
        long lastTime = System.nanoTime();
        long timer = 0;

        long currentTime;
        int drawnCount = 0;

        // Game loop
        while (gameThread != null) { // enquanto a thread existir ele continua rodando

            currentTime = System.nanoTime();
            delta += (currentTime - lastTime) / drawInterval;

            timer += (currentTime - lastTime);
            lastTime = currentTime;

            /* 1 Update; update informations such as caracter position*/
            if (delta >= 1) {
                update();
                repaint(); // é assim que chama o metodo paintComponent
                delta--;
                drawnCount++;
            }
            if (timer >= 1000000000) {
                System.out.println("FPS:" + drawnCount);
                drawnCount = 0;
                timer = 0;
            }
        }
    }

    public void update() {

        player.update();
    }

    public void paintComponent(Graphics g) { // isso já ta bild in no java
        /*
            Podemos imaginar esse Graphics como um "pincel"
         */

        super.paintComponent(g);

        Graphics2D g2 = (Graphics2D) g;  // Graphics2D tem mais funções interessantes

        /**
         * Temos que nos certificar que os tiles serão pintados antes do player,
         * para que o player fique uma camada acima
         *
         */
        //TILE
        tileM.draw(g2);

        //OBJECT
        for (int i = 0; i < obj.length; i++) {
            if (obj[i] != null) {
                obj[i].draw(g2, this);
            }
        }
        //UI
        ui.draw(g2);

        //PLAYER
        player.draw(g2);

        g2.dispose();
    }

    public void playMusic(int i) {
        music.setFile(i);
        music.play();
        music.loop();

    }

    public void stopMusic() {
        music.stop();

    }

    //Play sound effects
    public void playSE(int i) {
        soundEffects.setFile(i);
        soundEffects.play();

    }

}