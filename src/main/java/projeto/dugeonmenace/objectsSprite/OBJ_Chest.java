/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package projeto.dugeonmenace.objectsSprite;

import java.io.IOException;
import javax.imageio.ImageIO;

/**
 *
 * @author LucianoNeto
 */
public class OBJ_Chest extends SuperObject {

    public OBJ_Chest() {
        name = "Chest";
        try {
            image = ImageIO.read(getClass().getResourceAsStream("/objectsSprite/chest.png"));
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}
