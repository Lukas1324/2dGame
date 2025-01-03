package object;

import entity.Entity;
import main.GamePanel;

import javax.imageio.ImageIO;
import java.io.IOException;

public class OBJ_Door extends Entity {

    public OBJ_Door(GamePanel gp){
        super(gp);
        name = "Door";
        down1 = setUp("/objects/door");
        collision = true;

        solidArea.x = 0;
        solidArea.y = 18;
        solidArea.height = 30;
        solidArea.width = 48;
        solidAreaDefaultX = solidArea.x;
        solidAreaDefaultY = solidArea.y;
    }
}
