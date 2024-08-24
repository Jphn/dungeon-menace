/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package projeto.dugeonmenace.tile;

import java.awt.Graphics2D;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import javax.imageio.ImageIO;
import projeto.dugeonmenace.GamePanel;

/**
 *
 * @author LucianoNeto
 */
public class TileManager {

    GamePanel gp;
    public Tile[] tile;
    public int mapTileNumber[][];

    public TileManager(GamePanel gp) {
        this.gp = gp;
        tile = new Tile[10];
        getTileImage();
        this.mapTileNumber = new int[gp.maxWorldCol][gp.maxWorldRow]; // esse array vai guardar a informação contida nos mapas
        loadMap("/maps/world01.txt");
    }

    public void getTileImage() {

        try {
            /**
             * da para adicionar a colisão pelo construtor
             */

            tile[0] = new Tile();
            tile[0].image = ImageIO.read(getClass().getResourceAsStream("/tiles/grass.png"));

            tile[1] = new Tile();
            tile[1].image = ImageIO.read(getClass().getResourceAsStream("/tiles/wall.png"));
            tile[1].collision = true;

            tile[2] = new Tile();
            tile[2].image = ImageIO.read(getClass().getResourceAsStream("/tiles/water.png"));
            tile[2].collision = true;

            tile[3] = new Tile();
            tile[3].image = ImageIO.read(getClass().getResourceAsStream("/tiles/earth.png"));

            tile[4] = new Tile();
            tile[4].image = ImageIO.read(getClass().getResourceAsStream("/tiles/tree.png"));
            tile[4].collision = true;

            tile[5] = new Tile();
            tile[5].image = ImageIO.read(getClass().getResourceAsStream("/tiles/sand.png"));

        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public void loadMap(String mapFilePath) {
        try {
            InputStream is = getClass().getResourceAsStream(mapFilePath);
            BufferedReader br = new BufferedReader(new InputStreamReader(is));
            // com o "is" vamos importar esse arquivo para dentro da variável br
            // "br" vai ler o conteúdo do arquivo
            // esse é o formato para ler esse arq de texto

            int col = 0;
            int row = 0;
            while (col < gp.maxWorldCol && row < gp.maxWorldRow) {
                String line = br.readLine();
                while (col < gp.maxWorldCol) {
                    String numbers[] = line.split(" "); // dessa forma estou separando a line
                    //por valores entre " " comentário: pqp  que bagulho legal

                    int num = Integer.parseInt(numbers[col]); // convertendo string para int

                    this.mapTileNumber[col][row] = num;
                    col++;

                }
                if (col == gp.maxWorldCol) {
                    col = 0;
                    row++;

                }

            }
            br.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void draw(Graphics2D g2) {

        int worldCol = 0;
        int worldRow = 0;

        //g2.drawImage(tile[0].image, 0, 0, gp.tileSize, gp.tileSize, null);
        while (worldCol < gp.maxWorldCol && worldRow < gp.maxWorldRow) {

            int tileNum = mapTileNumber[worldCol][worldRow];

            int worldX = worldCol * gp.tileSize;
            int worldY = worldRow * gp.tileSize;

            /**
             * Essa parte do código aparentemente serve pra corrigir a posição
             * do player com relação a posição da tela no mundo Video aula: 5
             *
             * worldY - gp.player.worldY representa basicamente a posição do
             * tile que precisa ser desenhado na tela
             *
             */
            int screenX = worldX - gp.player.worldX + gp.player.screenX;
            int screenY = worldY - gp.player.worldY + gp.player.screenY;

            /**
             * Oque esse if significa é que se o tile tiver nessa região ele
             * será pintado
             */
            if (worldX + gp.tileSize > gp.player.worldX - gp.player.screenX
                    && worldX - gp.tileSize < gp.player.worldX + gp.player.screenX
                    && worldY + gp.tileSize > gp.player.worldY - gp.player.screenY
                    && worldY - gp.tileSize < gp.player.worldY + gp.player.screenY) {

                g2.drawImage(tile[tileNum].image, screenX, screenY, gp.tileSize, gp.tileSize, null);

            }
            worldCol++;
            if (worldCol == gp.maxWorldCol) {
                worldCol = 0;

                worldRow++;

            }

        }

    }
}
