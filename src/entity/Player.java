package entity;

import main.GamePanel;
import main.KeyHandler;

import java.awt.*;
import java.awt.image.BufferedImage;

import static java.awt.Font.BOLD;


public class Player extends Entity{
    KeyHandler keyH;
    public final int screenX;
    public final int screenY;
    public int hasKeys = 0;
    public boolean attacking = false;

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
        getPlayerAttackImage();

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
        if(attacking == true){
            attacking();
        }
        else if (keyH.upPressed || keyH.downPressed || keyH.leftPressed || keyH.rightPressed || keyH.enterPressed) {
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

            int monsterIndex = gp.collsionChecker.checkEntity(this,gp.monster);
            contactMonster(monsterIndex);

            //Check Event
            gp.eventHandler.checkEvent();

            if (collisionOn == false && keyH.enterPressed == false) {
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
            keyH.enterPressed = false;
        }


        if(invincible == true){
            invincibleCounter++;
            if (invincibleCounter > 20){
                invincibleCounter = 0;
                invincible = false;
            }
        }

    }

    public void contactMonster(int i){
        if(i != 999){
            if(invincible == false){
                life -= 1;
                invincible = true;
            }
        }
    }

    public void interactNPC(int i){
        if (gp.keyH.enterPressed == true){
            if (i!= 999){
                gp.gameState = gp.diaglogState;
                gp.npc[i].speak();
            }else{
                attacking = true;
            }
        }
    }

    public void attacking(){
        spriteCounter++;
        if(spriteCounter <= 5){
            spriteNum = 1;
        }
        if(spriteCounter>5 && spriteCounter < 25){
            spriteNum = 2;
        }
        if(spriteCounter > 25){
            spriteNum = 1;
            spriteCounter = 0;
            attacking = false;
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

    public void getPlayerAttackImage(){
        attackUp1 = setUp("/player/boy_attack_up_1", gp.tileSize, gp.tileSize * 2);
        attackUp2 = setUp("/player/boy_attack_up_2", gp.tileSize, gp.tileSize * 2);
        attackDown1 = setUp("/player/boy_attack_down_1", gp.tileSize, gp.tileSize * 2);
        attackDown2 = setUp("/player/boy_attack_down_2", gp.tileSize, gp.tileSize * 2);
        attackRight1 = setUp("/player/boy_attack_right_1", gp.tileSize * 2, gp.tileSize);
        attackRight2 = setUp("/player/boy_attack_right_2", gp.tileSize * 2, gp.tileSize);
        attackLeft1 = setUp("/player/boy_attack_left_1", gp.tileSize * 2, gp.tileSize);
        attackLeft2 = setUp("/player/boy_attack_left_2", gp.tileSize * 2, gp.tileSize);
    }


    public void draw(Graphics2D g2){
        BufferedImage image = null;
        int tempScreenX = screenX;
        int tempScreenY = screenY;
        switch (direction){
            case "up":
                if(attacking == true){
                    tempScreenY = screenY - gp.tileSize;
                    if (spriteNum == 1){image = attackUp1;}
                    if (spriteNum == 2){image = attackUp2;}
                }else {
                    if (spriteNum == 1) {image = up1;}
                    if (spriteNum == 2) {image = up2;}
                }

                break;
            case "down":
                if(attacking == true){
                    if (spriteNum == 1){image = attackDown1;}
                    if (spriteNum == 2){image = attackDown2;}
                }else {
                    if (spriteNum == 1) {image = down1;}
                    if (spriteNum == 2) {image = down2;}
                }

                break;
            case "right":
                if(attacking == true){
                    if (spriteNum == 1){image = attackRight1;}
                    if (spriteNum == 2){image = attackRight2;}
                }else {
                    if (spriteNum == 1) {image = right1;}
                    if (spriteNum == 2) {image = right2;}
                }

                break;
            case "left":
                if(attacking == true){
                    tempScreenX = screenX - gp.tileSize;
                    if (spriteNum == 1){image = attackLeft1;}
                    if (spriteNum == 2){image = attackLeft2;}
                }else {
                    if (spriteNum == 1) {image = left1;}
                    if (spriteNum == 2) {image = left2;}
                }

                break;
        }
        if(invincible == true){
            g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 0.3f));
        }
        g2.drawImage(image, tempScreenX, tempScreenY, null);

        //Reset Composite
        g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 1f));
    }
}
