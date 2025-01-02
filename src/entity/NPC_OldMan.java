package entity;

import main.GamePanel;

import java.util.Random;

public class NPC_OldMan extends Entity{
    int actionLockCounter = 0;
    Random r = new Random();
    public NPC_OldMan(GamePanel gp){
        super(gp);

        direction = "down";
        speed = 2;
        getOldManImage();
        solidArea.x = 4;
        solidArea.y = 8;
        solidArea.height = 40;
        solidArea.width = 40;

        setDialog();
    }

    public void setDialog(){
        dialog[0] = "Hello young fella!";
        dialog[1] = "Wanna play a round Fortnite";
        dialog[2] = "I crank the 90tis";
        dialog[3] = "You wouldn't know!";

    }

    public void speak(){
        super.speak();
    }

    public void getOldManImage(){
        up1 = setUp("/NPC/oldman_up_1");
        up2 = setUp("/NPC/oldman_up_2");
        down1 = setUp("/NPC/oldman_down_1");
        down2 = setUp("/NPC/oldman_down_2");
        left1 = setUp("/NPC/oldman_left_1");
        left2 = setUp("/NPC/oldman_left_2");
        right1 = setUp("/NPC/oldman_right_1");
        right2 = setUp("/NPC/oldman_right_2");
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
