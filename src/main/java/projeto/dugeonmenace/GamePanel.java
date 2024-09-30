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
import java.awt.GraphicsDevice;
import java.awt.GraphicsEnvironment;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

import javax.swing.JPanel;
import projeto.dugeonmenace.ai.PathFinder;
import projeto.dugeonmenace.data.SaveLoad;
import projeto.dugeonmenace.entity.Entity;
import projeto.dugeonmenace.entity.Player;
import projeto.dugeonmenace.enviroment.EnviromentManager;
import projeto.dugeonmenace.tile.Map;
import projeto.dugeonmenace.tile.TileManager;
import tile_interactive.InteractiveTile;

/**
 *
 * @author LucianoNeto
 */
public class GamePanel extends JPanel implements Runnable { // A ideia é funcionar como uma Game Screen
    
    //COR TESTE
    Color CORTESTE = new Color(0,0,0,0.93f);
    // Game settings
    final int originalTileSize = 16; // significa que o Tile vai ser 16x16
    // 16x16 fica muito pequeno por conta do tamanho atual das resoluções das telas
    // Scaling
    final int scale = 3; // dessa forma o tile vai poder ser aumentado
    public final int tileSize = originalTileSize * scale;//para 16 x 3 = 48x48
    public final int maxScreenCol = 20;
    public final int maxScreenRow = 12;
    public final int screenWidth = tileSize * maxScreenCol; // maxScreenCol = 16 (768 pixels), se  for 20 (960 pixels) 
    public final int screenHeight = tileSize * maxScreenRow; //576 pixels
    // o Ratio disso é 4:3
    
    // FOR FULL SCREEN
    int screenWidth2 = screenWidth;
    int screenHeight2 = screenHeight;
    BufferedImage tempScreen;
    Graphics2D g2;
    public boolean fullScreenOn = false;

    // WORLD SETTINGS
    public int maxWorldCol;
    public int maxWorldRow;
    public final int maxMap=10;
    public final int worldWidth = tileSize * maxWorldCol;
    public final int worldHeight = tileSize * maxWorldRow;
    
    public int currentMap=0; 
           
    // FPS
    int FPS = 60;

    // Tile manager
    public TileManager tileM = new TileManager(this);
    public KeyHandler keyH = new KeyHandler(this);
    Thread gameThread; //
    
    // SOUND
    Sound music = new Sound();
    Sound soundEffects = new Sound();

    // COLISÃO
    public CollisionChecker cChecker = new CollisionChecker(this);
    public AssetSetter aSetter = new AssetSetter(this);

    // UI
    public UI ui = new UI(this);

    // EVENT HANDLER
    public EventHandler eHandler = new EventHandler(this);
    
    // ENTITY GENERATOR
    public EntityGenerator eGenerator = new EntityGenerator(this);
    
    // CONFIG
    Config config = new Config(this); 
    SaveLoad saveLoad = new SaveLoad(this);
    
    // ENTITY AND OBJECT
    public Player player = new Player(this, keyH);
    public Entity obj[][] = new Entity[this.maxMap][30]; // significa que vamos mostrar até 30 objetos ao mesmo tempo // 10
    public Entity npc[][] = new Entity[this.maxMap][10];
    public Entity monster[][] = new Entity[this.maxMap][20];
    public InteractiveTile iTile[][] = new InteractiveTile[this.maxMap][50];
    public Entity projectile [][] = new Entity[maxMap][20];
//    public ArrayList<Entity> projectileList = new ArrayList<>();
    public ArrayList<Entity> particleList = new ArrayList<>();
    ArrayList<Entity> entityList = new ArrayList<>();
    
    //PATHFINDER
    public PathFinder pFinder = new PathFinder(this);
    
    //MAP
    Map map = new Map(this);
    
    // EnviromentManager
    EnviromentManager eManager = new EnviromentManager(this);

    // GAME STATE
    public int gameState;
    public final int titleState = 0;
    public final int playState = 1;
    public final int pauseState = 2;
    public final int dialogueState = 3;
    public final int characterState = 4;
    public final int optionsState = 5;
    public final int gameOverState = 6;
    public final int trasitionState = 7;
    public final int tradeState = 8;
    public final int sleepState = 9;
    public final int mapState = 10;
    final int cutsceneState = 11;
    
    // AREA STATE 
    
   
    public int nextArea;
    public final int outside = 50;
    public final int indoor = 51;
    public final int dungeon = 52;
    public int currentArea;
    
    //BOSS
    public boolean bossBattleOn = false;
    
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
        aSetter.setInteractiveTile();
        eManager.setup();
        playMusic(0);
        gameState = titleState;
        currentArea=outside;
        
        tempScreen = new BufferedImage(screenWidth, screenHeight, BufferedImage.TYPE_INT_ARGB);
        g2 = (Graphics2D) tempScreen.getGraphics();
        if(this.fullScreenOn==true){
        setFullScreen(); // Coloca em tela cheia
        }
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
//                repaint(); // é assim que chama o metodo paintComponent
                drawFullScreen(); // draw everything to the buffered image
                drawToFullScreen(); // draw the buffered image to the screen
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

            // NPC "MOVIMENT"
            for (int i = 0; i < npc[currentMap].length; i++) {
                if (npc[currentMap][i] != null) {
                    npc[currentMap][i].update();
                }
            }

            // MONSTER "MOVIMENT"
            for (int i = 0; i < monster[currentMap].length; i++) {
                if (monster[currentMap][i] != null) {
                    if (monster[currentMap][i].alive == true && monster[currentMap][i].dying == false) {
                        monster[currentMap][i].update();
                    }
                    if (monster[currentMap][i].alive == false) {
                        monster[currentMap][i].checkDrop();
                        monster[currentMap][i] = null;
                    }
                }
            }
            
            // PROJECTILE
            for (int i = 0; i < projectile[1].length; i++) {
                if (projectile[currentMap][i] != null){
                    if (projectile[currentMap][i].alive == true) {
                        projectile[currentMap][i].update();
                    }
                    if(projectile[currentMap][i].alive == false) {
                        projectile[currentMap][i] = null;
                    }
                }
            }
            
            // PARTICLE
            for (int i = 0; i < particleList.size(); i++) {
                if (particleList.get(i) != null){
                    if (particleList.get(i).alive == true) {
                        particleList.get(i).update();
                    }
                    if(particleList.get(i).alive == false) {
                        particleList.remove(i);
                    }
                }
            }
            
            for (int i = 0; i < iTile[currentMap].length; i++) {
                if (iTile[currentMap][i] != null) {
                    iTile[currentMap][i].update();
                }
            }
            
        } else if (gameState == pauseState) {
            // Nothing
        }
    }
    
    public void setFullScreen() {
        // GET LOCAL SCREEN DEVICE
        GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
        GraphicsDevice gd = ge.getDefaultScreenDevice();
        gd.setFullScreenWindow(DugeonMenace.window);
        
        // GET FULL SCREEN WIDTH AND HEIGHT
        screenWidth2 = DugeonMenace.window.getWidth();
        screenHeight2 = DugeonMenace.window.getHeight();
    }
    
    // Ele chama essa classe de drawToTempScreen()
    public void drawFullScreen() { 
        //DEBUG
        long drawStart = 0;
        if (keyH.showDebugText) {
            drawStart = System.nanoTime();
        }

        // TITLE SCREEN
        if (gameState == titleState) {
            // titleScreen.draw(g2);
            ui.draw(g2);

        } else if(gameState == mapState){
            map.drawFullMapScreen(g2);
         
        }else {
            /**
             * Temos que nos certificar que os tiles serão pintados antes do
             * player, para que o player fique uma camada acima
             *
             */
            // TILE
            tileM.draw(g2);
            
            // INTERACTIVE TILE
            for (int i = 0; i < iTile[currentMap].length; i++) {
                if (iTile[currentMap][i] != null) {
                    iTile[currentMap][i].draw(g2);
                }
            }
            
            // ADD PLAYER
            entityList.add(player);

            //ADD ENTITY TO LISTS
            for (int i = 0; i < obj[currentMap].length; i++) {
                if (obj[currentMap][i] != null) {
                    entityList.add(obj[currentMap][i]);
                }
            }
            
            // ADD NPC
            for (int i = 0; i < npc[currentMap].length; i++) {
                if (npc[currentMap][i] != null) {
                    entityList.add(npc[currentMap][i]);
                }

            }
            // ADD MONSTER
            for (int i = 0; i < monster[currentMap].length; i++) {
                if (monster[currentMap][i] != null) {
                    entityList.add(monster[currentMap][i]);
                }
            }
            
            // ADD PROJECTILE
            for (int i = 0; i < projectile[1].length; i++) {
                if (projectile[currentMap][i] != null) {
                    entityList.add(projectile[currentMap][i]);
                }
            }
            
            // ADD PARTICLE
            for (int i = 0; i < particleList.size(); i++) {
                if (particleList.get(i) != null) {
                    entityList.add(particleList.get(i));
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
            
            
            
            ///
                // Precisa ser revisado
            ///
            if(currentArea == indoor){
                eManager.draw2(g2);
            }else 
                if(false || player.currentLight != null){
            //Draw light
                eManager.draw(g2);
                } else {
                    g2.setColor(CORTESTE);
                    g2.fillRect(0, 0, screenWidth, screenHeight);
                }
            
            ///
                // Precisa ser revisado
            ///
            
            // MAP
            map.drawMiniMap(g2);
            
            
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
            g2.drawString("Col: " + (player.worldX + player.solidArea.x )/ tileSize, x, y); y += lineHeight;
            g2.drawString("Row: " + (player.worldY + player.solidArea.y )/ tileSize, x, y);  y += lineHeight;
            g2.drawString("Draw time: " + passed, x, y);
            System.out.println("Draw Time: " + passed);
        }
    }
    
    
    
    // Ele chama essa classe de drawToScreen()
    public void drawToFullScreen () { 
        Graphics g = getGraphics();
        g.drawImage(tempScreen, 0, 0, screenWidth2, screenHeight2, null);
        g.dispose();
    } 

//    public void paintComponent(Graphics g) { // isso já ta bild in no java
//        /*
//            Podemos imaginar esse Graphics como um "pincel"
//         */
//        super.paintComponent(g);
//        Graphics2D g2 = (Graphics2D) g;  // Graphics2D tem mais funções interessantes
//
//        //DEBUG
//        long drawStart = 0;
//        if (keyH.showDebugText) {
//            drawStart = System.nanoTime();
//        }
//
//        // TITLE SCREEN
//        if (gameState == titleState) {
//            // titleScreen.draw(g2);
//            ui.draw(g2);
//
//        } else {
//            /**
//             * Temos que nos certificar que os tiles serão pintados antes do
//             * player, para que o player fique uma camada acima
//             *
//             */
//            // TILE
//            tileM.draw(g2);
//            
//            // INTERACTIVE TILE
//            for (int i = 0; i < iTile.length; i++) {
//                if (iTile[i] != null) {
//                    iTile[i].draw(g2);
//                }
//            }
//            
//            // ADD PLAYER
//            entityList.add(player);
//
//            //ADD ENTITY TO LISTS
//            for (int i = 0; i < obj.length; i++) {
//                if (obj[i] != null) {
//                    entityList.add(obj[i]);
//                }
//            }
//            
//            // ADD NPC
//            for (int i = 0; i < npc.length; i++) {
//                if (npc[i] != null) {
//                    entityList.add(npc[i]);
//                }
//
//            }
//            // ADD MONSTER
//            for (int i = 0; i < monster.length; i++) {
//                if (monster[i] != null) {
//                    entityList.add(monster[i]);
//                }
//            }
//            
//            // ADD PROJECTILE
//            for (int i = 0; i < projectileList.size(); i++) {
//                if (projectileList.get(i) != null) {
//                    entityList.add(projectileList.get(i));
//                }
//            }
//            
//            // ADD PARTICLE
//            for (int i = 0; i < particleList.size(); i++) {
//                if (particleList.get(i) != null) {
//                    entityList.add(particleList.get(i));
//                }
//            }
//
//            // SORT
//            Collections.sort(entityList, new Comparator<Entity>() {
//                @Override
//                public int compare(Entity o1, Entity o2) {
//                    int result = Integer.compare(o1.worldY, o2.worldY);
//                    return result;
//                }
//            });
//
//            //Draw entitys
//            for (Entity entity : entityList) {
//                entity.draw(g2);
//            }
//            
//            //Remove entitys from list
//            entityList.clear();
//            
//            //UI
//            ui.draw(g2);
//        }
//
//        //DEBUG
//        if (keyH.showDebugText) {
//            long drawEnd = System.nanoTime();
//            long passed = drawEnd - drawStart;
//            
//            g2.setFont(new Font("Arial", Font.PLAIN, 20));
//            g2.setColor(Color.white);
//            int x = 10;
//            int y = 400;
//            int lineHeight = 20;
//            
//            g2.drawString("WorldX: " + player.worldX, x, y); y += lineHeight;
//            g2.drawString("WorldY: " + player.worldY, x, y); y += lineHeight;
//            g2.drawString("Col: " + (player.worldX + player.solidArea.x )/ tileSize, x, y); y += lineHeight;
//            g2.drawString("Row: " + (player.worldY + player.solidArea.y )/ tileSize, x, y);  y += lineHeight;
//            g2.drawString("Draw time: " + passed, x, y);
//            System.out.println("Draw Time: " + passed);
//        }
//        g2.dispose();
//    }
    
    public void changeArea()
    {
        if(nextArea != currentArea)
        {
            stopMusic();

            if(nextArea == outside)
            {
                playMusic(0);
            }
            if(nextArea == indoor)
            {
                playMusic(18);
            }
            if(nextArea == dungeon)
            {
                playMusic(19);
            }
            aSetter.setNPC(); //reset for at the dungeon puzzle's stuck rocks.
        }

        currentArea = nextArea;
        aSetter.setMonster();
    }
    
    public void resetGame(boolean restart){
        
        currentArea=outside;
        player.setDefaultPositions();
        player.restoreStatus();
        aSetter.setNPC();
        aSetter.setMonster();
        player.resetCounter();
        if(restart == true){
            player.setDefaultValues();
            
            aSetter.setObject();
            aSetter.setInteractiveTile();
            eManager.lighting.resetDay();
        }
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