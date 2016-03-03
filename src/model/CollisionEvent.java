package model;

import java.io.IOException;

import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;

import model.GameConstants.SoundMode;

//Class to handle action for Collision event

public class CollisionEvent {
	
	//play sound when there is collision between two sprites
	public void PlayCollisionSoundAction() throws UnsupportedAudioFileException, IOException, LineUnavailableException{
		PlaySound playSound = new PlaySound();
		playSound.playSoundMethod(SoundMode.COLLISION);
	}
	
	
	//check collision between two sprites
	public boolean detectCollision (Sprite spriteObjectOne, Sprite spriteObjectTwo)
	{
		boolean isCollision = false;
	
		if (spriteObjectOne.getRect().intersects(spriteObjectTwo.getRect()))
		{
			isCollision = true;
		}
		
		return isCollision;
	}
	
	//bounce objects on collision
	public void BounceOnCollision (Sprite spriteObjectOne, Sprite spriteObjectTwo){
		
		int spriteObjectOneLPos = (int) spriteObjectOne.getRect().getMinX();
        int spriteObjectTwoLPos = (int) spriteObjectTwo.getRect().getMinX();
        int first = spriteObjectOneLPos + 8;
        int second = spriteObjectOneLPos + 16;
        int third = spriteObjectOneLPos + 24;
        int fourth = spriteObjectOneLPos + 32;

        if (spriteObjectTwoLPos < first) {
        	spriteObjectTwo.setXdir(-1);
        	spriteObjectTwo.setYdir(-1);
        }

        if (spriteObjectTwoLPos >= first && spriteObjectTwoLPos < second) {
        	spriteObjectTwo.setXdir(-1);
        	spriteObjectTwo.setYdir(-1 * spriteObjectTwo.getYdir());
        }

        if (spriteObjectTwoLPos >= second && spriteObjectTwoLPos < third) {
        	spriteObjectTwo.setXdir(0);
        	spriteObjectTwo.setYdir(-1);
        }

        if (spriteObjectTwoLPos >= third && spriteObjectTwoLPos < fourth) {
        	spriteObjectTwo.setXdir(1);
        	spriteObjectTwo.setYdir(-1 * spriteObjectTwo.getYdir());
        }

        if (spriteObjectTwoLPos > fourth) {
        	spriteObjectTwo.setXdir(1);
        	spriteObjectTwo.setYdir(-1);
        }
	}
	
}

	
	
	
	


