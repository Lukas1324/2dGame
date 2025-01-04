package object;

import entity.Entity;
import main.GamePanel;



public class OBJ_Hearth extends Entity {
    public OBJ_Hearth(GamePanel gp){
        super(gp);
        name = "Hearth";
        image = setUp("/objects/heart_full");
        image2 = setUp("/objects/heart_half");
        image3 = setUp("/objects/heart_blank");


    }
}
