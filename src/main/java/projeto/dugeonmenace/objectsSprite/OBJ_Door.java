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
public class OBJ_Door extends SuperObject {

    public OBJ_Door() {
        name = "Door";
        try {
            image = ImageIO.read(getClass().getResourceAsStream("/objectsSprite/door.png"));
        } catch (IOException e) {
            e.printStackTrace();
        }
        this.collision = true;
    }

}
