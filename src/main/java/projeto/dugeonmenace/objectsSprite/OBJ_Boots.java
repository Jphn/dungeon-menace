/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package projeto.dugeonmenace.objectsSprite;

import java.io.IOException;
import javax.imageio.ImageIO;
import projeto.dugeonmenace.GamePanel;

/**
 *
 * @author LucianoNeto
 */
public class OBJ_Boots extends SuperObject {

    GamePanel gp;

    public OBJ_Boots(GamePanel gp) {
        this.gp = gp;
        name = "Boots";
        try {
            image = ImageIO.read(getClass().getResourceAsStream("/objectsSprite/boots.png"));
            image = uTool.scaleImage(image, gp.tileSize, gp.tileSize);
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

}
