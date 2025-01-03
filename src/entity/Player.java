package entity;

import main.GamePanel;
import main.KeyHandler;

import java.awt.*;
import java.awt.image.BufferedImage;


public class Player extends Entity{
    KeyHandler keyH;
    public final int screenX;
    public final int screenY;
    public int hasKeys = 0;

    public Player(GamePanel gp, KeyHandler keyH){
        super(gp);
        this.keyH = keyH;

        screenX = gp.screenWidth/2 - gp.tileSize/2;
        screenY = gp.screenHeight/2 - gp.tileSize/2;

        solidArea = new Rectangle();
        solidArea.x = 8;
        solidArea.y = 16;
        solidAreaDefaultX = solidArea.x;
        solidAreaDefaultY = solidArea.y;
        solidArea.height = 32;
        solidArea.width = 32;

        setDefaultValues();
        getPlayerImage();

    }

    public void setDefaultValues(){
        worldX = gp.tileSize * 15;
        worldY = gp.tileSize * 15;
        speed = 4;
        direction = "down";

        //Player Status
        maxLife = 8;
        life = maxLife;
    }

    public void update(){
        if (keyH.upPressed || keyH.downPressed || keyH.leftPressed || keyH.rightPressed) {
            if (keyH.upPressed) {
                direction = "up";
            } else if (keyH.downPressed) {
                direction = "down";
            } else if (keyH.rightPressed) {
                direction = "right";
            } else if (keyH.leftPressed) {
                direction = "left";
            }

                //Check Collision
                collisionOn = false;
                gp.collsionChecker.checkTile(this);


                //If Collsion is False, Player can Move

            //Check Object Collsion
            int objIndex = gp.collsionChecker.checkObject(this,true);
            pickUpObject(objIndex);

            //Check NPC Collsion
            int npcIndex = gp.collsionChecker.checkEntity(this,gp.npc);
            interactNPC(npcIndex);

            //Check Event
            gp.eventHandler.checkEvent();

            if (collisionOn == false) {
                switch (direction) {
                    case "up":
                        worldY -= speed;
                        break;
                    case "down":
                        worldY += speed;
                        break;
                    case "left":
                        worldX -= speed;
                        break;
                    case "right":
                        worldX += speed;
                        break;
                }


                spriteCounter++;
                if (spriteCounter > 10) {
                    if (spriteNum == 1) {
                        spriteNum = 2;
                    } else if (spriteNum == 2) {
                        spriteNum = 1;
                    }

                    spriteCounter = 0;
                }
            }
        }




    }

    public void interactNPC(int i){
        if (i!= 999){
            if (gp.keyH.enterPressed == true){
                gp.gameState = gp.diaglogState;
                gp.npc[i].speak();
            }
        }
    }

    public void pickUpObject(int objIndex){
        if (objIndex != 999){
            String objName = gp.obj[objIndex].name;
            switch (objName){
                case "Key":
                    hasKeys++;
                    gp.obj[objIndex] = null;
                    gp.ui.showMessage("You got a key!");
                    break;
                case "Door":
                    if (hasKeys>0){
                        gp.obj[objIndex] = null;
                        hasKeys--;
                        gp.ui.showMessage("You opened the door!");
                    }else {
                        gp.ui.showMessage("You need a key!");
                    }

                    break;
                case "Chest":
                    gp.ui.gameFinished = true;
                    gp.stopMusic();
                    gp.playSE(1);
                    break;
                case "Boots":
                    gp.ui.showMessage("You got Boots and are now faster!");
                    gp.player.speed += 2;
                    gp.obj[objIndex] = null;
                    gp.playSE(1);
                    break;
            }
        }
    }

    public void getPlayerImage(){

        up1 = setUp("/player/boy_up_1");
        up2 = setUp("/player/boy_up_2");
        down1 = setUp("/player/boy_down_1");
        down2 = setUp("/player/boy_down_2");
        left1 = setUp("/player/boy_left_1");
        left2 = setUp("/player/boy_left_2");
        right1 = setUp("/player/boy_right_1");
        right2 = setUp("/player/boy_right_2");
    }


    public void draw(Graphics2D g2){
        /*
        g2.setColor(Color.white);
        g2.fillRect(x,y,gp.tileSize,gp.tileSize);
         */

        BufferedImage image = null;
        switch (direction){
            case "up":
                if (spriteNum == 1){
                    image = up1;
                }else if(spriteNum == 2) {
                    image = up2;
                }

                break;
            case "down":
                if (spriteNum == 1){
                    image = down1;
                } else if (spriteNum == 2) {
                    image = down2;
                }

                break;
            case "right":
                if (spriteNum == 1){
                    image = right1;
                }else if (spriteNum == 2) {
                    image = right2;
                }

                break;
            case "left":
                if (spriteNum == 1){
                    image = left1;
                }else if (spriteNum == 2) {
                    image = left2;
                }

                break;
        }


        g2.drawImage(image, screenX, screenY,gp.tileSize, gp.tileSize, null);

        /*
        g2.setColor(Color.red);
        g2.drawRect(this.screenX - (speed/2) + solidArea.x, this.screenY - speed/2 + solidArea.y, gp.player.solidArea.width + gp.player.speed/2, gp.player.solidArea.height + gp.player.speed/2);

        g2.setColor(Color.black);
        g2.drawRect(screenX,screenY,10,10);

         */
    }
}
