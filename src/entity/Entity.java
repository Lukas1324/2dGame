package entity;

import main.GamePanel;
import main.UtilityTool;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;

public class Entity {
    public int worldX, worldY;
    public int speed;
    GamePanel gp;
    String[] dialog = new String[30];
    int dialogeIndex = 0;

    public BufferedImage up1, up2, down1, down2, left1, left2, right1, right2;
    public String direction;

    public int spriteCounter = 0;
    public int spriteNum = 1;
    public Rectangle solidArea = new Rectangle(0,0,48,48);
    public int solidAreaDefaultX, solidAreaDefaultY;
    public boolean collisionOn = false;

    //Character Status
    public int maxLife;
    public int life;

    public Entity(GamePanel gp){
        this.gp = gp;
    }

    public void setAction(){

    }

    public void speak(){
        gp.ui.currentDialog = dialog[dialogeIndex];
        dialogeIndex++;
        if (dialog[dialogeIndex] == null){
            dialogeIndex = 0;
        }

        switch (gp.player.direction){
            case "up": direction = "down"; break;
            case "down":direction = "up"; break;
            case"left":direction = "right"; break;
            case "right":direction = "left"; break;
        }
    }

    public void update(){
        setAction();

        collisionOn = false;
        gp.collsionChecker.checkTile(this);
        gp.collsionChecker.checkObject(this, false);
        gp.collsionChecker.checkPlayer(this);

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

    public void draw(Graphics2D g2){
        BufferedImage image = null;
        int screenX = worldX - gp.player.worldX + gp.player.screenX;
        int screenY = worldY - gp.player.worldY + gp.player.screenY;


        //Rendering, wird nur gezeichnet wenn es auf dem Screen vom Spieler ist +/- Tilesize
        if (worldX > gp.player.worldX - gp.player.screenX - gp.tileSize &&
                worldX < gp.player.worldX + gp.player.screenX + gp.tileSize&&
                worldY > gp.player.worldY - gp.player.screenY - gp.tileSize&&
                worldY < gp.player.worldY + gp.player.screenY + gp.tileSize){

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
            g2.drawImage(image,screenX,screenY,gp.tileSize, gp.tileSize,null);
        }
    }

    public BufferedImage setUp(String imagePath){
        UtilityTool uTool = new UtilityTool();
        BufferedImage image = null;
        try{
            image = ImageIO.read(getClass().getResourceAsStream(imagePath + ".png"));
            image = uTool.scaleImage(image,gp.tileSize,gp.tileSize);
        }catch (IOException e){
            e.printStackTrace();
        }
        return image;
    }
}
