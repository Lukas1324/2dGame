package object;

import entity.Entity;
import main.GamePanel;

import javax.imageio.ImageIO;
import java.io.IOException;

public class OBJ_Hearth extends Entity {
    public OBJ_Hearth(GamePanel gp){
        super(gp);
        name = "Hearth";
        image = setUp("/objects/heart_full");
        image2 = setUp("/objects/heart_half");
        image3 = setUp("/objects/heart_blank");


    }
}
