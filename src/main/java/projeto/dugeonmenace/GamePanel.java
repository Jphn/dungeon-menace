/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package projeto.dugeonmenace;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import javax.swing.JPanel;
import projeto.dugeonmenace.entity.Entity;
import projeto.dugeonmenace.entity.Player;
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
    public KeyHandler keyH = new KeyHandler(this);
    Thread gameThread; //

    //SOUND
    Sound music = new Sound();
    Sound soundEffects = new Sound();

    // COLISÃO
    public CollisionChecker cChecker = new CollisionChecker(this);
    public AssetSetter aSetter = new AssetSetter(this);

    //UI
    public UI ui = new UI(this);

    //EVENT HANDLER
    public EventHandler eHandler = new EventHandler(this);

    //ENTITY AND OBJECT
    public Player player = new Player(this, keyH);
    public Entity obj[] = new Entity[10]; // significa que vamos mostrar até 10 objetos ao mesmo tempo
    public Entity npc[] = new Entity[10];
    public Entity monster[] = new Entity[20];

    ArrayList<Entity> entityList = new ArrayList<>();

    // GAME STATE
    public int gameState;
    public final int titleState = 0;
    public final int playState = 1;
    public final int pauseState = 2;
    public final int dialogueState = 3;
    public final int characterState = 4;

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
        aSetter.setNPC();
        aSetter.setMonster();
        playMusic(0);
        gameState = titleState;
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
                //System.out.println("FPS:" + drawnCount);
                drawnCount = 0;
                timer = 0;
            }
        }
    }

    public void update() {
        if (gameState == playState) {
            player.update();

            //NPC "MOVIMENT"
            for (int i = 0; i < npc.length; i++) {
                if (npc[i] != null) {
                    npc[i].update();
                }
            }

            //MONSTER "MOVIMENT"
            for (int i = 0; i < monster.length; i++) {
                if (monster[i] != null){
                    if ( monster[i].alive && monster[i].dying == false) {
                        monster[i].update();
                    }
                    if(!monster[i].alive){
                        monster[i]=null;
                    }
                }
            }

        } else if (gameState == pauseState) {
            // Nothing
        }
    }

    public void paintComponent(Graphics g) { // isso já ta bild in no java
        /*
            Podemos imaginar esse Graphics como um "pincel"
         */
        super.paintComponent(g);

        Graphics2D g2 = (Graphics2D) g;  // Graphics2D tem mais funções interessantes

        //DEBUG
        long drawStart = 0;
        if (keyH.showDebugText) {
            drawStart = System.nanoTime();
        }

        // TITLE SCREEN
        if (gameState == titleState) {
            // titleScreen.draw(g2);
            ui.draw(g2);

        } else {
            /**
             * Temos que nos certificar que os tiles serão pintados antes do
             * player, para que o player fique uma camada acima
             *
             */
            //TILE
            tileM.draw(g2);

            //ADD ENTITY TO LISTS
            for (int i = 0; i < obj.length; i++) {
                if (obj[i] != null) {
                    entityList.add(obj[i]);
                }
            }

            //ADD PLAYER
            entityList.add(player);

            //ADD NPC
            for (int i = 0; i < npc.length; i++) {
                if (npc[i] != null) {
                    entityList.add(npc[i]);
                }

            }
            //ADD MONSTER
            for (int i = 0; i < monster.length; i++) {
                if (monster[i] != null) {
                    entityList.add(monster[i]);
                }
            }

            // SORT
            Collections.sort(entityList, new Comparator<Entity>() {
                @Override
                public int compare(Entity o1, Entity o2) {
                    int result = Integer.compare(o1.worldY, o2.worldY);
                    return result;
                }
            });

            //Draw entitys
            for (Entity entity : entityList) {
                entity.draw(g2);
            }
            
            //Remove entitys from list
            entityList.clear();
            
            //UI
            ui.draw(g2);
        }

        //DEBUG
        if (keyH.showDebugText) {
            long drawEnd = System.nanoTime();
            long passed = drawEnd - drawStart;
            
            g2.setFont(new Font("Arial", Font.PLAIN, 20));
            g2.setColor(Color.white);
            int x = 10;
            int y = 400;
            int lineHeight = 20;
            
            g2.drawString("WorldX: " + player.worldX, x, y); y += lineHeight;
            g2.drawString("WorldY: " + player.worldY, x, y); y += lineHeight;
            g2.drawString("Col: " + (player.worldX + player.solidArea.x / tileSize), x, y); y += lineHeight;
            g2.drawString("Row: " + (player.worldY + player.solidArea.y / tileSize), x, y);  y += lineHeight;
            g2.drawString("Draw time: " + passed, x, y);
            System.out.println("Draw Time: " + passed);
        }
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