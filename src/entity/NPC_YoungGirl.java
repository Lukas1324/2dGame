package entity;

import main.GamePanel;

import java.util.Random;

public class NPC_YoungGirl extends Entity{
    int actionLockCounter = 0;
    Random r = new Random();
    public NPC_YoungGirl(GamePanel gp){
        super(gp);

        direction = "down";
        speed = 2;
        getImage();
    }

    public void getImage(){
        up1 = setUp("/NPC/YoungGirl/down_01");
        up2 = setUp("/NPC/YoungGirl/up_02");
        down1 = setUp("/NPC/YoungGirl/down_01");
        down2 = setUp("/NPC/YoungGirl/down_02");
        left1 = setUp("/NPC/YoungGirl/left_01");
        left2 = setUp("/NPC/YoungGirl/left_02");
        right1 = setUp("/NPC/YoungGirl/right_01");
        right2 = setUp("/NPC/YoungGirl/right_02");
    }

    public void setAction(){

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
