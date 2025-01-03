package main;

import entity.Entity;
import object.OBJ_Hearth;
import object.OBJ_Key;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.text.DecimalFormat;

public class UI {
    GamePanel gp;
    Font arial_40, arial_80B;
    BufferedImage keyImage;
    public boolean messageOn = false;
    public String message = "";
    int messageCounter = 0;
    public boolean gameFinished = false;
    double playTime;
    BufferedImage heart_full, heart_half, heart_void;

    //Title Screen
    public int numTitelScreen = 0;
    public int subTitleScreenState = 0; // 0 ist firstScreen, 1 ist secondScreen
    DecimalFormat decimalFormat = new DecimalFormat("#0.00");
    Graphics2D g2;
    public String currentDialog = "";


    public UI(GamePanel gp){
        this.gp = gp;

        arial_40 = new Font("Arial", Font.PLAIN, 40);
        arial_80B = new Font("Arial",Font.BOLD,80);
        OBJ_Key key = new OBJ_Key(gp);
        keyImage = key.image;

        //Create HUD Object
        Entity heart = new OBJ_Hearth(gp);
        heart_full = heart.image;
        heart_half = heart.image2;
        heart_void = heart.image3;
    }

    public void showMessage(String text){
        message = text;
        messageOn = true;
    }

    public void draw(Graphics2D g2){
        this.g2 = g2;
        if (gameFinished == true){

            g2.setFont(arial_40);
            g2.setColor(Color.WHITE);

            String text;
            int textLenght;
            int x;
            int y;

            text = "You found the treasure!";
            textLenght = (int) g2.getFontMetrics().getStringBounds(text, g2).getWidth();
            x = gp.screenWidth/2 - textLenght/2;
            y = gp.screenHeight/2 - gp.tileSize * 1;
            g2.drawString(text,x,y);

            g2.setFont(arial_80B);
            g2.setColor(Color.MAGENTA);
            text = "Congratulations!";
            textLenght = (int) g2.getFontMetrics().getStringBounds(text, g2).getWidth();
            x = gp.screenWidth/2 - textLenght/2;
            y = gp.screenHeight/2 - gp.tileSize * 3;
            g2.drawString(text,x,y);

            //Print Playtime
            g2.setColor(Color.cyan);
            g2.setFont(g2.getFont().deriveFont(20F));
            g2.drawString("PlayTime: " + decimalFormat.format(playTime), gp.tileSize * 12, gp.tileSize * 11 );

            gp.gameThread = null;

        }else{
            if (gp.gameState == gp.playState){
                drawPlayerHearts();

                //Playtime
                playTime += (double)1/60;
            } else if (gp.gameState == gp.pauseState) {
                drawPausedGameUI();
            } else if (gp.gameState == gp.diaglogState) {
                drawDiaglogScreen();
            }else if(gp.gameState == gp.titleScreenState){
                drawTitelScreen();
            }






            //Message
            if (messageOn == true){
                g2.setFont(g2.getFont().deriveFont(30F));
                g2.drawString(message,gp.tileSize/2,gp.tileSize * 5);

                messageCounter++;
                if (messageCounter >= 90){
                    messageCounter = 0;
                    messageOn = false;
                }
            }
        }

    }

    public void drawPlayerHearts(){

        int x = gp.tileSize;
        int y = gp.tileSize;
        for(int i = 0; i < (gp.player.maxLife/2); i++){
            g2.drawImage(heart_void, x, y, null);
            x += gp.tileSize;
        }

        x = gp.tileSize;
        y = gp.tileSize;
        for(int i = 0; i < (gp.player.life/2); i++){
            g2.drawImage(heart_full,x,y,null);
            x += gp.tileSize;
        }
        if(gp.player.life % 2 == 1){
            g2.drawImage(heart_half,x,y,null);
        }
    }

    public void drawTitelScreen(){

        if(subTitleScreenState == 0){
            //Hintergrund
            g2.setColor(Color.gray);
            g2.fillRect(0,0,gp.screenWidth, gp.worldHeight);
            g2.setFont(g2.getFont().deriveFont(60F));

            String text = "Das ist der Spielename";
            int x = getMiddelXOfText(text);
            int y = gp.tileSize * 3;


            //Draw Shaddow
            g2.setColor(Color.BLACK);
            g2.drawString(text,x + 5,y);

            //Draw Title
            g2.setColor(Color.red);
            g2.drawString(text,x,y);


            //Optionen
            g2.setFont(g2.getFont().deriveFont(35F));
            g2.setColor(Color.black);

            text = "Spiel starten";
            x = getMiddelXOfText(text);
            y += gp.tileSize * 3;
            g2.drawString(text,x,y);
            if(numTitelScreen == 0){
                g2.drawString(">", x-gp.tileSize,y);
            }

            text = "Spiel laden";
            x = getMiddelXOfText(text);
            y += gp.tileSize;
            g2.drawString(text,x,y);
            if(numTitelScreen == 1){
                g2.drawString(">", x-gp.tileSize,y);
            }

            text = "Beenden";
            x = getMiddelXOfText(text);
            y += gp.tileSize;
            g2.drawString(text,x,y);
            if(numTitelScreen == 2){
                g2.drawString(">", x-gp.tileSize,y);
            }
        } else if (subTitleScreenState == 1) {
            //Draw Second TitleScreen
            //Hintergrund
            g2.setColor(Color.gray);
            g2.fillRect(0,0,gp.screenWidth, gp.worldHeight);
            g2.setFont(g2.getFont().deriveFont(60F));

            String text = "Klassenauswahl";
            int x = getMiddelXOfText(text);
            int y = gp.tileSize * 3;
            g2.setColor(Color.ORANGE);
            g2.drawString(text,x,y);


            g2.setFont(g2.getFont().deriveFont(35F));
            g2.setColor(Color.black);
            text = "Ork";
            x = getMiddelXOfText(text);
            y += gp.tileSize * 3;
            g2.drawString(text,x,y);
            if(numTitelScreen == 0){
                g2.drawString(">", x-gp.tileSize,y);
            }

            text = "Bogenschütze";
            x = getMiddelXOfText(text);
            y += gp.tileSize;
            g2.drawString(text,x,y);
            if(numTitelScreen == 1){
                g2.drawString(">", x-gp.tileSize,y);
            }

            text = "Schwertkämpfer";
            x = getMiddelXOfText(text);
            y += gp.tileSize;
            g2.drawString(text,x,y);
            if(numTitelScreen == 2){
                g2.drawString(">", x-gp.tileSize,y);
            }

            text = "Lanzenträger";
            x = getMiddelXOfText(text);
            y += gp.tileSize;
            g2.drawString(text,x,y);
            if(numTitelScreen == 3){
                g2.drawString(">", x-gp.tileSize,y);
            }

            text = "Zurück";
            x = getMiddelXOfText(text);
            y += gp.tileSize *2;
            g2.drawString(text,x,y);
            if(numTitelScreen == 4){
                g2.drawString(">", x-gp.tileSize,y);
            }


        }
    }

    public void drawDiaglogScreen(){
        //Window
        int x = gp.tileSize * 2;
        int y = gp.tileSize/2;
        int height = gp.tileSize * 4;
        int width = gp.screenWidth - gp.tileSize*4;
        drawSubWindow(x,y,width,height);

        x += gp.tileSize;
        y += gp.tileSize;
        g2.setFont(g2.getFont().deriveFont(Font.BOLD, 25F));

        for(String line :currentDialog.split("\n")){
            g2.drawString(line,x,y);
            y += 25;
        }
    }
    public void drawSubWindow(int x, int y, int width, int height){
        Color c = new Color(0,0,0,220);
        g2.setColor(c);
        g2.fillRoundRect(x,y,width,height,40,40);

        c = new Color( 255,255,255);
        g2.setStroke(new BasicStroke(5));
        g2.setColor(c);
        g2.drawRoundRect(x,y,width,height,40,40);
    }
    private void drawPausedGameUI(){
        String pauseText = "Paused";
        g2.setFont(g2.getFont().deriveFont(80F));
        int x = getMiddelXOfText(pauseText);
        int y = gp.screenHeight/2;
        g2.setColor(Color.WHITE);
        g2.drawString(pauseText,x,y);
    }

    private int getMiddelXOfText(String text){
        int textLenght = (int) g2.getFontMetrics().getStringBounds(text, g2).getWidth();
        int x = (gp.screenWidth/2) - (textLenght/2);
        return x;
    }
}
