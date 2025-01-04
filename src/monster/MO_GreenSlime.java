package monster;

import entity.Entity;
import main.GamePanel;

import java.util.Random;

public class MO_GreenSlime extends Entity {
    Random r = new Random();

    public MO_GreenSlime(GamePanel gp) {
        super(gp);
        name = "Green Slime";
        speed = 1;
        maxLife = 4;
        life = maxLife;

        solidArea.x = 3;
        solidArea.y = 18;
        solidArea.width = 42;
        solidArea.height = 30;
        solidAreaDefaultX = solidArea.x;
        solidAreaDefaultY = solidArea.y;
        collision = true;
        type = 2;
        getImage();
    }

    public void getImage(){
        up1 = setUp("/monster/greenslime_down_1");
        up2 = setUp("/monster/greenslime_down_2");
        down1 = setUp("/monster/greenslime_down_1");
        down2 = setUp("/monster/greenslime_down_2");
        left1 = setUp("/monster/greenslime_down_1");
        left2 = setUp("/monster/greenslime_down_2");
        right1 = setUp("/monster/greenslime_down_1");
        right2 = setUp("/monster/greenslime_down_2");
    }

    @Override
    public void setAction() {

        actionLockCounter++;
        if (actionLockCounter >= 90){
            actionLockCounter = 0;

            int i = r.nextInt(100)+1;
            if (i <= 25){
                direction = "up";
            } else if (i<= 50) {
                direction = "down";
            } else if (i <= 75) {
                direction = "right";
            }else {
                direction = "left";
            }
        }
    }


}
