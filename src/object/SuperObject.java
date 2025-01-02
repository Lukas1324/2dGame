package object;

import main.GamePanel;
import main.UtilityTool;
import org.w3c.dom.css.Rect;

import java.awt.*;
import java.awt.image.BufferedImage;

public class SuperObject {
    public BufferedImage image, image2, image3;
    public String name;
    public boolean collision = false;
    public int worldX, worldY;
    public Rectangle solidArea = new Rectangle(0,0,48,48);
    public int solidAreaDefaultX = 0;
    public int solidAreaDefaultY= 0;
    UtilityTool uTool = new UtilityTool();

    public void draw(Graphics2D g2, GamePanel gp){
        int screenX = worldX - gp.player.worldX + gp.player.screenX;
        int screenY = worldY - gp.player.worldY + gp.player.screenY;


        //Rendering, wird nur gezeichnet wenn es auf dem Screen vom Spieler ist +/- Tilesize
        if (worldX > gp.player.worldX - gp.player.screenX - gp.tileSize &&
                worldX < gp.player.worldX + gp.player.screenX + gp.tileSize&&
                worldY > gp.player.worldY - gp.player.screenY - gp.tileSize&&
                worldY < gp.player.worldY + gp.player.screenY + gp.tileSize){

            g2.drawImage(image,screenX,screenY,gp.tileSize, gp.tileSize,null);
        }
    }
}
