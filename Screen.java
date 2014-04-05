/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package hurricanegame;
import java.awt.*;
import javax.swing.*;
import java.awt.image.*;
import java.io.*;

/**
 *
 * @author David & Alexis
 */
public class Screen extends JPanel implements Runnable{
   public Thread thread = new Thread(this);
  
   public static Image[] tileset_ground = new Image[100];
   public static Image[] tileset_air = new Image[100];
   public static Image[] tileset_res = new Image[100];
   public static Image[] tileset_enemy = new Image[100];
  
    
   public static int myWidth, myHeight;
   public static int money = 100, health = 100;
   public static int killed = 0, killsToWin = 0, level = 1;
   
   public static boolean isFirst = true;
   public static boolean isDebug = true;
   public static boolean isWin = false;
   
   public static Point mse = new Point(0, 0); //allows the mouse
        
   public static Room room;
   public static Save save;
   public static Store store;
   
   public static Enemy[] enemies = new Enemy[100];
        
   public Screen(Frame frame) {
        frame.addMouseListener(new KeyHandle());
        frame.addMouseMotionListener(new KeyHandle());
       
       thread.start();
        
    }
   public static void hasWon(){
       if(killed == killsToWin){
           isWin = true;
           killed = 0;
           level += 1;
       }
   }
    
    public void define() {
        room = new Room();
        save = new Save();
        store = new Store();
        
        
        
        for(int i=0;i<tileset_ground.length; i++){
            java.net.URL imageURL = this.getClass().getClassLoader().getResource("res/tileset_ground.png");
            tileset_ground[i] = new ImageIcon("res/tileset_ground.png").getImage();
            tileset_ground[i] = createImage(new FilteredImageSource(tileset_ground[i].getSource(), new CropImageFilter(0, 26*i, 26, 26)));           //gets image and crops it
        }
        for(int i=0;i<tileset_air.length; i++){
            java.net.URL imageURL = this.getClass().getClassLoader().getResource("res/tileset_air.png");
            tileset_air[i] = new ImageIcon("res/tileset_air.png").getImage();
            tileset_air[i] = createImage(new FilteredImageSource(tileset_air[i].getSource(), new CropImageFilter(0, 26*i, 26, 26)));           //gets image and crops it
        }
        
        java.net.URL imageURL = this.getClass().getClassLoader().getResource("res/heart.png");
        tileset_res[0] = new ImageIcon("res/heart.png").getImage();
        //java.net.URL imageURL = this.getClass().getClassLoader().getResource("res/coin.png");
        tileset_res[1] = new ImageIcon("res/coin.png").getImage();
        tileset_enemy[0] = new ImageIcon("res/enemy.png").getImage();
        
        save.loadSave(new File("save/save" + level + ".ulixava"));
        
        for (int i=0;i<enemies.length;i++){
            enemies[i] = new Enemy();
            
        }
    }
    public void paintComponent(Graphics g) {
       if(isFirst) {
           myWidth = getWidth();
           myHeight = getHeight();
           define();
           isFirst = false;
       }
        g.setColor(new Color(50, 50, 50));
        g.fillRect(0, 0, getWidth(), getHeight()); //sets small border around actual game window
        g.setColor(new Color(0, 0, 0));
        g.drawLine(room.block[0][0].x-1, 0, room.block[0][0].x-1, room.block[room.worldHeight-1][0].y + room.blockSize); //draws left border
        g.drawLine(room.block[0][0].x-1, 0, room.block[0][room.worldWidth - 1].x+ room.blockSize, room.block[room.worldHeight-1][0].y + room.blockSize); //draws right border
        
        room.draw(g); //Drawing the room
        
        for(int i=0;i<enemies.length;i++){
            if(enemies[i].inGame){
                enemies[i].draw(g);
            }
        }
        
        
        store.draw(g); //draws the store box
        
        if(health < 1){
            g.setColor(new Color(240, 20, 20));
            g.fillRect(0, 0, myWidth, myHeight);
            g.setColor(new Color(255, 255, 255));
            g.setFont(new Font("Courier New", Font.BOLD, 14));
            g.drawString("Game Over", 10, 20);
            
        }
        if(isWin){
            g.setColor(new Color(0,0,255));
            g.fillRect(0, 0, getWidth(), getHeight());
            g.setColor(new Color(255, 255, 255));
            g.setFont(new Font("Courier New", Font.BOLD, 14));
            g.drawString("Victory!... For now!!", 10, 20);
        }
    }
    public int spawnTime = 1600, spawnFrame = 0;  //increase spawnTime to change distance between enemies
    public void enemySpawner(){
        if(spawnFrame >= spawnTime){
            for(int i=0;i<enemies.length;i++){
                if(!enemies[i].inGame){
                    enemies[i].spawnEnemy(Value.enemyWater);
                    break;
                }
            }
            spawnFrame = 0;
        }else{
            spawnFrame += 1;
        }
    }
    
    public void run() {
        while(true){
            if(!isFirst && health > 0 && !isWin){
                room.physic();
                enemySpawner();
                for(int i=0;i<enemies.length;i++){
                    if(enemies[i].inGame){
                        enemies[i].physic();
                    }
                }
                
            }
           repaint();
           
           try{
           Thread.sleep(1);
           } catch(Exception e) {}
        }
   }
}


