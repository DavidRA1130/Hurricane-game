/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package hurricanegame;
import java.awt.*;

/**
 *
 * @author David
 * this class creates levels
 */
public class Room {
    public int worldWidth = 12;  //how big the block array will be
    public int worldHeight = 8;   //changes number of columns and rows
    public int blockSize = 52;
    
    public Block[][] block;
    
    public Room(){
        define();
    }
   public void define(){
       block = new Block[worldHeight][worldWidth];
       
       for(int y=0; y<block.length; y++ ){    //draw the block here
           for(int x=0; x<block[0].length; x++){
               block[y][x] = new Block((Screen.myWidth/2) - ((worldWidth*blockSize)/2) + (x * blockSize), y * blockSize, blockSize, blockSize, Value.groundGrass, Value.airAir);
           }
       }
   }
    public void physic() {
     for(int y=0;y<block.length;y++){
      for(int x=0;x<block[0].length;x++){
          block[y][x].physics();
      }   
     }
}
    
    
    public void draw(Graphics g){
        for(int y=0; y<block.length; y++){    //draw the block here too
           for(int x=0; x<block[0].length; x++){
               block[y][x].draw(g);
           }
       }
        
        for(int y=0; y<block.length; y++){    //draw the block here too
           for(int x=0; x<block[0].length; x++){
               block[y][x].fight(g);
           }
       }
        
    }
}
