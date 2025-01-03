package main;

import java.awt.*;

public class EventHandler {

    GamePanel gp;
    Rectangle eventRect;
    int eventRectDefaultX, eventRectDefaultY;
    int lastEventCol, lastEventRow;
    boolean  canDoEvent = true;

    public EventHandler(GamePanel gp){
        this.gp = gp;
        eventRect = new Rectangle();
        eventRect.x = 23;
        eventRect.y = 23;
        eventRect.width = 2;
        eventRect.height = 2;
        eventRectDefaultX = eventRect.x;
        eventRectDefaultY = eventRect.y;
    }

    public void checkEvent(){
        if(hit(16,14,"any")){damagePit(gp.diaglogState);}
        if(hit(16,16, "any")){healingPit(gp.diaglogState);}
    }
    public boolean checkIfCanDoEvent(){
        int diffX = Math.abs(gp.player.worldX - lastEventCol * gp.tileSize);
        int diffY = Math.abs(gp.player.worldY - lastEventRow * gp.tileSize);
        if(Math.max(diffX,diffY) > gp.tileSize){
            return true;
        }else{
            return false;
        }
    }

    public boolean hit(int eventCol, int eventRow, String reqDirection){
        boolean hit = false;
        if(canDoEvent == false){
            canDoEvent = checkIfCanDoEvent();
        }
        gp.player.solidArea.x = gp.player.worldX + gp.player.solidArea.x;
        gp.player.solidArea.y = gp.player.worldY + gp.player.solidArea.y;
        eventRect.x = eventCol* gp.tileSize + eventRect.x;
        eventRect.y = eventRow* gp.tileSize + eventRect.y;

        if(gp.player.solidArea.intersects(eventRect)) {
            if(canDoEvent == true) {
                if (gp.player.direction.contentEquals(reqDirection) || reqDirection.contentEquals("any")) {
                    lastEventCol = eventCol;
                    lastEventRow = eventRow;
                    hit = true;
                    canDoEvent = false;
                }
            }
        }
        gp.player.solidArea.x = gp.player.solidAreaDefaultX;
        gp.player.solidArea.y = gp.player.solidAreaDefaultY;
        eventRect.x = eventRectDefaultX;
        eventRect.y = eventRectDefaultY;


        return hit;
    }

    public void damagePit(int gameState){
        gp.gameState = gameState;
        gp.ui.currentDialog = "You have been taking damage!";
        gp.player.life -= 1;
    }

    public void healingPit(int gameState){
        gp.gameState = gameState;
        gp.ui.currentDialog = "You have been healed";
        gp.player.life = gp.player.maxLife;
        //canDoEvent = true;
    }
}
