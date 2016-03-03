package model;



import static model.GameConstants.GAME_AREA_HEIGHT;
import static model.GameConstants.GAME_AREA_WIDTH;

//Class to handle action for AUTO event
public class AutoEvent {
	
	//method to free move the sprite object 
	public void FreeMove(Sprite spriteObject){
		int x = spriteObject.getX();
		int y = spriteObject.getY();
		
	    spriteObject.setX(x += spriteObject.getXdir());
	    spriteObject.setY(y += spriteObject.getYdir());

	    if (x == 0 && spriteObject.getLeft() == "FRAME_LEFT") {
         spriteObject.setXdir(1); // left
      	}
        
        if (x == GAME_AREA_WIDTH && spriteObject.getRight() == "FRAME_RIGHT") {
                 spriteObject.setXdir(-1); //right
         }

        if (y == 0 && spriteObject.getTop() == "FRAME_TOP") {
          
            spriteObject.setYdir(1);   //top
        }	
        
        if(y== GAME_AREA_HEIGHT && spriteObject.getBottom() == "FRAME_BOTTOM")
        {
        	spriteObject.setXdir(1);
        	spriteObject.setYdir(-1);
        	
        }
	}
	

}