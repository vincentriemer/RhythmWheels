/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package rhythmwheels;

import java.awt.*;
import java.util.HashMap;

/**
 *
 * @author Vincent
 */
public class Icon {

    public static HashMap<String, Icon> installedIcons =
            new HashMap<String, Icon>();
    public Image icon;

    public Icon(Image imageFile) {
        icon = imageFile;
    }
}
