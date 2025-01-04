package main;

import entity.Entity;
import entity.Player;
import tile.TileManager;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

public class GamePanel extends JPanel implements Runnable {
    final int originalTileSize = 16;
    final int scale = 3;

    public final int tileSize = originalTileSize * scale;
    public final int maxScreenCol = 16;
    public final int maxScreenRow = 12;
    public final int screenWidth = tileSize * maxScreenCol;
    public final int screenHeight = tileSize * maxScreenRow;

    //World Settings
    public final int maxWorldCol = 20;
    public final int maxWorldRow = 20;
    public final int worldWidth = maxWorldCol * tileSize;
    public final int worldHeight = maxWorldRow * tileSize;

    //PLAYSTATES

    public int pauseState = 0;
    public int playState = 1;
    public int diaglogState = 2;
    public int titleScreenState = 3;
    public int gameState;

    final int FPS = 60;

    public KeyHandler keyH = new KeyHandler(this);
    public EventHandler eventHandler = new EventHandler(this);
    Thread gameThread;
    TileManager tileManager = new TileManager(this);
    public Player player = new Player(this, keyH);

    //Entity and Object
    public Entity obj[] = new Entity[10];
    public Entity npc[] = new Entity[10];
    public Entity monster[] = new Entity[20];
    ArrayList<Entity> entityList = new ArrayList<>();
    public CollisionChecker collsionChecker = new CollisionChecker(this);
    public AssetSetter assetSetter = new AssetSetter(this);
    public UI ui = new UI(this);
    Sound soundEffect = new Sound();
    Sound music = new Sound();



    public GamePanel(){
        this.setPreferredSize(new Dimension(screenWidth, screenHeight));
        this.setBackground(Color.black);
        this.setDoubleBuffered(true);
        this.addKeyListener(keyH);
        this.setFocusable(true);
    }

    public void setUpGame(){
        assetSetter.setObject();
        assetSetter.setNPC();
        assetSetter.setMonster();
        //playMusic(0);
        gameState = titleScreenState;
    }

    public void startGameThread(){
        gameThread = new Thread(this);
        gameThread.start();
    }

    @Override
    public void run() {
        double drawInterval = 1000/FPS;
        double nextDrawTime = System.currentTimeMillis();
        while (gameThread != null){
            update();
            repaint();

            try {
                double remainingTime = nextDrawTime - System.currentTimeMillis();
                if (remainingTime < 0){
                    remainingTime = 0;
                }
                Thread.sleep((long) remainingTime);
                nextDrawTime += drawInterval;
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
    }

    public void update(){
        if (gameState == playState){
            //PLAYER
            player.update();

            //NPC
            for (int i = 0; i<npc.length;i++){
                if (npc[i] != null){
                    npc[i].update();
                }
            }
            //Monster
            for (int i = 0; i<monster.length;i++){
                if (monster[i] != null){
                    monster[i].update();
                }
            }
        } else if (gameState == pauseState) {

        }


    }

    public void paintComponent(Graphics g){
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D) g;

        if(gameState == titleScreenState){
            ui.draw(g2);
        }else{
            //DEBUG
            long drawStart = 0;
            if (keyH.checkDrawTime == true) {
                drawStart = System.nanoTime();
            }

            tileManager.draw(g2);

            //Add Entity and Objects in the list
            entityList.add(player);
            for(int i = 0; i < npc.length; i++){
                if (npc[i] != null){
                    entityList.add(npc[i]);
                }
            }
            for(int i = 0; i < obj.length; i++){
                if (obj[i] != null){
                    entityList.add(obj[i]);
                }
            }
            for(int i = 0; i < monster.length; i++){
                if (monster[i] != null){
                    entityList.add(monster[i]);
                }
            }

            //Sort
            Collections.sort(entityList, new Comparator<Entity>() {
                @Override
                public int compare(Entity o1, Entity o2) {
                    if(o1.worldY > o2.worldY){
                        return 1;
                    }else if (o1.worldY < o2.worldY){
                        return -1;
                    }else{
                        return 0;
                    }

                }
            });
            //Draw Entities
            for(int i = 0; i < entityList.size(); i++){
                entityList.get(i).draw(g2);
            }
            //Empty List
            entityList.clear();



            ui.draw(g2);

            //DEBUG
            if (keyH.checkDrawTime == true){
                long drawEnd = System.nanoTime();
                long passed = drawEnd - drawStart;
                g2.setColor(Color.WHITE);
                g2.drawString("Draw Time: "+ passed, 10, 400);
                System.out.println("Draw Time: "+ passed);
            }
        }


        g2.dispose();
    }

    public void playMusic(int i){
        music.setFile(i);
        music.play();
        music.loop();
    }

    public void stopMusic(){
        music.stop();
    }

    public void playSE(int i){
        soundEffect.setFile(i);
        soundEffect.play();
    }
}
